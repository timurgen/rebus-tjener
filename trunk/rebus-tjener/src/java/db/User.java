
package db;

import java.io.Serializable;
import javax.jdo.annotations.Unique;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author 490501
 * @author 1.0.0
 */

@Entity
public class User implements Serializable {
    @Id  @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    /**
     * default constructor uten argumenter
     */
    public User() {
    }

    public Long getId() {
        return id;
    }

    //variabler
    @Unique private String name;
    private String pass;
    ////////////////////////////

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    /**
     * constructor
     * @param name brukernavn
     * @param pass passord (i klartekst nå)
     */
    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
        //System.out.println("user created OK"); //debugg
    }
    
    
}
