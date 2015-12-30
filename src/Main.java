
public class Main {

    public static void main(String[] args) {
        Network network = new Network();
        network.generateNetwork(10);
        //Iterujemy po ilości generowanych pakietów
        for (int generatedPackets = 10; generatedPackets <= 60; generatedPackets += 5) {
            System.out.println("Testing " + generatedPackets + " packets per one time unit");
            //Robimy 500 "jednostek czasu"
            for (int i = 0; i< 500; i++) {
                network.generatePackets(20);
                network.transferPackets();
            }
            System.out.println(generatedPackets + " packets per one time unit result:");
            System.out.println("Delivered Packets: " + network.getDeliveredPackets());
            System.out.println("Average Hops: " + network.getHopsSum() / network.getDeliveredPackets());
            System.out.println("Lost Packets: " + network.getLostPackets());
        }
    }
//      
    
//    public static void main(String[] args) {
//        Node[][] network = new Network().generateNetwork(100);
//        System.out.println("Generated");
//        
//        Node source = network[0][0];
//        Node target = network[48][78];
//        Dijkstra d = new Dijkstra(network);
//        d.execute(source);
//        List<Node> path = d.getPath(target);
//        if (path != null) {
//            System.out.println("Shortest Path : " + path.size());
////            for (Node n : path) {
////                System.out.println(n.toString());
////            }
//        } else {
//            System.out.println("Shortest path is empty");
//        }
//        
//        List<Node> longestPath = LongestPath.getLongestPath(source, target);
//        System.out.println("Longest Path : " + longestPath.size());
////        for (Node n : longestPath) {
////            System.out.println(n.toString());
////        }
//        
//        List<Node> randomPath = RandomPath.getRandomPath(source, target);
//        System.out.println("Random Path : " + randomPath.size());
////        for (Node n : randomPath) {
////            System.out.println(n.toString());
////        }
//    }
}
