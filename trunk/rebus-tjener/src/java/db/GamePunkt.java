package db;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * Denne klassen representerer ett rebuspunkt i løp
 * @author 490501
 * @version 1.0.0
 */
@Entity
public class GamePunkt implements Serializable{
    
    /**
     * lattitude til punkt
     */
    private double lat;
    
    /**
     * longitude til punkt
     */
    private double lng;
    
    /**
     * radius for proximity alarm gitt i meter
     */
    private int radius;
    
    /**
     * navn av punkt
     */
    private String name;
    
    /**
     * rebustekst
     */
    private String rebus;//rebustekst
    
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    /**
     * primary key, id til punkt
     */
    private Long idPunkt; // id til punkt 

    /**
     * default empty constructor
     */
    
    public GamePunkt() {
    //   
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
    
    /**************************************************************************/
   
    /**
     * 
     * @return lattitude til punkt
     */
    public double getLat() {
        return lat;
    }
    
    /**
     * 
     * @param lat stter lattitude til punkt
     */
    public void setLat(double lat) {
        this.lat = lat;
    }
    
    /**
     * 
     * @return longitude til punkt
     */
    public double getLng() {
        return lng;
    }
    
    /**
     * 
     * @param lng setter longitude til punkt
     */
    public void setLng(double lng) {
        this.lng = lng;
    }
    
    /**
     * 
     * @return navn av punkt
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @param name stter opp navn av punkt
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 
     * @return redaius av punkt gitt i meter 
     */
    public int getRadius() {
        return radius;
    }
    
    /**
     * 
     * @param radius setter oppp radius av punkt gitt i meter
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * 
     * @return rebus tekst
     */
    public String getRebus() {
        return rebus;
    }
    
    /**
     * 
     * @param rebus setter rebustekst
     */
    public void setRebus(String rebus) {
        this.rebus = rebus;
    }
    
    /**
     * 
     * @return id til punkt
     */
    public Long getIdPunkt() {
        return idPunkt;
    }
    
    /**
     * 
     * @param idPunkt setter opp id til punkt
     */
    public void setIdPunkt(Long idPunkt) {
        this.idPunkt = idPunkt;
    }

}
