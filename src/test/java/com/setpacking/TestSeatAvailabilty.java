package com.setpacking;

import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestSeatAvailabilty {

    private SeatAvailability seatAvail = null;
    private List<Passenger> passengers =null;
    private List<Passenger> passengers1= null;
    @Before
    public void setUp() {
        seatAvail = new SeatAvailability(3,3);
        passengers = new ArrayList<Passenger>() {{
            add( new Passenger(true,1));
            add(new Passenger( false,2));
            add(new Passenger( false,3));
            add(new Passenger( false,4));
        }};
        passengers1 = new ArrayList<Passenger> () {{
            add( new Passenger(true,5));
            add(new Passenger( false,6));
            add(new Passenger( false,7));
            add(new Passenger( false,8));
            add(new Passenger( false,9));
            add(new Passenger( false,10));
        }};

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



}
