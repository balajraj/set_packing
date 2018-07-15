package com.setpacking;

import java.util.List;

public class Group {

    int size;
    List<Passenger> passengers=null;
    Passenger winPref;
    double satisfaction = 0.0D; //satisfaction 0 %

    public Group(List<Passenger> passengers) {
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

    public void setSatisfaction(double satisfaction) {
        this.satisfaction = satisfaction;
    }

    public double getSatisfaction() {
        return satisfaction;
    }


}
