
package db;

import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Klassen representerer uregistrerte bruker som deltar i løp
 * @author 490501
 * @version 1.0.0
 */
@Entity
public class Guest {
    /**
     * primary key
     */
    @Id
    private String id;
    
    /**
     * 
     * @return id til gjest
     */
    public String getId() {
        return id;
    }
    
    /**
     * 
     * @param id setter opp id til gjest
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * holder rede på påmeldte løp
     */
    private ArrayList<Long> gameList;

    public Guest(String id) {
        this.id = id;
        this.gameList = new ArrayList<Long>();
    }

    public Guest() {
        this.gameList = new ArrayList<Long>();
    }
    
    /**
     * registrere seg på løp
     */
    public void addGame(long id) throws Exception {
        if(this.gameList.contains(id))
            throw new Exception("gest registrert allerede");
        this.gameList.add(id);
    }
    
    /**
     * returnere om bruker registrerte seg på gitt løp
     */
    public boolean isRegistred(long gameId){
        return this.gameList.contains(gameId);
    }
    
    
    
}
