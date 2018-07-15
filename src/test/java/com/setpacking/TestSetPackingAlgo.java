package com.setpacking;


import org.junit.*;

import java.util.*;

import static org.mockito.Mockito.*;

import static org.junit.Assert.assertEquals;

public class TestSetPackingAlgo {

    private SeatAvailability seatAvail =null;
    private SetPackingAlgo setpack = null;
    Map<Integer,Stack<Group>> winPref = null;
    private List<Passenger> passengers =null;
    private List<Passenger> passengers1= null;
    private Group grp1=null;
    private Group grp2=null;

    @Before
    public void setup() {

        seatAvail = mock(SeatAvailability.class);
        setpack = new SetPackingAlgo(3,3, 2);
        passengers = new ArrayList<Passenger>() {{
                add(new Passenger(true, 1));
                add(new Passenger(false, 2));
        }};
        grp1 = new Group(passengers);
        passengers1 = new ArrayList<Passenger> () {{
                add(new Passenger(true, 4));
                add(new Passenger(false, 5));
        }};
        grp2 = new Group(passengers1);
        Stack<Group> stackGrp = new Stack<>();
        stackGrp.push(grp1);
        stackGrp.push(grp2);
        winPref = new HashMap<>();
        winPref.put(2,stackGrp);
    }

    @Test
    public void testFillGroup() {
       List<Group> result = new ArrayList<>();
       setpack.tryAndFillSeats(2,winPref,true,result);
       setpack.tryAndFillSeats(2,winPref,true,result);
       int expected=0;
       assertEquals(expected,winPref.size());
    }

}
