/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Jaïr Zijp
 */
public class Flight {
    
    private String flightId;
    private String airport;
    private String destination;
    private String date;
    private String time;

    public void Flight(String FlightId, String Airport, String Destination,
            String Date, String Time){
        this.flightId = FlightId;
        this.airport = Airport;
        this.destination = Destination;
        this.date = Date;
        this.time = Time;
    }
    
    public void Flight (String Airport, String Destination,
            String Date, String Time){
        this.airport = Airport;
        this.destination = Destination;
        this.date = Date;
        this.time = Time;
    }
    
    public boolean saveFlight() throws SQLException {
        
        DB database = new DB();
        
        // Insert flight data
        String query = String.format("INSERT INTO flight (flight_id, airport, destination, date, time)" +
                " VALUES ('%s', '%s', '%s', '%s', '%s')",
                this.flightId, this.airport, this.destination, this.date, this.time);
                
        database.executeUpdateQuery(query);

        return true;
         
    }
    
    // Get flights
    public ResultSet getFlights() throws SQLException {
        
        DB database = new DB();
        
        String queryGetFlight = String.format("SELECT * FROM flight");
        
        // Get luggage from DB for overview
        ResultSet queryResults = database.executeResultSetQuery(queryGetFlight);
        
        return queryResults;
        
    }
    
     // Get flight
    public ResultSet getFlight(String id) throws SQLException {
        
        DB database = new DB();
        
        String queryGetFlight = String.format("SELECT * FROM flight WHERE flight_id = '%s'", id);
        
        // Get luggage from DB for overview
        ResultSet queryResults = database.executeResultSetQuery(queryGetFlight);
        
        return queryResults;
        
    }
     
    public boolean isValid(){
        
        // Check if fields aren't empty
        return this.flightId != null &&
              this.airport != null &&                
              this.destination != null &&
              this.date != null &&
              this.time != null;
    }
    
    // Getter and setters
    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
 
}
