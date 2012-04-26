
package db;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author 490501
 */

@Entity
public class User {
    @Id
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
