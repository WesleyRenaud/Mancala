package mancala;

/**
 * An exception class used to generate exceptions when a move that
 * does not fall withi the rules of the game has been requested.
 */
public class InvalidMoveException extends Exception {
    private static final long serialVersionUID = 4053673262215290176L;

    /**
     * Creates an InvalidMoveException with a given message.
     * 
     * @param message   The message corresponding to the exception.
     */
    public InvalidMoveException(final String message) {
        super(message);
    }
}