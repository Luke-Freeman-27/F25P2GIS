import student.TestCase;

/**
 * The purpose of this class is to test the BSTNode class.
 * 
 * @author walkerberndt, lukefreeman
 * @version Oct 1, 2025
 */
public class BSTTest extends TestCase {
    private BST<City> it; // ✅ specify type parameter
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
        assertFuzzyEquals(it.toString(),
            "Chicago Dallas Denver Houston Las Vegas");
    }


    /**
     * Test the delete method
     */
    public void testDeleteName() {
        assertNull(it.deleteName(null));
        it.insert(city8);
        it.insert(city4);
        it.insert(city12);
        it.insert(city2);
        it.insert(city6);
        it.insert(city10);
        it.insert(city14);
        it.insert(city1);
        it.insert(city3);
        it.insert(city5);
        it.insert(city7);
        it.insert(city9);
        it.insert(city11);
        it.insert(city13);
        it.insert(city15);
        it.deleteName("Atlanta");   // Case D1: leaf, no children
        it.deleteName("Boston");    // Case D2: one child
        it.deleteName("Dallas");    // Case D3: two children
        it.deleteName("Las Vegas"); // Case D3 again (root or deep)
        it.deleteName("Washington");// Case D2 or leaf at end
        it.deleteName("Miami");     // Forces traversal both sides
    }

    /**
     * Test the delete method
     */
// public void testDeleteCoords() {
// it.insert(city3); // Los Angles
// it.insert(city5); // Houston
// it.insert(city2); // New York
// it.insert(city1); // Washington
// it.insert(city13); // Jacksonville
// it.insert(city13); // Jacksonville twice
// it.insert(city12); // Austin
// assertFuzzyEquals(it.toString(), "Austin Houston Jacksonville Jacksonville
// Los Angeles New York Washington");
// it.deleteCoords(10, 10);
// //assertFuzzyEquals(it.toString(), "Austin Houston Jacksonville Jacksonville
// Los Angeles New York");
// it.deleteCoords(21, 14);
// //assertFuzzyEquals(it.toString(), "Houston Jacksonville Jacksonville Los
// Angeles New York");
// it.deleteCoords(16, 19);
// //assertFuzzyEquals(it.toString(), "Houston Jacksonville Los Angeles New
// York");
// }
}
