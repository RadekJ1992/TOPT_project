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
    private static Integer currentMode = 1;
    private static Integer testedTimeUnits = 500;
    private static Integer networkSize = 10;
    private static Integer bufferSize = 10;
    private static Integer ttl = 10;
    private static Integer generatedPackets = 50;
    
    //TRYB 1 - sprawdzamy ilość generowanych pakietów:
    public final static Integer PACKETS_MODE = 1;
    private static Integer generatedPacketsFrom = 30;
    private static Integer generatedPacketsTo = 100;
    private static Integer generatedPacketsStep = 5;

    //TRYB 2 - sprawdzamy względem głębokości bufora:
    public final static Integer BUFFER_MODE = 2;
    private static Integer bufferSizeFrom = 1;
    private static Integer bufferSizeTo = 20;
    private static Integer bufferSizeStep = 1;
    
    //TRYB 3 - sprawdzamy względem czasu przeterminowania pakietu
    public final static Integer TTL_MODE = 3;
    private static Integer ttlFrom = 1;
    private static Integer ttlTo = 20;
    private static Integer ttlStep = 1;
    

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
    
    public static Integer getGeneratedPackets() {
        return generatedPackets;
    }

    public static void setGeneratedPackets(Integer generatedPackets) {
        Settings.generatedPackets = generatedPackets;
    }

    public static Integer getBufferSizeFrom() {
        return bufferSizeFrom;
    }

    public static void setBufferSizeFrom(Integer bufferSizeFrom) {
        Settings.bufferSizeFrom = bufferSizeFrom;
    }

    public static Integer getBufferSizeTo() {
        return bufferSizeTo;
    }

    public static void setBufferSizeTo(Integer bufferSizeTo) {
        Settings.bufferSizeTo = bufferSizeTo;
    }

    public static Integer getBufferSizeStep() {
        return bufferSizeStep;
    }

    public static void setBufferSizeStep(Integer bufferSizeStep) {
        Settings.bufferSizeStep = bufferSizeStep;
    }

    public static Integer getTtlFrom() {
        return ttlFrom;
    }

    public static void setTtlFrom(Integer ttlFrom) {
        Settings.ttlFrom = ttlFrom;
    }

    public static Integer getTtlTo() {
        return ttlTo;
    }

    public static void setTtlTo(Integer ttlTo) {
        Settings.ttlTo = ttlTo;
    }

    public static Integer getTtlStep() {
        return ttlStep;
    }

    public static void setTtlStep(Integer ttlStep) {
        Settings.ttlStep = ttlStep;
    }

    public static Integer getTestedTimeUnits() {
        return testedTimeUnits;
    }

    public static void setTestedTimeUnits(Integer testedTimeUnits) {
        Settings.testedTimeUnits = testedTimeUnits;
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

    public static Integer getCurrentMode() {
        return currentMode;
    }

    public static void setCurrentMode(Integer currentMode) {
        Settings.currentMode = currentMode;
    }
}
