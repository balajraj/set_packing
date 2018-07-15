package com.setpacking;

import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestSeatAvailabilty {

    private SeatAvailability seatAvail = null;
    private SeatAvailability seatAvail1 = null;
    private List<Passenger> passengers =null;
    private List<Passenger> passengers1= null;
    private List<Passenger> passengers2= null;
    private List<Passenger> passengers3= null;
    private Group grp1 = null;
    private Group grp2 = null;
    private Group grp3 = null;
    @Before
    public void setUp() {
        seatAvail = new SeatAvailability(3,3);
        passengers = new ArrayList<Passenger>() {{
            add( new Passenger(true,1));
            add(new Passenger( false,2));
            add(new Passenger( false,3));
            add(new Passenger( false,4));
        }};
        grp1 = new Group(passengers);
        passengers1 = new ArrayList<Passenger> () {{
            add( new Passenger(true,5));
            add(new Passenger( false,6));
            add(new Passenger( false,7));
            add(new Passenger( false,8));
            add(new Passenger( false,9));
            add(new Passenger( false,10));
        }};
        seatAvail1 = new SeatAvailability(4,4);
        passengers2 = new ArrayList<Passenger>() {{
                add(new Passenger(true, 1));
                add(new Passenger(false, 2));
                add(new Passenger(false, 3));
        }};
        grp2 = new Group(passengers2);
        passengers3 = new ArrayList<Passenger>() {{
                add(new Passenger(true, 4));
        }};
        grp3 = new Group(passengers3);

    }

    @Test
    public void testFreeSeatsInRow() {
        int avail = seatAvail.getFreeSeatsInRow();
        assertEquals(avail,3);
    }

    @Test
    public void testWindowPrefAssignment() throws Exception {

        List<Integer> numRows = seatAvail.updateSeats(passengers,4,true,1);
        assertEquals(numRows.get(0),new Integer(3));
        assertEquals(numRows.get(1),new Integer(1));
    }

    @Test(expected = FullFillException.class)
    public void testIndexOutOfBoundsException() throws FullFillException {
        List<Integer> numRows = seatAvail.updateSeats(passengers,4,true,1);
        numRows = seatAvail.updateSeats(passengers1,6,true,5);

    }

    /**
     * The group of 4 has 3 passengers assinged to row1 and 1 passenger assigned to row2.
     * Group Satisfaction = (3/4 + 1/4)/2 = 0.5
     * @throws FullFillException
     */
    @Test
    public void testPassengerSatisfaction() throws FullFillException {
        seatAvail.fillGroup(grp1,true);
        double expected = 0.5D;
        assertEquals(grp1.getSatisfaction() ,expected,0.001 );
    }

    @Test
    public void testGetFreeRowCount() throws FullFillException{
        assertEquals(seatAvail1.getFreeSeatsInRow(),4);
        seatAvail1.fillGroup(grp2,true);
        assertEquals(seatAvail1.getFreeSeatsInRow(),1);
        seatAvail1.fillGroup(grp3,true);
        assertEquals(seatAvail1.getFreeSeatsInRow(),4);

    }


}
