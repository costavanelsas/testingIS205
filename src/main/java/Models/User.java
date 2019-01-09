/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

/**
 *
 * @author Simon
 */
public class User{
    private int ID;
    private String Username;
    private String Name;    
    private String Role;
    private String Email;
    private String Password;

    public void User(int ID, String Username, String Name, String Role, String Email, String Password){
        this.ID = ID;
        this.Username = Username;
        this.Name = Name;
        this.Role = Role;
        this.Email = Email;
        this.Password = Password;
    }
    
    public void setID(int ID) {
        this.ID = ID;
    }
    
    public void setUsername(String Username){
        this.Username = Username;
    }
    
    public void setName(String Name){
        this.Name = Name;
    }
    
    public void setRole(String Role){
        this.Role = Role;
    }
    
    public void setEmail(String Email){
        this.Email = Email;
    }
    
    public void setPassword(String Password) throws NoSuchAlgorithmException{
        this.Password = HashPassword(Password);
    }
    
    public void setHashedPassword(String Password){
        this.Password = Password;
    }
    
    public int getID() {
        return ID;
    }
    
    public String getUsername(){
        return Username;
    }
    
    public String getName(){
        return Name;
    }
    
    public String getRole(){
        return Role;
    }
    
    public String getEmail(){
        return Email;
    }
    
    public String getPassword(){
        return Password;
    }
    

    public boolean IsNotEmpty(){
        return this.Username != null &&
                this.Name != null &&                
                this.Role != null &&
                this.Email != null &&
                this.Password != null;
    }
    
     public void Save() throws NoSuchAlgorithmException{
        //Make Connection                
        DB Connection = new DB(); 
        String sql = String.format("INSERT INTO user (username, name, role, password, email)" +
                "VALUES ('%s', '%s', '%s', '%s', '%s')",
                this.Username, this.Name, this.Role, this.Password, this.Email);
        
        //execute query and close connection     
        Connection.executeUpdateQuery(sql);
        Connection.close();
    }
    
    public boolean Exists() throws SQLException{
        boolean exists;
        DB Connection = new DB();
        //Simpele query om te kijken of er een record dubbel is, niet specifiek welke record.
        String sql = String.format("SELECT * FROM user WHERE `username` = '%s' OR `email` = '%s'", this.Username, this.Email);
        ResultSet queryResult = Connection.executeResultSetQuery(sql);
        
        exists = queryResult.first();
        
        Connection.close();
        
        return exists;
    }
    
    public boolean hasValidEmail(){
        //mail has to match regex
        Pattern ptr = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE);
        return ptr.matcher(this.Email).matches();
    }
    
    public static String HashPassword(String Password) throws NoSuchAlgorithmException{
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(Password.getBytes(), 0, Password.length());    
        return new BigInteger(1, m.digest()).toString(16);
    }

    public void Update() {        
        // create connection
        DB Connection = new DB();
        
        // create update-query string
        String sql = String.format("UPDATE user SET `username` = '%s', `name` = '%s', `role` = '%s', `password` = '%s', `email` = '%s' WHERE `ID` = %s",
                this.Username, this.Name, this.Role, this.Password, this.Email, this.ID);
        
        // send query to database
        Connection.executeUpdateQuery(sql);
    }
}
