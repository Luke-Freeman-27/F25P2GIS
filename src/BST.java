// -------------------------------------------------------------------------import
// sun.tools.tree.ThisExpression;


/**
 * This class is a basic implementation of a binary search tree. The end purpose
 * of this class is to be used in conjunction with a k-d tree.
 * 
 * @author walkerberndt, lukefreeman
 * @version Oct 1, 2025
 */
public class BST<T extends Comparable<? super T>> {

    // ~ Fields ................................................................
    /**
     * @param root
     *            is the root of the BST
     */
    private BSTNode<T> root;

    // ~ Constructors ..........................................................
    /**
     * Create a new BST object.
     */
    BST() {
        root = null;
    }


    // ~Public Methods ........................................................
    /**
     * Inserts a new node into a BST with a helper method
     * 
     * @param obj
     *            is the new value added to the BST
     */
    public void insert(T obj) {
        root = insertHelper(root, obj);
    }


    /**
     * Inserts a new node into a BST recursively
     * 
     * @param node
     *            is the node we are comparing the value to
     * @param obj
     *            is the new value added to the BST
     * @return node that the value was added to
     */
    private BSTNode<T> insertHelper(BSTNode<T> node, T obj) {
        // If node is empty, make a new node
        if (node == null) {
            return new BSTNode<T>(obj);
        }

        // If x is less than the current node value, set node to the left
        else if (obj.compareTo(node.getElement()) <= 0) {
            node.setLeft(insertHelper(node.getLeft(), obj));
        }

        // If x is greater than the current node value, set node to the right
        else {
            node.setRight(insertHelper(node.getRight(), obj));
        }

        return node;
    }


    /**
     * Find the city of the given x and y values
     * 
     * @param x
     *            is the x coordinate value
     * @param y
     *            is the y coordinate value
     * @return true/false if the node was found or not
     */
    public boolean findXY(int x, int y) {
        return findXYHelper(root, x, y);
    }


    /**
     * Helper method to find the city of the given x and y values
     * 
     * @param node
     * @param x
     *            is the x coordinate value
     * @param y
     *            is the y coordinate value
     * @return true/false if the node was found or not
     */
    private boolean findXYHelper(BSTNode<T> node, int x, int y) {
        // If node not found return null
        if (node == null) {
            return false;
        }

        // Checks to ensure that this is of the city class
        T element = node.getElement();
        if (element instanceof City) {
            City city = (City)element;

            // Found a city with matching coordinates
            if (city.getX() == x && city.getY() == y) {
                return true;
            }
        }

        // Iterate through the entire tree to search for the given node location
        return findXYHelper(node.getLeft(), x, y) || findXYHelper(node
            .getRight(), x, y);
    }


    /**
     * Find the city given a name
     * 
     * @param name
     *            is the name of the city
     * @return the number of instances of the name
     */
    public int findName(String name) {
        return findNameHelper(root, name);
    }


    /**
     * Helper method to find the city given a name
     * 
     * @param node
     * @param name
     *            is the name of the city
     * @return the number of instances found
     */
    private int findNameHelper(BSTNode<T> node, String name) {
        // Base case: empty subtree
        if (node == null) {
            return 0;
        }

        int count = 0;

        // Check if the node element is a City
        T element = node.getElement();
        if (element instanceof City) {
            City city = (City)element;

            // Compare names using .equals or .equalsIgnoreCase
            if (city.getName().equalsIgnoreCase(name)) {
                count = 1;
            }
        }

        // Recurse into left and right subtrees and add results
        count += findNameHelper(node.getLeft(), name);
        count += findNameHelper(node.getRight(), name);

        return count;
    }


    /**
     * Deletes a node from BST based on X & Y coordinate
     * 
     * @param x
     *            is the x coordinate
     * @param y
     *            is the y coordinate
     * @return true/false if the node has been deleted
     */
    public boolean deleteXY(int x, int y) {
        if (this.findXY(x, y)) {
            root = deleteXYHelper(root, x, y);
            return true;
        }
        return false;
    }


    /**
     * Helper method that deletes
     * 
     * @param node
     *            is the given node checked for the correct coordinate
     * @param x
     *            is the x coordinate
     * @param y
     *            is the y coordinate
     */
    public BSTNode<T> deleteXYHelper(BSTNode<T> node, int x, int y) {
        if (node == null) {
            return null;
        }
        City city = (City)node.getElement();

        // Navigate the BST by comparing coordinates
        if (x < city.getX() || (x == city.getX() && y < city.getY())) {
            node.setLeft(deleteXYHelper(node.getLeft(), x, y));
        }
        else if (x > city.getX() || (x == city.getX() && y > city.getY())) {
            node.setRight(deleteXYHelper(node.getRight(), x, y));
        }
        else {
            // Found the node to delete
            if (node.getLeft() == null) {
                return node.getRight();
            }
            else if (node.getRight() == null) {
                return node.getLeft();
            }
            else {
                // Two children: replace with max of left subtree
                BSTNode<T> maxNode = getMax(node.getLeft());
                node.setElement(maxNode.getElement());
                node.setLeft(deleteMax(node.getLeft()));
            }
        }
        return node;
    }


