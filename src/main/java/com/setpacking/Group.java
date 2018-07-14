package com.setpacking;

import java.util.List;

public class Group {

    int size;
    boolean hasWindowPref;
    List<Passenger> passengers=null;

    public Group(List<Passenger> passengers, boolean hasWindowPref) {
        this.hasWindowPref = hasWindowPref;
        this.passengers = passengers;
        this.size = passengers.size();
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

}
