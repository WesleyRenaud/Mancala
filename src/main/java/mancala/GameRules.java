package mancala;

import java.io.Serializable;

/**
 * Abstract class representing the rules of a Mancala game.
 * KalahRules and AyoRules will subclass this class.
 */
public abstract class GameRules implements Serializable, PlayerPositions
{
    private static final long serialVersionUID = -4089065002380176909L;
    
    /**
     * The data structure which the game is played on.
     */
    final private MancalaDataStructure gameBoard;
    /**
     * The number corresponding to the current player of the game.
     */
    private int currentPlayer = 1; // Player number (1 or 2)
    /**
     * Used at the end of a turn to indicate whether we are to perform a
     * steal or not.
     */
    private boolean performSteal = false;
    /**
     * The index of the last pit a stone was put in during a turn.
     */
    private int lastPitIndex = -1;

    /**
     * Constructor to initialize the game board.
     */
    public GameRules() {
        gameBoard = new MancalaDataStructure();
        gameBoard.setUpPits();
    }

    /**
     * Gets the number of stones in a pit.
     *
     * @param pitNum    The number of the pit.
     * @return  The number of stones in the pit.
     */
    public int getNumStones(final int pitNum) {
        return gameBoard.getNumStones(pitNum);
    }

    /**
     * Gets the number of stones in a player's pit.
     * 
     * @param playerNum The player whose store we are checking.
     * @return  The number of stones in the store.
     */
    public int getStoreCount(final int playerNum) {
        return gameBoard.getStoreCount(playerNum);
    }

    /**
     * Gets the game data structure.
     *
     * @return  The MancalaDataStructure.
     */
    MancalaDataStructure getDataStructure() {
        return gameBoard;
    }

    /**
     * Checks if a side (player's pits) is empty.
     *
     * @param pitNum    The player whose side to check.
     * @return  True if the side is empty, false otherwise.
     */
    boolean isSideEmpty(final int playerNum) {
        return gameBoard.isSideEmpty(playerNum);
    }

    /**
     * Sets the current player.
     *
     * @param playerNum The player number (1 or 2).
     */
    public void setPlayer(final int playerNum) {
        currentPlayer = playerNum;
    }

    /**
     * Performs a move and return the number of stones added to the player's store.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return  The number of stones added to the player's store.
     * @throws  InvalidMoveException If the move is invalid.
     */
    public abstract int moveStones(int startPit, int playerNum) throws InvalidMoveException;

    /**
     * Distributes stones from a pit and return the number distributed.
     *
     * @param startPit  The starting pit for distribution.
     * @return  The number of stones distributed.
     */
    abstract int distributeStones(int startPit);

    /**
     * Capture stones from the opponent's pit and return the number captured.
     *
     * @param stoppingPoint The stopping point for capturing stones.
     * @return  The number of stones captured.
     */
    abstract int captureStones(int stoppingPoint);

    /**
     * Register two players and set their stores on the board.
     *
     * @param one   The first player.
     * @param two   The second player.
     */
    public void registerPlayers(final Player one, final Player two) {
        /* make a new store in this method, set the owner
         then use the setStore(store,playerNum) method of the data structure*/
        final Store store1 = new Store(one);
        gameBoard.setStore(store1, 1);
        one.setStore(store1);
        final Store store2 = new Store(two);
        gameBoard.setStore(store2, 2);
        two.setStore(store2);
    }

    /**
     * Resets the game board by setting up pits and emptying stores.
     */
    public void resetBoard() {
        gameBoard.setUpPits();
        gameBoard.emptyStores();
    }

    /**
     * Returns the number corresponding to the current player.
     * 
     * @return  the number corresponding to the current player.
     */
    public int getPlayer() {
        return currentPlayer;
    }
    
    /**
     * Checks for an invalid move by studying the player, pit position, and the number of stones inside.
     * 
     * @param startPit  The provided pit to start from.
     * @param playerNum The number corresponding to the current player.
     * @throws InvalidMoveException If the move is deemed invalid.
     */
    protected void checkForInvalidMove(final int startPit, final int playerNum) throws InvalidMoveException {
        if (startPit < 1 || startPit > 12) {
            throw new InvalidMoveException("Starting Pit Out Of Bounds");
        }
        if (playerNum < 1 || playerNum > 2) {
            throw new InvalidMoveException("Invalid Player Number");
        }
        if (getNumStones(startPit) == 0) {
            throw new InvalidMoveException("Empty Starting Pit");
        }
    }

    /**
     * Checks if the board needs to complete a steal.
     * 
     * @param countable The last countable a stone was put in.
     */
    protected void checkForSteal(final Countable countable) {
        // if the countable is a pit, we could have a steal
        if (countable.getClass().equals(Pit.class)) {
            final int pitPos = getDataStructure().getPitPos((Pit)countable);
            // we have to check if the pit has one stone, and is on the current player's side
            if (getDataStructure().isOnPlayersSide(pitPos, currentPlayer) && getDataStructure().getNumStones(pitPos)==1
                && getNumStones(13 - pitPos)!=0) {
                setPerformSteal(true);
            }
            setLastPitIndex(pitPos);
        }
    }

