// -------------------------------------------------------------------------
/**
 * Write a one-sentence summary of your class here.
 * Follow it with additional details about its purpose, what abstraction
 * it represents, and how to use it.
 * 
 * @author walkerberndt, lukefreeman
 * @version Oct 1, 2025
 */
public class BST<T> {
    private BSTNode<T> root; // Root of the BST
    private int nodecount; // Number of nodes in the BST

    // constructor
    // ----------------------------------------------------------
    /**
     * Create a new BST object.
     */
    BST() {
        root = null;
        nodecount = 0;
    }


    // Reinitialize tree
    // ----------------------------------------------------------
    /**
     * empties the BST
     */
    public void clear() {
        root = null;
        nodecount = 0;
    }


    // Insert a record into the tree.
    // Records can be anything, but they must be Comparable
    // e: The record to insert.
    public void insert(Comparable e) {
        root = inserthelp(root, e);
        nodecount++;
    }


    // Remove a record from the tree
    // key: The key value of record to remove
    // Returns the record removed, null if there is none.
    public Comparable remove(Comparable key) {
        Comparable temp = findhelp(root, key); // First find it
        if (temp != null) {
            root = removehelp(root, key); // Now remove it
            nodecount--;
        }
        return temp;
    }


    // Return the record with key value k, null if none exists
    // key: The key value to find
    public Comparable find(Comparable key) {
        return findhelp(root, key);
    }


    // Return the number of records in the dictionary
    public int size() { return nodecount; }
