/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.ResultSet;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jairz
 */
public class CustomerTest {
    
    Customer instance;
    
    public CustomerTest() {
    }
    
    @Before
    public void setUp() {
        instance = new Customer();
    }
    
    /**
     * Test to see if the method getCustomerss returns at least 1 flight/row
     * @throws Exception
     */
    @Test
    public void testGetCustomers() throws Exception {
        System.out.println("getCustomers");
        ResultSet result = instance.getCustomers();
        assertTrue(result.first());
    }
    
    /**
     * Test to see if the method getCustomerss returns at least 1 flight/row
     * @throws Exception
     */
    @Test
    public void testGetCustomer() throws Exception {
        System.out.println("getCustomer");
        ResultSet result = instance.getCustomer(1);
        assertTrue(result.first());
    }
    
    /**
     * Test to see if the method getCustomerss returns 0 results with an false ID
     * @throws Exception
     */
    @Test
    public void testGetFalseCustomer() throws Exception {
        System.out.println("getCustomer");
        ResultSet result = instance.getCustomer(912137);
        assertFalse(result.first());
    }
    
     @Test
    public void testSaveCustomer() throws Exception {
        System.out.println("saveFlight");
        String name = "Test!";
        String email = "jair.zijp@hva.nl";
        String address = "Wibautstraat";
        String city = "Amsterdam";
        String zipcode = "2031AA";
        String country = "NL";
        String phoneNumber = "+31 0612232423";
        instance.Customer(name, email, address, city, zipcode, country, phoneNumber);
        boolean expResult = true;
        boolean result = instance.saveCustomer();
        assertThat(expResult, equalTo(result));
    }
    
 
}
