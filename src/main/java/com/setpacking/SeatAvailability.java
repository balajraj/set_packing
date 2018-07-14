package com.setpacking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class  SeatAvailability {

    private final Logger logger = LoggerFactory.getLogger(SeatAvailability.class);

    private int rowSize=0;

    int [][] seats = null;
    int freex=0;
    int freey=0;
    int numRows =0;

    public SeatAvailability( int rowSize, int numRows) {

        this.rowSize = rowSize;
        this.numRows = numRows;
        seats = new int[numRows][rowSize];
        for (int i=0 ; i < numRows;++i) {
            Arrays.fill(seats[i],-1);
        }
    }

    public int getFreeSeatsInRow() {
      return rowSize - freey;
    }

    /**
     * Searches for the next available seat
     * @return true if next row is reached false if seat found on same row
     * @throws FullFillException
     */
    public boolean searchForNextFree() throws FullFillException{
        boolean ret =false;
        while( seats[freex][freey] != -1 ) {
            if( freex == numRows-1 && freey == rowSize-1) {
                throw new  FullFillException();
            }
            if(freey == rowSize -1) {
                freey=0;
                freex++;
                ret =true;
            }
            else {
                freey++;
            }
        }
        return ret;
    }

    /**
     * If the passenger has request for window pref the person is
     * assigned to either the start of the column or end of the column which is a window
     * freex and freey keeps tracks of currently available free seat
     * Depending on the group size and current available seats the passengers will
     * occupy one or more rows.
     * @param passengers who have to be filled
     * @param size size of the group
     * @param windowPref true if the group needs window pref
     * @param prefId the passenger id who has requested for window pref
     * @return list of integer no of passengers per row
     * @throws FullFillException is thrown when there are less number of seats available than
     * the list of passengers in the group.
     */
    public List<Integer> updateSeats(List<Passenger> passengers,int size,
                                     boolean windowPref, int prefId) throws FullFillException{
        List<Integer>  passengersPerRow = new ArrayList<Integer>();
        int passPerRow = 0;
        if( windowPref ) {
            if (seats[freex][0] == -1 ) {
               seats[freex][0]  = prefId;
            }
            else if( seats[freex][rowSize-1] == -1 ) {
                seats[freex][rowSize-1] = prefId;
            }
            passPerRow++;
            size -= 1;
        }
        for ( int i=freey ; i < size; ++i ) {
            boolean nextRow = searchForNextFree();
            if(nextRow) {
                passengersPerRow.add(passPerRow);
                passPerRow =0;
            }
            seats[freex][freey] = passengers.get(i).getId();
            passPerRow++;
            if(freey == rowSize-1) {
                freey=0;
                freex++;
                passengersPerRow.add(passPerRow);
                passPerRow =0;
                if(freex >= numRows -1 || (size-1-i) >0  ) {
                    logger.error("Reached the end of the seats ");
                    throw new FullFillException();
                }
            }
            else {
                freey++;
            }

        }
        passengersPerRow.add(passPerRow);
        return passengersPerRow;
    }



    public void fillGroup(Group grp, boolean windowPref) throws FullFillException {
        List<Passenger> passengers = grp.getPassengers();
        List<Integer> assignment =null;
        if(windowPref) {
                Passenger winPref = grp.getWindowPassenger();
                assignment = updateSeats(passengers,grp.getSize(),true,winPref.getId());
        }
        else {
            assignment = updateSeats(passengers,grp.getSize(),false,-1);
        }
        if(assignment.size() > 1) {
            //The passengers are distributed more than one row.
            double groupSatisfaction = assignment.stream().mapToDouble(
                    x -> (double)x/(double)grp.getSize() ).average().orElse(0.0D);
            grp.setSatisfaction(groupSatisfaction);
        }
        else {
            grp.setSatisfaction(1.0D);
        }

    }
}
