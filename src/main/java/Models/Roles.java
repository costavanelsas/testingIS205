/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Simon
 */
public class Roles {

    // defining the list
    private static List<String> roles = new ArrayList<>();

    private static void setRoles() {
        // add all the roles to the list
        roles.add("Manager");
        roles.add("Servicedesk");
    }

    public static List GetRolesList(){
        // return the roles in list format
        return roles;
    }
    
    public static ObservableList GetRolesObservableList() {
        // set all roles
        setRoles();
        
        // put roles in observable list
        ObservableList RoleList = FXCollections.observableList(roles);

        //return observablelist
        return RoleList;
    }
}
