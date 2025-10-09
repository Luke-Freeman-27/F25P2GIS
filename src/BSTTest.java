import student.TestCase;

/**
 * The purpose of this class is to test the BSTNode class.
 * 
 * @author walkerberndt, lukefreeman
 * @version Oct 1, 2025
 */
public class BSTTest extends TestCase {
    private BST<City> it; // ✅ specify type parameter
    private BST<String> stringBST;
    private City city1;
    private City city2;
    private City city3;
    private City city4;
    private City city5;
    private City city6;
    private City city7;
    private City city8;
    private City city9;
    private City city10;
    private City city11;
    private City city12;
    private City city13;
    private City city14;
    private City city15;

    /**
     * Sets up the tests that follow.
     */
    public void setUp() {
        it = new BST<City>(); // ✅ diamond operator (inferred as BST<Integer>)
        stringBST = new BST<String>(); // String BST for edge cases
        city1 = new City("Atlanta", 10, 10);
        city2 = new City("Boston", 20, 15);
        city3 = new City("Chicago", 30, 25);
        city4 = new City("Dallas", 40, 30);
        city5 = new City("Denver", 50, 35);
        city6 = new City("Houston", 60, 40);
        city7 = new City("Las Vegas", 70, 45);
        city8 = new City("Los Angeles", 80, 50);
        city9 = new City("Miami", 90, 55);
        city10 = new City("New York", 100, 60);
        city11 = new City("Philadelphia", 110, 65);
        city12 = new City("Phoenix", 120, 70);
        city13 = new City("San Francisco", 130, 75);
        city14 = new City("Seattle", 140, 80);
        city15 = new City("Washington", 150, 85);
    }


    /**
     * Test the insert method
     */
    public void testInsert() {
        it.insert(city5);
        it.insert(city4);
        it.insert(city6);
        it.insert(city3);
        it.insert(city7);
        assertFuzzyEquals(it.printBST(), "2    Chicago (30, 25)\n"
            + "1  Dallas (40, 30)\n" + "0Denver (50, 35)\n"
            + "1  Houston (60, 40)\n" + "2    Las Vegas (70, 45)");
    }


    /**
     * Tests the clear method
     */
    public void testClear() {
        it.insert(city5);
        it.insert(city4);
        it.insert(city6);
        it.insert(city3);
        it.insert(city7);
        assertFuzzyEquals(it.printBST(), "2    Chicago (30, 25)\n"
            + "1  Dallas (40, 30)\n" + "0Denver (50, 35)\n"
            + "1  Houston (60, 40)\n" + "2    Las Vegas (70, 45)");
        it.clear();
        assertFuzzyEquals(it.printBST(), "");

    }


    /**
     * This method checks for the location data of a given city node
     */
    public void testFindXY() {
        // Insert base of the BST
        it.insert(city5);
        it.insert(city4);
        it.insert(city6);
        assertFuzzyEquals(it.printBST(), "1  Dallas (40, 30)\n"
            + "0Denver (50, 35)\n" + "1  Houston (60, 40)");
        // Check for the node on the left and right
        assertTrue(it.findXY(40, 30));
        assertTrue(it.findXY(60, 40));
        // Values that are not in the tree
        assertFalse(it.findXY(60, 39));
        assertFalse(it.findXY(59, 40));
        assertFalse(it.findXY(100, 100));
        // Case where the BST is empty
        it.clear();
        assertFalse(it.findXY(0, 0));
        // Case where the BST is of a different instance
        stringBST.insert("Hello");
        stringBST.insert("Hi");
        stringBST.insert("Bye");
        assertFalse(stringBST.findXY(40, 30)); // Does not have city type
    }


    /**
     * This method checks for the name of a given city node
     */
    public void testFindName() {
        // Insert base of the BST
        City cityRepeat = new City("Los Angeles", 60, 70);
        it.insert(city9);
        it.insert(city8);
        it.insert(cityRepeat);
        it.insert(city10);
        assertFuzzyEquals(it.printBST(), "2    Los Angeles (60, 70)\n"
            + "1  Los Angeles (80, 50)\n" + "0Miami (90, 55)\n"
            + "1  New York (100, 60)");
        // Check for name on the left and right
        assertEquals(it.findName("Los Angeles"), 2);
        assertEquals(it.findName("Miami"), 1);
        assertEquals(it.findName("New York"), 1);
        // Cases where the BST is not found or empty
        assertEquals(it.findName("DC"), 0);
        it.clear();
        assertEquals(it.findName("Miami"), 0);
        // Cases where the BST is of a different instance
        stringBST.insert("Hello");
        stringBST.insert("Hi");
        stringBST.insert("Bye");
        assertEquals(stringBST.findName("Hello"), 0);
    }


    /**
     * This method checks for a location to delete a city node from
     */
    public void testDeleteXY() {
        // Insert city nodes
        it.insert(city13);
        it.insert(city12);
        it.insert(city14);
        it.insert(city11);
        it.insert(city15);
        assertEquals(it.printBST(), "2    Philadelphia (110, 65)\n"
            + "1  Phoenix (120, 70)\n" + "0San Francisco (130, 75)\n"
            + "1  Seattle (140, 80)\n" + "2    Washington (150, 85)\n");
        // Delete nodes at level 2
        assertTrue(it.deleteXY(110, 65));
        assertTrue(it.deleteXY(150, 85));
        assertFuzzyEquals(it.printBST(), "1  Phoenix (120, 70)\n"
            + "0San Francisco (130, 75)\n" + "1  Seattle (140, 80)\n");
        // Delete Root
        assertTrue(it.deleteXY(130, 75));
        assertFuzzyEquals(it.printBST(), "0Phoenix (120, 70)\n"
            + "1  Seattle (140, 80)\n");
        // Delete right leaf
        it.insert(city15);
        assertTrue(it.deleteXY(140, 80));
        assertFuzzyEquals(it.printBST(), "0Phoenix (120, 70)\n"
            + "1  Washington (150, 85)\n");
        // Delete left leaf
        it.insert(city14);
        it.insert(city13);
        assertTrue(it.deleteXY(150, 85));
        assertTrue(it.deleteXY(140, 80));
        assertEquals(it.printBST(), "0Phoenix (120, 70)\n"
            + "1  San Francisco (130, 75)\n");

        // Delete node with one leaf
        assertTrue(it.deleteXY(130, 75));
        assertEquals(it.printBST(), "0Phoenix (120, 70)\n");

        // Deleting a node with no location
        assertFalse(it.deleteXY(999, 999));
        assertFalse(it.deleteXY(110, 65));
    }

// /**
// * Test the delete method
// */
// public void testDeleteName() {
// assertNull(it.deleteName(null));
// it.insert(city8);
// it.insert(city4);
// it.insert(city12);
// it.insert(city2);
// it.insert(city6);
// it.insert(city10);
// it.insert(city14);
// it.insert(city1);
// it.insert(city3);
// it.insert(city5);
// it.insert(city7);
// it.insert(city9);
// it.insert(city11);
// it.insert(city13);
// it.insert(city15);
// it.deleteName("Atlanta"); // Case D1: leaf, no children
// it.deleteName("Boston"); // Case D2: one child
// it.deleteName("Dallas"); // Case D3: two children
// it.deleteName("Las Vegas"); // Case D3 again (root or deep)
// it.deleteName("Washington");// Case D2 or leaf at end
// it.deleteName("Miami"); // Forces traversal both sides
// }
}
