package db;

import java.io.Serializable;
/*import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;*/

/**
 *
 * @author 490501
 * @version 1.0.0
 */

public class Result implements Serializable {
    
    private Long id; //
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    private long gamerId; //userid
    private long result; // medgått tid i millisekunder
    private long points; //

    public long getGamerId() {
        return gamerId;
    }

    public void setGamerId(long gamerId) {
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

    public Result(long gamerId, long result, long points) {
        this.gamerId = gamerId;
        this.result = result;
        this.points = points;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }
    
    
}
