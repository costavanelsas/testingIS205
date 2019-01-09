/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Customer;
import Models.DB;
import Models.Flight;
import Models.Luggage;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Giovanni Verroca FXML Controller Edit Lost and Found
 */
public class LostLuggage implements Initializable {

    private static int currentLuggageId;

    private Customer currentCustomer = new Customer();
    private Luggage currentLuggage = new Luggage();
    
    // create database connection 
    DB database = new DB();

    //step 1    
    @FXML
    private AnchorPane Step1;
    @FXML
    private TextField NameField;
    @FXML
    private TextField EmailField;
    @FXML
    private TextField PhoneNumberField;
    @FXML
    private TextField AddressField;
    @FXML
    private TextField CityField;
    @FXML
    private TextField ZipcodeField;
    @FXML
    private TextField CountryField;

    //step 2
    @FXML
    private AnchorPane Step2;
    @FXML
    private TextField LabelNumberField;
    @FXML
    private TextField TypeField;
    @FXML
    private TextField BrandField;
    @FXML
    private TextField SpecialFeaturesField;
    @FXML
    private TextField ColorField;
    @FXML
    private TextField LocationField;
    @FXML
    private DatePicker FoundDateField;
    @FXML
    private DatePicker LostDateField;
    @FXML
    private TextArea StatusField;
    @FXML
    private TextField CompesationField;
    @FXML
    private TextField LostLocationField;

    @FXML
    private TextField Compensation;
    @FXML
    private DatePicker Datefield;

    // initialize the screen
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            //this part shouldve been done with an inner join, but strangely only makes errors
            String luggageQuery = "SELECT * FROM `luggage` WHERE ID = " + getCurrentLuggageId();
            ResultSet luggageResult = database.executeResultSetQuery(luggageQuery);

            if (luggageResult.first()) {

                String customerQuery = "SELECT * FROM `customer` WHERE ID = " + luggageResult.getInt("customer_ID");
                ResultSet customerResult = database.executeResultSetQuery(customerQuery);
                customerResult.first();

                //set the properties for customer
                currentCustomer.setName(customerResult.getString("name"));
                currentCustomer.setEmail(customerResult.getString("email"));
                currentCustomer.setPhoneNumber(customerResult.getString("phone"));
                currentCustomer.setAddress(customerResult.getString("address"));
                currentCustomer.setCity(customerResult.getString("city"));
                currentCustomer.setZipcode(customerResult.getString("zipcode"));
                currentCustomer.setCountry(customerResult.getString("country"));

                //set the properties for luggage
                currentLuggage.setLabelNumber(luggageResult.getString("label_number"));
                currentLuggage.setType(luggageResult.getString("type"));
                currentLuggage.setBrand(luggageResult.getString("brand"));
                currentLuggage.setSpecialFeatures(luggageResult.getString("special_features"));
                currentLuggage.setColor(luggageResult.getString("color"));
                currentLuggage.setLocation(luggageResult.getString("location"));
                currentLuggage.setLostLocation(luggageResult.getString("lost_location"));
                currentLuggage.setLostDate(luggageResult.getString("lost_date"));
                currentLuggage.setFoundDate(luggageResult.getString("found_date"));
                currentLuggage.setCompesation(luggageResult.getInt("compesation"));
                currentLuggage.setStatus(luggageResult.getString("status"));

                //Setting all values of the fields
                NameField.setText(currentCustomer.getName());
                EmailField.setText(currentCustomer.getEmail());
                AddressField.setText(currentCustomer.getAddress());
                CityField.setText(currentCustomer.getCity());
                ZipcodeField.setText(currentCustomer.getZipcode());
                CountryField.setText(currentCustomer.getCountry());

                LabelNumberField.setText(currentLuggage.getLabelNumber());
                TypeField.setText(currentLuggage.getType());
                BrandField.setText(currentLuggage.getBrand());
                SpecialFeaturesField.setText(currentLuggage.getSpecialFeatures());
                ColorField.setText(currentLuggage.getColor());
                LocationField.setText(currentLuggage.getLocation());
////                FoundDateField.setValue(LOCAL_DATE(currentLuggage.getFoundDate()));
////                LostDateField.setValue(currentLuggage.getLostDate());
                StatusField.setText(currentLuggage.getStatus());
                CompesationField.setText(Double.toString(currentLuggage.getCompesation()));
                LostLocationField.setText(currentLuggage.getLostLocation());
            }
        } catch (SQLException ex) {
            Logger.getLogger(LostLuggage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    @FXML
    private boolean updateLuggage(ActionEvent event) throws IOException, SQLException { 
        
         // Make instances for the classes
        Luggage luggage = new Luggage();
        Customer customer = new Customer();
       
        //step 1 
        customer.setName(NameField.getText().toString());
        customer.setZipcode(ZipcodeField.getText().toString());
        customer.setAddress(AddressField.getText().toString());
        customer.setEmail(EmailField.getText().toString());
        customer.setPhoneNumber(PhoneNumberField.getText().toString());
        customer.setCity(CityField.getText().toString());
        customer.setCountry(CountryField.getText().toString());
        
        //step 2
        luggage.setLabelNumber(LabelNumberField.getText().toString());
        luggage.setType(TypeField.getText().toString());
        luggage.setBrand(BrandField.getText().toString());
        luggage.setLocation(LocationField.getText().toString());
        luggage.setColor(ColorField.getText().toString());
        luggage.setStatus(StatusField.getText().toString());
        luggage.setLostLocation(LostLocationField.getText().toString());
        if(!CompesationField.getText().isEmpty()) {
            luggage.setCompesation(Double.parseDouble(CompesationField.getText()));
        }
        
        if(FoundDateField.getValue() != null) {
            luggage.setFoundDate(FoundDateField.getValue().toString());
        }
         
        if(LostDateField.getValue() != null) {
            luggage.setLostDate(LostDateField.getValue().toString());
        }
        luggage.setSpecialFeatures(SpecialFeaturesField.getText());
        
        // Update luggage
        luggage.updateLuggage(getCurrentLuggageId());
        //customer.updateCustomer(customerResult.getInt("ID"));
        
        Main.GoToScreen("LostAndFound.fxml");
        
        return true;
       
    }

    
    @FXML
    private void nextStep(ActionEvent event) throws IOException {
        Step1.setVisible(false);
        Step2.setVisible(true);

    }

    @FXML
    private void previousStep(ActionEvent event) throws IOException {
        Step1.setVisible(true);
        Step2.setVisible(false);
    }

    /**
     * @return the currentLuggageId
     */
    public static int getCurrentLuggageId() {
        return currentLuggageId;
    }

    /**
     * @param aCurrentLuggageId the currentLuggageId to set
     */
    public static void setCurrentLuggageId(int aCurrentLuggageId) {
        currentLuggageId = aCurrentLuggageId;
    }
}
