// -------------------------------------------------------------------------
/**
 * A basic node that stores an element for a binary search tree.
 * 
 * @author walkerberndt, Luke-Freeman-27
 * @version Oct 1, 2025
 */
public class BSTNode<T> {
    // ~ Fields ................................................................
    /**
     * @param Element
     *            is the value that is being stored in the node
     * @param left
     *            is the non-null node left leaf
     * @param right
     *            is the non-null node right leaf
     */
    private T element;
    private BSTNode<T> left;
    private BSTNode<T> right;
    // ~ Constructors ..........................................................

    /**
     * Creates a node with no children.
     * 
     * @param theElement
     *            the element to store in this node.
     */
    BSTNode(T theElement) {
        element = theElement;
        left = null;
        right = null;
    }


    // ~Public Methods ........................................................
    // ----------------------------------------------------------
    /**
     * Get the current data value stored in this node.
     * 
     * @return the element
     */
    public T getElement() {
        return element;
    }


    // ----------------------------------------------------------
    /**
     * Set the data value stored in this node.
     * 
     * @param value
     *            the new data value to set
     */
    public void setElement(T value) {
        element = value;
    }


    // ----------------------------------------------------------
    /**
     * Get the left child of this node.
     * 
     * @return a reference to the left child.
     */
    public BSTNode<T> getLeft() {
        return left;
    }


    // ----------------------------------------------------------
    /**
     * Set this node's left child.
     * 
     * @param value
     *            the node to point to as the left child.
     */
    public void setLeft(BSTNode<T> value) {
        left = value;
    }


    // ----------------------------------------------------------
    /**
     * Get the right child of this node.
     * 
     * @return a reference to the right child.
     */
    public BSTNode<T> getRight() {
        return right;
    }


    // ----------------------------------------------------------
    /**
     * Set this node's right child.
     * 
     * @param value
     *            the node to point to as the right child.
     */
    public void setRight(BSTNode<T> value) {
        right = value;
    }

}
