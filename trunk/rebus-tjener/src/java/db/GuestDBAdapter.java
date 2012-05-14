/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import javax.jdo.JDOHelper;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

/**
 *
 * @author 490501
 * @version 1.0.0
 */
public class GuestDBAdapter {
    EntityManagerFactory emf;
    EntityManager em;    
    
        //constructor
    public GuestDBAdapter() {
        emf = Persistence.createEntityManagerFactory("$objectdb/db/guest.odb");
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
     * legger ny gest til database
     * @param g
     * @return true om bruker ble lagret ellers persistence exception
     */
    public boolean persistGuest(Guest g) {
        try {
            em.getTransaction().begin();
            em.persist(g);
            em.getTransaction().commit();
            System.out.println("besøkende lagt til database"); //debugg
            em.clear();
            return true;
        }
        catch(PersistenceException e){
            System.out.println(e.getMessage());
            em.clear();
            em.close();
            emf.close();
            return false;
        }
    }
    
    public Guest getGuestById(String id) {
        Guest g1 = em.find(Guest.class, id);
        return g1;      
    }
    public void addGame(long gameId, String guestId) throws Exception {
        Guest g = this.getGuestById(guestId);
        em.getTransaction().begin();
        g.addGame(gameId);
        JDOHelper.makeDirty(g, "gameList");
        em.getTransaction().commit();
    }
}
