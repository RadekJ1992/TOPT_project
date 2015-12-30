/**
 * created on 09:52:01 18 lis 2015 by Radoslaw Jarzynka
 * 
 * @author Radoslaw Jarzynka
 */

public class LinkFactory {

    private static LinkFactory instance = null;
    
    protected LinkFactory() {
    }
 
    public static LinkFactory getInstance() {
        if (instance == null) {
            synchronized (LinkFactory.class) {
                if (instance == null) {
                    instance = new LinkFactory();
                }
            }
        }
        return instance;
    }

    public void generateLinkForNode(Node[][] networkMesh, Integer nodeX, Integer nodeY) {
        //Zakładamy że tablica jest kwadratowa
        Integer size = networkMesh.length;
        
        boolean linkRight = nodeY % 2 == 0;
        boolean linkUp = nodeX % 2 == 0;
        
        Link horizontalLink = new Link(networkMesh);
        if (linkRight) {
            horizontalLink.setStartCoordinates(
                    new Coordinates(nodeX, nodeY));
            horizontalLink.setEndCoordinates(
                    new Coordinates((((nodeX + 1) % size) + size) % size, nodeY));
        } else {
            horizontalLink.setStartCoordinates(
                    new Coordinates(nodeX, nodeY));
            horizontalLink.setEndCoordinates(
                    new Coordinates((((nodeX - 1) % size) + size) % size, nodeY));
        }
        networkMesh[nodeX][nodeY].getLinkList().add(horizontalLink);
//        System.out.println("Generating link " + horizontalLink.toString());

        Link verticalLink = new Link(networkMesh);
        if (linkUp) {
            verticalLink.setStartCoordinates(
                    new Coordinates(nodeX, nodeY));
            verticalLink.setEndCoordinates(
                    new Coordinates(nodeX, (((nodeY - 1) % size) + size) % size));
        } else {
            verticalLink.setStartCoordinates(
                    new Coordinates(nodeX, nodeY));
            verticalLink.setEndCoordinates(
                    new Coordinates(nodeX, (((nodeY + 1) % size) + size) % size));
        }
        networkMesh[nodeX][nodeY].getLinkList().add(verticalLink);
//        System.out.println("Generating link " + verticalLink.toString());
    }
}
