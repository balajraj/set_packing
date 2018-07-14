package com.setpacking;

public class Passenger {

    boolean windowPref;
    int id;

    public Passenger(boolean windowPref, int id){
        this.id = id;
        this.windowPref = windowPref;
    }

    public int getId() {
        return id;
    }

}
