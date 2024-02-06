package mancala;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Mancala data structure for the Mancala game.
 * Do not change the signature of any of the methods provided.
 * You may add methods if you need them.
 * Do not add game logic to this class
 */
public class MancalaDataStructure implements Serializable, PlayerPositions {
    private static final long serialVersionUID = -5645683611478329210L;

    /**
     * The number of stones each pit will have at the start of the game
     */
    private static int START_STONES = 4;  //not final because we might want a different size board in the future

    /**
     * The list of countable objects on the board.
     */
    final private List<Countable> data = new ArrayList<>();
    /**
     * The position of the iterator in the list of countable objects.
     */
    private int iteratorPos = 0;
    /**
     * Indicates which store on the board we are to skip during a turn.
     */
    private int playerSkip = PLAYER_TWO;
    /**
     * Indicates which pit we are to skip (the starting pit) when using the ayo rules.
     */
    private int pitSkip = -1; // will never match the iteratorPos unless set specifically

    /**
     * Constructor to initialize the MancalaDataStructure.
     * 
     * @param startStones The number of stones to place in pits at the start of the game. Default values is 4.
     */
    public MancalaDataStructure(final int startStones){
        START_STONES = startStones;
        for (int i = 0; i < PLAYER_ONE; i++) {
            data.add(new Pit());
        }
        data.add(new Store());
        for (int i = 7; i < PLAYER_TWO; i++) {
            data.add(new Pit());
        }
        data.add(new Store());
    }

    /**
     * Constructor to initialize the MancalaDataStructure.
     */
    public MancalaDataStructure() {
        this(4);
    }

    /**
     * Adds stones to a pit.
     *
     * @param pitNum   The number of the pit.
     * @param numToAdd The number of stones to add.
     * @return The current number of stones in the pit.
     */
    public int addStones(final int pitNum, final int numToAdd) {
        final Countable pit = data.get(pitPos(pitNum));
        pit.addStones(numToAdd);
        return pit.getStoneCount();
    }

    /**
     * Removes stones from a pit.
     *
     * @param pitNum The number of the pit.
     * @return The number of stones removed.
     */
    public int removeStones(final int pitNum) {
        final Countable pit = data.get(pitPos(pitNum));
        return pit.removeStones();
    }

    /**
     * Adds stones to a player's store.
     *
     * @param playerNum The player number (1 or 2).
     * @param numToAdd  The number of stones to add to the store.
     * @return The current number of stones in the store.
     */
    public int addToStore(final int playerNum, final int numToAdd) {
        final Countable store = data.get(storePos(playerNum));
        store.addStones(numToAdd);
        return store.getStoneCount();
    }

    /**
     * Gets the stone count in a player's store.
     *
     * @param playerNum The player number (1 or 2).
     * @return The stone count in the player's store.
     */
    public int getStoreCount(final int playerNum) {
        final Countable store = data.get(storePos(playerNum));
        return store.getStoneCount();
    }

    /**
     * Gets the stone count in a given  pit.
     *
     * @param pitNum The number of the pit.
     * @return The stone count in the pit.
     */
    public int getNumStones(final int pitNum) {
        final Countable pit = data.get(pitPos(pitNum));
        return pit.getStoneCount();
    }    

    /*helper method to convert 1 based pit numbers into array positions*/
    private int pitPos(final int pitNum) {
        /*Runtime execeptions don't need to be declared and are
        automatically passed up the chain until caught. This can
        replace the PitNotFoundException*/
        if(pitNum<1 || pitNum > 12){
            throw new RuntimeException("Pit Number Out of Range");
        }
        int pos = pitNum;
        if (pos <= PLAYER_ONE) {
            pos--;
        }
        return pos;
    }

    /*helper method to convert array positions into 1 based pit numbers*/
    private int pitNum(final int arrNum) {
        if (arrNum < 0 || arrNum > 12 || arrNum == 6) {
            throw new RuntimeException("Pit Index Number Out of Range");
        }
        int pitNum = arrNum;
        if (pitNum < PLAYER_ONE) {
            pitNum++;
        }
        return pitNum;
    }

