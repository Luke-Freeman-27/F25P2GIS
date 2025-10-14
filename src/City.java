
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
    
    /**
     * Checks equality between this city and another object.
     * Two cities are equal if they have the same name, x, and y coordinates.
     *
     * @param obj the object to compare with
     * @return true if the cities are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;  // same reference
        if (obj == null) return false; // null check
        if (!(obj instanceof City)) return false; // type check

        City other = (City) obj;
        return this.name.equals(other.name) && this.x == other.x && this.y == other.y;
    }
    
    /**
     * Compares two cities based on their coordinates (x first, then y).
     *
     * @param city the other city to compare
     * @return negative if this city comes before, positive if after, 0 if equal
     */
    public int compareToCoordinates(City city) {
        if (this.x != city.x) {
            return this.x - city.x; // compare by x-coordinate first
        } else {
            return this.y - city.y; // if x is equal, compare by y-coordinate
        }
    }
}
