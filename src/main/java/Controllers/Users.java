/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.DB;
import Models.User;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Simon Het overzicht van users
 */
public class Users implements Initializable {

    @FXML
    private TableView<User> UsersTable;

    private ObservableList<User> UsersList = FXCollections.observableArrayList();
    
    @FXML
    private TableColumn ID;
    @FXML
    private TableColumn Username;
    @FXML
    private TableColumn Name;
    @FXML
    private TableColumn Role;
    @FXML
    private TableColumn Email;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DB Connection = new DB();
        //Query to select the right data from the user
        String sql = String.format("SELECT `ID`, `username`, `name`, `role`, `email` FROM user");
        try {
            ResultSet queryResult = Connection.executeResultSetQuery(sql);

            //loop through all data
            while (queryResult.next()) {
                User user = new User();

                //create user and set properties
                user.setID(queryResult.getInt("ID"));
                user.setUsername(queryResult.getString("username"));
                user.setName(queryResult.getString("name"));
                user.setRole(queryResult.getString("role"));
                user.setEmail(queryResult.getString("email"));

                UsersList.add(user);
            }
            UsersTable.setItems(UsersList);
            
            // set all the getters for all the tablecolumns
            for (int i = 0; i < UsersTable.getColumns().size(); i++) {
                TableColumn tc = (TableColumn) UsersTable.getColumns().get(i);
                String PropertyName = tc.getId();
                if (PropertyName != null && !PropertyName.isEmpty()) {
                    tc.setCellValueFactory(new PropertyValueFactory<User, String>(PropertyName));
                    String temp = "";
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }  

        Connection.close();
    }

    /**
     * Redirects to the userscreate.fxml
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void GoToCreate(ActionEvent event) throws IOException {
        Main.GoToScreen("UsersCreate.fxml");
    }
    
    /**
     * Delete the selected user, is triggered on the delete button click
     * @param event
     * @throws SQLException 
     */
    @FXML
    private void DeleteUser(ActionEvent event) throws SQLException{
        User selectedUser = (User) UsersTable.getSelectionModel().getSelectedItem();
        int userIDToDelete = selectedUser.getID();
        
        //Removing record from database
        DB Connection = new DB();
        String sql = String.format("DELETE FROM `user` WHERE `ID` = %s", userIDToDelete);
        Connection.executeDeleteQuery(sql);
        Connection.close();
        
        //Removing record from tableview
        UsersList.remove(selectedUser);
    }
    
    /**
     * go to the user edit screen, is triggered on the edit button click
     * @param event
     * @throws IOException 
     */
    @FXML
    private void GoToEdit(ActionEvent event) throws IOException{
        User selectedUser = (User) UsersTable.getSelectionModel().getSelectedItem();
        int userIDToEdit = selectedUser.getID();
        UsersEdit.SetUsedToBeEdited(userIDToEdit);
        Main.GoToScreen("UsersEdit.fxml");
    }
}
