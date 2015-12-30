import java.util.Random;

/**
 * Created by radoslawjarzynka on 11.10.2015.
 */
public class Network {

    private Node[][] networkMesh;
    
    private Integer deliveredPackets = 0;
    private Integer hopsSum = 0;
    private Integer lostPackets = 0;

    public Integer getDeliveredPackets() {
        return deliveredPackets;
    }

    public Integer getHopsSum() {
        return hopsSum;
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
            p.setRoute(Settings.getPathByCurrentAlgorithm(networkMesh, startNode, endNode));
            p.setTtl(Settings.getTtl());
            //usuwamy pierwszy węzeł ze ścieżki bo to węzeł startowy
            p.setRoute(p.getRoute().subList(1, p.getRoute().size()));
            //jeżeli wylosowany został węzeł startowy z zapełnioną kolejką to tracimy pakiet #jak_żyć
            if (!startNode.putPacket(p)) {
                lostPackets++;
            }
        }
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
                        hopsSum += p.getHops();
                    }
                }
            }
        }
    }
}
