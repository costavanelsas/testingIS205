/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import static org.hamcrest.CoreMatchers.equalTo;
import org.hamcrest.Matcher;
import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Costa van Elsas, 500782594
 */
public class LuggageTest {
    
    private Luggage currentLuggage;
    
    @Before
    public void setUp() {
        currentLuggage = new Luggage("LABEL1", "Koffer", "Gucci", "Red",
            "Trolly", "Unknown", "Not Found", "Amsterdam",
            "Amsterdam", "12-10-2018", "11-10-2018", "ABC123",
            1, 100.00);
    }

    @Test
    public void testSaveLuggage() {
        boolean result = currentLuggage.saveLuggage();
        
        assertTrue("Luggage can be saved into the database", result);
    }

    @Test
    public void testGetLuggage() throws Exception {
        Luggage instance = new Luggage();
        
        ResultSet data = instance.getLuggage();
        
        data.last();
        int rowCount = data.getRow();
        data.beforeFirst();
        
        assertTrue("The data can be retrieved from the database", rowCount > 0);
    }

    /**
     * Test of isValid method, of class Luggage.
     */
    @Test
    public void testIsValid() {
        assertTrue("The luggage-properties are valid", currentLuggage.isValid());        
    }
    
    /**
     * Test of isValid method, of class Luggage.
     */
    @Test
    public void inValidLuggage() {
        Luggage instance = new Luggage();
        boolean expectedResult = false;
        
        assertEquals("The luggage-properties are valid", expectedResult, instance.isValid());        
    }
    
    @Test
    public void labelNumberCanBeChanged() {
        String newLabelNumber = "This is new";
        currentLuggage.setLabelNumber(newLabelNumber);
        
        assertThat(newLabelNumber, equalTo(currentLuggage.getLabelNumber()));
    }
    
    @Test
    public void testGetLuggageById() throws SQLException{
        int existingId = 3;        
        ResultSet theLuggage = Luggage.getLuggage(existingId);
        
        theLuggage.last();
        int rowCount = theLuggage.getRow();
        theLuggage.beforeFirst();
        
        assertThat("Luggage can be retrieved from database", rowCount, equalTo(1));        
    }
    
    @Test
    public void luggageDoesNotExist() throws SQLException{
        int existingId = 999999999;        
        ResultSet theLuggage = Luggage.getLuggage(existingId);
        
        theLuggage.last();
        int rowCount = theLuggage.getRow();
        theLuggage.beforeFirst();
        
        assertThat("No luggage with given ID", rowCount, equalTo(0));
    }    
}
