
package db;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
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
        System.out.println("entity manager factory OK"); //debugg
        em = emf.createEntityManager();
        System.out.println("entity manager OK"); //debugg
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
     * @return true om bruker ble lagret ellers false
     */
    public boolean persistUser(User u) {
        try {
            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();
            System.out.println("bruker lagt til database"); //debugg
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Metode sjekker om bruker med gitt brukernavn eksisterer i database
     * @param u Bruker objekt
     * @return true om brukernavn eksisterer ellers false 
     */
    public boolean checkIfUserExists(User u) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> q = cb.createQuery(User.class);
        Root<User> c = q.from(User.class);
        q.select(c);
        ParameterExpression<String> p = cb.parameter(String.class);
        q.where(cb.equal(c.get("username"), p));
        TypedQuery query = em.createQuery(q);
        List<User> result = query.getResultList();
        return true; //om brukernavn eksisterer i database
    }
    
    
    
}
