package Controllers;

import Models.DB;
import Models.User;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author Simon voor het inloggen van gebruikers
 */
public class Inloggen {

    @FXML
    private TextField UsernameField;
    @FXML
    private PasswordField PasswordField;
    @FXML
    private Label ErrorLabel;

    @FXML
    private void Login(ActionEvent event) throws NoSuchAlgorithmException, IOException, SQLException {

        // create new user
        User user = new User();

        // set username and password from textfields
        String username = UsernameField.textProperty().get(),
                password = PasswordField.textProperty().get(),
                hashedPassword = User.HashPassword(password);

        //create connection and execute query
        DB Connection = new DB();

        //this query will only select 1 row if the data is correct
        String sql = String.format("SELECT * FROM user WHERE `username` = '%s' AND `password` = '%s'",
                username, hashedPassword);

        ResultSet queryResult = Connection.executeResultSetQuery(sql);

        //get the first row from the returned data and set user-properties
        if (queryResult.first()) {

            //set properties of current user
            user.setUsername(queryResult.getString("username"));
            user.setName(queryResult.getString("name"));
            user.setEmail(queryResult.getString("email"));
            user.setRole(queryResult.getString("role"));

            //set current user in main class
            Main.setCurrentUser(user);

            //Navigate back to old screen
            Main.GoToScreen("LostAndFound.fxml");
        } else {
            ErrorLabel.setText("Incorrect username/password");
        }
    }
}
