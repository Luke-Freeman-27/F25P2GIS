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
        assertFuzzyEquals(db.inorder(), multiline("2    city4 10 16", "1  city2 6 18",
            "0city1 12 16", "2    city5 27 14", "1  city3 15 23"));

        db.insert(city6);
        db.insert(city7);

        assertFuzzyEquals(db.inorder(), multiline("2    city4 10 16", "1  city2 6 18",
            "0city1 12 16", "3      city6 20 20", "2    city5 27 14", "1  city3 15 23",
            "2    city7 20 25"));

    }
    
    public void testDeleteRootFirst() {
        City city1 = new City("city1", 12, 16);
        City city2 = new City("city2", 6, 18);
        City city3 = new City("city3", 15, 23);
        City city4 = new City("city4", 10, 16);
        City city5 = new City("city5", 27, 14);
        City city6 = new City("city6", 20, 20);
        City city7 = new City("city7", 20, 25);
        City cityNull = null;
        
        db.insert(city1);
        db.insert(city2);
        db.insert(city3);
        db.insert(city4);
        db.insert(city5);
        db.insert(city6);
        db.insert(city7);
        
        assertEquals(db.delete(city1), city1);
        assertEquals(db.delete(city3), city3);
        assertEquals(db.delete(cityNull), null);
        assertEquals(db.delete(city7), city7);
        assertEquals(db.delete(city5), city5);
    }
    
    public void testDeleteRootLast() {
        City city1 = new City("city1", 12, 16);
        City city2 = new City("city2", 6, 18);
        City city3 = new City("city3", 15, 23);
        City city4 = new City("city4", 10, 16);
        City city5 = new City("city5", 27, 14);
        City city6 = new City("city6", 20, 20);
        City city7 = new City("city7", 20, 25);
        City city8 = new City("city8", 5, 30);
        City city9 = new City ("city9", 9, 9);
        City cityNull = null;
        
        db.insert(city1);
        db.insert(city2);
        db.insert(city3);
        db.insert(city4);
        db.insert(city5);
        db.insert(city6);
        db.insert(city7);
        db.insert(city8);
        
        assertEquals(db.delete(cityNull), null);
        
        assertEquals(db.delete(city7), city7);
        assertEquals(db.delete(city4), city4);
        assertEquals(db.delete(city6), city6);
        assertEquals(db.delete(city5), city5);
        assertEquals(db.delete(city3), city3);
        assertEquals(db.delete(city1), city1);
        assertEquals(db.delete(city9), null);
        assertFuzzyEquals(db.delete(10, 16), "");
        assertFuzzyEquals(db.delete(6, 18), "6 18 city2");
    }
    
    public void testDeleteName() {
        City city1 = new City("city1", 12, 16);
        City city2 = new City("city2", 6, 18);
        City city3 = new City("city3", 15, 23);
        City city4 = new City("city4", 10, 16);
        City city5 = new City("city5", 27, 14);
        City city6 = new City("city6", 20, 20);
        City city7 = new City("city7", 20, 25);
        City city8 = new City("city8", 5, 30);
        City city9 = new City ("city9", 9, 9);
        City cityNull = null;
        
        db.insert(city1);
        db.insert(city2);
        db.insert(city3);
        db.insert(city4);
        db.insert(city5);
        db.insert(city6);
        db.insert(city7);
        db.insert(city8);
        
        assertFuzzyEquals(db.delete("cityNull"), "");
        assertFuzzyEquals(db.delete("city9"), "");
        assertFuzzyEquals(db.delete("city4"), "10 16");
    }
}
