package db;

import exceptions.GameEndException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.*;

/**
 *Denne klassen representerer ett spill
 * @author 490501
 */
@Entity
public class Game implements Serializable{
    @Id  @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id; //primary key 
    private String name; //navn av spill
    private int varighet; //varighet av spill
    private boolean isOpen; //true om løp åpent for alle ellers false
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date startDate; //klokkeslett for oppstart
    @OneToMany(cascade=CascadeType.PERSIST)
    private ArrayList<GamePunkt> pointList;
    private int currentPoint;

    public Game() {
    }
    /**
     * constructor
     * @param name
     * @param varighet
     * @param pointQuantity
     * @param isOpen
     * @param startDate 
     */
    public Game(String name, int varighet, boolean isOpen, Date startDate) {
        this.name = name;
        this.varighet = varighet;
        this.isOpen = isOpen;
        this.startDate = startDate;
        this.pointList = new ArrayList<GamePunkt>();
        this.currentPoint = 0;
    }
    
////////////////////////////////////////////////////////////////////////////////
    //Getter&setter block

    public Long getId() {
        return id;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getVarighet() {
        return varighet;
    }

    public void setVarighet(int varighet) {
        this.varighet = varighet;
    }
    ////////////////////////////////////////////////////////////////////////////
    /**
     * 
     * @param gp 
     */
    public void addPoint(GamePunkt gp) {
        this.pointList.add(gp);
    }
    public GamePunkt getNextPunkt() throws Exception {
        if(this.currentPoint < this.pointList.size()) {   
            return this.pointList.get(this.currentPoint++);
        }
        else {
            throw new GameEndException("max antal reached");
        }
    }
    

}
