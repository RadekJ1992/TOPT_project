/**
 * Reprezentacja jednokierunkowego łącza pomiędzy dwoma węzłami
 *
 * Created by radoslawjarzynka on 11.10.2015.
 */
public class Link {

    private Integer startNodeId;
    private Integer endNodeId;
    
    private Node[][] networkMesh;
    
    private Coordinates startCoordinates;
    private Coordinates endCoordinates;

    public Link(Node[][] networkMesh) {
        this.networkMesh = networkMesh;
    }
    
    public Integer getEndNodeId() {
        return endNodeId;
    }

    public void setEndNodeId(Integer endNodeId) {
        this.endNodeId = endNodeId;
    }

    public Integer getStartNodeId() {
        return startNodeId;
    }

    public void setStartNodeId(Integer startNodeId) {
        this.startNodeId = startNodeId;
    }

    public Coordinates getStartCoordinates() {
        return startCoordinates;
    }

    public void setStartCoordinates(Coordinates startCoordinates) {
        this.startCoordinates = startCoordinates;
    }

    public Coordinates getEndCoordinates() {
        return endCoordinates;
    }

    public void setEndCoordinates(Coordinates endCoordinates) {
        this.endCoordinates = endCoordinates;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Link: " + startCoordinates.toString() + " -> " + endCoordinates.toString();
    }
    /*
     * 
     * Metody na potrzeby dijkstry
     * 
     */
    public Node getDestination() {
        return networkMesh[endCoordinates.getX()][endCoordinates.getY()];
      }

    public Node getSource() {
        return networkMesh[startCoordinates.getX()][startCoordinates.getY()];
    }
    
    public Integer getWeight() {
        return 100;
    }
}
