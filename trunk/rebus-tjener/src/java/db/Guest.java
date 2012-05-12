/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author 490501
 */
@Entity
public class Guest {
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
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
