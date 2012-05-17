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
    /**
     * pribamry key
     */
    private Long idGame;
    /**
     * navn av l�p
     */
    private String name;
    /**
     * varighet av l�p
     */
    private int varighet;
    /**
     * true om l�p �pent for alle ellers false (kun for registrerte brukere)
     */
    private boolean isOpen; 
    /**
     * klokkeslett for oppstart
     */
    private long startDate; //
    /**
     * list som holder rede p� rebuspunkter i et l�p
     */
    private ArrayList<GamePunkt> pointList;
    /**
     * id-verdier til p�meldte deltakere
     */
    private ArrayList<Long> partisipants;
    /**
     * nummer p� n�v�rende punkt
     */
    private int currentPoint;
    /**
     * navn av bruker som skapte l�p
     */
    private String authorName; 
    /**
     * list som holder rede p� resultater som tilh�rer l�p
     */
    private ArrayList<Result> results; 

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
        this.authorName = authorName;
        this.name = name;
        this.varighet = varighet;
        this.isOpen = isOpen;
        this.partisipants = new ArrayList<Long>();
        this.pointList = new ArrayList<GamePunkt>();
        this.results = new ArrayList<Result>();
        this.currentPoint = 0;
    }
    
/******************************************************************************/
    
    /**
     * 
     * @return id til l�p
     */
    public Long getId() {
        return idGame;
    }
    /**
     * 
     * @return true om l�p er �pent for alle, false - om kun registrerte brukere kan melde seg p�
     */
    public boolean isIsOpen() {
        return isOpen;
    }
    /**
     * 
     * @param isOpen set true dersom l�p er �pent for alle ellers false
     */
    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }
    /**
     * 
     * @return navn av spill
     */
    public String getName() {
        return name;
    }
    /**
     * 
     * @param name lagrer nytt navn av spill
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * 
     * @return returnerer klokkeslett for oppstart i millisekunder fra 1970 �r
     */
    public long getStartDate() {
        return startDate;
    }
    /**
     * 
     * @param startDate setter opp klokkeslett for oppstart i milllisekunder fra 1970 �r
     */
    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }
    /**
     * 
     * @return varighet av spill, gitt i minutter
     */
    public int getVarighet() {
        return varighet;
    }
    /**
     * 
     * @param varighet setter opp varighet av spill i minutter
     */
    public void setVarighet(int varighet) {
        this.varighet = varighet;
    }
    /**
     * 
     * @return returnerer navn av author
     */
    public String getAuthorName() {
        return authorName;
    }
    /**
     * 
     * @param idGame setter opp id til l�p
     */
    public void setIdGame(Long idGame) {
        this.idGame = idGame;
    }

    /**
     * 
     * @return 
     */
    public int getCurrentPoint() {
        return currentPoint;
    }
    
    
    
    /*************************************************************************/
    
    /**
     * Lagrer nytt rebuspunkt til l�p
     * @param gp nytt punkt som skal tilh�re l�p
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
    public void setResult(long gameId, String userName, long result, int points) throws Exception {
        if(this.results.contains(new Result(userName, result,points))) {
            throw new Exception("result exists for this gamer id");
        }
        this.results.add(new Result(userName, result, points));
    }
    
    /**
     * 
     * @return returnerer list med resultater som tilh�rer l�p
     */
    public ArrayList<Result> getResults() {
        return this.results;
    }
    

    

}
