import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Klasa reprezentująca węzeł w sieci
 *
 * Created by radoslawjarzynka on 11.10.2015.
 */
public class Node {

    private Coordinates coordinates;
    private List<Link> linkList;
    private Queue<Packet> buffer;

    public Node(Integer bufferSize, Coordinates coordinates) {
        this.coordinates = coordinates;
        buffer = new LinkedBlockingQueue<Packet>(bufferSize);
        linkList = new ArrayList<Link>(4);
    }

    /**
     * Zwraca listę łączy tego pakietu
     * @return
     */
    public List<Link> getLinkList() {
        return linkList;
    }

    /**
     * Ściąga najstarszy pakiet z bufora
     * @return Pakiet wstawiony najwcześniej do bufora
     */
    public Packet getPacketFromBuffer() {
        return buffer.poll();
    }
    
    public Queue<Packet> getBuffer() {
        return buffer;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((coordinates == null) ? 0 : coordinates.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Node other = (Node) obj;
        if (coordinates == null) {
            if (other.coordinates != null)
                return false;
        } else if (!coordinates.equals(other.coordinates))
            return false;
        return true;
    }

    /**
     * Wstawia pakiet do bufora
     * @param packet pakiet do wstawienia
     * @return true jeżeli udało się wstawić, false jeżeli pakiet jest utracony
     */
    public boolean putPacket(Packet packet) {
        return buffer.offer(packet);
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Node: " + getCoordinates().getX() + " : " + getCoordinates().getY();
    }
}
