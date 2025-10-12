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


    /**
     * The city with these coordinates is deleted from the database
     * (if it exists).
     * Print the name of the city if it exists.
     * If no city at this location exists, print the empty string.
     * 
     * @param x
     *            City x-coordinate.
     * @param y
     *            City y-coordinate.
     * @return A string with the number of nodes visited during the deletion
     *         followed by the name of the city (this is blank if nothing
     *         was deleted).
     */
    public String delete(int x, int y) {
        City[] deletedCity = new City[1]; // to store the deleted city
        int[] searched = new int[1]; // to count searched nodes

        root = delete(root, x, y, 0, deletedCity, searched);

        if (deletedCity[0] == null) {
            return ""; // city not found, return empty string
        }

        // city found and deleted
        StringBuilder result = new StringBuilder();
        result.append("(").append(searched[0]).append(")\n");
        result.append(deletedCity[0].getName()).append("\n");
        return result.toString();
    }


    private Node delete(
        Node node,
        int x,
        int y,
        int depth,
        City[] deletedCity,
        int[] searched) {
        if (node == null)
            return null;

        searched[0]++;

        int cd = depth % 2;

        if (node.city.getX() == x && node.city.getY() == y) {
            deletedCity[0] = node.city;

            if (node.right != null) {
                Node min = findMinNode(node.right, cd);
                searched[0]++;
                node.city = min.city;
                node.right = delete(node.right, min.city.getX(), min.city
                    .getY(), depth + 1, new City[1], searched);
            }
            else if (node.left != null) {
                Node min = findMinNode(node.left, cd);
                searched[0]++;
                node.city = min.city;
                node.right = delete(node.left, min.city.getX(), min.city.getY(),
                    depth + 1, new City[1], searched);
                node.left = null;
            }
            else {
                return null;
            }
            return node;
        }

        int targetCoord = (cd == 0) ? x : y;
        int nodeCoord = (cd == 0) ? node.city.getX() : node.city.getY();

        if (targetCoord < nodeCoord) {
            node.left = delete(node.left, x, y, depth + 1, deletedCity,
                searched);
        }
        else {
            node.right = delete(node.right, x, y, depth + 1, deletedCity,
                searched);
        }

        return node;
    }


    private Node findMinNode(Node node, int dim) {
        if (node == null)
            return null;

        Node min = node;

        Node leftMin = findMinNode(node.left, dim);
        if (leftMin != null && compareDim(leftMin.city, min.city, dim) < 0) {
            min = leftMin;
        }

        Node rightMin = findMinNode(node.right, dim);
        if (rightMin != null && compareDim(rightMin.city, min.city, dim) < 0) {
            min = rightMin;
        }

        return min;
    }


    private int compareDim(City a, City b, int dim) {
        return (dim == 0)
            ? Integer.compare(a.getX(), b.getX())
            : Integer.compare(a.getY(), b.getY());
    }


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

        if (radius == 0) {
            // Special case: return the city exactly at (x, y) if it exists
            City city = findCityAt(root, x, y);
            if (city != null) {
                return city.getName() + " (" + city.getX() + ", " + city.getY()
                    + ")\n1\n";
            }
            else {
                return "";
            }
        }

        StringBuilder foundCities = new StringBuilder();
        int[] count = new int[1]; // count of cities within radius

        search(root, x, y, radius, foundCities, count);

        foundCities.append(count[0]).append("\n");
        return foundCities.toString();
    }


    private City findCityAt(Node node, int x, int y) {
        if (node == null) {
            return null;
        }

        if (node.city.getX() == x && node.city.getY() == y) {
            return node.city;
        }

        int cd = node.depth % 2;
        int coord = (cd == 0) ? x : y;
        int nodeCoord = (cd == 0) ? node.city.getX() : node.city.getY();

        if (coord < nodeCoord) {
            return findCityAt(node.left, x, y);
        }
        return findCityAt(node.right, x, y);
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
