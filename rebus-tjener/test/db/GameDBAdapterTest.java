/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author obu
 */
public class GameDBAdapterTest {
    
    public GameDBAdapterTest() {
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
     * Test of finalize method, of class GameDBAdapter.
     */
    @Test
    public void testFinalize() {
        System.out.println("finalize");
        GameDBAdapter instance = new GameDBAdapter();
        instance.finalize();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of persistGame method, of class GameDBAdapter.
     */
    @Test
    public void testPersistGame() {
        System.out.println("persistGame");
        Game g = null;
        GameDBAdapter instance = new GameDBAdapter();
        boolean expResult = false;
        boolean result = instance.persistGame(g);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGameById method, of class GameDBAdapter.
     */
    @Test
    public void testGetGameById() {
        System.out.println("getGameById");
        long id = 0L;
        GameDBAdapter instance = new GameDBAdapter();
        Game expResult = null;
        Game result = instance.getGameById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addPointToGameInDB method, of class GameDBAdapter.
     */
    @Test
    public void testAddPointToGameInDB() {
        System.out.println("addPointToGameInDB");
        GamePunkt gp = null;
        long gameId = 0L;
        GameDBAdapter instance = new GameDBAdapter();
        instance.addPointToGameInDB(gp, gameId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addPartisipantToGame method, of class GameDBAdapter.
     */
    @Test
    public void testAddPartisipantToGame() throws Exception {
        System.out.println("addPartisipantToGame");
        long gameId = 0L;
        long userId = 0L;
        GameDBAdapter instance = new GameDBAdapter();
        instance.addPartisipantToGame(gameId, userId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllGames method, of class GameDBAdapter.
     */
    @Test
    public void testGetAllGames() {
        System.out.println("getAllGames");
        GameDBAdapter instance = new GameDBAdapter();
        List expResult = null;
        List result = instance.getAllGames();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of closeConnection method, of class GameDBAdapter.
     */
    @Test
    public void testCloseConnection() {
        System.out.println("closeConnection");
        GameDBAdapter instance = new GameDBAdapter();
        instance.closeConnection();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
