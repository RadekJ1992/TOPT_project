/**
 * created on 09:58:06 18 lis 2015 by Radoslaw Jarzynka
 * 
 * @author Radoslaw Jarzynka
 */

public class Coordinates {

    private Integer xCoord;
    private Integer yCoord;

    public Coordinates(Integer x, Integer y) {
        this.xCoord = x;
        this.yCoord = y;
    }
    
    public Integer getX() {
        return xCoord;
    }

    public void setX(Integer x) {
        this.xCoord = x;
    }

    public Integer getY() {
        return yCoord;
    }

    public void setY(Integer y) {
        this.yCoord = y;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Coordinate: " + xCoord + " : " + yCoord;
    }

}
