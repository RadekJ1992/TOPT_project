import java.util.List;

/**
 * Klasa reprezentująca pakiet
 *
 * Created by radoslawjarzynka on 11.10.2015.
 */
public class Packet {

    /**
     * Identyfikator
     */
    private Integer id;


    /**
     * Trasa pakietu.
     */
    private List<Node> route;

    /**
     * Time To Live pakietu.
     */
    private Integer ttl;
    
    /**
     * Liczba hopów, jakie wykonał pakiet
     */
    private Integer hops = 0;

    public void incrementHops() {
        hops++;
    }
    
    public Integer getHops() {
        return hops;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Node> getRoute() {
        return route;
    }

    public void setRoute(List<Node> route) {
        this.route = route;
    }

    public Integer getTtl() {
        return ttl;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }
}
