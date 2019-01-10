/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Costa van Elsas, Walter Tesevic
 */
public class UserTest {
    
    private User currentInvalidUser = new User();    
    private User emptyUser = new User();


    /**
     *
     * @throws NoSuchAlgorithmException
     */    
    @Before
    public void setUp() throws NoSuchAlgorithmException {
        currentInvalidUser.setID(1);
        currentInvalidUser.setUsername("Costa1");
        currentInvalidUser.setName("Costa");
        currentInvalidUser.setRole("");
        currentInvalidUser.setEmail("costa@.nl");
        currentInvalidUser.setPassword("a");
    }
    
    @Test
    public void invalidEmail(){
        assertEquals(false, currentInvalidUser.hasValidEmail());
    }
    
    @Test
    public void emptyUser(){
        assertEquals(false, emptyUser.IsNotEmpty());
    }
    
    @Test
    public void userDoesNotExist() throws SQLException{
        assertEquals(false, currentInvalidUser.Exists());
    }
}
