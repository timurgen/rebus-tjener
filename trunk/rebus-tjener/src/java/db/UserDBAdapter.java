
package db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

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
        em = emf.createEntityManager();
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
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    /**
     * Metode sjekker om bruker med gitt brukernavn eksisterer i database
     * @param u Bruker objekt
     * @return true om brukernavn eksisterer ellers false 
     */
    public boolean checkIfUserExists(User u) {
        return true; //om brukernavn eksisterer i database
    }
    
    
    
}
