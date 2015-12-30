import java.util.List;

/**
 * created on 20:23:55 30 gru 2015 by Radoslaw Jarzynka
 * 
 * @author Radoslaw Jarzynka
 */

public interface RouteCalculator {
    List<Node> getRoute(Node[][] networkMesh, Node source, Node target);
}
