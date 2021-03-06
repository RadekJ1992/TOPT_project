import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * created on 18:31:15 30 gru 2015 by Radoslaw Jarzynka
 * 
 * Wyszukuje najkrótszej ścieżki pomiędzy węzłami
 * 
 * @author Radoslaw Jarzynka
 */
public class Dijkstra implements RouteCalculator {

    private final List<Node> nodes;
    private final List<Link> edges;
    private Set<Node> settledNodes;
    private Set<Node> unSettledNodes;
    private Map<Node, Node> predecessors;
    private Map<Node, Integer> distance;

    public Dijkstra(Node[][] graph) {
        // create a copy of the array so that we can operate on this array
        this.nodes = new ArrayList<Node>();
        this.edges = new ArrayList<Link>();
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                nodes.add(graph[i][j]);
                edges.addAll(graph[i][j].getLinkList());
            }
        }
    }

    public void execute(Node source) {
        settledNodes = new HashSet<Node>();
        unSettledNodes = new HashSet<Node>();
        distance = new HashMap<Node, Integer>();
        predecessors = new HashMap<Node, Node>();
        distance.put(source, 0);
        unSettledNodes.add(source);
     //   System.out.println("Calculating paths for node " + source.toString());
        while (unSettledNodes.size() > 0) {
            Node node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Node node) {
        List<Node> adjacentNodes = getNeighbors(node);
        for (Node target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private int getDistance(Node node, Node target) {
        for (Link edge : edges) {
            if (edge.getSource().equals(node)
                    && edge.getDestination().equals(target)) {
                return edge.getWeight();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<Node>();
        for (Link edge : edges) {
            if (edge.getSource().equals(node)
                    && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    private Node getMinimum(Set<Node> vertexes) {
        Node minimum = null;
        for (Node vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Node vertex) {
        return settledNodes.contains(vertex);
    }

    private int getShortestDistance(Node destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public LinkedList<Node> getPath(Node target) {
        LinkedList<Node> path = new LinkedList<Node>();
     //   System.out.println("Finding shortest path to node " + target.toString());
        Node step = target;
        // check if a path exists
        //    for (Node n : predecessors.keySet()) {
        //        System.out.println(n.toString() + " value: " + predecessors.get(n).toString());
        //    }
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }

    /* (non-Javadoc)
     * @see RouteCalculator#getRoute(Node, Node)
     */
    @Override
    public List<Node> getRoute(Node[][] networkMesh, Node source, Node target) {
        Dijkstra d = new Dijkstra(networkMesh);
        d.execute(source);
        return d.getPath(target);
    }
}
