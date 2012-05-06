package student.hin.no;

import java.io.Serializable;

public class GamePunktRebus implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double lat; 	//lattitude
    private double lng; 	//longitude
    private int radius; 	//radius for proksimity alarm
    private String name;	//punktnavn
    private String rebus;	//rebustekst
    private Long idPunkt;
	
    public GamePunktRebus() {
    }
    
    /**
     * 
     * @param lat lattitude
     * @param lng longitude
     * @param radius proximity alarm radius
     * @param name name of point
     * @param rebus text of rebus
     */
    public GamePunktRebus(double _lat, double _lng, int _radius, String _name, String _rebus) {
        lat = _lat;
        lng = _lng;
        radius = _radius;
        name = _name;
        rebus = _rebus;
    }//end of constructor
    
    /**
     * GET or SET metodes
     * ****************************************************************************************
     */
    
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
    
    /**
     * End of Get or SET metodes
     */
    
}//end of GamePunkRebus class
