/**
 * Reprezentacja jednokierunkowego łącza pomiędzy dwoma węzłami
 *
 * Created by radoslawjarzynka on 11.10.2015.
 */
public class Link {

    private Integer startNodeId;
    private Integer endNodeId;
    
    private Coordinates startCoordinates;
    private Coordinates endCoordinates;

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
}
