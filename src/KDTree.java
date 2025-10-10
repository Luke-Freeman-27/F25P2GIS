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


//    // ----------------------------------------------------------
//    /**
//     * Deletes a node that has matching x and y values and returns its status
//     * 
//     * @param x
//     * @param y
//     * @return a string that gives information on the deleted node
//     */
//    public String deleteXY(int x, int y) {
//        DeletionResult result = delete(root, x, y, 0, 0);
//        root = result.node; // update root in case it changed
//
//        if (result.deletedCity != null) {
//            return result.visited + "\n" + result.deletedCity.getName();
//        }
//        return ""; // empty string if no city found/deleted
//    }
//
//    private static class DeletionResult {
//        Node node;
//        int visited;
//        City deletedCity;
//
//        DeletionResult(Node node, int visited, City deletedCity) {
//            this.node = node;
//            this.visited = visited;
//            this.deletedCity = deletedCity;
//        }
//    }
//
//    private DeletionResult delete(
//        Node node,
//        int x,
//        int y,
//        int depth,
//        int visited) {
//        if (node == null) {
//            return new DeletionResult(null, visited, null); // Not found
//        }
//
//        visited++;
//
//        // Check if current node matches (x,y)
//// if (node.city.getX() == x && node.city.getY() == y) {
//// // Node found; time to delete
////
//// // Case 1: Right subtree exists — replace with min in right subtree
//// // by current dimension
//// if (node.right != null) {
//// int cd = depth % 2;
//// Node min = findMin(node.right, cd, depth + 1);
//// // Replace current node's city with min node's city
//// node.city = min.city;
//// // Delete the min node from right subtree (recursive delete)
//// DeletionResult delRes = delete(node.right, min.city.getX(),
//// min.city.getY(), depth + 1, visited);
//// node.right = delRes.node; // Update right subtree root
//// visited = delRes.visited;
//// return new DeletionResult(node, visited, min.city);
//// }
//// // Case 2: Right subtree null but left subtree exists — replace with
//// // min in left subtree by current dimension
//// else if (node.left != null) {
//// int cd = depth % 2;
//// Node min = findMin(node.left, cd, depth + 1);
//// node.city = min.city;
//// DeletionResult delRes = delete(node.left, min.city.getX(),
//// min.city.getY(), depth + 1, visited);
//// node.left = delRes.node; // Update left subtree root
//// visited = delRes.visited;
//// return new DeletionResult(node, visited, min.city);
//// }
//// // Case 3: No children — simply delete node
//// else {
//// return new DeletionResult(null, visited, node.city);
//// }
//// }
//
//        // Node doesn't match; recurse down correct subtree based on current
//        // dimension
//        int cd = depth % 2;
//
//        if ((cd == 0 && x < node.city.getX()) || (cd == 1 && y < node.city
//            .getY())) {
//            DeletionResult delRes = delete(node.left, x, y, depth + 1, visited);
//            node.left = delRes.node;
//            visited = delRes.visited;
//            return new DeletionResult(node, visited, delRes.deletedCity);
//        }
//        else {
//            DeletionResult delRes = delete(node.right, x, y, depth + 1,
//                visited);
//            node.right = delRes.node;
//            visited = delRes.visited;
//            return new DeletionResult(node, visited, delRes.deletedCity);
//        }
//    }
//
//// /**
//// * Finds the node with the minimum coordinate value in dimension `dim`
//// * within the subtree rooted at `node`.
//// *
//// * The preorder preference is maintained by always preferring the root node
//// * if values are equal.
//// */
//// private Node findMin(Node node, int dim, int depth) {
//// if (node == null) {
//// return null;
//// }
////
//// int cd = depth % 2;
////
//// if (cd == dim) {
//// // Only need to check left subtree when discriminator matches
//// // dimension
//// if (node.left == null) {
//// return node;
//// }
//// Node leftMin = findMin(node.left, dim, depth + 1);
////
//// int currentVal = getCoordinate(node.city, dim);
//// int leftVal = getCoordinate(leftMin.city, dim);
////
//// // If leftMin is strictly less, pick leftMin, else pick current node
//// // (preorder preference)
//// if (leftVal < currentVal) {
//// return leftMin;
//// }
//// else {
//// return node;
//// }
//// }
//// else {
//// // Need to check both subtrees and current node when discriminator
//// // doesn't match dimension
//// Node leftMin = findMin(node.left, dim, depth + 1);
//// Node rightMin = findMin(node.right, dim, depth + 1);
////
//// Node minNode = node;
//// int minVal = getCoordinate(node.city, dim);
////
//// if (leftMin != null) {
//// int leftVal = getCoordinate(leftMin.city, dim);
//// if (leftVal < minVal) {
//// minNode = leftMin;
//// minVal = leftVal;
//// }
//// }
////
//// if (rightMin != null) {
//// int rightVal = getCoordinate(rightMin.city, dim);
//// if (rightVal < minVal) {
//// minNode = rightMin;
//// minVal = rightVal;
//// }
//// }
////
//// return minNode;
//// }
//// }
////
//// private int getCoordinate(City city, int dim) {
//// return dim == 0 ? city.getX() : city.getY();
//// }
//
//
//    // ----------------------------------------------------------
//    /**
//     * Takes a string input and deletes the first found node with that string as
//     * a name
//     * 
//     * @param name
//     * @return - a string indicating if the node was deleted correctly.
//     */
//    public String deleteByName(String name) {
//        DeletionByNameResult result = deleteByName(root, name, 0, 0);
//        root = result.node;
//        if (result.deletedCity != null) {
//            return result.deletedCity.getName() + " " + result.deletedCity
//                .getX() + " " + result.deletedCity.getY() + " ";
//        }
//        return ""; // empty string if no city found/deleted
//    }
//
//    private static class DeletionByNameResult {
//        Node node;
//        int visited;
//        City deletedCity;
//
//        DeletionByNameResult(Node node, int visited, City deletedCity) {
//            this.node = node;
//            this.visited = visited;
//            this.deletedCity = deletedCity;
//        }
//    }
//
//    private DeletionByNameResult deleteByName(
//        Node node,
//        String name,
//        int depth,
//        int visited) {
//        if (node == null) {
//            return new DeletionByNameResult(null, visited, null);
//        }
//
//        visited++;
//
//        if (node.city.getName().equalsIgnoreCase(name)) {
//            System.out.println("Deleting node: " + node.city.getName() + " ("
//                + node.city.getX() + "," + node.city.getY() + ")");
//            int x = node.city.getX();
//            int y = node.city.getY();
//
//            DeletionResult delRes = delete(node, x, y, depth, visited - 1);
//            return new DeletionByNameResult(delRes.node, delRes.visited,
//                delRes.deletedCity);
//        }
//
//        DeletionByNameResult leftRes = deleteByName(node.left, name, depth + 1,
//            visited);
//        if (leftRes.deletedCity != null) {
//            node.left = leftRes.node;
//            return new DeletionByNameResult(node, leftRes.visited,
//                leftRes.deletedCity);
//        }
//
//        DeletionByNameResult rightRes = deleteByName(node.right, name, depth
//            + 1, leftRes.visited);
//        node.right = rightRes.node;
//        return new DeletionByNameResult(node, rightRes.visited,
//            rightRes.deletedCity);
//    }
//

    /**
     * Display the name of the city at coordinate (x, y) if it exists.
     * 
     * @param x
     *            X coordinate.
     * @param y
     *            Y coordinate.
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
        else {
            return infoXY(node.right, x, y, depth + 1);
        }
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
            return ""; // no cities found
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

        int dx = node.city.getX() - x;
        int dy = node.city.getY() - y;
        int distSquared = dx * dx + dy * dy;
        int radiusSquared = radius * radius;

        if (distSquared <= radiusSquared) {
            foundCities.append(node.city.getName()).append(" (").append(
                node.city.getX()).append(", ").append(node.city.getY()).append(
                    ")\n");
            count[0]++;
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
