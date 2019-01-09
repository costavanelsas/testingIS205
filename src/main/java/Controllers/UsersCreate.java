package Controllers;

import Models.Roles;
import Models.User;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

/**
 * 
 * @author Simon Controller voor het aanmaken van een user
 */
public class UsersCreate implements Initializable {
    
    @FXML
    private TextField UserNameField;
    @FXML
    private TextField NameField;
    @FXML
    private TextField MailField;
    @FXML
    private ComboBox RoleField;
    @FXML
    private PasswordField PasswordField;
    @FXML
    private PasswordField ConfirmPasswordField;
    @FXML
    private Label ValidationLabel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){        
        ObservableList RolesList = Roles.GetRolesObservableList();
        RoleField.getItems().clear();
        RoleField.setItems(RolesList);
    }    
    
    /**
     * Function to create/edit a user. After saving redirects back to Users.FXML
     * @throws IOException 
     */
    @FXML
    private boolean SaveUser(ActionEvent event) throws IOException, SQLException, NoSuchAlgorithmException{
        //use this variable to check if user can be registered
        boolean passed = true;
        String validationErrors = "";
        
        //nieuwe user aanmaken
        User newUser = new User();
        
        //To String after every getText so it can be checked on being empty
        newUser.setUsername(UserNameField.textProperty().get());
        newUser.setName(NameField.textProperty().get());
        newUser.setRole(RoleField.getSelectionModel().getSelectedItem().toString());
        newUser.setEmail(MailField.textProperty().get());
        newUser.setPassword(PasswordField.textProperty().get());
        final String confirmPassword = ConfirmPasswordField.textProperty().get();
        
        
        //values can not be null
        if(!newUser.IsNotEmpty()){
            ValidationLabel.setText("Fill in all fields");
            return false;
        }
        
        //User needs valid getEmail
        if(!newUser.hasValidEmail()){
            ValidationLabel.setText("E-mail is invalid");
            return false;
        }
        
        //password and confirmpassword has to be same values
        if(newUser.getPassword() == null ? confirmPassword != null : !newUser.getPassword().equals(User.HashPassword(confirmPassword))){
            ValidationLabel.setText("Passwords do not match.");
            return false;
        }
        
        if(!newUser.Exists()){
            newUser.Save();
        }else{
            ValidationLabel.setText("User already exists with this username/email");
            return false;
        }
                
        //Navigate back to the list
        Main.GoToScreen("Users.fxml");
        return true;
    }
}