
import db.*;
import java.util.Date;
import java.util.List;

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
        a.persistUser(new User("tim", "tim"));
        User u = a.getUserByName("tim");
        System.out.println(u.getName());
        //System.out.println(a.autentificate("test", "test222w"));
        //GameDBAdapter b = new GameDBAdapter();
        //Game g = new Game("name", 90,true, new Date(99999999));
        //GamePunkt gg = new GamePunkt(47.5645,125.4565,50,"name 5", "text of mega rebus");
        //g.addPoint(gg);   
        //gg = new GamePunkt(48.5645,125.4565,50,"name 6", "text of mega rebus");
        //g.addPoint(gg);
        //b.persistGame(g);
        //b.addPointToGameInDB(gg, 1);
        //List<Game> c = b.getAllGames();
        //for(int i = 0; i < c.size(); i++) {
        //    System.out.println(c.get(i).getName());
        //}
        
    }
    
}
