package com.setpacking;

import java.util.List;

public class Group {

    int size;
    boolean hasWindowPref;
    List<Passenger> passengers=null;
    Passenger winPref;
    public Group(List<Passenger> passengers, boolean hasWindowPref) {
        this.hasWindowPref = hasWindowPref;
        this.passengers = passengers;
        this.size = passengers.size();
        for(Passenger pass: passengers) {
            if(pass.getWindowPref()) {
                winPref = pass;
                break;
            }
        }
        passengers.remove(winPref);
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public int getSize() {
        return size;
    }

    public Passenger getWindowPassenger() {
        return winPref;
    }

}
