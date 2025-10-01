/**
 * City class for the implementation of a BST tree.
 * 
 * @author walkerberndt, Luke-Freeman-27
 * @version Oct 1, 2025
 */

public class City {
    // ~ Fields ................................................................
    /**
     * @param Name
     *            is the given name for the city
     * @param x
     *            is the x-cordinate value for the location of the city
     * @param y
     *            is the y-cordinate value for the location of the city
     */
    private String name;
    private int x;
    private int y;

    // ~ Constructors ..........................................................

    /**
     * Create a new city object
     * 
     * @param
     */
    public City(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }


    // ~Public Methods ........................................................
    /**
     * Getter method for the name of the city
     */
    public String getName() {
        return name;
    }


    /**
     * Getter method for the x-cordinate value
     */
    public int getX() {
        return x;
    }


    /**
     * Getter method for the y-cordinate value
     */
    public int getY() {
        return y;
    }


    /**
     * Setter method for the name of the city
     */
    public void setName(String newName) {
        name = newName;
    }


    /**
     * Setter method for the x-cordinate value
     */
    public void setX(int newX) {
        x = newX;
    }


    /**
     * Setter method for the y-cordinate value
     */
    public void setY(int newY) {
        y = newY;
    }
}
