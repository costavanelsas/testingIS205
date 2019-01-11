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
        System.out.println("Save the luggage");
        boolean result = currentLuggage.saveLuggage();

        assertTrue("Luggage can be saved into the database", result);
    }

    @Test
    public void testGetLuggage() throws Exception {
        System.out.println("Get the luggage");
        Luggage instance = new Luggage();

        ResultSet data = instance.getLuggage();

        data.last();
        int rowCount = data.getRow();
        data.beforeFirst();

        assertTrue("The data can be retrieved from the database", rowCount > 0);
    }

    @Test
    public void testUpdateLuggage() {
        System.out.println("Update the luggage");
        boolean result = currentLuggage.updateLuggage(1);

        assertTrue("Luggage can be saved into the database", result);
    }

    @Test
    public void testIsValid() {
        System.out.println("check if luggage is valid");

        assertTrue("The luggage-properties are valid", currentLuggage.isValid());
    }

    @Test
    public void inValidLuggage() {
        System.out.println("check if the luggage is invalid");
        Luggage instance = new Luggage();
        boolean expectedResult = false;

        assertEquals("The luggage-properties are valid", expectedResult, instance.isValid());
    }

    @Test
    public void getCurrentLuggageId() throws SQLException {
        System.out.println("Get current luggage ID");

        int currentLuggageID = currentLuggage.getID();
        ResultSet theLuggage = Luggage.getLuggage(currentLuggageID);

        assertThat("Luggage can be retrieved from database", theLuggage, equalTo(theLuggage));
    }

    @Test
    public void labelNumberCanBeChanged() {
        System.out.println("One item change");

        String newLabelNumber = "This is new";
        currentLuggage.setLabelNumber(newLabelNumber);

        assertThat(newLabelNumber, equalTo(currentLuggage.getLabelNumber()));
    }

    @Test
    public void moreThanOneCanBeChangedTogether() {
        System.out.println("Multiple items changing at once");

        String newLabelNumber = "This is new";
        String newType = "This is new too";
        String newBrand = "Dunlop";

        currentLuggage.setLabelNumber(newLabelNumber);
        currentLuggage.setType(newType);
        currentLuggage.setBrand(newBrand);

        assertThat(newLabelNumber, equalTo(currentLuggage.getLabelNumber()));
        assertThat(newType, equalTo(currentLuggage.getType()));
        assertThat(newBrand, equalTo(currentLuggage.getBrand()));
    }

    @Test
    public void testGetLuggageById() throws SQLException {
        System.out.println("Get the luggage by a given ID");
        int existingId = 3;
        ResultSet theLuggage = Luggage.getLuggage(existingId);

        theLuggage.last();
        int rowCount = theLuggage.getRow();
        theLuggage.beforeFirst();

        assertThat("Luggage can be retrieved from database", rowCount, equalTo(1));
    }

    @Test
    public void luggageDoesNotExist() throws SQLException {
        System.out.println("No luggage to be found if its non existent");
        int existingId = 999999999;
        ResultSet theLuggage = Luggage.getLuggage(existingId);

        theLuggage.last();
        int rowCount = theLuggage.getRow();
        theLuggage.beforeFirst();

        assertThat("No luggage with given ID", rowCount, equalTo(0));
    }
}

