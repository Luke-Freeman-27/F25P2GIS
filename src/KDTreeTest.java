import student.TestCase;

/**
 * @author walkerberndt, Luke-Freeman-27
 * @version Oct 2, 2025
 */
public class KDTreeTest extends TestCase {

    private KDTree<City> db;
    private KDTree<City> tree;

    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        db = new KDTree<City>();
        tree = new KDTree<>();
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
        assertFuzzyEquals(db.delete(10, 16), "3, \n city4");
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

        assertFuzzyEquals(db.delete(12, 16), "8 \n city1");
        assertFuzzyEquals(db.delete(20, 25), "7 \n city7");
    }


    /**
     * Deletes a leaf node from the tree.
     */
    public void testDelete_LeafNode() {
        City c1 = new City("A", 10, 10);
        City c2 = new City("B", 20, 20);
        City c3 = new City("C", 5, 5);
        db.insert(c1);
        db.insert(c2);
        db.insert(c3);

        String result = db.delete(5, 5);
        assertFuzzyEquals(result, "2\nC");

        // Confirm it's deleted
        assertFuzzyEquals(db.infoXY(5, 5), "");
    }


    /**
     * Deletes a node with only right child.
     */
    public void testDelete_OnlyRightChild() {
        City c1 = new City("A", 10, 10);
        City c2 = new City("B", 20, 20);
        db.insert(c1);
        db.insert(c2);

        String result = db.delete(10, 10);
        assertFuzzyEquals(result, "3\nA");

        assertFuzzyEquals(db.infoXY(10, 10), "");
        assertFuzzyEquals(db.infoXY(20, 20), "B");
    }


    /**
     * Deletes a node with only left child.
     */
    public void testDelete_OnlyLeftChild() {
        City c1 = new City("A", 20, 20);
        City c2 = new City("B", 10, 10);
        db.insert(c1);
        db.insert(c2);

        String result = db.delete(20, 20);
        assertFuzzyEquals(result, "3\nA");

        assertFuzzyEquals(db.infoXY(20, 20), "");
        assertFuzzyEquals(db.infoXY(10, 10), "B");
    }


    /**
     * Deletes a node with two children.
     */
    public void testDelete_TwoChildren() {
        City c1 = new City("A", 30, 40);
        City c2 = new City("B", 20, 30);
        City c3 = new City("C", 40, 50);
        db.insert(c1);
        db.insert(c2);
        db.insert(c3);

        String result = db.delete(30, 40);
        assertTrue(result.contains("A"));
        assertFuzzyEquals(result.split("\n")[1], "A");

        assertFuzzyEquals(db.infoXY(30, 40), ""); // A should be gone
        assertFuzzyEquals(db.infoXY(40, 50), "C");
        assertFuzzyEquals(db.infoXY(20, 30), "B");
    }


    /**
     * Deletes the root node repeatedly until tree is empty.
     */
    public void testDelete_RootUntilEmpty() {
        City c1 = new City("Root", 50, 50);
        City c2 = new City("L", 25, 25);
        City c3 = new City("R", 75, 75);
        db.insert(c1);
        db.insert(c2);
        db.insert(c3);

        assertTrue(db.delete(50, 50).contains("Root"));
        assertTrue(db.delete(25, 25).contains("L"));
        assertTrue(db.delete(75, 75).contains("R"));

        assertFuzzyEquals(db.infoXY(50, 50), "");
        assertFuzzyEquals(db.delete(50, 50), "");
    }


    /**
     * Attempt to delete a city not in the tree.
     */
    public void testDelete_NonExistentCity() {
        City c1 = new City("A", 10, 10);
        City c2 = new City("B", 20, 20);
        db.insert(c1);
        db.insert(c2);

        String result = db.delete(15, 15);
        assertEquals(result, "");
    }


    /**
     * Deletes node requiring minimum search in different dimension.
     */
    public void testDelete_MinSearchDifferentDimension() {
        db.insert(new City("A", 30, 40)); // root
        db.insert(new City("B", 20, 10)); // left
        db.insert(new City("C", 40, 60)); // right
        db.insert(new City("D", 25, 70)); // left-right
        db.insert(new City("E", 35, 50)); // right-left

        String result = db.delete(30, 40);
        assertTrue(result.contains("A"));

        // Confirm A is gone
        assertFuzzyEquals(db.infoXY(30, 40), "");

        // Check if E still exists
        assertFuzzyEquals(db.infoXY(35, 50), "E");
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


    /**
     * Tests the info function with coordinates not in the tree.
     */
    public void testInfoXY_NotInTree() {
        City city1 = new City("A", 10, 10);
        City city2 = new City("B", 20, 20);
        db.insert(city1);
        db.insert(city2);

        assertFuzzyEquals(db.infoXY(15, 15), "");
        assertFuzzyEquals(db.infoXY(0, 0), "");
        assertFuzzyEquals(db.infoXY(100, 100), "");
    }


    /**
     * Tests the info function with multiple cities having same x or y values.
     */
    public void testInfoXY_SameXorY() {
        City city1 = new City("A", 10, 10);
        City city2 = new City("B", 10, 20);
        City city3 = new City("C", 20, 10);
        db.insert(city1);
        db.insert(city2);
        db.insert(city3);

        assertFuzzyEquals(db.infoXY(10, 10), "A");
        assertFuzzyEquals(db.infoXY(10, 20), "B");
        assertFuzzyEquals(db.infoXY(20, 10), "C");
        assertFuzzyEquals(db.infoXY(20, 20), "");
    }


    /**
     * Tests infoXY with only one city inserted.
     */
    public void testInfoXY_OneCity() {
        City city1 = new City("SoloCity", 7, 7);
        db.insert(city1);

        assertFuzzyEquals(db.infoXY(7, 7), "SoloCity");
        assertFuzzyEquals(db.infoXY(7, 8), "");
    }


    /**
     * Tests infoXY with duplicate coordinates (should return the first inserted
     * one).
     */
    public void testInfoXY_DuplicateCoordinates() {
        City city1 = new City("CityOne", 5, 5);
        City city2 = new City("CityTwo", 5, 5);
        db.insert(city1);
        db.insert(city2); // Duplicate coordinates

        assertFuzzyEquals(db.infoXY(5, 5), "CityOne"); // Should return the
                                                       // first one inserted
    }


    /**
     * Tests infoXY with an empty database.
     */
    public void testInfoXY_EmptyDB() {
        assertFuzzyEquals(db.infoXY(0, 0), "");
        assertFuzzyEquals(db.infoXY(100, 100), "");
    }


    /**
     * Tests infoXY with cities inserted in a pattern to force deeper tree
     * levels.
     */
    public void testInfoXY_DeepTree() {
        db.insert(new City("A", 30, 40));
        db.insert(new City("B", 5, 25));
        db.insert(new City("C", 70, 70));
        db.insert(new City("D", 10, 12));
        db.insert(new City("E", 50, 30));
        db.insert(new City("F", 35, 45));

        assertFuzzyEquals(db.infoXY(70, 70), "C");
        assertFuzzyEquals(db.infoXY(35, 45), "F");
        assertFuzzyEquals(db.infoXY(50, 30), "E");
        assertFuzzyEquals(db.infoXY(11, 11), "");
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
        assertFuzzyEquals(db.search(20, 20, 0), "city6 (20, 20)\n 4");
        assertFuzzyEquals(db.search(15, 15, 0), "4");

        KDTree<City> db2 = new KDTree<City>();

        assertFuzzyEquals(db2.search(0, 0, 0), "0");
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