    /*helper method to convert player number to an array position*/
    private int storePos(final int playerNum) {
        if(playerNum <1 || playerNum > 2){
            throw new RuntimeException("Invalid Player Position");
        }

        int pos = PLAYER_ONE;
        if (playerNum == PLAYER_TWO_NUM) {
            pos = PLAYER_TWO;
        }
        return pos;
    }

    /**
     * Empties both players' stores.
     */
    public void emptyStores() {
        data.set(storePos(1), new Store());
        data.set(storePos(2), new Store());
    }

    /**
     * Sets up pits with a specified number of starting stones.
     */
    public void setUpPits() {
        for (int i = 0; i < PLAYER_ONE; i++) {
            data.get(i).removeStones();
            data.get(i).addStones(START_STONES);
        }

        for (int i = 7; i < PLAYER_TWO; i++) {
            data.get(i).removeStones();
            data.get(i).addStones(START_STONES);
        }
    }

    /**
     * Adds a store that is already connected to a Player.
     *
     * @param store     The store to set.
     * @param playerNum The player number (1 or 2).
     */
    public void setStore(final Countable store, final int playerNum) {
        data.set(storePos(playerNum), store);
    }
    /*helper method for wrapping the iterator around to the beginning again*/
    private void loopIterator() {
        if (iteratorPos == PLAYER_TWO + 1) {
            iteratorPos = 0;
        }
    }

    private void skipPosition() {
        while (iteratorPos == playerSkip || iteratorPos == pitSkip) {
            iteratorPos++;
            loopIterator();
        }
    }

    private void setSkipPlayer(final int playerNum) {
        //sets the skip store to be the opposite player
        playerSkip = PLAYER_TWO;
        if (playerNum == PLAYER_TWO_NUM) {
            playerSkip = PLAYER_ONE;
        }
    }

    private void setSkipPit(final int pitNum) {
        pitSkip = pitPos(pitNum);
    }

    /**
     * Sets the iterator position and positions to skip when iterating.
     *
     * @param startPos       The starting position for the iterator.
     * @param playerNum      The player number (1 or 2).
     * @param skipStartPit   Whether to skip the starting pit.
     */
    public void setIterator(final int startPos, final int playerNum, final boolean skipStartPit) {
        iteratorPos = pitPos(startPos);
        setSkipPlayer(playerNum);
        if (skipStartPit) {
            setSkipPit(startPos);
        }
    }

    /**
     * Moves the iterator to the next position.
     *
     * @return The countable object at the next position.
     */
    public Countable next() {
        iteratorPos++;
        loopIterator(); // in case we've run off the end
        skipPosition(); // skip store and start position if necessary
        return data.get(iteratorPos);
    }

    /**
     * Checks if one of the player's side of the board is empty.
     * 
     * @param playerNum The player whose side must be checked.
     * @return  True if the side of the board is empty, and false otherwise.
     */
    public boolean isSideEmpty(final int playerNum) {
        if (playerNum < 1 || playerNum > 2) {
            throw new RuntimeException("Invalid Player Position");
        }
        boolean isSideEmpty = true;
        for (int i = getPlayerStartPos(playerNum); i < getPlayerEndPos(playerNum); i++) {
            if (getNumStones(i) != 0) {
                isSideEmpty = false;
            }
        }
        return isSideEmpty;
    }

    /**
     * Finds the index of the first pit for a player.
     * 
     * @return  The starting position of the pits for the player.
     */
    private int getPlayerStartPos(final int playerNum) {
        int startPos = -1;
        if (playerNum == PLAYER_ONE_NUM) {
            startPos = PLAYER_ONE_START;
        }
        else if (playerNum == PLAYER_TWO_NUM) {
            startPos = PLAYER_TWO_START;
        }
        return startPos;
    }

    /**
     * Finds the index of the last pit for a player.
     * 
     * @return  The ending position of the pits for the player.
     */
    private int getPlayerEndPos(final int playerNum) {
        int endPos = -1;
        if (playerNum == PLAYER_ONE_NUM) {
            endPos = PLAYER_TWO_START;
        }
        else if (playerNum == PLAYER_TWO_NUM) {
            endPos = PLAYER_TWO;
        }
        return endPos;
    }

