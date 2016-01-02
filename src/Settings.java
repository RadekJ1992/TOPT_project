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
    
    private static Integer bufferSize = 10;
    private static Integer ttl = 10;
    private static Integer maxHop = 10;
    private static Integer generatedPacketsFrom = 30;
    private static Integer generatedPacketsTo = 100;
    private static Integer generatedPacketsStep = 5;
    private static Integer networkSize = 10;
    
    public static Integer getMaxHop() {
        return maxHop;
    }

    public static void setMaxHop(Integer maxHop) {
        Settings.maxHop = maxHop;
    }

    public static Integer getGeneratedPacketsFrom() {
        return generatedPacketsFrom;
    }

    public static void setGeneratedPacketsFrom(Integer generatedPacketsFrom) {
        Settings.generatedPacketsFrom = generatedPacketsFrom;
    }

    public static Integer getGeneratedPacketsTo() {
        return generatedPacketsTo;
    }

    public static void setGeneratedPacketsTo(Integer generatedPacketsTo) {
        Settings.generatedPacketsTo = generatedPacketsTo;
    }

    public static Integer getGeneratedPacketsStep() {
        return generatedPacketsStep;
    }

    public static void setGeneratedPacketsStep(Integer generatedPacketsStep) {
        Settings.generatedPacketsStep = generatedPacketsStep;
    }

    public static Integer getNetworkSize() {
        return networkSize;
    }

    public static void setNetworkSize(Integer networkSize) {
        Settings.networkSize = networkSize;
    }

    public static void setBufferSize(Integer bufferSize) {
        Settings.bufferSize = bufferSize;
    }

    public static void setTtl(Integer ttl) {
        Settings.ttl = ttl;
    }

    private static String currentAlgorithm = DIJKSTRA;

    public static String getCurrentAlgorithm() {
        return currentAlgorithm;
    }
    
    public static Integer getBufferSize() {
        return bufferSize;
    }
    
    public static Integer getTtl() {
        return Settings.ttl;
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
