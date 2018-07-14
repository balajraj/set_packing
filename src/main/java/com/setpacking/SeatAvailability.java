package com.setpacking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SeatAvailability {

    private int capacity =0;
    private int rowSize=0;
    int currAvailability = 0;
    int [][] seats = null;
    int freex=0;
    int freey=0;
    int numRows =0;
    int [] costForRow =null;
    int unSatisfied = 0;

    public SeatAvailability(int capacity, int rowSize, int numRows) {
        this.capacity = capacity;
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

    public void updateSeats(List<Passenger> passengers,int size) {
        List<Integer>  passengersPerRow = new ArrayList<Integer>();
        int passPerRow = 0;
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
                passPerRow =0;
            }
            if(freex == numRows -1) {

            }
        }
    }



    public void fillGroup(Group grp, boolean windowPref) {
        List<Passenger> passengers = grp.getPassengers();
        if(windowPref) {

                int size = passengers.size();
                if( passengers.size() <= rowSize ) {
                   updateLessThanRow(size,passengers);
                }
                else {

                    while(size != 0 && (freex < numRows ) ) //size of passengers in group is greater than rowSize
                    {
                        if (size > rowSize) {
                            updateSeats(passengers,rowSize);
                            size = size - rowSize;
                            freex++;
                        }
                        else if( size <= rowSize) {
                            size = updateLessThanRow(size,passengers);
                        }

                    }
                    unSatisfied = size;
                }

        }
        else {

        }

    }
}
