/**
 * created on 09:58:06 18 lis 2015 by Radoslaw Jarzynka
 * 
 * @author Radoslaw Jarzynka
 */

public class Coordinates {

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((xCoord == null) ? 0 : xCoord.hashCode());
        result = prime * result + ((yCoord == null) ? 0 : yCoord.hashCode());
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
        Coordinates other = (Coordinates) obj;
        if (xCoord == null) {
            if (other.xCoord != null)
                return false;
        } else if (!xCoord.equals(other.xCoord))
            return false;
        if (yCoord == null) {
            if (other.yCoord != null)
                return false;
        } else if (!yCoord.equals(other.yCoord))
            return false;
        return true;
    }

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
