package mancala;

/**
 * An exception class used for generated exceptions when a player has
 * been accessed that is not actually part of the game.
 */
public class NoSuchPlayerException extends Exception {
    private static final long serialVersionUID = 7791337417655979794L;
    
    /**
     * Makes a NoSuchPlayerException with a given message.
     * 
     * @param message   The message corresponding to the exception.
     */
    public NoSuchPlayerException(final String message) {
        super(message);
    }
}