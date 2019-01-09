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
 * 
 */
public class Customer {
   
    private String name;
    private String email;
    private String address;
    private String city;
    private String zipcode;
    private String country;
    private String phoneNumber;
    
    public void Customer(String Name, String Email, String Address,
            String City, String Zipcode, String Country, String Phonenumber){
        this.name = Name;
        this.email = Email;
        this.address = Address;
        this.city = City;
        this.zipcode = Zipcode;
        this.country = Country;
        this.phoneNumber = Phonenumber;
    }

    // Save customer
    public boolean saveCustomer() throws SQLException {
        
        DB database = new DB();
        
        // Save customer in DB
        String queryStep1 = String.format("INSERT INTO customer (name, email, address, city, zipcode, country, phone)" +
                " VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                name, email, address, city, zipcode, country, phoneNumber);
                
        database.executeUpdateQuery(queryStep1);

        return true;
         
    }
    
    // update customer
     public boolean updateCustomer(int customerId) throws SQLException {
        
        DB database = new DB();
        
        // Save customer in DB
        String queryStep1 = String.format("UPDATE customer SET name = '%s', email = '%s', address = '%s', city = '%s', zipcode = '%s', country = '%s', phone = '%s' WHERE = '%s'",
                name, email, address, city, zipcode, country, phoneNumber, customerId);
                
        database.executeUpdateQuery(queryStep1);

        return true;
         
    }
     
    public ResultSet getCustomers() throws SQLException {
          
        DB database = new DB();
        
        String queryGetCustomers = String.format("SELECT * FROM customer");
        
        // Get luggage from DB for overview
        ResultSet queryResults = database.executeResultSetQuery(queryGetCustomers);
        
        return queryResults;
    }
    
     public ResultSet getCustomer(int id) throws SQLException {
          
        DB database = new DB();
        
        String queryGetCustomers = String.format("SELECT * FROM customer WHERE ID = '%s'", id);
        
        // Get luggage from DB for overview
        ResultSet queryResults = database.executeResultSetQuery(queryGetCustomers);
        
        return queryResults;
    }

    public String getLastId() {
        
        DB database = new DB();
        
        // Get the ID from the last created customer.
        String queryLastId = String.format("SELECT ID FROM customer ORDER BY ID DESC LIMIT 1;");
        String queryResult = database.executeStringQuery(queryLastId);
         
        return queryResult;
    }
    
    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

   
    
}
