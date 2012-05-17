package db;

import java.util.List;
import javax.jdo.JDOHelper;
import javax.persistence.*;

/**
 * Klass håndterer alle operasjoner knyttet til database som inneholder løp objekter
 * @author 490501
 * @version 1.0.0
 */
public class GameDBAdapter {
    
    /**
     * represents the relevant database
     */
    EntityManagerFactory emf;
    
    /**
     * represents a connection to the database
     */
    EntityManager em;
    
    /**
     * empty default constructor
     */
    public GameDBAdapter() {
        emf = Persistence.createEntityManagerFactory("$objectdb/db/game.odb");
        em = emf.createEntityManager();
    }
    
    /**
     * Destruktør, stenger forbindelse med database
     */
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
            em.clear();
            em.close();
            return true;
        }
        catch(PersistenceException e){
            System.out.println(e.getMessage());
            return false; //bare for å ungå feil i kompiler, aldri returneres
        }
    }
    
    /**
     * returnerer løp med gitt id
     * @param id
     * @return Game object lagred i database
     */
    public Game getGameById(long id) {
        Game g = em.find(Game.class, id);
        return g;
    }
    
    /**
     * add rebuspunkt til spill
     * @param gp
     * @param gameId 
     */
    public void addPointToGameInDB(GamePunkt gp, long gameId) {
        Game g = this.getGameById(gameId);
        em.getTransaction().begin();
        g.addPoint(gp);
        JDOHelper.makeDirty(g, "pointList");
        em.getTransaction().commit();
        em.clear();
    }
    /**
     * Registrerer ny bruker til løp
     * @param gameId - id til løp hvor en vil bli deltaker
     * @param userId - id til bruker som vil bli deltaker
     * @throws Exception 
     */
    public void addPartisipantToGame(long gameId, long userId) throws Exception {
        Game g = this.getGameById(gameId);
        em.getTransaction().begin();
        g.addPartisipant(userId);
        JDOHelper.makeDirty(g, "partisipants");
        em.getTransaction().commit();
        em.clear();
    }
    /**
     * Adderer resultater for en bruker til gitt løp
     * @param gameid - id til løp 
     * @param userid - id til bruker som gjennomførte løp
     * @param points - poenger som en bruker fikk
     * @param result - medgått tid 
     */
//    public void addResultToGame(long gameid, long userid, int points, long result) throws Exception {
//        Game g = this.getGameById(gameid);
//        em.getTransaction().begin();
//        g.setResult(gameid, userid, result, points);
//        JDOHelper.makeDirty(g, "partisipants");
//        em.getTransaction().commit();
//        em.clear();
//    }
    
        /**
     * Adderer resultater for en bruker til gitt løp
     * @param gameid - id til løp 
     * @param userName - navn til bruker som gjennomførte løp
     * @param points - poenger som en bruker fikk
     * @param result - medgått tid 
     */
    public void addResultToGame(long gameid, String userName, int points, long result) throws Exception {
        Game g = this.getGameById(gameid);
        em.getTransaction().begin();
        g.setResult(gameid, userName, result, points);
        JDOHelper.makeDirty(g, "results");
        em.getTransaction().commit();
        em.clear();
    }
    /**
     * 
     * @return alle løp som er lagret i database
     */
    public List<Game> getAllGames () {
        TypedQuery<Game> q1 = em.createQuery("SELECT g FROM Game g", Game.class);
        List<Game> results = q1.getResultList();
        return results;
    }
    
    /**
     * Stenger forbindelse med database
     */
    public void closeConnection() {
        em.clear();
        em.close();
        emf.close();
    }
    

}
