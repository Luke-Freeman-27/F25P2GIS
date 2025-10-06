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
     * @param x
     *            is the new value added to the BST
     */
    public void insert(T x) {
        root = insertHelper(x, root);
    }
    

    /**
     * Inserts a new node into a BST recursively
     * 
     * @param x
     *            is the new value added to the BST
     * @param node
     *            is the node we are comparing the value to
     * @return node that the value was added to
     */
    private BSTNode<T> insertHelper(T x, BSTNode<T> node) {
        // If node is empty, make a new node
        if (node == null) {
            return new BSTNode<T>(x);
        }

        // If x is less than the current node value, set node to the left
        else if (x.compareTo(node.getElement()) <= 0) {
            node.setLeft(insertHelper(x, node.getLeft()));
        }

        // If x is greater than the current node value, set node to the right
        else {
            node.setRight(insertHelper(x, node.getRight()));
        }

        return node;
    }

    /**
     * 
     * @param x
     */
    public void delete(T x) {
        root = removeHelp(root, x);
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
     * Deletes a node from the BST tree
     * 
     * @param node
     *            is the node that is being checked
     * @param key
     *            the value that is being compared to
     * @return
     */
    private BSTNode<T> removeHelp(BSTNode<T> node, T x) {
        if (node == null) {
            return null;
        }

        if (node.getElement().compareTo(x) > 0) {
            node.setLeft(removeHelp(node.getLeft(), x));
        }

        else if (node.getElement().compareTo(x) < 0) {
            node.setRight(removeHelp(node.getRight(), x));
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
