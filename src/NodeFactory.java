/**
 * created on 21:53:22 12 lis 2015 by Radoslaw Jarzynka
 * 
 * Fabryka węzłów
 * 
 * @author Radoslaw Jarzynka
 */

public class NodeFactory {

    private static NodeFactory instance = null;
    
    protected NodeFactory() {
    }
 
    public static NodeFactory getInstance() {
        if (instance == null) {
            synchronized (NodeFactory.class) {
                if (instance == null) {
                    instance = new NodeFactory();
                }
            }
        }
        return instance;
    }

    public Node getNode(Integer bufferSize, Integer x, Integer y) {
        Node result = new Node(bufferSize, new Coordinates(x, y));
        return result;
    }
    
}
