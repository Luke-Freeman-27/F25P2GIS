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
        Node left, right;
        int depth;

        // Constructor for the private Node Class
        Node(City city, int depth) {
            this.city = city;
            this.depth = depth;
        }
    }

    private Node root;

    /**
     * Insert a city into the KDTree
     * 
     * @param city
     */
    public void insert(City city) {
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
     * Simple inorder traversal to print all cities
     */
    public void inorder() {
        inorder(root);
    }


    private void inorder(Node node) {
        if (node == null)
            return;
        inorder(node.left);
        System.out.println(node.city);
        inorder(node.right);
    }
}
