import java.util.List;

/**
 * created on 20:22:30 30 gru 2015 by Radoslaw Jarzynka
 * 
 * @author Radoslaw Jarzynka
 */

public class Settings {
    public final static String DIJKSTRA = "DIJKSTRA";
    public final static String LONGEST = "LONGEST";
    public final static String RANDOM = "RANDOM";
    
    //TODO POBRAC Z USTAWIEN;
    private static String currentAlgorithm = DIJKSTRA;

    public static String getCurrentAlgorithm() {
        return currentAlgorithm;
    }
    
    public static Integer getBufferSize() {
        //TODO implementacja
        return 10;
    }
    
    public static Integer getTtl() {
        //TODO implementacja
        return 10;
    }
    
    public static List<Node> getPathByCurrentAlgorithm(Node[][] networkMesh, Node source, Node target) {
        RouteCalculator r;
        switch (currentAlgorithm) {
            case DIJKSTRA:
                r = new Dijkstra(networkMesh);
                return r.getRoute(networkMesh, source, target);
            case LONGEST:
                r = new LongestPath();
                return r.getRoute(networkMesh, source, target);
            case RANDOM:
            default:
                r = new RandomPath();
                return r.getRoute(networkMesh, source, target);
        }
    }

    public static void setCurrentAlgorithm(String currentAlgorithm) {
        Settings.currentAlgorithm = currentAlgorithm;
    }
}
