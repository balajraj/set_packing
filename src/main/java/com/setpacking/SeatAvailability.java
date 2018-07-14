package com.setpacking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class  SeatAvailability {

    private final Logger logger = LoggerFactory.getLogger(SeatAvailability.class);

    private int rowSize=0;
    int currAvailability = 0;
    int [][] seats = null;
    int freex=0;
    int freey=0;
    int numRows =0;
    int [] costForRow =null;
    int unSatisfied = 0;

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

    public List<Integer> updateSeats(List<Passenger> passengers,int size,boolean windowPref, int prefId) throws FullFillException{
        List<Integer>  passengersPerRow = new ArrayList<Integer>();
        int passPerRow = 0;
        if( windowPref ) {
            if (seats[freex][0] == -1 ) {
               seats[freex][0]  = prefId;
            }
            else if( seats[freex][rowSize-1] == -1 ) {
                seats[freex][rowSize-1] = prefId;
            }
            size -= 1;
        }
        for ( int i=freey ; i < size; ++i ) {
            while( seats[freex][freey] != -1 ) {
                freey++;
            }
            seats[freex][freey] = passengers.get(i).getId();
            freey++;
            passPerRow++;
            if(freey == rowSize-1) {
                freey=0;
                freex++;
                passengersPerRow.add(passPerRow);
                passPerRow =0;
                if(freex == numRows -1 && (size-1-i) >0  ) {
                    logger.error("Reached the end of the seats ");
                    throw new FullFillException();
                }
            }

        }
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
