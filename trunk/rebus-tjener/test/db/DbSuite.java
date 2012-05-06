/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author obu
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({db.GameTest.class, db.UserDBAdapterTest.class, db.GameDBAdapterTest.class, db.UserTest.class, db.GamePunktTest.class})
public class DbSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
