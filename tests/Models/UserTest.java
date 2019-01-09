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
 * @author Simon
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
        currentInvalidUser.setUsername("Simon1");
        currentInvalidUser.setName("Simon");
        currentInvalidUser.setRole("");
        currentInvalidUser.setEmail("Simon@.com");
        currentInvalidUser.setPassword("aaa");
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
