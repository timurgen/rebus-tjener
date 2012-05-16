package student.hin.no;
import java.io.Serializable;

/**
 * Denne klassen representerer ett punkt i spill
 * @author 490501
 * @version 1.0.0
 */

public class GamePunkt implements Serializable{
    private double lat; //lattitude
    private double lng; //longitude
    private int radius; //radius for proksimity alarm
    private String name;//punktnavn
    private String rebus;//rebustekst
    private Long idPunkt; // id til punkt

    /**
     * default empty constructor
     */
    public GamePunkt() {
       
    }
    /**
     *
     * @param lat lattitude
     * @param lng longitude
     * @param radius proximity alarm radius
     * @param name name of point
     * @param rebus text of rebus
     */
    public GamePunkt(double lat, double lng, int radius, String name, String rebus) {
        this.lat = lat;
        this.lng = lng;
        this.radius = radius;
        this.name = name;
        this.rebus = rebus;
    }
   
    /////////////////////////////////////////////////////////////////////////////
    //Getter&setter

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getRebus() {
        return rebus;
    }

    public void setRebus(String rebus) {
        this.rebus = rebus;
    }

    public Long getIdPunkt() {
        return idPunkt;
    }

    public void setIdPunkt(Long idPunkt) {
        this.idPunkt = idPunkt;
    }

}

