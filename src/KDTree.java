// -------------------------------------------------------------------------
/**
 * This class is a basic implementation of a k-d tree. The end purpose
 * of this class is to be used in conjunction with a BST.
 * 
 * @author walkerberndt, Luke-Freeman-27
 * @version Oct 1, 2025
 * @param <T>
 */
public class KDTree<T> {

    private Node root;

    /**
     * Global Variable allowed by TA's to keep count of traversed nodes for
     * functions for which this is required.
     */
    public static int traversedNodes = 0;

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

        int cd = depth % 2; // current dimension: 0 for x, 1 for y

        int cityCoord = (cd == 0) ? city.getX() : city.getY();
        int nodeCoord = (cd == 0) ? node.city.getX() : node.city.getY();

        if (cityCoord < nodeCoord) {
            node.left = insert(node.left, city, depth + 1);
        }
        else {
            // equal values go to the right
            node.right = insert(node.right, city, depth + 1);
        }
        return node;
    }


    /**
     * 
     * Display the name of the city at coordinate (x, y) if it exists.
     * 
     * @param x
     *            X coordinate.
     * 
     * @param y
     *            Y coordinate.
     * 
     * @return The city name if there is such a city, empty otherwise
     */

    public String infoXY(int x, int y) {
        Node found = infoXY(root, x, y, 0);
        if (found != null) {
            return found.city.getName();
        }
        return "";
    }


    private Node infoXY(Node node, int x, int y, int depth) {
        if (node == null) {
            return null;
        }
        // Check if this node matches the coordinates
        if (node.city.getX() == x && node.city.getY() == y) {
            return node;
        }
        int cd = depth % 2; // current dimension
        // Decide which subtree to explore based on comparison at current
        // dimension
        if ((cd == 0 && x < node.city.getX()) || (cd == 1 && y < node.city
            .getY())) {
            return infoXY(node.left, x, y, depth + 1);
        }
        return infoXY(node.right, x, y, depth + 1);
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


    // Helper method to find City object at given coords (no counting)
    private City findCity(Node node, int x, int y) {
        if (node == null)
            return null;
        if (node.city.getX() == x && node.city.getY() == y)
            return node.city;

        City foundInLeft = findCity(node.left, x, y);
        if (foundInLeft != null)
            return foundInLeft;

        return findCity(node.right, x, y);
    }


    // ----------------------------------------------------------
    /**
     * Finds a node based on an entered x and y value and deletes it.
     * 
     * @param x
     *            X coordinate.
     * @param y
     *            y coordinate.
     * @return a string with information on how many nodes the delete function
     *         had to traverse and the node deleted's name.s
     */
    public String delete(int x, int y) {
        // Reset the global counter
        KDTree.traversedNodes = 0;

        // If no city found return empty string
        City cityToDelete = findCity(root, x, y);
        if (cityToDelete == null) {
            return "";
        }

        // Call the delete help method
        root = deleteAndCount(root, x, y, 0);

        // Return the
        return KDTree.traversedNodes + "\n" + cityToDelete.getName();
    }


    private Node deleteAndCount(Node node, int x, int y, int depth) {
        if (node == null)
            return null;

        // Count every node visited during search and delete
        KDTree.traversedNodes++;

        int cd = depth % 2;
        int nodeX = node.city.getX();
        int nodeY = node.city.getY();

        if (nodeX == x && nodeY == y) {
            // Node to delete found
            if (node.right != null) {
                Node minNode = findMin(node.right, cd, depth + 1);
                node.city = minNode.city;
                node.right = deleteAndCount(node.right, minNode.city.getX(),
                    minNode.city.getY(), depth + 1);
                return node;
            }
            else if (node.left != null) {
                Node maxNode = findMax(node.left, cd, depth + 1);
                node.city = maxNode.city;
                node.left = deleteAndCount(node.left, maxNode.city.getX(),
                    maxNode.city.getY(), depth + 1);
                return node; // Keep structure, but with replaced data
            }
            else {
                return null;
            }
        }

        int coord = (cd == 0) ? x : y;
        int nodeCoord = (cd == 0) ? nodeX : nodeY;

        if (coord < nodeCoord) {
            node.left = deleteAndCount(node.left, x, y, depth + 1);
        }
        else {
            node.right = deleteAndCount(node.right, x, y, depth + 1);
        }
        return node;
    }


    // findMin remains unchanged, counts nodes visited
    private Node findMin(Node node, int d, int depth) {
        if (node == null)
            return null;

        KDTree.traversedNodes++;

        int cd = depth % 2;

        if (cd == d) {
            if (node.left == null)
                return node;
            return findMin(node.left, d, depth + 1);
        }

        Node leftMin = findMin(node.left, d, depth + 1);
        Node rightMin = findMin(node.right, d, depth + 1);

        Node minNode = node;
        if (leftMin != null && getCoord(leftMin.city, d) < getCoord(
            minNode.city, d)) {
            minNode = leftMin;
        }
        if (rightMin != null && getCoord(rightMin.city, d) < getCoord(
            minNode.city, d)) {
            minNode = rightMin;
        }
        return minNode;
    }


    // Add findMax (similar to findMin, but maximize coord):
    private Node findMax(Node node, int d, int depth) {
        if (node == null)
            return null;
        KDTree.traversedNodes++;
        int cd = depth % 2;
        if (cd == d) {
            if (node.right == null)

                return findMax(node.right, d, depth + 1); // Go right for max
        }
        Node leftMax = findMax(node.left, d, depth + 1);
        Node rightMax = findMax(node.right, d, depth + 1);
        Node maxNode = node;
        if (leftMax != null && getCoord(leftMax.city, d) > getCoord(
            maxNode.city, d)) {
            maxNode = leftMax;
        }
        if (rightMax != null && getCoord(rightMax.city, d) > getCoord(
            maxNode.city, d)) {
            maxNode = rightMax;
        }
        return maxNode;
    }


    private int getCoord(City city, int d) {
        return (d == 0) ? city.getX() : city.getY();
    }


    // ----------------------------------------------------------
    /**
     * All cities within radius distance from location (x, y) are listed.
     * A city that is exactly radius distance from the query point should be
     * listed.
     * This operation should be implemented so that as few nodes as possible in
     * the k-d tree are visited.
     * 
     * @param x
     *            Search circle center: X coordinate. May be negative.
     * @param y
     *            Search circle center: Y coordinate. May be negative.
     * @param radius
     *            Search radius, must be non-negative.
     * @return String listing the cities found (if any) , followed by the count
     *         of the number of k-d tree nodes looked at during the
     *         search process. If the radius is bad, return an empty string.
     *         If k-d tree is empty, the number of nodes visited is zero.
     */
    public String search(int x, int y, int radius) {
        if (radius < 0) {
            return "";
        }

        if (root == null) {
            return "0"; // no cities found
        }

        StringBuilder foundCities = new StringBuilder();
        int[] count = new int[1]; // count of cities within radius

        search(root, x, y, radius, foundCities, count);

        foundCities.append(count[0]).append("\n");

        return foundCities.toString();
    }


    private void search(
        Node node,
        int x,
        int y,
        int radius,
        StringBuilder foundCities,
        int[] count) {

        if (node == null) {
            return;
        }

        // Increment count since this node is being searched (visited)
        count[0]++;

        int dx = node.city.getX() - x;
        int dy = node.city.getY() - y;
        int distSquared = dx * dx + dy * dy;
        int radiusSquared = radius * radius;

        if (distSquared <= radiusSquared) {
            foundCities.append(node.city.getName()).append(" (").append(
                node.city.getX()).append(", ").append(node.city.getY()).append(
                    ")\n");
        }

        int cd = node.depth % 2;

        int coord = (cd == 0) ? node.city.getX() : node.city.getY();
        int queryCoord = (cd == 0) ? x : y;

        if (queryCoord - radius <= coord) {
            search(node.left, x, y, radius, foundCities, count);
        }

        if (queryCoord + radius >= coord) {
            search(node.right, x, y, radius, foundCities, count);
        }
    }


    /**
     * Clears the KDTree, removing all nodes.
     */
    public void clear() {
        root = null;
    }

}
