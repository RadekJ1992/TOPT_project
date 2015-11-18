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
    private List<Link> route;

    /**
     * Time To Live pakietu.
     */
    private Integer ttl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Link> getRoute() {
        return route;
    }

    public void setRoute(List<Link> route) {
        this.route = route;
    }

    public Integer getTtl() {
        return ttl;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }
}
