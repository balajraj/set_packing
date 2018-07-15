package com.setpacking;


import org.junit.*;

import java.util.*;

//import static org.mockito.Mockito.*;

import static org.junit.Assert.assertEquals;

public class TestSetPackingAlgo {

    //private SeatAvailability seatAvail =null;
    private SetPackingAlgo setpack = null;
    Map<Integer,Stack<Group>> winPref = null;
    private List<Passenger> passengers =null;
    private List<Passenger> passengers1= null;
    private List<Passenger> passengers2= null;
    private List<Passenger> passengers3= null;
    private List<Passenger> passengers4= null;
    private List<Passenger> passengers5= null;
    private List<Passenger> passengers6= null;
    private Group grp1=null;
    private Group grp2=null;
    private Group grp3=null;
    private Group grp4=null;
    private Group grp5=null;
    private Group grp6=null;
    private Group grp7=null;
    Map<Integer,Stack<Group>> noPref = null;

    @Before
    public void setup() {

        //seatAvail = mock(SeatAvailability.class);
        setpack = new SetPackingAlgo(4,4, 4);
        passengers = new ArrayList<Passenger>() {{
                add(new Passenger(true, 1));
                add(new Passenger(false, 2));
                add(new Passenger(false, 3));
        }};
        grp1 = new Group(passengers);
        passengers1 = new ArrayList<Passenger> () {{
                add(new Passenger(true, 11));
                add(new Passenger(false, 10));
                add(new Passenger(false, 9));
        }};
        grp2 = new Group(passengers1);
        passengers2 = new ArrayList<Passenger>() {{
                add(new Passenger(true, 12));
        }};
        grp3 = new Group(passengers2);
        Stack<Group> stackGrp = new Stack<>();
        stackGrp.push(grp1);
        stackGrp.push(grp2);
        winPref = new HashMap<>();
        winPref.put(3,stackGrp);
        stackGrp = new Stack();
        stackGrp.push(grp3);
        winPref.put(1,stackGrp);
        passengers3 = new ArrayList<Passenger>() {{
            add(new Passenger(false, 4));
            add(new Passenger(false, 5));
            add(new Passenger(false, 6));
            add(new Passenger(false, 7));
        }};
        grp4 = new Group(passengers3);
        noPref = new HashMap<>();
        stackGrp = new Stack();
        stackGrp.push(grp4);
        noPref.put(4,stackGrp);
        passengers4 = new ArrayList<Passenger>() {{
                add(new Passenger(false, 8));
        }};
        grp5 = new Group(passengers4);
        stackGrp = new Stack();
        stackGrp.push(grp5);
        noPref.put(1,stackGrp);
        passengers5 = new ArrayList<Passenger>() {{
            add(new Passenger(false, 13));
            add(new Passenger(false, 14));
        }};
        grp6 = new Group(passengers5);
        passengers6 = new ArrayList<Passenger>() {{
            add(new Passenger(false, 15));
            add(new Passenger(false, 16));
        }};
        grp7 = new Group(passengers6);
        stackGrp = new Stack();
        stackGrp.push(grp6);
        stackGrp.push(grp7);
        noPref.put(2,stackGrp);


    }

    @Test
    public void testFillGroup() {
       List<Group> result = new ArrayList<>();
       setpack.tryAndFillSeats(3,winPref,true,result);
       setpack.tryAndFillSeats(3,winPref,true,result);
       int expected=0;
       assertEquals(expected,winPref.size());
    }

    @Test
    public void testSeatAlloc() {
        List<Group> result = new ArrayList<>();
        double satisfaction = setpack.getAllocationSatisfaction(winPref,noPref);
        double expected=1.0D;
        assertEquals(expected,satisfaction,0.0001);
    }

}
