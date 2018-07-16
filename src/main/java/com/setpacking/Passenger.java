package com.setpacking;

public class Passenger {

    boolean windowPref;
    int id;
    boolean noWindowAvailable=false;

    public Passenger(boolean windowPref, int id){
        this.id = id;
        this.windowPref = windowPref;
    }

    public int getId() {
        return id;
    }

    public boolean getWindowPref() {
        return windowPref;
    }

    public void setNoWindowAvailable(boolean noWindowAvailable) {
        this.noWindowAvailable = noWindowAvailable;
    }

    public boolean getNoWindowAvailable() {
        return noWindowAvailable;
    }

}
