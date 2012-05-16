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

/**
 *Denne klassen representerer ett spill
 * @author 490501
 * @version 1.0.0
 */
public class Game implements Serializable{
    private Long idGame; //primary key 
    private String name; //navn av spill
    private int varighet; //varighet av spill
    private boolean isOpen; //true om l�p �pent for alle ellers false
    private long startDate; //klokkeslett for oppstart
    private ArrayList<GamePunkt> pointList; //list som holder rede p� punkter i et l�p
    private ArrayList<Long> partisipants; //idier til p�meldte
    private int currentPoint; //nummer p� n�v�rende punkt
    private String authorName; //navn av bruker som skapte l�p
    private ArrayList<Result> results; //list som holder rede p� resultater som tilh�rer l�p

    public Game() {
    }
    /**
     * constructor
     * @param name - Hvem som skapte l�p
     * @param varighet av l�p gitt i minutter
     * @param isOpen - true om det er mulig � delta uten registrasjon
     * @param startDate  - klokkeslett
     */
    public Game(String authorName, String name, int varighet, boolean isOpen, String start) {
        //parser dato fra String
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        System.out.print(start);
        try {
            Date date = formatter.parse(start);
            this.startDate = date.getTime();
        } catch (ParseException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        this.authorName = authorName;
        this.name = name;
        this.varighet = varighet;
        this.isOpen = isOpen;
        this.partisipants = new ArrayList<Long>();
        this.pointList = new ArrayList<GamePunkt>();
        this.results = new ArrayList<Result>();
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
    
    public void setIdGame(Long idGame)
    {
    	this.idGame = idGame;
    }
    
    
    
    ////////////////////////////////////////////////////////////////////////////
    /**
     * 
     * @param gp adderer nytt punkt som skal tilh�re l�p
     */
    public void addPoint(GamePunkt gp) {
        if(gp == null)
            return;
        this.pointList.add(gp);
    }
    
    /**
     * 
     * @return returnerer neste punkt etter at foreg�ende var gjennomf�rt
     * @throws Exception dersom det finnes ikke neste punkt
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
     * Adderer registrert bruker til deltakere
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
     * @return true om deltaker meldt p� l�p ellers false
     */
    public boolean getPartisipant(long id) {
        return (this.partisipants.contains(id));
    }
    /**
     * lagrer resultater i database
     * @param gamerId
     * @param result
     * @param gameId 
     * @param points 
     * @throws Exception 
     */
    public void setResult(long gameId, long userId, long result, long points) throws Exception {
        if(this.results.contains(new Result(userId, result,points))) {
            throw new Exception("result exists for this gamer id");
        }
        this.results.add(new Result(userId, result, points));
    }
    
    /**
     * 
     * @return returnerer list med resultater
     */
    public ArrayList<Result> getResults() {
        return this.results;
    }
    

}
