/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.DB;
import Models.Luggage;
import Models.Roles;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Jaïr Zijp
 */
public class LuggageDetailController implements Initializable {

    private static int luggageId;
    
    
    @FXML
    private Label labelNumber;
    @FXML
    private Label typeField;
    @FXML
    private Label brandField;
    @FXML
    private Label colorField;
    @FXML
    private Label locationField;
    @FXML
    private Label foundDateField;
    @FXML
    private Label lostDateField;
    @FXML
    private Label statusField;
    @FXML
    private Label compesationField;
    @FXML
    private Label specialFeaturesField;
    @FXML
    private Label lostLocationField;
    @FXML
    private Label ownerField;
    @FXML
    private Label flightIdField;
    
    // new instance of user and define properties to save it quicker
    Luggage LuggageView = new Luggage();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           
        DB Connection = new DB();
       
        // Get user by selected ID
        String sql = String.format("SELECT * FROM `luggage` WHERE `ID` = %s", luggageId);

        try {
            
            ResultSet queryResult = Connection.executeResultSetQuery(sql);

            if (queryResult.first()) {
                
                // Set all the properties from luggage model
                LuggageView.setLabelNumber(queryResult.getString("label_number"));
                LuggageView.setType(queryResult.getString("type"));
                LuggageView.setColor(queryResult.getString("color"));
                LuggageView.setBrand(queryResult.getString("brand"));
                LuggageView.setLocation(queryResult.getString("location"));
                LuggageView.setLostDate(queryResult.getString("lost_date"));
                LuggageView.setLostLocation(queryResult.getString("lost_location"));
                LuggageView.setStatus(queryResult.getString("status"));
                LuggageView.setOwner(queryResult.getString("owner"));
                LuggageView.setSpecialFeatures(queryResult.getString("special_features"));
                LuggageView.setCompesation(queryResult.getInt("compesation"));
                LuggageView.setFlightId(queryResult.getString("flight_id"));
                
                // Get all the properties from luggage model
                labelNumber.setText(LuggageView.getLabelNumber());
                typeField.setText(LuggageView.getType());
                brandField.setText(LuggageView.getBrand());
                colorField.setText(LuggageView.getColor());
                locationField.setText(LuggageView.getLocation());
                foundDateField.setText(LuggageView.getFoundDate());
                lostDateField.setText(LuggageView.getLostDate());
                statusField.setText(LuggageView.getStatus());
                compesationField.setText(""+LuggageView.getCompesation());
                specialFeaturesField.setText(LuggageView.getSpecialFeatures());
                lostLocationField.setText(LuggageView.getLostLocation());
                ownerField.setText(LuggageView.getOwner());
                flightIdField.setText(LuggageView.getFlightId());
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersEdit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
     public static void setLuggageViewId(int id) {
        luggageId = id;
    }
    
}