    /**
     * Delete
     */
    public String deleteName(String name) {
        return deleteNameHelper(root, name).trim();
    }


    /**
     * Deletes all city nodes that a specific name
     * 
     * @param node
     *            is the given node being checked
     * @param name
     *            is the name of the city
     * @return A string of the coordinates deleted by the delete method.
     */
    private String deleteNameHelper(BSTNode<T> node, String name) {
        if (node == null) {
            return "";
        }

        StringBuilder deleted = new StringBuilder();
        City city = (City) node.getElement();

        // If city name matches, delete and record coordinates
        if (city.getName().equalsIgnoreCase(name)) {
            deleted.append("(")
                   .append(city.getX())
                   .append(", ")
                   .append(city.getY())
                   .append(") ");
            deleteXY(city.getX(), city.getY());
        }
        // Traverse left and right subtrees (preorder: process → left → right)
        deleted.append(deleteNameHelper(node.getLeft(), name));
        deleted.append(deleteNameHelper(node.getRight(), name));

        return deleted.toString();
    }


    /**
     * Delete the maximum value of an element in a subtree
     *
     * @param node
     *            is the node that is being checked
     * @return The node that is deleted
     */
    private BSTNode<T> deleteMax(BSTNode<T> node) {
        if (node.getRight() == null) {
            return node.getLeft();
        }
        node.setRight(deleteMax(node.getRight()));
        return node;
    }


    /**
     * Get the maximum value element in a subtree
     *
     * @param node
     *            is the node that is being checked
     * @return The max node in the subtree
     */
    private BSTNode<T> getMax(BSTNode<T> node) {
        if (node.getRight() == null) {
            return node;
        }
        return getMax(node.getRight());
    }


    /**
     * Clear method, restarts the tree
     */
    public void clear() {
        root = null;
    }


    /**
     * Lists the information for a given city name in the BST.
     * 
     * @param name
     *            The city name to search for.
     * @return
     *         A string listing all cities with that name and their coordinates,
     *         one per line in the format "City (x, y)".
     *         If no cities are found, returns "No city with this name exists."
     */
    public String infoName(String name) {
        StringBuilder result = new StringBuilder();
        infoNameHelp(root, name, result);

        if (result.length() == 0) {
            return "";
        }

        // Remove last newline if needed
        return result.toString().trim();
    }


    /**
     * Recursive helper to find and append all cities with the given name.
     */
    private void infoNameHelp(
        BSTNode<T> node,
        String name,
        StringBuilder result) {
        if (node == null) {
            return;
        }

        City city = (City)node.getElement();

        // Case-insensitive comparison
        if (city.getName().equalsIgnoreCase(name)) {
            result.append(city.getName()).append(" (").append(city.getX())
                .append(", ").append(city.getY()).append(")\n");
        }

        // Continue searching both sides (there can be duplicates)
        infoNameHelp(node.getLeft(), name, result);
        infoNameHelp(node.getRight(), name, result);
    }


    /**
     * Print a listing of the BST in alphabetical order on the names.
     * Each city printed on a separate line.
     * Each line starts with the level, then indented 2*level spaces.
     * Root is level 0.
     * 
     * @return String listing the cities as specified.
     */
    public String printBST() {
        if (root == null) {
            return ""; // Return empty string if BST is empty
        }

        StringBuilder sb = new StringBuilder();
        printBSTHelper(root, 0, sb);
        return sb.toString();
    }


    private void printBSTHelper(BSTNode<T> node, int level, StringBuilder sb) {
        if (node == null)
            return;

        // Inorder traversal: left, current, right
        printBSTHelper(node.getLeft(), level + 1, sb);

        // Append level to sb
        sb.append(level);

        // Indent based on level
        for (int i = 0; i < level * 2; i++) {
            sb.append(' ');
        }

        // Print level and city info
        City city = (City)node.getElement(); // cast generic T to City
        sb.append(city.getName()).append(" (").append(city.getX()).append(", ")
            .append(city.getY()).append(")").append("\n");

        printBSTHelper(node.getRight(), level + 1, sb);
    }
}
