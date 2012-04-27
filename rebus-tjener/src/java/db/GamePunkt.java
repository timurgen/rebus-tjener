package db;

import java.io.Serializable;

/**
 * Denne klassen representerer ett punkt i spill
 * @author 490501
 */
public class GamePunkt implements Serializable{
    private double lat; //lattitude
    private double lng; //longitude
    private int radius; //radius for proksimity alarm
    private String name;//punktnavn
    private String rebus;//rebustekst

}
