/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author obu
 */
public class UserTest {
    
    public UserTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getId method, of class User.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        User instance = new User();
        Long expResult = null;
        Long result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class User.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        User instance = new User();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class User.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        User instance = new User();
        instance.setName(name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPass method, of class User.
     */
    @Test
    public void testGetPass() {
        System.out.println("getPass");
        User instance = new User();
        String expResult = "";
        String result = instance.getPass();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPass method, of class User.
     */
    @Test
    public void testSetPass() {
        System.out.println("setPass");
        String pass = "";
        User instance = new User();
        instance.setPass(pass);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
