// -------------------------------------------------------------------------
/**
 * This class is a basic implementation of a k-d tree. The end purpose
 * of this class is to be used in conjunction with a BST.
 * 
 * @author walkerberndt, lukefreeman
 * @version Oct 1, 2025
 * @param <T>
 */
public class KDTree<T> {
    private static class Node {
        City city;
        Node left;
        Node right;
        int depth;

        // Constructor for the private Node Class
        Node(City city, int depth) {
            this.city = city;
            this.depth = depth;
        }
    }

    // ----------------------------------------------------------
    /**
     * Create a new KDTree object. (Empty)
     */
    public KDTree() {
        this.root = null;
    }

    private Node root;

    /**
     * Insert a city into the KDTree
     * 
     * @param city
     *            - The data to be added into the node of the KDTree being
     *            added.
     */
    public void insert(City city) {
        if (city == null) {
            return;
        }
        root = insert(root, city, 0);
    }


    private Node insert(Node node, City city, int depth) {
        if (node == null) {
            return new Node(city, depth);
        }

        int cd = node.depth % 2; // current dimension: 0 = x, 1 = y

        if ((cd == 0 && city.getX() < node.city.getX()) || (cd == 1 && city
            .getY() < node.city.getY())) {
            node.left = insert(node.left, city, depth + 1);
        }
        else {
            node.right = insert(node.right, city, depth + 1);
        }

        return node;
    }


    /**
     * Pre-order traversal of the KDTree that prints the information in the
     * database according to the project specification.
     * 
     * Uses a private function nested within itself to allow for recursion to
     * print the full tree easily.
     * 
     * @return sb.toString - returns the string builder that is made through
     *         Pre-order recursion of the database.
     */
    public String preorder() {
        StringBuilder sb = new StringBuilder();
        preorder(root, 0, sb);
        return sb.toString();
    }


    private void preorder(Node node, int level, StringBuilder sb) {
        if (node == null)
            return;

        sb.append(level).append(" ".repeat(level * 2)).append(node.city
            .getName()).append("\n");

        // Recurse on left and right children
        preorder(node.left, level + 1, sb);
        preorder(node.right, level + 1, sb);
    }
}
