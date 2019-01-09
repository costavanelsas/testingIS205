/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.DB;
import Models.Luggage;
import Models.Roles;
import Models.User;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Simon Controller voor het bewerken van een user
 */
public class UsersEdit implements Initializable {

    private static int userId;

    private DB Connection;

    // new instance of user and define properties to save it quicker
    User UserEdit = new User();
    
    Luggage luggageEdit = new Luggage();

    @FXML
    private TextField UserNameField;
    @FXML
    private TextField NameField;
    @FXML
    private TextField MailField;
    @FXML
    private ComboBox RoleField;
    @FXML
    private TextField PasswordField;
    @FXML
    private TextField ConfirmPasswordField;
    @FXML
    private Label ValidationLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // open connection
        Connection = new DB();

        // make query to get user by id
        String sql = String.format("SELECT * FROM `user` WHERE `ID` = %s", userId);

        try {
            // execute query
            ResultSet queryResult = Connection.executeResultSetQuery(sql);

            // get the first result of the query
            if (queryResult.first()) {
                UserEdit.setID(queryResult.getInt("ID"));
                UserEdit.setUsername(queryResult.getString("username"));
                UserEdit.setName(queryResult.getString("name"));
                UserEdit.setRole(queryResult.getString("role"));
                UserEdit.setHashedPassword(queryResult.getString("password"));
                UserEdit.setEmail(queryResult.getString("email"));

                UserNameField.setText(UserEdit.getUsername());
                NameField.setText(UserEdit.getName());
                MailField.setText(UserEdit.getEmail());

                // rolefield is a combobox so it needs to be filled differently
                ObservableList RolesList = Roles.GetRolesObservableList();
                RoleField.getItems().clear();
                RoleField.setItems(RolesList);
                RoleField.setValue(UserEdit.getRole());
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersEdit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void SetUsedToBeEdited(int id) {
        userId = id;
    }

    /**
     * this event is triggered on the button save click, it saves the user with the current filled in data
     * @param event
     * @throws NoSuchAlgorithmException
     * @throws IOException 
     */
    @FXML
    private void SaveUser(ActionEvent event) throws NoSuchAlgorithmException, IOException {
        boolean valid = true;

        // set all the properties of the user object
        UserEdit.setUsername(UserNameField.textProperty().get());
        UserEdit.setName(NameField.textProperty().get());
        UserEdit.setEmail(MailField.textProperty().get());
        UserEdit.setRole(RoleField.getSelectionModel().getSelectedItem().toString());

        //validation if password isnt empty
        if (!PasswordField.getText().trim().isEmpty() && !ConfirmPasswordField.getText().trim().isEmpty() && PasswordField.getText().equals(ConfirmPasswordField.getText())) {
            UserEdit.setPassword(PasswordField.getText());
        } else if (!PasswordField.getText().equals(ConfirmPasswordField.getText())) {
            // passwords are different
            valid = false;
        }
        if (!UserEdit.IsNotEmpty()) {
            ValidationLabel.setText("Fill in all fields");
            valid = false;
        }

        //user needs valid email
        if (!UserEdit.hasValidEmail()) {
            ValidationLabel.setText("E-mail is invalid");
            valid = false;
        }

        // if the user has valid data, then save and redirect to users
        if (valid) {
            UserEdit.Update();
            Main.GoToScreen("Users.fxml");
        }
    }
}