    /**
     * Checks if we need to give the current player a steal.
     * 
     * @param countable The last countable a stone was put in.
     */
    protected void checkForExtraTurn(final Countable countable) {
        //if the countable is a store, we may need to give an extra turn      
        if (!(countable.getClass().equals(Store.class) && getDataStructure().isPlayersStore((Store)countable, currentPlayer))) {
            swapPlayers();
        }
    }

    /**
     * Switches the current player of the game.
     */
    protected void swapPlayers() {
        if (currentPlayer == PLAYER_ONE_START) {
            currentPlayer = 2;
        } else {
            currentPlayer = 1;
        }
    }

    /**
     * Gets the number corresponding to the current player.
     * 
     * @return  The number corresponding to the current player.
     */
    protected int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Move any stones leftover at the end of the game into the corresponding stores.
     */
    protected void moveFinalStonesIntoStores() {
        for (int i = 1; i <= 6; i++) {
            final int numStones = gameBoard.removeStones(i);
            gameBoard.addToStore(1, numStones);
        }
        for (int i = 7; i <= 12; i++) {
            final int numStones = gameBoard.removeStones(i);
            gameBoard.addToStore(2, numStones);
        }
    }


    /**
     * Gets the number corresponding to the player who is the winner of the game.
     * 
     * @return  The number corresponding to the player (1 or 2) or 0 if it is a tie.
     * @throws GameNotOverException If the game is not over yet.
     */
    protected int getWinner() throws GameNotOverException {
        if (!(isSideEmpty(1) || isSideEmpty(2))) {
            throw new GameNotOverException("Game Not Over");
        }

        int winner = 0;
        moveFinalStonesIntoStores();
        if (getStoreCount(PLAYER_ONE_NUM) > getStoreCount(PLAYER_TWO_NUM)) {
            winner = 1;
        } else if (getStoreCount(PLAYER_TWO_NUM) > getStoreCount(PLAYER_ONE_NUM)) {
            winner = 2;
        }
        return winner;
    }

    /**
     * Sums and returns the number of stones in a player's pits.
     * 
     * @param playerNum The player whose pits to sum.
     * @return  The sum of the player's pits.
     */
    protected int sumStonesInPlayersPits(final int playerNum) {
        int sumOfPits = 0;
        if (playerNum < 1 || playerNum > 2) {
            throw new RuntimeException("Invalid Player Num");
        } else if (playerNum == PLAYER_ONE_START) {
            for (int i = PLAYER_ONE_START; i <= PLAYER_ONE; i++) {
                sumOfPits += getNumStones(i);
            }
        } else {
            for (int i = PLAYER_TWO_START; i < PLAYER_TWO; i++) {
                sumOfPits += getNumStones(i);
            }
        }
            
        return sumOfPits;
    }

    /**
     * Connects the players to their stores.
     * 
     * @param playerOne The player for the first store.
     * @param playerTwo The player for the second store.
     */
    protected void connectPlayersToStores(final Player playerOne, final Player playerTwo) {
        getDataStructure().connectPlayersToStores(playerOne, playerTwo);
    }

    /**
     * Sets a variable for whether we should perform a steal on the next tunr.
     * 
     * @param doPerformSteal    Whether we should perform the steal.
     */
    protected void setPerformSteal(final boolean doPerformSteal) {
        performSteal = doPerformSteal;
    }

    /**
     * Checks if we are to perform a steal at the end of a move.
     * 
     * @return  True if we are to steal, and false otherwise.
     */
    protected boolean doPerformSteal() {
        return performSteal;
    }

    /**
     * Sets the index of the last pit which a stone landed in.
     * 
     * @param countable The countable where the last stone landed in.
     */
    protected void setLastPitIndex(final Countable countable) {
        lastPitIndex = getDataStructure().getPitPos((Pit)countable);
    }

    /**
     * Sets the index of the last pit which a stone landed in.
     * 
     * @param pitIndex  The number of the pit where the last stone landed in.
     */
    protected void setLastPitIndex(final int pitIndex) {
        lastPitIndex = pitIndex;
    }

    /**
     * Gets the index of the last pit which a stone landed in.
     * 
     * @return  The number of the pit where the last stone landed in.
     */
    protected int getLastPitIndex() {
        return lastPitIndex;
    }

    /**
     * Gets the number of the first pit of the current player.
     * 
     * @return  The number for the current player's first pit.
     */
    public int currPlayerStartPit() {
        int currStartPit = PLAYER_TWO_START;
        if (currentPlayer == PLAYER_ONE_START) {
            currStartPit = PLAYER_ONE_START;
        }
        return currStartPit;
    }

    /**
     * Gets the number of the last pit of the current player.
     * 
     * @return  The number for the current player's last pit.
     */
    public int currPlayerEndPit() {
        int currEndPit = PLAYER_TWO-1;
        if (currentPlayer == PLAYER_ONE_START) {
            currEndPit = PLAYER_ONE;
        }
        return currEndPit;
    }

    /**
     * Gets a string representation of the pit numbered pitNum.
     * 
     * @param pitNum    The number of the pit to get the string for.
     * @return  The string for the pit.
     */
    public String getPitString(final int pitNum) {
        return gameBoard.getPitString(pitNum);
    }

    /**
     * Gets a basic string containing a description of the rules of Mancala.
     * 
     * @return  A string with the basic description of Mancala.
     */
    @Override
    public String toString() {
        return "Mancala is a two-player game where players take turns picking pits to move stones from with the objective of having more stones in their store and their opponent's by the end of the game";
    }
}