    /**
     * Gets the number of a pit when given the pit.
     * 
     * @param pit   The pit to get the number for.
     * @return  The number corresponding to the pit.
     */
    protected int getPitPos(final Pit pit) {
        for (int i = 0; i < data.size(); i++) {
            if (pit.equals(data.get(i))) {
                return pitNum(i);
            }
        }
        throw new RuntimeException("Pit is Not on Board");
    }

    /**
     * Checks if a pit is on the current player's side.
     * 
     * @param pitNum    The pit number to check.
     * @param playerNum The number corresponding to the current player
     * @return  True if the pit is on the player's side, and false otherwise.
     */
    public boolean isOnPlayersSide(final int pitNum, final int playerNum) {
        boolean isOnPlayersSide = false;
        if (playerNum == PLAYER_ONE_NUM && pitNum >= 1 && pitNum <= 6) {
            isOnPlayersSide = true;
        } else if (playerNum == PLAYER_TWO_NUM && pitNum >= 7 && pitNum <= 12) {
            isOnPlayersSide = true;
        }
        return isOnPlayersSide;
    }

    /**
     * Checks if a store is equal to that of a certain player.
     * 
     * @param store     The store to compare.
     * @param playerNum The player whose store we are comparing too.
     * @return True if the store is of the current player, and false otherwise.
     */
    protected boolean isPlayersStore(final Store store, final int playerNum) {
        boolean isPlayersStore = false;
        if (playerNum == 1 && store.equals(data.get(PLAYER_ONE))) {
            isPlayersStore = true;
        } else if (playerNum == 2 && store.equals(data.get(PLAYER_TWO))) {
            isPlayersStore = true;
        }
        return isPlayersStore;
    }

    /**
     * Connects the players to their stores.
     * 
     * @param playerOne The player for the first store.
     * @param playerTwo The player for the second store.
     */
    protected void connectPlayersToStores(final Player playerOne, final Player playerTwo) {
        playerOne.setStore((Store)data.get(PLAYER_ONE));
        playerTwo.setStore((Store)data.get(PLAYER_TWO));
    }

    /**
     * Gets a string representation of the pit numbered pitNum.
     * 
     * @param pitNum    The number of the pit to get the string for.
     * @return  The string for the pit.
     */
    public String getPitString(final int pitNum) {
        return data.get(pitPos(pitNum)).toString();
    }

    /**
     * Checks if a countableobject is a pit.
     * 
     * @param countable The countable object to check.
     * @return  True if it is a store, and false otherwise.
     */
    protected boolean isPit(final Countable countable) {
        boolean isStore = false;
        if (countable.getClass().equals(Pit.class)) {
            isStore = true;
        }
        return isStore;
    }

    /**
     * Checks if a countableobject is a store.
     * 
     * @param countable The countable object to check.
     * @return  True if it is a store, and false otherwise.
     */
    protected boolean isStore(final Countable countable) {
        boolean isStore = false;
        if (countable.getClass().equals(Store.class)) {
            isStore = true;
        }
        return isStore;
    }

    /**
     * Returns a string representation of the Board
     */
    @Override
    public String toString() {
        return playerTwoStoreString() + playerTwoPitsString() + playerOnePitsString() + playerOneStoreString();
    }

    private String playerOnePitsString() {
        String string = "\n\t\t\t";
        for (int i = 1; i <= 6; i++) {
            try {
                string += "[" + i + "]: " + getNumStones(i) + "\t";
            } catch (Exception e) {
                System.out.println("Error - pit " + (i + 1) + " was not found");
            }
        }
        return string;
    }

    private String playerOneStoreString() {
        return "   " + "Player One's Store: " + getStoreCount(1);
    } 
    
    private String playerTwoPitsString() {
        String string = "\t";
        for (int i = 12; i >= 7; i--) {
            try {
                string += "[" + i + "]: " + getNumStones(i) + "\t";
            } catch (Exception e) {
                System.out.println("Error - pit " + (i + 1) + " was not found");
            }
        }
        return string;
    }

    private String playerTwoStoreString() {
        return "Player Two's Store: " + getStoreCount(2);
    }
}