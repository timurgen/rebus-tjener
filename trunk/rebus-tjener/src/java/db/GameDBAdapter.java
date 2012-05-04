package db;

import java.util.ArrayList;
import java.util.List;
import javax.jdo.JDOHelper;
import javax.persistence.*;

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
    public boolean persistGame(Game g) {
        try {
            em.getTransaction().begin();
            em.persist(g);
            em.getTransaction().commit();
            em.close();
            emf.close();
            return true;
        }
        catch(PersistenceException e){
            System.out.println(e.getMessage());
            return false; //bare for å ungå feil i kompiler, aldri returneres
        }
    }
    public Game getGameById(long id) {
        Game g = em.find(Game.class, id);
        return g;
    }
    
    public void addPointToGameInDB(GamePunkt gp, long gameId) {
        Game g = this.getGameById(gameId);
        em.getTransaction().begin();
        g.addPoint(gp);
        JDOHelper.makeDirty(g, "pointList");
        em.getTransaction().commit();
        
    }
    
    public List<Game> getAllGames () {
        TypedQuery<Game> q1 = em.createQuery("SELECT g FROM Game g", Game.class);
        List<Game> results = q1.getResultList();
        return results;
    }
    

}
