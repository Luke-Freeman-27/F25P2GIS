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
     * In-Order traversal of the KDTree that prints the information in the
     * database according to the project specification.
     * 
     * Uses a private function nested within itself to allow for recursion to
     * print the full tree easily.
     * 
     * @return sb.toString - returns the string builder that is made through
     *         Pre-order recursion of the database.
     */
    public String inorder() {
        StringBuilder sb = new StringBuilder();
        inorder(root, 0, sb);
        return sb.toString();
    }


    private void inorder(Node node, int level, StringBuilder sb) {
        if (node == null)
            return;

        // Recurse on left child first
        inorder(node.left, level + 1, sb);

        // Then visit the current node
        sb.append(level).append(" ".repeat(level * 2)).append(node.city
            .getName()).append(" ").append(node.city.getX()).append(" ").append(
                node.city.getY()).append("\n");

        // Recurse on right child next
        inorder(node.right, level + 1, sb);
    }


    public City delete(City city) {
        if (city == null)
            return null;
        Node[] result = deleteHelper(root, city, 0);
        root = result[0]; // update root
        return result[1] != null ? result[1].city : null; // return deleted
                                                          // city's data
    }


    private Node[] deleteHelper(Node node, City target, int depth) {
        if (node == null)
            return new Node[] { null, null };

        int cd = depth % 2;

        if (node.city.equals(target)) {
            // Case 1: Node to delete found

            // Case 1.1: Node has right child
            if (node.right != null) {
                Node min = findPreorderMin(node.right, cd, depth + 1);
                node.city = min.city;
                Node[] result = deleteHelper(node.right, min.city, depth + 1);
                node.right = result[0];
                return new Node[] { node, new Node(target, depth) };
            }
            // Case 1.2: No right child, but has left
            else if (node.left != null) {
                Node min = findPreorderMin(node.left, cd, depth + 1);
                node.city = min.city;
                Node[] result = deleteHelper(node.left, min.city, depth + 1);
                node.left = result[0];
                return new Node[] { node, new Node(target, depth) };
            }
            // Case 1.3: Leaf node
            else {
                return new Node[] { null, new Node(target, depth) };
            }
        }

        // Recur left or right depending on dimension
        if ((cd == 0 && target.getX() < node.city.getX()) || (cd == 1 && target
            .getY() < node.city.getY())) {
            Node[] result = deleteHelper(node.left, target, depth + 1);
            node.left = result[0];
            return new Node[] { node, result[1] };
        }
        else {
            Node[] result = deleteHelper(node.right, target, depth + 1);
            node.right = result[0];
            return new Node[] { node, result[1] };
        }
    }


    private Node findPreorderMin(Node node, int dim, int depth) {
        if (node == null)
            return null;

        Node min = node;
        Node leftMin = findPreorderMin(node.left, dim, depth + 1);
        Node rightMin = findPreorderMin(node.right, dim, depth + 1);

        if (leftMin != null && compareByDim(leftMin.city, min.city, dim) < 0) {
            min = leftMin;
        }
        if (rightMin != null && compareByDim(rightMin.city, min.city,
            dim) < 0) {
            min = rightMin;
        }

        return min;
    }


    private int compareByDim(City a, City b, int dim) {
        return dim == 0
            ? Integer.compare(a.getX(), b.getX())
            : Integer.compare(a.getY(), b.getY());
    }

}
