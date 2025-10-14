import java.io.IOException;
import student.TestCase;

/**
 * @author walkerberndt, lukefreeman
 * @version Oct 1, 2025
 */
public class GISTest extends TestCase {

    private GIS it;
    private GIS db;

    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        it = new GISDB();
        db = new GISDB();
    }


    /**
     * Tests using a BST Database in the GIS project for the print function
     */
    public void testPrint() {
        assertTrue(it.insert("Los Angeles", 11, 12));
        assertTrue(it.insert("Baton Rouge", 20, 30));
        assertTrue(it.insert("Denver", 34, 45));
        assertTrue(it.insert("New York", 24, 35));
        assertTrue(it.insert("Summerville", 54, 63));
        assertFuzzyEquals(it.print(), "1  Baton Rouge (20, 30)\n"
            + "2    Denver (34, 45)\n" + "0Los Angeles (11, 12)\n"
            + "1  New York (24, 35)\n" + "2    Summerville (54, 63)\n");
    }


    /**
     * Tests using a KDTree Database in the GIS project for the debug function
     */
    public void testDebug() {
        assertTrue(it.insert("city1", 12, 16));
        assertTrue(it.insert("city2", 6, 18));
        assertTrue(it.insert("city3", 15, 23));
        assertTrue(it.insert("city4", 10, 16));
        assertTrue(it.insert("city5", 27, 14));

        assertFuzzyEquals(it.debug(), multiline("2    city4 10 16",
            "1  city2 6 18", "0city1 12 16", "2    city5 27 14",
            "1  city3 15 23"));

        assertTrue(it.insert("city6", 20, 20));
        assertTrue(it.insert("city7", 20, 25));

        assertFuzzyEquals(it.debug(), multiline("2    city4 10 16",
            "1  city2 6 18", "0city1 12 16", "3      city6 20 20",
            "2    city5 27 14", "1  city3 15 23", "2    city7 20 25"));
    }


    // ----------------------------------------------------------
    /**
     * Tests using a KDTree database for the delete function with x, y input
     * parameters
     */
    public void testDeleteDatabaseXY() {
        assertTrue(it.insert("city1", 12, 16));
        assertTrue(it.insert("city2", 6, 18));
        assertTrue(it.insert("city3", 15, 23));
        assertTrue(it.insert("city4", 10, 16));
        assertTrue(it.insert("city5", 27, 14));

        assertFuzzyEquals(it.delete(6, 18), "4\n" + "city2");
        assertFuzzyEquals(it.delete(27, 14), "3\n" + "city5");
    }


    // ----------------------------------------------------------
    /**
     * Tests the delete function for the name parameter function call.
     */
    public void testDeleteDatabaseName() {
        assertTrue(it.insert("city1", 12, 16));
        assertTrue(it.insert("city2", 6, 18));
        assertTrue(it.insert("city3", 15, 23));
        assertTrue(it.insert("city4", 10, 16));
        assertTrue(it.insert("city5", 27, 14));

        assertFuzzyEquals(it.delete("city4"), "city4 10 16");
        assertFuzzyEquals(it.delete("city2"), "city2 6 18");
        assertFuzzyEquals(it.delete("city6"), "");
    }


    /**
     * Print testing for empty trees
     * 
     * @throws IOException
     */
    public void testRefEmptyPrints() throws IOException {
        assertFuzzyEquals("", it.print());
        assertFuzzyEquals("", it.debug());
        assertFuzzyEquals("", it.info("CityName"));
        assertFuzzyEquals("", it.info(5, 5));
        assertFuzzyEquals("", it.delete("CityName"));
        assertFuzzyEquals("", it.delete(5, 5));
    }


    /**
     * Print bad input checks
     * 
     * @throws IOException
     */
    public void testRefBadInput() throws IOException {
        assertFalse(it.insert("CityName", -1, 5));
        assertFalse(it.insert("CityName", 5, -1));
        assertFalse(it.insert("CityName", 100000, 5));
        assertFalse(it.insert("CityName", 5, 100000));
        assertFuzzyEquals("", it.search(-1, -1, -1));
    }


    /**
     * Test the clear method
     */
    public void testClear() {
        assertTrue(it.insert("Los Angeles", 11, 12));
        assertTrue(it.insert("Baton Rouge", 20, 30));
        assertTrue(it.insert("Denver", 34, 45));
        assertTrue(it.insert("New York", 24, 35));
        assertTrue(it.insert("Summerville", 54, 63));
        assertTrue(it.clear());
    }


    /**
     * Test the delete method
     */
    public void testDeleteName() {
        assertTrue(it.insert("Los Angeles", 11, 12));
        assertTrue(it.insert("Baton Rouge", 20, 30));
        assertTrue(it.insert("Denver", 34, 45));
        assertTrue(it.insert("New York", 24, 35));
        assertTrue(it.insert("Summerville", 54, 63));
        assertFuzzyEquals(it.delete("Summerville"), "summerville (54, 63)");
        assertFuzzyEquals(it.debug(), "0Los Angeles (11, 12)\n"
            + "1  Baton Rouge (20, 30)\n" + "3      New York (24, 35)\n"
            + "2    Denver (34, 45)\n");
        assertFuzzyEquals(it.delete("Denver"), "denver (34, 45)");
        assertFuzzyEquals(it.delete("Los Angeles"), "los angeles (11, 12)");
        assertFuzzyEquals(it.delete("Baton Rouge"), "baton rouge (20, 30)");
        assertFuzzyEquals(it.delete("New York"), "new york (24, 35)");
    }


    /**
     * Insert some records and check output requirements for various commands
     * 
     * @throws IOException
     */
    public void testRefOutput() throws IOException {
        assertTrue(it.insert("Chicago", 100, 150));
        assertTrue(it.insert("Atlanta", 10, 500));
        assertTrue(it.insert("Tacoma", 1000, 100));
        assertTrue(it.insert("Baltimore", 0, 300));
        assertTrue(it.insert("Washington", 5, 350));
        assertFalse(it.insert("X", 100, 150));
        assertTrue(it.insert("L", 101, 150));
        assertTrue(it.insert("L", 11, 500));
        assertFuzzyEquals("1  Atlanta (10, 500)\n" + "2    Baltimore (0, 300)\n"
            + "0Chicago (100, 150)\n" + "3      L (11, 500)\n"
            + "2    L (101, 150)\n" + "1  Tacoma (1000, 100)\n"
            + "2    Washington (5, 350)\n", it.print());
        assertFuzzyEquals("2    Baltimore (0, 300)\n"
            + "3      Washington (5, 350)\n" + "1  Atlanta (10, 500)\n"
            + "2    L (11, 500)\n" + "0Chicago (100, 150)\n"
            + "1  Tacoma (1000, 100)\n" + "2    L (101, 150)\n", it.debug());
        assertFuzzyEquals("L (101, 150)\nL (11, 500)", it.info("L"));
        assertFuzzyEquals("L", it.info(101, 150));
        assertFuzzyEquals("Tacoma (1000, 100)", it.delete("Tacoma"));
        assertFuzzyEquals("3\nChicago", it.delete(100, 150));
        assertFuzzyEquals("L (101, 150)\n" + "Atlanta (10, 500)\n"
            + "Baltimore (0, 300)\n" + "Washington (5, 350)\n"
            + "L (11, 500)\n5", it.search(0, 0, 2000));
        // assertFuzzyEquals("Baltimore (0, 300)\n4", it.search(0, 300, 0));
    }


    // ----------------------------------------------------------
    /**
     * Tests inserting nodes (Cities) to the KDTree.
     */
    public void testInsertKD() {

        db.insert("city1", 12, 16);
        db.insert("city2", 6, 18);
        db.insert("city3", 15, 23);
        db.insert("city4", 10, 16);
        db.insert("city5", 27, 14);

        assertFuzzyEquals(db.debug(), multiline("2    city4 10 16",
            "1  city2 6 18", "0city1 12 16", "2    city5 27 14",
            "1  city3 15 23"));

        db.insert("city6", 20, 20);
        db.insert("city7", 20, 25);

        assertFuzzyEquals(db.debug(), multiline("2    city4 10 16",
            "1  city2 6 18", "0city1 12 16", "3      city6 20 20",
            "2    city5 27 14", "1  city3 15 23", "2    city7 20 25"));

    }


    // ----------------------------------------------------------
    /**
     * Deletes a city based on an input x and y position
     */
    public void testDeletePositionKD() {
        db.insert("city1", 12, 16);
        db.insert("city2", 6, 18);
        db.insert("city3", 15, 23);
        db.insert("city4", 10, 16);
        db.insert("city5", 27, 14);
        db.insert("city6", 20, 20);
        db.insert("city7", 20, 25);
        db.insert("city8", 5, 30);

        assertFuzzyEquals(db.delete(0, 0), "");
        assertFuzzyEquals(db.delete(5, 30), "3 \n city8");
        assertFuzzyEquals(db.delete(20, 20), "4 \n city6");
        assertFuzzyEquals(db.delete(20, 25), "3 \n city7");
        assertFuzzyEquals(db.delete(-10, 10), "");
        assertFuzzyEquals(db.delete(27, 6), "");
        assertFuzzyEquals(db.delete(10, 16), "3, \n city4");
    }


    public void testDeletePositionKD2() {
        db.insert("Memphis", 35, 35);
        db.insert("Jacksonville", 20, 20);
        db.insert("Daytona", 20, 25);
        db.insert("Los Angeles", 50, 35);
        db.insert("Richmond", 5, 10);
        db.insert("Harrisonburg", 15, 15);
        db.insert("Seattle", 40, 50);
        db.insert("Chicago", 40, 20);
        db.insert("Portland", 60, 55);
        db.insert("Houston", 30, 35);

        assertFuzzyEquals(db.delete(50, 35), "7 \n Los Angeles");
        assertFuzzyEquals(db.delete(40, 20), "3 \n Chicago");
        assertFuzzyEquals(db.delete(60,55), "3 \n Portland");
        assertFuzzyEquals(db.delete(40, 50), "2 \n Seattle");
        assertFuzzyEquals(db.delete(20, 25), "5 \n Daytona");
    }


    // ----------------------------------------------------------
    /**
     * Deletes a city based on an input x and y position
     */
    public void testDeletePositionRootKD() {
        db.insert("city1", 12, 16);
        db.insert("city2", 6, 18);
        db.insert("city3", 15, 23);
        db.insert("city4", 10, 16);
        db.insert("city5", 27, 14);
        db.insert("city6", 20, 20);
        db.insert("city7", 20, 25);
        db.insert("city8", 5, 30);

        assertFuzzyEquals(db.delete(12, 16), "8 \n city1");
        assertFuzzyEquals(db.delete(20, 25), "7 \n city7");
    }


    // ----------------------------------------------------------
    /**
     * Tests the info function for a x and y input.
     */
    public void testInfoXYKD() {
        db.insert("city1", 12, 16);
        db.insert("city2", 6, 18);
        db.insert("city3", 15, 23);
        db.insert("city4", 10, 16);
        db.insert("city5", 27, 14);
        db.insert("city6", 20, 20);
        db.insert("city7", 20, 25);
        db.insert("city8", 5, 30);
        db.insert("city9", 9, 9);

        assertFuzzyEquals(db.info(0, 0), "");
        assertFuzzyEquals(db.info(5, 30), "city8");
        assertFuzzyEquals(db.info(10, 16), "city4");
        assertFuzzyEquals(db.info(20, 14), "");
        assertFuzzyEquals(db.info(14, 20), "");
    }


    // ----------------------------------------------------------
    /**
     * Tests the Search function
     */
    public void testSearchKD() {
        db.insert("city1", 12, 16);
        db.insert("city2", 6, 18);
        db.insert("city3", 15, 23);
        db.insert("city4", 10, 16);
        db.insert("city5", 27, 14);
        db.insert("city6", 20, 20);
        db.insert("city7", 20, 25);
        db.insert("city8", 5, 30);
        db.insert("city9", 9, 9);

        assertFuzzyEquals(db.search(5, 10, -1), "");
        assertFuzzyEquals(db.search(20, 20, 1), "city6 (20, 20)\n4");
        assertFuzzyEquals(db.search(20, 20, 5),
            "city6 (20, 20)\n city7 (20, 25)\n 5");
        assertFuzzyEquals(db.search(20, 20, 0), "city6 (20, 20)\n4");
        assertFuzzyEquals(db.search(15, 15, 0), "4");

        GISDB db2 = new GISDB();

        assertFuzzyEquals(db2.search(0, 0, 0), "0");
    }


    // ----------------------------------------------------------
    /**
     * Tests the clear function on a filled tree.
     */
    public void testClearKD() {
        db.insert("city1", 12, 16);
        db.insert("city2", 6, 18);
        db.insert("city3", 15, 23);
        db.insert("city4", 10, 16);
        db.insert("city5", 27, 14);
        db.insert("city6", 20, 20);
        db.insert("city7", 20, 25);

        assertFuzzyEquals(db.debug(), multiline("2    city4 10 16",
            "1  city2 6 18", "0city1 12 16", "3      city6 20 20",
            "2    city5 27 14", "1  city3 15 23", "2    city7 20 25"));

        db.clear();

        assertFuzzyEquals(db.debug(), "");
    }
}
