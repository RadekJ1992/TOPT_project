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
}
