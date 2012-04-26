
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
        a.persistUser(new User("test2", "test222"));
    }
    
}
