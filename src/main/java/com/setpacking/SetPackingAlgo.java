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

    public SetPackingAlgo(int rowSize, int numRows, int maxGroupSize) {
        this.rowSize = rowSize;
        this.numRows = numRows;
        this.maxGroupSize = maxGroupSize;
        seatAlloc = new SeatAvailability(rowSize, numRows);
    }


    public double calculateSatisfaction(List<Group> filledList) {
        return filledList.stream().mapToDouble(Group::getSatisfaction).average().orElse(0.0D);
    }

    /**
     * Will loop up the group that will be best suited for available seats in a row.
     * The reason to use stack data structure instead of list is it makes removing elements easier.
     * When stack element size becomes zero the entry is removed from the map as well.
     * @param availCount the key to be looked in the map
     * @param allocGrp map which maintains the groups of same size
     * @param winPref whether the group has window preference or not
     * @param result list of groups with satisfaction updated
     */
    public void tryAndFillSeats(int availCount, Map<Integer, Stack<Group>> allocGrp, boolean winPref,
                                List<Group> result) {
        Stack<Group> listGrp = allocGrp.get(availCount);
        if (listGrp.size() > 0) {
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

    public void printAssignment() {
        seatAlloc.printAssignment();
    }

    /**
     * The method will first try to look up the group which has same passengers equal to available seats
     * in the given row. If the each match is not found, then a search is made to find the next possible match
     * in the range [freeSeatsInRow-1,1]
     * @param prefGrp map which contains groups belonging to same size
     * @param winPref whether window preference is required or not
     * @param result list of groups with satisfaction updated
     */
    private void allocateForPrefAndNonPref(Map<Integer, Stack<Group>> prefGrp, boolean winPref, List<Group> result) {

        while (prefGrp.size() > 0) {
            int freeSeatsInRow = seatAlloc.getFreeSeatsInRow();
            if (prefGrp.containsKey(freeSeatsInRow)) {
                tryAndFillSeats(freeSeatsInRow, prefGrp, winPref, result);
            } else {
                int index = -1;
                for (int i = freeSeatsInRow - 1; i > 0; --i) {
                    if (prefGrp.containsKey(i)) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {
                    tryAndFillSeats(index, prefGrp, winPref, result);
                }
            }
        }
    }

    public double getAllocationSatisfaction(Map<Integer, Stack<Group>> winPrefGrpMap,
                                            Map<Integer, Stack<Group>> noPrefGrpMap) {
        List<Group> result = new ArrayList<>();
        allocateForPrefAndNonPref(winPrefGrpMap, true, result);
        allocateForPrefAndNonPref(noPrefGrpMap, false, result);
        double endRes = calculateSatisfaction(result);
        logger.info("Overall satisfaction of set packing algorithm is " + endRes);
        return endRes;
    }
}
