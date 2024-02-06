package mancala;

/**
 * An interface used to group constants corresponding to player's
 * positions, and their pit's positions.
 */
public interface PlayerPositions {
    /**
     * A number corresponding to player one's spot in the game.
     */
    int PLAYER_ONE_NUM = 1;
    /**
     * A number corresponding to playee two's spot in the game.
     */
    int PLAYER_TWO_NUM = 2;
    /**
     * A number corresponding to the position of player one's first pit.
     */
    int PLAYER_ONE_START = 1;
    /**
     * A number corresponding to the position of player two's first pit.
     */
    int PLAYER_TWO_START = 7;
    /**
     * A number corresponding to the position of the end of player one's
     * pits in the array of countables in the data structure class.
     */
    int PLAYER_ONE = 6;
    /**
     * A number corresponding to the position of the end of player two's
     * pits in the array of countables in the data structure class.
     */
    int PLAYER_TWO = 13;    
}