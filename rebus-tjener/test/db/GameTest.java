/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author obu
 */
public class GameTest {
    
    public GameTest() {
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
     * Test of getId method, of class Game.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Game instance = new Game();
        Long expResult = null;
        Long result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isIsOpen method, of class Game.
     */
    @Test
    public void testIsIsOpen() {
        System.out.println("isIsOpen");
        Game instance = new Game();
        boolean expResult = false;
        boolean result = instance.isIsOpen();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setIsOpen method, of class Game.
     */
    @Test
    public void testSetIsOpen() {
        System.out.println("setIsOpen");
        boolean isOpen = false;
        Game instance = new Game();
        instance.setIsOpen(isOpen);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class Game.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Game instance = new Game();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class Game.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        Game instance = new Game();
        instance.setName(name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStartDate method, of class Game.
     */
    @Test
    public void testGetStartDate() {
        System.out.println("getStartDate");
        Game instance = new Game();
        long expResult = 0L;
        long result = instance.getStartDate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setStartDate method, of class Game.
     */
    @Test
    public void testSetStartDate() {
        System.out.println("setStartDate");
        long startDate = 0L;
        Game instance = new Game();
        instance.setStartDate(startDate);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVarighet method, of class Game.
     */
    @Test
    public void testGetVarighet() {
        System.out.println("getVarighet");
        Game instance = new Game();
        int expResult = 0;
        int result = instance.getVarighet();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setVarighet method, of class Game.
     */
    @Test
    public void testSetVarighet() {
        System.out.println("setVarighet");
        int varighet = 0;
        Game instance = new Game();
        instance.setVarighet(varighet);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAuthorName method, of class Game.
     */
    @Test
    public void testGetAuthorName() {
        System.out.println("getAuthorName");
        Game instance = new Game();
        String expResult = "";
        String result = instance.getAuthorName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addPoint method, of class Game.
     */
    @Test
    public void testAddPoint() {
        System.out.println("addPoint");
        GamePunkt gp = null;
        Game instance = new Game();
        instance.addPoint(gp);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNextPunkt method, of class Game.
     */
    @Test
    public void testGetNextPunkt() throws Exception {
        System.out.println("getNextPunkt");
        Game instance = new Game();
        GamePunkt expResult = null;
        GamePunkt result = instance.getNextPunkt();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addPartisipant method, of class Game.
     */
    @Test
    public void testAddPartisipant() throws Exception {
        System.out.println("addPartisipant");
        long id = 0L;
        Game instance = new Game();
        instance.addPartisipant(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllPartisipants method, of class Game.
     */
    @Test
    public void testGetAllPartisipants() {
        System.out.println("getAllPartisipants");
        Game instance = new Game();
        ArrayList expResult = null;
        ArrayList result = instance.getAllPartisipants();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPartisipant method, of class Game.
     */
    @Test
    public void testGetPartisipant() {
        System.out.println("getPartisipant");
        long id = 0L;
        Game instance = new Game();
        boolean expResult = false;
        boolean result = instance.getPartisipant(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
