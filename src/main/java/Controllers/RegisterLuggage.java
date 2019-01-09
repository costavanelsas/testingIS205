/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Luggage;
import Models.Customer;
import Models.Flight;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Jaïr Zijp
 * FXML Controller RegisterLuggage
 */
public class RegisterLuggage {
        
    
    // Define all the input fields for the different steps
    @FXML
    private AnchorPane Step1;
    @FXML
    private TextField NameField;
    @FXML
    private TextField CityField;
    @FXML
    private TextField EmailField;
    @FXML
    private TextField AddressField;
    @FXML
    private TextField CountryField;
    @FXML
    private TextField ZipcodeField;
    @FXML
    private TextField PhoneNumberField;
    
    /* Step 2. */
    @FXML
    private AnchorPane Step2;
    @FXML
    private TextField FlightIdField;
    @FXML
    private TextField AirportField;
    @FXML
    private TextField DestinationField;
    @FXML
    private DatePicker DateField;
    @FXML
    private TextField TimeField;
    
    
    /* Step 3. */
    @FXML
    private Label ValidationLabel;
    @FXML
    private AnchorPane Step3;
    @FXML
    private TextField LabelNumberField;
    @FXML
    private TextField TypeField;
    @FXML
    private TextField BrandField;
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
    private TextField SpecialFeaturesField;
    @FXML
    private TextField LostLocationField;
    
    @FXML
    private boolean saveLuggage(ActionEvent event) throws IOException, SQLException {
        
        // Make instances for the classes
        Luggage luggage = new Luggage();
        Customer customer = new Customer();
        Flight flight = new Flight();
        
        // Set all variables to the input from user.
        
        /* Step 1. */
        customer.setName(NameField.getText().toString());
        customer.setEmail(EmailField.getText().toString());
        customer.setAddress(AddressField.getText().toString());
        customer.setCity(CityField.getText().toString());
        customer.setCountry(CountryField.getText().toString());
        customer.setZipcode(ZipcodeField.getText().toString());
        customer.setPhoneNumber(PhoneNumberField.getText().toString());
        
        /* Step 2 */
        flight.setFlightId(FlightIdField.getText().toString());
        flight.setAirport(AirportField.getText().toString()); 
        flight.setDestination(DestinationField.getText().toString());
        flight.setTime(TimeField.getText().toString());
        
        if(DateField.getValue() != null) {
            flight.setDate(DateField.getValue().toString());
        }
        
        // Values can not be null
        if(!flight.isValid()){
           ValidationLabel.setText("Please fill in all the required fields."); 
           return false;
        }
        
        /* Step 3. */
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
        
        luggage.setSpecialFeatures(SpecialFeaturesField.getText().toString());
        luggage.setFlightId(FlightIdField.getText().toString());
        luggage.setOwner(NameField.getText().toString());
   
         // Values can not be null
        if(!luggage.isValid()){
           ValidationLabel.setText("Please fill in all the required fields.");          
           return false;
        }
        
        // Save the data
        flight.saveFlight();
        customer.saveCustomer();
        String addedCustomerId = customer.getLastId();
        
        luggage.setCustomerId(Integer.parseInt(addedCustomerId));
        
        luggage.saveLuggage();
        
        // Go back to overview
        Main.GoToScreen("LostAndFound.fxml");
       
        return true;
    }
    
    
    // Next screen
    @FXML
    private void nextStep(ActionEvent event) throws IOException {
        Step1.setVisible(false);
        Step2.setVisible(true);
    }
    
    @FXML
    private void nextStep2(ActionEvent event) throws IOException {
        Step1.setVisible(false);
        Step2.setVisible(false);
        Step3.setVisible(true);
        
    }
    
    // Previous screen
    @FXML
    private void previousStep(ActionEvent event) throws IOException {
        Step1.setVisible(true);
        Step2.setVisible(false);
    }
    
    @FXML
    private void previousStep2(ActionEvent event) throws IOException {
        Step1.setVisible(false);
        Step2.setVisible(true);
        Step3.setVisible(false);
    }
    
}
