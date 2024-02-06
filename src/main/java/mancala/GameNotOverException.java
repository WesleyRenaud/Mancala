package mancala;

/**
 * An exception class used to generate exceptions when trying to access a part 
 * of the game logic that is only relevant when the game is over, when the game 
 * is not over.
 */
public class GameNotOverException extends Exception {
    private static final long serialVersionUID = -1894883535467494497L;
    
    /**
     * Makes a GameNotOverException with a given message.
     * 
     * @param message   The message corresponding to the exception.
     */
    public GameNotOverException(final String message) {
        super(message);
    }
}