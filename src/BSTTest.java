import student.TestCase;

/**
 * The purpose of this class is to test the BSTNode class.
 * 
 * @author walkerberndt, lukefreeman
 * @version Oct 1, 2025
 */
public class BSTTest extends TestCase {
    private BST<City> it;  // ✅ specify type parameter
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
        it = new BST<City>();  // ✅ diamond operator (inferred as BST<Integer>)
        city1 = new City("Washington", 10, 10);
        city2 = new City("New York", 15, 20);
        city3 = new City("Los Angeles", 30, 5);
        city4 = new City("Chicago", 12, 25);
        city5 = new City("Houston", 20, 15);
        city6 = new City("Phoenix", 25, 8);
        city7 = new City("Philadelphia", 18, 22);
        city8 = new City("San Antonio", 22, 12);
        city9 = new City("San Diego", 28, 10);
        city10 = new City("Dallas", 19, 17);
        city11 = new City("San Jose", 26, 7);
        city12 = new City("Austin", 21, 14);
        city13 = new City("Jacksonville", 16, 19);
        city14 = new City("Fort Worth", 23, 13);
        city15 = new City("Columbus", 14, 21);
    }

    /**
     * Test the insert method
     */
    public void testInsert() {
        it.insert(city1);
        it.insert(city2);
        it.insert(city3);
        it.insert(city4);
        it.insert(city5);
        assertFuzzyEquals(it.toString(), "chicago houston los angeles new york washington");
    }
    
    /**
     * Test the delete method
     */
    public void testDeleteName() {
        it.insert(city3);       // Los Angles
        it.insert(city5);       // Houston
        it.insert(city2);       // New York
        it.insert(city1);       // Washington
        it.insert(city13);      // Jacksonville
        it.insert(city13);      // Jacksonville twice
        it.insert(city12);      // Austin
        assertFuzzyEquals(it.toString(), "Austin Houston Jacksonville Jacksonville Los Angeles New York Washington");
        it.deleteName("Washington");
        assertFuzzyEquals(it.toString(), "Austin Houston Jacksonville Jacksonville Los Angeles New York");
        it.deleteName("Austin");
        assertFuzzyEquals(it.toString(), "Houston Jacksonville Jacksonville Los Angeles New York");
        it.deleteName("Jacksonville");
        assertFuzzyEquals(it.toString(), "Houston Jacksonville Los Angeles New York");
    }
    
    /**
     * Test the delete method
     */
    public void testDeleteCoords() {
        it.insert(city3);       // Los Angles
        it.insert(city5);       // Houston
        it.insert(city2);       // New York
        it.insert(city1);       // Washington
        it.insert(city13);      // Jacksonville
        it.insert(city13);      // Jacksonville twice
        it.insert(city12);      // Austin
        assertFuzzyEquals(it.toString(), "Austin Houston Jacksonville Jacksonville Los Angeles New York Washington");
        it.deleteCoords(10, 10);
        //assertFuzzyEquals(it.toString(), "Austin Houston Jacksonville Jacksonville Los Angeles New York");
        it.deleteCoords(21, 14);
        //assertFuzzyEquals(it.toString(), "Houston Jacksonville Jacksonville Los Angeles New York");
        it.deleteCoords(16, 19);
        //assertFuzzyEquals(it.toString(), "Houston Jacksonville Los Angeles New York");
    }
}
