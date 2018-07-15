package com.setpacking;


import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetPackingAlgo {

    private final Logger logger = LoggerFactory.getLogger(SetPackingAlgo.class);

    private SeatAvailability seatAlloc = null;

    private int rowSize;
    private int numRows;
    private int maxGroupSize;

    public SetPackingAlgo(int rowSize, int numRows,int maxGroupSize) {
        this.rowSize=rowSize;
        this.numRows =numRows;
        this.maxGroupSize = maxGroupSize;
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
                seatAlloc.fillGroup(grp, winPref);
                result.add(grp);
            } catch (FullFillException ex) {
                //There are more people than available seats
                logger.error("Seat allocation failed since there was less number of seats and more people");
            }
            if (listGrp.size() == 0) {
                allocGrp.remove(availCount);
            }

        }
    }


    private void allocateForPrefAndNonPref(Map<Integer,Stack<Group>> prefGrp,boolean winPref ,List<Group> result) {

        while(prefGrp.size() > 0 ) {
            int freeSeatsInRow = seatAlloc.getFreeSeatsInRow();
            if(prefGrp.containsKey(freeSeatsInRow)) {
                tryAndFillSeats(freeSeatsInRow,prefGrp,winPref,result);
            }
            else {
                //int index =-1;
                for( int i = freeSeatsInRow; i > 0 ; --i ) {
                    if(prefGrp.containsKey(i) ) {
                        tryAndFillSeats(i,prefGrp,winPref,result);
                    }
                }

                 for(int i=rowSize+1; i <= maxGroupSize;++i) {
                        tryAndFillSeats(i,prefGrp,winPref,result);
                 }
            }
        }
    }

    public double getAllocationSatisfaction(Map<Integer,Stack<Group>> winPrefGrpMap,
                                            Map<Integer,Stack<Group>> noPrefGrpMap) {

        List<Group> result = new ArrayList<>();
        allocateForPrefAndNonPref(winPrefGrpMap,true,result);
        allocateForPrefAndNonPref(noPrefGrpMap,true,result);
        double endRes = calculateSatisfaction(result);
        logger.info("Overall satisfaction of set packing algorithm is "+endRes);

        return endRes;
    }
}
