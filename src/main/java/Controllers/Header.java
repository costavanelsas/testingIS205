/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Simon Voor het verwelkomen van een gebruiker
 */
public class Header implements Initializable{
    @FXML
    private Label Welcome;
    
    /**
     * show the name of the current logged in user
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Welcome.setText(String.format("Welcome, %s!", Main.getCurrentUser().getName()));
    }
}
