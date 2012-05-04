package db;

import exceptions.GameEndException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.*;

/**
 *Denne klassen representerer ett spill
 * @author 490501
 * @version 1.0.0
 */
@Entity
public class Game implements Serializable{
    @Id  @GeneratedValue(strategy=GenerationType.AUTO)
    private Long idGame; //primary key 
    private String name; //navn av spill
    private int varighet; //varighet av spill
    private boolean isOpen; //true om løp åpent for alle ellers false
    private long startDate; //klokkeslett for oppstart
    @OneToMany(cascade=CascadeType.PERSIST)
    private ArrayList<GamePunkt> pointList;
    private ArrayList<Long> partisipants; //idier til påmeldte
    private int currentPoint;
    private String authorName;

    public Game() {
    }
    /**
     * constructor
     * @param name
     * @param varighet
     * @param isOpen
     * @param startDate 
     */
    public Game(String authorName, String name, int varighet, boolean isOpen, String start) {
        DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        try {
            Date date = formatter.parse(start);
            this.startDate = date.getTime();
        } catch (ParseException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.authorName = authorName;
        this.name = name;
        this.varighet = varighet;
        this.isOpen = isOpen;
        
        this.pointList = new ArrayList<GamePunkt>();
        this.currentPoint = 0;
    }
    
////////////////////////////////////////////////////////////////////////////////
    //Getter&setter block

    public Long getId() {
        return idGame;
    }

    public boolean isIsOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public int getVarighet() {
        return varighet;
    }

    public void setVarighet(int varighet) {
        this.varighet = varighet;
    }

    public String getAuthorName() {
        return authorName;
    }
    
    
    
    ////////////////////////////////////////////////////////////////////////////
    /**
     * 
     * @param gp 
     */
    public void addPoint(GamePunkt gp) {
        this.pointList.add(gp);
    }
    /**
     * 
     * @return
     * @throws Exception 
     */
    public GamePunkt getNextPunkt() throws Exception {
        if(this.currentPoint < this.pointList.size()) {   
            return this.pointList.get(this.currentPoint++);
        }
        else {
            throw new GameEndException("max antal reached");
        }
    }
    /**
     * 
     * @param id til deltaker
     * @throws Exception 
     */
    public void addPartisipant(long id) throws Exception {
        if(this.partisipants.contains(id)){
            throw new Exception("user allerede i list");
        }
        else {
            this.partisipants.add(id);
        }
    }
    
    /**
     * 
     * @return ArrayList med id'er til deltakere
     */
    public ArrayList<Long> getAllPartisipants() {
        return this.partisipants;
    }
    /**
     * 
     * @param id til deltaker
     * @return true om deltaker meldt på løp ellers false
     */
    public boolean getPartisipant(long id) {
        return (this.partisipants.contains(id));
    }
    

    

}
