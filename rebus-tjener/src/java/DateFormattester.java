
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 490501
 */
public class DateFormatTester {
    public static void main(String[] args) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String date_string = "24-05-2012 18:53:13";
        Date date = formatter.parse(date_string);
        System.out.println(date.getTime());
    }
}
