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
 * @author Lorenzo Wijtman
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
     * Test to see if the method getCustomers returns at least 1 flight/row
     * @throws Exception
     */
    @Test
    public void testGetCustomers() throws Exception {
        System.out.println("get customers");
        ResultSet result = instance.getCustomers();
        assertTrue(result.first());
    }
    
    /**
     * Test to see if the method getCustomers returns at least 1 flight/row
     * @throws Exception
     */
    @Test
    public void testGetCustomer() throws Exception {
        System.out.println("get customer");
        ResultSet result = instance.getCustomer(1);
        assertTrue(result.first());
    }
    
    /**
     * Test to see if the method getCustomers returns 0 results with an false ID
     * @throws Exception
     */
    @Test
    public void testGetFalseCustomer() throws Exception {
        System.out.println("get a false customer");
        ResultSet result = instance.getCustomer(123456);
        assertFalse(result.first());
    }
    
    @Test
    public void testSaveCustomer() throws Exception {
        System.out.println("make a flight");

        String name = "Costa";
        String email = "elsasc@hva.nl";
        String address = "Wibautstraat";
        String city = "Amsterdam";
        String zipcode = "1000AA";
        String country = "NL";
        String phoneNumber = "06123456789";

        instance.Customer(name, email, address, city, zipcode, country, phoneNumber);

        boolean expResult = true;
        boolean result = instance.saveCustomer();

        assertThat(expResult, equalTo(result));
    }

    @Test
    public void NameCanBeChanged() {
        String newName = "This is a new name";
        instance.setName(newName);

        assertThat(newName, equalTo(instance.getName()));
    }

}
