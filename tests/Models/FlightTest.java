/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.ResultSet;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author jairz
 */
public class FlightTest {
    
    Flight instance;
    
    public FlightTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new Flight();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of Flight method, of class Flight.
     */
    @Test
    public void testFlightId() {
        System.out.println("Flight");
        String FlightId = "AA11";
        String Airport = "Amsterdam";
        String Destination = "";
        String Date = "";
        String Time = "";
        instance.Flight(FlightId, Airport, Destination, Date, Time);     
        assertThat("AA11", equalTo(instance.getFlightId()));
    }

    /**
     * Test of saveFlight method, of class Flight.
     */
    @Test
    public void testSaveFlight() throws Exception {
        System.out.println("saveFlight");
        String FlightId = "AA11";
        String Airport = "Amsterdam";
        String Destination = "";
        String Date = "";
        String Time = "";
        instance.Flight(FlightId, Airport, Destination, Date, Time);
        boolean expResult = true;
        boolean result = instance.saveFlight();
        assertThat(expResult, equalTo(result));
    }

    /**
     * Test of isValid method, of class Flight.
     */
    @Test
    public void testIsValid() {
        System.out.println("isValid");
        String FlightId = "AA11";
        String Airport = "Amsterdam";
        String Destination = "Londen";
        String Date = "20-01-11";
        String Time = "19:00";
        instance.Flight(FlightId, Airport, Destination, Date, Time);
        boolean expResult = true;
        boolean result = instance.isValid();
        assertThat(expResult, equalTo(result));
    }
    
    /**
     * Test of isValid method, of class Flight
     * To see if it returns false when there are fields null
     */
    @Test
    public void testInvalid() {
        System.out.println("isInvalid");
        String FlightId = null;
        String Airport = null;
        String Destination = "Londen";
        String Date = "20-01-11";
        String Time = "19:00";
        instance.Flight(FlightId, Airport, Destination, Date, Time);
        boolean expResult = false;
        boolean result = instance.isValid();
        assertThat(expResult, equalTo(result));
    }

    /**
     * Test to see if the method getFlights returns at least 1 flight/row
     * @throws Exception
     */
    @Test
    public void testGetFlights() throws Exception {
        System.out.println("getFlights");
        ResultSet result = instance.getFlights();
        assertTrue(result.first());
    }
    
    /**
     * Test to see if the method getFlight returns a flight
     * @throws Exception
     */
    @Test
    public void testGetFlight() throws Exception {
        System.out.println("getFlight");
        ResultSet result = instance.getFlight("ABC123");
        assertTrue(result.first());
    }
    
    /**
     * Test to see if the method getFlight returns null if there is a non existing ID given
     * @throws Exception
     */
    @Test
    public void testGetFlightNonExistingId() throws Exception {
        System.out.println("getFlight");
        ResultSet result = instance.getFlight("ABC1235555");
        assertFalse(result.first());
    }
    
}
