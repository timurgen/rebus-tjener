package exceptions;

/**
 *
 * @author 490501
 */
public class GameEndException extends Exception{

    public GameEndException(String max_antal_reached) {
        super(max_antal_reached);
    }

}
