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
public class UserDBAdapterTest {
    
    public UserDBAdapterTest() {
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
     * Test of finalize method, of class UserDBAdapter.
     */
    @Test
    public void testFinalize() {
        System.out.println("finalize");
        UserDBAdapter instance = new UserDBAdapter();
        instance.finalize();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of persistUser method, of class UserDBAdapter.
     */
    @Test
    public void testPersistUser() {
        System.out.println("persistUser");
        User u = null;
        UserDBAdapter instance = new UserDBAdapter();
        boolean expResult = false;
        boolean result = instance.persistUser(u);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserById method, of class UserDBAdapter.
     */
    @Test
    public void testGetUserById() {
        System.out.println("getUserById");
        long id = 0L;
        UserDBAdapter instance = new UserDBAdapter();
        User expResult = null;
        User result = instance.getUserById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserByName method, of class UserDBAdapter.
     */
    @Test
    public void testGetUserByName() {
        System.out.println("getUserByName");
        String name = "";
        UserDBAdapter instance = new UserDBAdapter();
        User expResult = null;
        User result = instance.getUserByName(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of autentificate method, of class UserDBAdapter.
     */
    @Test
    public void testAutentificate() {
        System.out.println("autentificate");
        String name = "";
        String pass = "";
        UserDBAdapter instance = new UserDBAdapter();
        boolean expResult = false;
        boolean result = instance.autentificate(name, pass);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
