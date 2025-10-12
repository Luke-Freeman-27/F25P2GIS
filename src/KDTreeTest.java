import java.io.IOException;
import student.TestCase;

/**
 * @author walkerberndt, lukefreeman
 * @version Oct 2, 2025
 */
public class KDTreeTest extends TestCase {

    private KDTree<City> db;

    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        db = new KDTree<City>();
    }


    // ----------------------------------------------------------
    /**
     * Tests inserting nodes (Cities) to the KDTree.
     */
    public void testInsert() {

        City city1 = new City("city1", 12, 16);
        City city2 = new City("city2", 6, 18);
        City city3 = new City("city3", 15, 23);
        City city4 = new City("city4", 10, 16);
        City city5 = new City("city5", 27, 14);
        City city6 = new City("city6", 20, 20);
        City city7 = new City("city7", 20, 25);
        City citynull = null;

        db.insert(citynull);

        db.insert(city1);
        db.insert(city2);
        db.insert(city3);
        db.insert(city4);
        db.insert(city5);
        assertFuzzyEquals(db.inorder(), multiline("2    city4 10 16",
            "1  city2 6 18", "0city1 12 16", "2    city5 27 14",
            "1  city3 15 23"));

        db.insert(city6);
        db.insert(city7);

        assertFuzzyEquals(db.inorder(), multiline("2    city4 10 16",
            "1  city2 6 18", "0city1 12 16", "3      city6 20 20",
            "2    city5 27 14", "1  city3 15 23", "2    city7 20 25"));

    }


    // ----------------------------------------------------------
    /**
     * Deletes a city based on an input x and y position
     */
    public void testDeletePosition() {
        City city1 = new City("city1", 12, 16);
        City city2 = new City("city2", 6, 18);
        City city3 = new City("city3", 15, 23);
        City city4 = new City("city4", 10, 16);
        City city5 = new City("city5", 27, 14);
        City city6 = new City("city6", 20, 20);
        City city7 = new City("city7", 20, 25);
        City city8 = new City("city8", 5, 30);

        db.insert(city1);
        db.insert(city2);
        db.insert(city3);
        db.insert(city4);
        db.insert(city5);
        db.insert(city6);
        db.insert(city7);
        db.insert(city8);

        assertFuzzyEquals(db.delete(0, 0), "");
        assertFuzzyEquals(db.delete(5, 30), "3 \n city8");
        assertFuzzyEquals(db.delete(20, 20), "4 \n city6");
        assertFuzzyEquals(db.delete(20, 25), "3 \n city7");
        assertFuzzyEquals(db.delete(-10, 10), "");
        assertFuzzyEquals(db.delete(27, 6), "");
        assertFuzzyEquals(db.delete(10,16), "3, \n city4");
    }

 // ----------------------------------------------------------
    /**
     * Deletes a city based on an input x and y position
     */
    public void testDeletePositionRoot() {
        City city1 = new City("city1", 12, 16);
        City city2 = new City("city2", 6, 18);
        City city3 = new City("city3", 15, 23);
        City city4 = new City("city4", 10, 16);
        City city5 = new City("city5", 27, 14);
        City city6 = new City("city6", 20, 20);
        City city7 = new City("city7", 20, 25);
        City city8 = new City("city8", 5, 30);

        db.insert(city1);
        db.insert(city2);
        db.insert(city3);
        db.insert(city4);
        db.insert(city5);
        db.insert(city6);
        db.insert(city7);
        db.insert(city8);

        assertFuzzyEquals(db.delete(12, 16), "5 \n city1");
        assertFuzzyEquals(db.delete(20, 25), "6 \n city7");
    }

    // ----------------------------------------------------------
    /**
     * Tests the info function for a x and y input.
     */
    public void testInfoXY() {
        City city1 = new City("city1", 12, 16);
        City city2 = new City("city2", 6, 18);
        City city3 = new City("city3", 15, 23);
        City city4 = new City("city4", 10, 16);
        City city5 = new City("city5", 27, 14);
        City city6 = new City("city6", 20, 20);
        City city7 = new City("city7", 20, 25);
        City city8 = new City("city8", 5, 30);
        City city9 = new City("city9", 9, 9);
        City cityNull = null;

        db.insert(city1);
        db.insert(city2);
        db.insert(city3);
        db.insert(city4);
        db.insert(city5);
        db.insert(city6);
        db.insert(city7);
        db.insert(city8);
        db.insert(city9);

        assertFuzzyEquals(db.infoXY(0, 0), "");
        assertFuzzyEquals(db.infoXY(5, 30), "city8");
        assertFuzzyEquals(db.infoXY(10, 16), "city4");
        assertFuzzyEquals(db.infoXY(20, 14), "");
        assertFuzzyEquals(db.infoXY(14, 20), "");
    }


    // ----------------------------------------------------------
    /**
     * Tests the Search function
     */
    public void testSearch() {
        City city1 = new City("city1", 12, 16);
        City city2 = new City("city2", 6, 18);
        City city3 = new City("city3", 15, 23);
        City city4 = new City("city4", 10, 16);
        City city5 = new City("city5", 27, 14);
        City city6 = new City("city6", 20, 20);
        City city7 = new City("city7", 20, 25);
        City city8 = new City("city8", 5, 30);
        City city9 = new City("city9", 9, 9);

        db.insert(city1);
        db.insert(city2);
        db.insert(city3);
        db.insert(city4);
        db.insert(city5);
        db.insert(city6);
        db.insert(city7);
        db.insert(city8);
        db.insert(city9);

        assertFuzzyEquals(db.search(5, 10, -1), "");
        assertFuzzyEquals(db.search(20, 20, 1), "city6 (20, 20)\n4");
        assertFuzzyEquals(db.search(20, 20, 5),
            "city6 (20, 20)\n city7 (20, 25)\n 5");
        assertFuzzyEquals(db.search(20, 20, 0), "city6 (20, 20)\n 1");
        assertFuzzyEquals(db.search(15, 15, 0), "");

        KDTree<City> db2 = new KDTree<City>();

        assertFuzzyEquals(db2.search(0, 0, 0), "");
    }


    // ----------------------------------------------------------
    /**
     * Tests the clear function on a filled tree.
     */
    public void testClear() {
        City city1 = new City("city1", 12, 16);
        City city2 = new City("city2", 6, 18);
        City city3 = new City("city3", 15, 23);
        City city4 = new City("city4", 10, 16);
        City city5 = new City("city5", 27, 14);
        City city6 = new City("city6", 20, 20);
        City city7 = new City("city7", 20, 25);

        db.insert(city1);
        db.insert(city2);
        db.insert(city3);
        db.insert(city4);
        db.insert(city5);
        db.insert(city6);
        db.insert(city7);

        assertFuzzyEquals(db.inorder(), multiline("2    city4 10 16",
            "1  city2 6 18", "0city1 12 16", "3      city6 20 20",
            "2    city5 27 14", "1  city3 15 23", "2    city7 20 25"));

        db.clear();

        assertFuzzyEquals(db.inorder(), "");
    }
}
