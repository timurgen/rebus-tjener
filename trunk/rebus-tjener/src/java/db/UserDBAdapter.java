
package db;

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
    /**
     * 
     * @param id
     * @return 
     */
    public User getUserById(long id) {
        User u1 = em.find(User.class, id);
        return u1;      
    }
    
    /**
     * 
     * @param name
     * @return 
     */
    public User getUserByName(String name) {
        TypedQuery q = em.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class);
        User u1 = (User) q.setParameter("name", name).getSingleResult();
        return u1;     
    }
    /**
     * 
     * @param name
     * @param pass
     * @return 
     */
    public boolean autentificate(String name, String pass) {
        TypedQuery q = em.createQuery("SELECT u FROM User u WHERE u.name = :name AND u.pass = :pass", User.class);
        try {
            User u1 = (User)q.setParameter("name", name).setParameter("pass", pass).getSingleResult();
            return true;
        }
        catch(PersistenceException pe) {
            System.out.println(pe.getMessage());
            return false;
        }
    }
    
    
    
    
}
