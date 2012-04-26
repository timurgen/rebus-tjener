
package db;

import java.util.List;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

/**
 *
 * @author 490501
 */
public class UserDBAdapter {
    EntityManagerFactory emf;
    EntityManager em;
    
    //constructor
    public UserDBAdapter() {
        emf = Persistence.createEntityManagerFactory("$objectdb/db/usr.odb");
        //System.out.println("entity manager factory OK"); //debugg
        em = emf.createEntityManager();
        //System.out.println("entity manager OK"); //debugg
    }
    //destructor
    @Override
    protected void finalize(){
       em.close();
       emf.close(); 
    }
    /**
     * legger ny bruker til database
     * @param u
     * @return true om bruker ble lagret ellers persistence exception
     */
    public boolean persistUser(User u) {
        try {
            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();
            //System.out.println("bruker lagt til database"); //debugg
            return true;
        }
        catch(PersistenceException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    
    
}
