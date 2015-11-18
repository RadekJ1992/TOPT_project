/**
 * Created by radoslawjarzynka on 11.10.2015.
 */
public class Network {

    private Node[][] networkMesh;

    public Node[][] generateNetwork(Integer n) {
        networkMesh = new Node[n][n];
        // Generowanie tablicy węzłów
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                networkMesh[i][j] = NodeFactory.getInstance().getNode(n, i, j);
            }
        }
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                LinkFactory.getInstance().generateLinkForNode(networkMesh, i, j);
            }
        }
        return networkMesh;
    }
}
