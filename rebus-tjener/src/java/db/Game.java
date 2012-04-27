package db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *Denne klassen representerer ett spill
 * @author 490501
 */
public class Game implements Serializable{
    @Id  @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id; //primary key 
    private String name; //navn av spill
    private int varighet; //varighet av spill
    private int pointQuantity; //antall av punkter
    private boolean isOpen; //true om løp åpent for alle ellers false
    private Date startDate; //klokkeslett for oppstart
    /**
     * constructor
     * @param name
     * @param varighet
     * @param pointQuantity
     * @param isOpen
     * @param startDate 
     */
    public Game(String name, int varighet, int pointQuantity, boolean isOpen, Date startDate) {
        this.name = name;
        this.varighet = varighet;
        this.pointQuantity = pointQuantity;
        this.isOpen = isOpen;
        this.startDate = startDate;
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

    public int getPointQuantity() {
        return pointQuantity;
    }

    public void setPointQuantity(int pointQuantity) {
        this.pointQuantity = pointQuantity;
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
    
    
    

}
