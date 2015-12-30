import java.util.List;

public class Main {

    public static void main(String[] args) {
        Node[][] network = new Network().generateNetwork(100);
        System.out.println("Generated");
        
        Node source = network[0][0];
        Node target = network[48][78];
        Dijkstra d = new Dijkstra(network);
        d.execute(source);
        List<Node> path = d.getPath(target);
        if (path != null) {
            for (Node n : path) {
                System.out.println(n.toString());
            }
        } else {
            System.out.println("path is empty");
        }
    }
}
