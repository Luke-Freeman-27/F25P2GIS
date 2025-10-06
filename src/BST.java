// -------------------------------------------------------------------------
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
     * Deletes a node from BST based on the name
     * 
     * @param name
     *            is the given name of the node that is to be deleted
     */
    public BSTNode<T> deleteName(String name) {
        return deleteNameHelp(root, name);
    }


    /**
     * Deletes a node form the BST based on the coordinates
     * 
     * @param x
     *            is the x coordinate of the node
     * @param y
     *            is the y coordinate of the node
     */
    public BSTNode<T> deleteCoords(int x, int y) {
        return deleteCoordsHelp(root, x, y);
    }


    /**
     * Deletes a node from the BST tree
     * 
     * @param node
     *            is the node that is being checked
     * @param name
     *            the string that is being compared to
     * @return
     */
    private BSTNode<T> deleteNameHelp(BSTNode<T> node, String name) {
        if (node == null) {
            return null;
        }

        // Cast current node element to City to access its name
        City currentCity = (City)node.getElement();
        City otherCity = new City(name, 0, 0); // temporary key for comparison

        if (currentCity.getName().compareToIgnoreCase(otherCity
            .getName()) > 0) {
            node.setLeft(deleteNameHelp(node.getLeft(), name));
        }

        else if (currentCity.getName().compareToIgnoreCase(otherCity
            .getName()) < 0) {
            node.setRight(deleteNameHelp(node.getRight(), name));
        }

        else {
            if (node.getLeft() == null) {
                return node.getRight();
            }
            else if (node.getRight() == null) {
                return node.getLeft();
            }
            else {
                BSTNode<T> temp = getMax(node.getLeft());
                node.setElement(temp.getElement());
                node.setLeft(deleteMax(node.getLeft()));
            }
        }
        return node;

    }


    /**
     * Deletes a node from the BST tree
     * 
     * @param node
     *            is the node that is being checked
     * @param x
     *            the x coordinate of the given node
     * @param y
     *            the y coordinate of the given node
     * @return
     */
    private BSTNode<T> deleteCoordsHelp(BSTNode<T> node, int x, int y) {
        if (node == null) {
            return null;
        }

        // Cast current node element to City to access its name
        City currentCity = (City)node.getElement();
        City otherCity = new City("", x, y); // temporary key for comparison

        if (currentCity.getName().compareToIgnoreCase(otherCity
            .getName()) > 0) {
            node.setLeft(deleteCoordsHelp(node.getLeft(), x, y));
        }

        else if (currentCity.getName().compareToIgnoreCase(otherCity
            .getName()) < 0) {
            node.setRight(deleteCoordsHelp(node.getRight(), x, y));
        }

        else {
            if (node.getLeft() == null) {
                return node.getRight();
            }
            else if (node.getRight() == null) {
                return node.getLeft();
            }
            else {
                BSTNode<T> temp = getMax(node.getLeft());
                node.setElement(temp.getElement());
                node.setLeft(deleteMax(node.getLeft()));
            }
        }
        return node;

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


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        inorder(root, sb);
        return sb.toString().trim(); // remove trailing space
    }


    private void inorder(BSTNode<T> node, StringBuilder sb) {
        if (node == null) {
            return;
        }

        // Left subtree
        inorder(node.getLeft(), sb);

        // Visit node
        sb.append(((City)node.getElement()).getName()).append(" ");

        // Right subtree
        inorder(node.getRight(), sb);
    }
}
