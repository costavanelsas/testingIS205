package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author Jaïr Zijp
 */

public class Luggage {
    
    private String labelNumber;
    private String type;
    private String brand;
    private String color;
    private String specialFeatures;
    private String owner;
    private String status;
    private String location;
    private String lostLocation;
    private String foundDate;
    private String lostDate;
    private String flightId;
    private int customerId;
    private double compesation;
    private int ID;

   
    public Luggage() {}

    public Luggage(String LabelNumber, String Type, String Brand, String Color,
            String SpecialFeatures, String Owner, String Status, String Location,
            String LostLocation, String FoundDate, String LostDate, String FlightId,
            int CustomerId, double Compensation){
        this.labelNumber = LabelNumber;
        this.type = Type;
        this.brand = Brand;
        this.color = Color;
        this.specialFeatures = SpecialFeatures;
        this.owner = Owner;
        this.status = Status;
        this.location = Location;
        this.lostLocation = LostLocation;
        this.foundDate = FoundDate;
        this.lostDate = LostDate;
        this.flightId = FlightId;
        this.customerId = CustomerId;
        this.compesation = Compensation;
    }
    
    // Save luggage
    public boolean saveLuggage() {
        
        DB database = new DB();
        
        String queryStep2 = String.format("INSERT INTO luggage (label_number, type, brand, special_features, owner, status, location, lost_location, found_date, lost_date, compesation, customer_ID, color, flight_id)" +
                " VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s','%s')",
                getLabelNumber(), getType(), getBrand(), getSpecialFeatures(), getOwner(), getStatus(), getLocation(), getLostLocation(), getFoundDate(), getLostDate(), getCompesation(), getCustomerId(), getColor(), getFlightId());
        
        // Save luggage in the DB
        return (database.executeUpdateQuery(queryStep2) > 0);        
    }
    
    // Update luggage
    public boolean updateLuggage(int luggageId) {
        
        DB database = new DB();
        
        String queryStep2 = String.format("UPDATE luggage SET label_number = '%s', type = '%s', brand = '%s', special_features = '%s', status = '%s', location = '%s', lost_location = '%s', found_date = '%s', lost_date = '%s', compesation = '%s', color = '%s' WHERE ID = '%s' ",
                getLabelNumber(), getType(), getBrand(), getSpecialFeatures(), getStatus(), getLocation(), getLostLocation(), getFoundDate(), getLostDate(), getCompesation(), getColor(), luggageId);
        
        // Update luggage in the DB
        database.executeUpdateQuery(queryStep2);
        
        return true;
    }
    
    // Get luggage
    public ResultSet getLuggage() throws SQLException {
        
        DB database = new DB();
        
        String queryGetLuggage = String.format("SELECT * FROM luggage ORDER BY ID ASC");
        
        // Get luggage from DB for overview
        ResultSet queryResults = database.executeResultSetQuery(queryGetLuggage);
        
        return queryResults;
        
    }
    
    public static ResultSet getLuggage(int ID) throws SQLException{
        DB database = new DB();
        
        String queryGetLuggage = String.format("SELECT * FROM luggage WHERE ID = %s", ID);
        
        // Get luggage from DB for overview
        ResultSet queryResults = database.executeResultSetQuery(queryGetLuggage);
        
        return queryResults;
    }
    
    public boolean isValid(){
        
        // Check if some fields are not empty
        return this.labelNumber != null &&
                this.type != null &&                
                this.color != null &&
                this.status != null &&
                this.lostDate != null &&
                this.lostLocation != null;
    }
    
    
    // Getters and setters
    public String getLabelNumber() {
        return labelNumber;
    }

    public void setLabelNumber(String labelNumber) {
        this.labelNumber = labelNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSpecialFeatures() {
        return specialFeatures;
    }

    public void setSpecialFeatures(String specialFeatures) {
        this.specialFeatures = specialFeatures;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLostLocation() {
        return lostLocation;
    }

    public void setLostLocation(String lostLocation) {
        this.lostLocation = lostLocation;
    }

    public String getFoundDate() {
        return foundDate;
    }

    public void setFoundDate(String foundDate) {
        this.foundDate = foundDate;
    }

    public String getLostDate() {
        return lostDate;
    }

    public void setLostDate(String lostDate) {
        this.lostDate = lostDate;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public double getCompesation() {
        return compesation;
    }

    public void setCompesation(double compesation) {
        this.compesation = compesation;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public int getID(){
        return ID;
    }
    public void setID(int id){
        this.ID= id;
    }
}