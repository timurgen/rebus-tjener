
import db.User;
import db.UserDBAdapter;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author obu
 */
public class Test {
    public static void main(String[] args) {
        UserDBAdapter a = new UserDBAdapter();
        //a.persistUser(new User("test3", "test222"));
        User u = a.getUserByName("test2");
        System.out.println(u.getName());
        System.out.println(a.autentificate("test", "test222w"));
    }
    
}