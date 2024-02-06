package mancala;

import java.io.Serializable;

/**
 * Class used to hold all components of a game and perform different
 * high-level actions such as moves, starting a new game, etc.
 */
public class MancalaGame implements Serializable, PlayerPositions {
    private static final long serialVersionUID = 7697553430759531650L;

    /**
     * The rule set for the game being played: kalah or ayo.
     */
    private GameRules gameRules;
    /**
     * The first player of the game.
     */
    private Player playerOne;
    /**
     * The second player of the game.
     */
    private Player playerTwo;

    /**
     * Gets the board/game rules of the game.
     * 
     * @return  The game rules for the game.
     */
    protected GameRules getBoard() {
        return gameRules;
    }

    /**
     * Sets the board/game rules of the game.
     * 
     * @param board The game rules for the game.
     */
    protected void setBoard(final GameRules board) {
        gameRules = board;
    }

    /**
     * Starts a new game with the Kalah rule set.
     */
    public void startKalahGame() {
        gameRules = new KalahRules();
    }

    /**
     * Starts a new game with the Ayo rule set.
     */
    public void startAyoGame() {
        gameRules = new AyoRules();
    }

    /**
     * Starts a new game by resetting the board.
     */
    public void startNewGame() {
        gameRules.resetBoard();
    } 

    /**
     * Gets the current player object in the game.
     * 
     * @return  The current player.
     */
    public Player getCurrentPlayer() {
        Player currPlayer = null;
        final int currPlayerNumber = gameRules.getCurrentPlayer();
        if (currPlayerNumber == PLAYER_ONE_NUM) {
            currPlayer = playerOne;
        } else if (currPlayerNumber == PLAYER_TWO_NUM) {
            currPlayer = playerTwo;
        }

        if (currPlayer==null) {
            throw new RuntimeException("Invalid Current Player");
        }
        return currPlayer;
    }

    /**
     * Gets the number corresponding to the number of the current player.
     * 
     * @return  The number of the current player (1 or 2).
     */
    public int getCurrPlayerNum() {
        return gameRules.getCurrentPlayer();
    }

    /**
     * Gets the winner of the game, if the game is over.
     * 
     * @return  The player who won the game or null if it is a tie.
     * @throws  GameNotOverException If the game is not over.
     */
    public Player getWinner() throws GameNotOverException {
        Player winner = null;
        final int winnerNum = gameRules.getWinner();
        
        if (winnerNum == PLAYER_ONE_NUM) {
            winner = playerOne;
        } else if (winnerNum == PLAYER_TWO_NUM) {
            winner = playerTwo;
        }
        return winner;
    }

    /**
     * Gets the name of the player who won the game.
     * 
     * @return  The name of the winner of the game.
     * @throws GameNotOverException If the game is not over.
     */
    public String getWinnerName() throws GameNotOverException {
        String winnerName = null;
        if (getWinner()!=null) {
            winnerName = getWinner().getName();
        }
        return winnerName;
    }

    /**
     * Checks if the game is over by inspecting both sides.
     * 
     * @return  True either side of the board is empty, false otherwise.
     */
    public boolean isGameOver() {
        boolean isOver = false;
        if (gameRules.isSideEmpty(1) || gameRules.isSideEmpty(2)) {
            isOver = true;
            endGame();
        }

        return isOver;
    }

    /**
     * Ends the game by moving all stones into the stores.
     */
    protected void endGame() {
        gameRules.moveFinalStonesIntoStores();
    }

    /**
     * Makes a move for the current player.
     * 
     * @param startPit  The pit to start from.
     * @return  The number of stones in the current player's pits.
     * @throws  InvalidMoveException If the move is invalid.
     */
    public int move(final int startPit) throws InvalidMoveException {
        gameRules.moveStones(startPit, gameRules.getCurrentPlayer());

        return gameRules.sumStonesInPlayersPits(gameRules.getCurrentPlayer());
    }

    /**
     * Sets the players for the duration of the game.
     * 
     * @param onePlayer The first player for the game.
     * @param twoPlayer The second playerf for the game.
     */
    public void setPlayers(final Player onePlayer, final Player twoPlayer) {
        playerOne = onePlayer;
        playerTwo = twoPlayer;
        gameRules.connectPlayersToStores(playerOne, playerTwo);
    }

    /**
     * Sets one of the players for the game.
     * 
     * @param onePlayer The player to set to the game.
     * @param playerNum The number to connect them to (1 or 2).
     */
    public void setOnePlayer(final Player onePlayer, final int playerNum) {
        if (playerNum == PLAYER_ONE_NUM) {
            playerOne = onePlayer;
        } else {
            playerTwo = onePlayer;
        }
        gameRules.connectPlayersToStores(playerOne, playerTwo);
    }

    /**
     * Gets the number of stones in a given pit.
     * 
     * @param pitNum    The pit to get the stones from.
     * @return  The number of stones in the pit.
     */
    public int getNumStones(final int pitNum) {
        return gameRules.getNumStones(pitNum);
    }

    /**
     * Returns the name of player one.
     * 
     * @return Player one's name.
     */
    public String getPlayerOneName() {
        return playerOne.getName();
    }

    /**
     * Returns the name of player two.
     * 
     * @return Player two's name.
     */
    public String getPlayerTwoName() {
        return playerTwo.getName();
    }

    /**
     * Gets the number of the first pit of the current player.
     * 
     * @return  The number for the current player's first pit.
     */
    public int currPlayerStartPit() {
        return gameRules.currPlayerStartPit();
    }

    /**
     * Gets the number of the last pit of the current player.
     * 
     * @return  The number for the current player's last pit.
     */
    public int currPlayerEndPit() {
        return gameRules.currPlayerEndPit();
    }

    /**
     * Gets a string representation of the pit numbered pitNum.
     * 
     * @param pitNum    The number of the pit to get the string for.
     * @return  The string for the pit.
     */
    public String getPitString(final int pitNum) {
        return gameRules.getPitString(pitNum);
    }

    /**
     * Sets the current player of the game.
     * 
     * @param playerNum The number of the current player
     */
    public void setPlayer(final int playerNum) {
        gameRules.setPlayer(playerNum);
    }

    /**
     * Gets the stone count in a player's store.
     *
     * @param playerNum The player number (1 or 2).
     * @return The stone count in the player's store.
     */
    public int getStoreCount(final int playerNum) {
        return gameRules.getStoreCount(playerNum);
    }

    @Override
    public String toString() {
        // TO DO: implement gameString
        String gameString = "\t\t\t\t\tMancala\t\t\t\t\t\n";
        for (int i = 0; i < 100; i++) {
            gameString += "-";
        }
        gameString += "\n" + gameRules.getDataStructure().toString() + "\n";
        for (int i = 0; i < 100; i++) {
            gameString += "-";
        }
        
        return gameString;
    }
}