import java.util.ArrayList;
import java.util.Random;

/**
 * Created by radoslawjarzynka on 11.10.2015.
 */
public class Network {

    private Node[][] networkMesh;
    
    private Integer deliveredPackets = 0;
    private Integer hopsSum = 0;
    private Integer lostPackets = 0;
    private Integer delaySum = 0;
    private ArrayList<Integer> delayList;

    public Integer getDeliveredPackets() {
        return deliveredPackets;
    }

    public Integer getHopsSum() {
        return hopsSum;
    }
    
    public Integer getDelaySum() {
        return delaySum;
    }

    public Integer getLostPackets() {
        return lostPackets;
    }

    public Node[][] generateNetwork(Integer n) {
        networkMesh = new Node[n][n];
        // Generowanie tablicy węzłów
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                networkMesh[i][j] = NodeFactory.getInstance().getNode(Settings.getBufferSize(), i, j);
            }
        }
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                LinkFactory.getInstance().generateLinkForNode(networkMesh, i, j);
            }
        }
        delaySum = 0;
        delayList = new ArrayList<Integer>();
        hopsSum = 0;
        lostPackets = 0;
        deliveredPackets = 0;
        return networkMesh;
    }
    
    public void generatePackets(Integer packetsNumber) {
        Random r = new Random();
        for (int i = 0; i < packetsNumber; i++) {
            Integer size = networkMesh.length;
            Node startNode = networkMesh[r.nextInt(size)][r.nextInt(size)];
            Node endNode = networkMesh[r.nextInt(size)][r.nextInt(size)];
            while (endNode.equals(startNode)) {
                endNode = networkMesh[r.nextInt(size)][r.nextInt(size)];
            }
            Packet p = new Packet();
          //  System.out.print("Calculating path for packet" + i + "\r");
            p.setRoute(Settings.getPathByCurrentAlgorithm(networkMesh, startNode, endNode));
            p.setTtl(Settings.getTtl());
            //usuwamy pierwszy węzeł ze ścieżki bo to węzeł startowy
            p.setRoute(p.getRoute().subList(1, p.getRoute().size()));
            //jeżeli wylosowany został węzeł startowy z zapełnioną kolejką to tracimy pakiet #jak_żyć
            if (!startNode.putPacket(p)) {
                lostPackets++;
            }
        }
      //  System.out.println();
    }
    
    /**
     * Dla każdego węzła w sieci pobranie pakietu z bufora, przeniesienie go dalej wzdłuż ścieżki
     */
    public void transferPackets() {
        Integer size = networkMesh.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Node node = networkMesh[i][j];
                Packet p = node.getPacketFromBuffer();
                if (p != null) {
                    if (p.getDelay() > Settings.getTtl()) {
                        lostPackets++;
                    } else {
                        p.incrementDelay();
                        if (p.getRoute().size() > 1) {
                            Node target = p.getRoute().get(0);
                            p.setRoute(p.getRoute().subList(1, p.getRoute().size()));
                            if (!target.putPacket(p)) {
                                lostPackets++;
                            } else {
                                p.incrementHops();
                            }
                        } else {
                            //dostarczono
                            deliveredPackets++;
                            delayList.add(p.getDelay());
                            delaySum += p.getDelay();
                            hopsSum += p.getHops();
                        }
                    }
                }
            }
        }
    }
    
    public Double getAverageDelay() {
        return (double) (getDeliveredPackets() != 0 ? getDelaySum() / getDeliveredPackets() : 0);
    }
    
    public Double getAverageHops() {
        return (double) (getDeliveredPackets() != 0 ? getHopsSum() / getDeliveredPackets() : 0);
    }
    
    public Double getDelayVariance() {
        if (getDeliveredPackets() != 0) {
            Double sum = 0d;
            for (Integer i : delayList) {
                sum += Math.pow((i - getAverageDelay()), 2) ;
            }
            return Math.sqrt((sum) / getDeliveredPackets());
        }
        return 0d;
    }
}
