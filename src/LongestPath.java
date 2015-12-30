import java.util.ArrayList;
import java.util.List;

/**
 * created on 18:31:15 30 gru 2015 by Radoslaw Jarzynka
 * 
 * Tworzy najdłuższą ścieżkę pomiędzy węzłami (bez powtórek węzłów)
 * 
 * @author Radoslaw Jarzynka
 */

public class LongestPath implements RouteCalculator {
    public static List<Node> getLongestPath(Node source, Node target) {
        List<Node> path = new ArrayList<Node>();
        Node currentNode = source;
        while (currentNode != target) {
            Node nodeHorizontalNeighbor = currentNode.getLinkList().get(0).getDestination();
            Node nodeVerticalNeighbor = currentNode.getLinkList().get(1).getDestination();
            if (!path.contains(nodeHorizontalNeighbor)) {
                path.add(nodeHorizontalNeighbor);
                currentNode = nodeHorizontalNeighbor;
            } else {
                path.add(nodeVerticalNeighbor);
                currentNode = nodeVerticalNeighbor;
            }
        }
        return path;
    }

    /* (non-Javadoc)
     * @see RouteCalculator#getRoute(Node[][], Node, Node)
     */
    @Override
    public List<Node> getRoute(Node[][] networkMesh, Node source, Node target) {
        return getLongestPath(source, target);
    }
}
