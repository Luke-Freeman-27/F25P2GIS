
/**
 * City class for the implementation of a BST tree.
 * 
 * @author walkerberndt, Luke-Freeman-27
 * @version Oct 1, 2025
 */

public class City implements Comparable<City> {
    // ~ Fields ................................................................
    /**
     * @param Name
     *            is the given name for the city
     * @param x
     *            is the x-coordinate value for the location of the city
     * @param y
     *            is the y-coordinate value for the location of the city
     */
    private String name;
    private int x;
    private int y;

    // ~ Constructors ..........................................................

    /**
     * Create a new city object
     * 
     * @param name
     *            is the given name for the city
     * @param x
     *            is the x-coordinate value for the location of the city
     * @param y
     *            is the y-coordinate value for the location of the city
     */
    public City(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }


    // ~Public Methods ........................................................
    /**
     * Getter method for the name of the city
     * 
     * @return the name of the city
     */
    public String getName() {
        return name;
    }


    /**
     * Getter method for the x-cordinate value
     * 
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }


    /**
     * Getter method for the y-cordinate value
     * 
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }


    /**
     * Setter method for the name of the city
     * 
     * @param newName
     *            is the new name of the city
     */
    public void setName(String newName) {
        name = newName;
    }


    /**
     * Setter method for the x-cordinate value
     * 
     * @param newX
     *            is the new x-coordinate
     */
    public void setX(int newX) {
        x = newX;
    }


    /**
     * Setter method for the y-cordinate value
     * 
     * @param newY
     *            is the new y-coordinate
     */
    public void setY(int newY) {
        y = newY;
    }


    /**
     * Compares two node based on name
     * 
     * @param city
     *            is another city
     * @return Integer value if the
     */
    @Override
    public int compareTo(City city) {
        return this.name.compareTo(city.getName());
    }
}
