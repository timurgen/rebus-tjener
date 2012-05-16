package exceptions;

/**
 *
 * @author 490501
 * @version 1.0.0
 */
public class GameEndException extends Exception{

    public GameEndException(String max_antal_reached) {
        super(max_antal_reached);
    }

}