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


    // ----------------------------------------------------------
    /**
     * Deletes a node that has matching x and y values and returns its status
     * 
     * @param x
     * @param y
     * @return a string that gives information on the deleted node
     */
    public String deleteXY(int x, int y) {
        DeletionResult result = delete(root, x, y, 0, 0);
        root = result.node; // update root in case it changed

        if (result.deletedCity != null) {
            return result.visited + "\n" + result.deletedCity.getName();
        }
        return ""; // empty string if no city found/deleted
    }

    private static class DeletionResult {
        Node node;
        int visited;
        City deletedCity;

        DeletionResult(Node node, int visited, City deletedCity) {
            this.node = node;
            this.visited = visited;
            this.deletedCity = deletedCity;
        }
    }

    private DeletionResult delete(
        Node node,
        int x,
        int y,
        int depth,
        int visited) {
        if (node == null) {
            return new DeletionResult(null, visited, null); // Not found
        }

        visited++;

        if (node.city.getX() == x && node.city.getY() == y) {
            // Node found, delete it

            // If right subtree exists, find min in right subtree and replace
            if (node.right != null) {
                Node min = findMin(node.right, depth % 2, depth + 1);
                node.city = min.city;
                DeletionResult delRes = delete(node.right, min.city.getX(),
                    min.city.getY(), depth + 1, visited);
                node.right = delRes.node;
                visited = delRes.visited;
                return new DeletionResult(node, visited, min.city);
            }
            // Else if left subtree exists, find min in left subtree and replace
            else if (node.left != null) {
                Node min = findMin(node.left, depth % 2, depth + 1);
                node.city = min.city;
                DeletionResult delRes = delete(node.left, min.city.getX(),
                    min.city.getY(), depth + 1, visited);
                node.left = delRes.node;
                visited = delRes.visited;
                return new DeletionResult(node, visited, min.city);
            }
            // No children - just delete this node
            else {
                return new DeletionResult(null, visited, node.city);
            }
        }

        int cd = depth % 2;

        if ((cd == 0 && x < node.city.getX()) || (cd == 1 && y < node.city
            .getY())) {
            DeletionResult delRes = delete(node.left, x, y, depth + 1, visited);
            node.left = delRes.node;
            visited = delRes.visited;
            return new DeletionResult(node, visited, delRes.deletedCity);
        }

        DeletionResult delRes = delete(node.right, x, y, depth + 1, visited);
        node.right = delRes.node;
        visited = delRes.visited;
        return new DeletionResult(node, visited, delRes.deletedCity);
    }


    private Node findMin(Node node, int dim, int depth) {
        if (node == null) {
            return null;
        }

        int cd = depth % 2;

        if (cd == dim) {
            // When discriminator matches the dimension, only check left subtree
            // (if any)
            if (node.left == null) {
                // No left subtree, current node is min candidate
                return node;
            }

            // Find min in left subtree
            Node leftMin = findMin(node.left, dim, depth + 1);

            // Compare leftMin with current node
            int currentVal = getCoordinate(node.city, dim);
            int leftMinVal = getCoordinate(leftMin.city, dim);

            // If leftMin is strictly less, choose leftMin, else current node
            if (leftMinVal < currentVal) {
                return leftMin;
            }
            else {
                // Equal or greater: prefer current node (preorder)
                return node;
            }
        }
        else {
            // Discriminator does not match dimension; check both subtrees and
            // current node

            Node leftMin = findMin(node.left, dim, depth + 1);
            Node rightMin = findMin(node.right, dim, depth + 1);

            Node minNode = node;
            int minVal = getCoordinate(minNode.city, dim);

            // Check leftMin: if strictly less than current minVal, update
            // minNode
            if (leftMin != null) {
                int leftVal = getCoordinate(leftMin.city, dim);
                if (leftVal < minVal) {
                    minNode = leftMin;
                    minVal = leftVal;
                }
            }

            // Check rightMin: if strictly less than current minVal, update
            // minNode
            // If equal, DO NOT update to preserve preorder preference for
            // minNode
            if (rightMin != null) {
                int rightVal = getCoordinate(rightMin.city, dim);
                if (rightVal < minVal) {
                    minNode = rightMin;
                    minVal = rightVal;
                }
            }

            return minNode;
        }
    }


    private int getCoordinate(City city, int dim) {
        return dim == 0 ? city.getX() : city.getY();
    }


    // ----------------------------------------------------------
    /**
     * Takes a string input and deletes the first found node with that string as
     * a name
     * 
     * @param name
     * @return - a string indicating if the node was deleted correctly.
     */
    public String deleteByName(String name) {
        DeletionByNameResult result = deleteByName(root, name, 0, 0);
        root = result.node;
        if (result.deletedCity != null) {
            return result.deletedCity.getName() + " " + result.deletedCity
                .getX() + " " + result.deletedCity.getY() + " ";
        }
        return ""; // empty string if no city found/deleted
    }

    private static class DeletionByNameResult {
        Node node;
        int visited;
        City deletedCity;

        DeletionByNameResult(Node node, int visited, City deletedCity) {
            this.node = node;
            this.visited = visited;
            this.deletedCity = deletedCity;
        }
    }

    private DeletionByNameResult deleteByName(
        Node node,
        String name,
        int depth,
        int visited) {
        if (node == null) {
            return new DeletionByNameResult(null, visited, null);
        }

        visited++;

        if (node.city.getName().equals(name)) {
            // Delete node found by name
            // Use the same deletion logic as delete by (x, y)
            int x = node.city.getX();
            int y = node.city.getY();

            DeletionResult delRes = delete(node, x, y, depth, visited - 1); // subtract
                                                                            // 1
                                                                            // because
                                                                            // 'delete'
                                                                            // increments
                                                                            // again
            return new DeletionByNameResult(delRes.node, delRes.visited,
                delRes.deletedCity);
        }

        // Recursively search left subtree
        DeletionByNameResult leftRes = deleteByName(node.left, name, depth + 1,
            visited);
        if (leftRes.deletedCity != null) {
            node.left = leftRes.node;
            return new DeletionByNameResult(node, leftRes.visited,
                leftRes.deletedCity);
        }

        // Recursively search right subtree
        DeletionByNameResult rightRes = deleteByName(node.right, name, depth
            + 1, leftRes.visited);
        node.right = rightRes.node;
        return new DeletionByNameResult(node, rightRes.visited,
            rightRes.deletedCity);
    }


    /**
     * Clears the KDTree, removing all nodes.
     */
    public void clear() {
        root = null;
    }

}
