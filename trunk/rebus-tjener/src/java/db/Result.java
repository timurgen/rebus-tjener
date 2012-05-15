
package db;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author 490501
 * @version 1.0.0
 */
@Entity
public class Result implements Serializable {
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id; //
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    private String gamerId; //userid eller gestid
    private long result; // medgått tid i millisekunder
    private long points; //

    public String getGamerId() {
        return gamerId;
    }

    public void setGamerId(String gamerId) {
        this.gamerId = gamerId;
    }

    public long getResult() {
        return result;
    }

    public void setResult(long result) {
        this.result = result;
    }

    public Result() {
    }

    public Result(String gamerId, long result) {
        this.gamerId = gamerId;
        this.result = result;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }
    
    
}
