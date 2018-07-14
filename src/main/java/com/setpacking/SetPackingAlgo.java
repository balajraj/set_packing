package com.setpacking;


import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetPackingAlgo {

    private final Logger logger = LoggerFactory.getLogger(SetPackingAlgo.class);
    private Set<Group> winPrefgroup  = null;
    private Set<Group> noPrefGroup = null;

    private Map<Integer,Stack<Group>> winPrefGrpMap = null;
    private Map<Integer,Stack<Group>> noPrefGrpMap = null;

    private SeatAvailability seatAlloc = null;
    public SetPackingAlgo(int rowSize, int numRows) {

        seatAlloc = new SeatAvailability(rowSize,numRows);
    }



    public double calculateSatisfaction(List<Group> filledList) {

        return filledList.stream().mapToDouble(Group::getSatisfaction).average().orElse(0.0D);

    }

    public void tryAndFillSeats(int availCount,Map<Integer,Stack<Group>> allocGrp,boolean winPref,
                                List<Group> result) {

        Stack<Group> listGrp = allocGrp.get(availCount);
        if(listGrp.size() > 0 ) {
            Group grp = listGrp.pop();
            try {
                seatAlloc.fillGroup(grp,winPref);
                result.add(grp);
            }
            catch(FullFillException ex) {
                //There are more people than available seats
                logger.error("Seat allocation failed since there was less number of seats and more people");
            }

        }
        else {
            winPrefGrpMap.remove(availCount);
        }
    }



    public double getAllocationSatisfaction() {

        List<Group> result = new ArrayList<Group>();
        while(winPrefgroup.size() > 0 ) {
            int freeSeatsInRow = seatAlloc.getFreeSeatsInRow();
            if(winPrefGrpMap.containsKey(freeSeatsInRow)) {
                tryAndFillSeats(freeSeatsInRow,winPrefGrpMap,true,result);
            }
            else {
              for( int i = freeSeatsInRow-1; i > 0 ; --i ) {
                  if(winPrefGrpMap.containsKey(i) ) {
                      break;
                  }
              }
                tryAndFillSeats(freeSeatsInRow,winPrefGrpMap,true,result);
            }
        }
        double endRes = calculateSatisfaction(result);
        logger.info("Overall satisfaction of set packing algorithm is "+endRes);

        return endRes;
    }
}
