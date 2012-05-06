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
public class GamePunktTest {
    
    public GamePunktTest() {
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
     * Test of getLat method, of class GamePunkt.
     */
    @Test
    public void testGetLat() {
        System.out.println("getLat");
        GamePunkt instance = new GamePunkt();
        double expResult = 0.0;
        double result = instance.getLat();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLat method, of class GamePunkt.
     */
    @Test
    public void testSetLat() {
        System.out.println("setLat");
        double lat = 0.0;
        GamePunkt instance = new GamePunkt();
        instance.setLat(lat);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLng method, of class GamePunkt.
     */
    @Test
    public void testGetLng() {
        System.out.println("getLng");
        GamePunkt instance = new GamePunkt();
        double expResult = 0.0;
        double result = instance.getLng();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLng method, of class GamePunkt.
     */
    @Test
    public void testSetLng() {
        System.out.println("setLng");
        double lng = 0.0;
        GamePunkt instance = new GamePunkt();
        instance.setLng(lng);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class GamePunkt.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        GamePunkt instance = new GamePunkt();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class GamePunkt.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        GamePunkt instance = new GamePunkt();
        instance.setName(name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRadius method, of class GamePunkt.
     */
    @Test
    public void testGetRadius() {
        System.out.println("getRadius");
        GamePunkt instance = new GamePunkt();
        int expResult = 0;
        int result = instance.getRadius();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRadius method, of class GamePunkt.
     */
    @Test
    public void testSetRadius() {
        System.out.println("setRadius");
        int radius = 0;
        GamePunkt instance = new GamePunkt();
        instance.setRadius(radius);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRebus method, of class GamePunkt.
     */
    @Test
    public void testGetRebus() {
        System.out.println("getRebus");
        GamePunkt instance = new GamePunkt();
        String expResult = "";
        String result = instance.getRebus();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRebus method, of class GamePunkt.
     */
    @Test
    public void testSetRebus() {
        System.out.println("setRebus");
        String rebus = "";
        GamePunkt instance = new GamePunkt();
        instance.setRebus(rebus);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIdPunkt method, of class GamePunkt.
     */
    @Test
    public void testGetIdPunkt() {
        System.out.println("getIdPunkt");
        GamePunkt instance = new GamePunkt();
        Long expResult = null;
        Long result = instance.getIdPunkt();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setIdPunkt method, of class GamePunkt.
     */
    @Test
    public void testSetIdPunkt() {
        System.out.println("setIdPunkt");
        Long idPunkt = null;
        GamePunkt instance = new GamePunkt();
        instance.setIdPunkt(idPunkt);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
