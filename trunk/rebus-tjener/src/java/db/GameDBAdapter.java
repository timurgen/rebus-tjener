package db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

/**
 *
 * @author 490501
 */
public class GameDBAdapter {
    EntityManagerFactory emf;
    EntityManager em;
    /**
     * empty constructor
     */
    public GameDBAdapter() {
        emf = Persistence.createEntityManagerFactory("$objectdb/db/game.odb");
        em = emf.createEntityManager();
    }
    //destructor
    @Override
    protected void finalize(){
       em.close();
       emf.close(); 
    }
    /**
     * metode legger spill til database 
     * @param g object av Game klasse
     * @return true om alt er OK ellers exception
     */
    public boolean persistgame(Game g) {
        try {
            em.getTransaction().begin();
            em.persist(g);
            em.getTransaction().commit();
            return true;
        }
        catch(PersistenceException e){
            System.out.println(e.getMessage());
            return false; //bare for å ungå feil i kompiler, aldri returneres
        }
    }
    

}
