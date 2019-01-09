/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.DB;
import Models.Luggage;
import java.net.URL;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;

/**
 *
 * @author jairz
 */
public class Statistics implements Initializable {

    @FXML
    private ComboBox AirportDropDown;

    @FXML
    private PieChart FoundLuggage;

    @FXML
    private PieChart AllLuggage;

    //the data to be filtered on
    private ResultSet lostLuggage;

    // defining the list
    private static List<String> airports = new ArrayList<>();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Luggage lug = new Luggage();

        try {
            lostLuggage = lug.getLuggage();
            InitStatistics();
        } catch (SQLException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *This method happens on the combobox value change, change the resultset and initialize the data again
     */
    @FXML
    private void DataByAirport() throws SQLException {
        DB Connection = new DB();

        String selectedValue = AirportDropDown.getSelectionModel().getSelectedItem().toString();
        //select the rows where the found location or lost location equals the selected value of the dropdown
        String sql = "SELECT * FROM luggage WHERE location = " + selectedValue + " OR lost_location = " + selectedValue;
        lostLuggage = Connection.executeResultSetQuery(sql);
        InitStatistics();
    }
    
    /**
     * Initialize all the data to be shown, do this in a seperate method so it can be called when the value of the dropdown changes
     */
    private void InitStatistics() {
        ObservableList RolesList = null;
        try {
            RolesList = GetAirportObservableList();
        } catch (SQLException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        AirportDropDown.getItems().clear();
        AirportDropDown.setItems(RolesList);

        try {
            /*
             * these methods are used to use 1 query instead of multiple.
             * the data will not be modified in the methods.
             */
            int totalRecords = CountRows(lostLuggage);
            int lostLuggageCount = CountByColumnValue(lostLuggage, "status", "lost");
            int foundLuggageCount = CountByColumnValue(lostLuggage, "status", "found");

            ObservableList<PieChart.Data> lostAndFoundData
                    = FXCollections.observableArrayList(
                            new PieChart.Data("Found", foundLuggageCount),
                            new PieChart.Data("Never found", lostLuggageCount));
            AllLuggage.setData(lostAndFoundData);

            // sum total compesation
            int compesation = SumByColumnName(lostLuggage, "compesation");

            float averageCompesation = compesation / totalRecords;

            //Found data
            int foundInThreeDays = luggageFoundInDayRange(lostLuggage, 3);
            int foundInTwentyOneDays = LuggageFoundInDayRange(lostLuggage, 21, 3);
            int neverFound = totalRecords - foundInThreeDays + foundInTwentyOneDays;

            ObservableList<PieChart.Data> foundData
                    = FXCollections.observableArrayList(
                            new PieChart.Data("Found in 3 days", foundInThreeDays),
                            new PieChart.Data("Found in 21 days", foundInTwentyOneDays),
                            new PieChart.Data("Never found", neverFound));
            FoundLuggage.setData(foundData);

        } catch (SQLException | ParseException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Set all the airports set in the database
     * @throws SQLException 
     */
    private static void setAirports() throws SQLException {
        // add all the roles to the list
        airports.clear();

        DB Connection = new DB();
        //make a query which selects distinct values
        String DistinctAirports = "SELECT DISTINCT lost_location, location FROM luggage";
        ResultSet queryResult = Connection.executeResultSetQuery(DistinctAirports);

        //loop and add the locations
        while (queryResult.next()) {
            String lost_location = queryResult.getString("lost_location");
            String found_location = queryResult.getString("location");

            if (!lost_location.isEmpty() && !airports.contains(lost_location)) {
                airports.add(lost_location);
            }
            if (found_location != null && !airports.contains(found_location)) {
                airports.add(found_location);
            }
        }
    }

    /**
     * return the list of airports
     * @return 
     */
    public static List GetAirportList() {
        // return the roles in list format
        return airports;
    }

    /**
     * make an observable list so the combobox can be filled
     * @return
     * @throws SQLException 
     */
    public static ObservableList GetAirportObservableList() throws SQLException {
        // set all roles
        setAirports();

        // put roles in observable list
        ObservableList RoleList = FXCollections.observableList(airports);

        //return observablelist
        return RoleList;
    }    

    /**
     * Count the rows matching the value
     *
     * @param Data The data the rows need to be counted of
     * @param Column The column of the values to match
     * @param ValueToMatch The value to find in the ResultSet
     * @return
     * @throws SQLException
     */
    private int CountByColumnValue(ResultSet Data, String Column, String ValueToMatch) throws SQLException {
        // reset iterator before looping again
        // if this method is used multiple with the same data it won't find a next row
        // because it's already looped through
        Data.beforeFirst();
        int counter = 0;

        while (Data.next()) {
            String columnValue = Data.getString(Column);

            if (columnValue == null ? ValueToMatch == null : columnValue.equals(ValueToMatch)) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Sum the value of the selected column
     * @param Data the resultset to be filtered on
     * @param Column the name of the column to be sumned
     * @return
     * @throws SQLException 
     */
    private int SumByColumnName(ResultSet Data, String Column) throws SQLException {
        //reset the iterator
        Data.beforeFirst();
        //begin with 0 and add up
        int total = 0;

        //loop through the data and count up
        while (Data.next()) {
            total += Data.getInt(Column);
        }

        //return the total
        return total;
    }

    /**
     * Count the rows of the data
     * @param Data the data/resultset where the rows need to be counted of
     * @return
     * @throws SQLException 
     */
    private int CountRows(ResultSet Data) throws SQLException {
        //reset the counter and iterator
        int counter = 0;
        Data.beforeFirst();
        
        //loop through data
        while (Data.next()) {
            counter++;
        }
        
        //return the counter
        return counter;
    }

    /**
     * call the LuggageFoundInDayRange with 0 as last parameter
     * @param Data The resultset it needs to be filtered on
     * @param DayRange amount of days ranging from the start
     * @return
     * @throws SQLException
     * @throws SQLException
     * @throws ParseException 
     */
    private int luggageFoundInDayRange(ResultSet Data, int DayRange) throws SQLException, SQLException, ParseException {
        // call  the method where the datefrom is required with 0
        return LuggageFoundInDayRange(Data, DayRange, 0);
    }

    /**
     * Return the amount of found luggage in the given range and days
     * @param Data ResultSet on which the data has to be filtered on
     * @param DayRange amount of days ranging from the start
     * @param RangeFrom the days it needs to be started from
     * @return
     * @throws SQLException
     * @throws ParseException 
     */
    private int LuggageFoundInDayRange(ResultSet Data, int DayRange, int RangeFrom) throws SQLException, ParseException {
        //reset iterator and counter
        Data.beforeFirst();
        int counter = 0;

        //loop through data
        while (Data.next()) {
            //select the date_found column
            String dateFoundString = Data.getString("found_date").toString();

            //if the date-strings arent empty, calculate the difference between it
            if (dateFoundString != null && !dateFoundString.isEmpty()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                Date dateFound = dateFormat.parse(dateFoundString);
                Date dateLost = dateFormat.parse(Data.getString("lost_date"));

                long difference = dateFound.getTime() - dateLost.getTime();
                // divide by hours * minutes * seconds * milliseconds (equal to 1 day) 
                int diffDays = (int) (difference / (24 * 60 * 60 * 1000));

                if (diffDays <= DayRange && diffDays > RangeFrom) {
                    counter++;
                }
            }
        }
        // return the counter
        return counter;
    }    
}
