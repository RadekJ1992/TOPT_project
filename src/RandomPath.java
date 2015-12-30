import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * created on 18:40:45 30 gru 2015 by Radoslaw Jarzynka
 * 
 * UWAGA - może tworzyć dłuższą ścieżkę niż {@link LongestPath}, gdyż może tworzyć pętle 
 * i "cofanie się" ścieżki
 * 
 * @author Radoslaw Jarzynka
 */

public class RandomPath implements RouteCalculator {
    public static List<Node> getRandomPath(Node source, Node target) {
        List<Node> path = new ArrayList<Node>();
        Node currentNode = source;
        Random r = new Random();
        while (currentNode != target) {
            Node nodeNeighbor = currentNode.getLinkList().get(r.nextInt(2)).getDestination();
            path.add(nodeNeighbor);
            currentNode = nodeNeighbor;
        }
        return path;
    }
    
    /* (non-Javadoc)
     * @see RouteCalculator#getRoute(Node[][], Node, Node)
     */
    @Override
    public List<Node> getRoute(Node[][] networkMesh, Node source, Node target) {
        return getRandomPath(source, target);
    }
}
