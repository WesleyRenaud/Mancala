package mancala;

import java.io.Serializable;

/**
 * A class used to represent one of the twelve pits on the board, with
 * ability to add and remove stones from the pit directly.
 */
public class Pit implements Serializable, Countable {
    private static final long serialVersionUID = -6011915598889187922L;
    
    /**
     * The number of stones inside the pit.
     */
    private int numStones;

    /**
     * Initializes a pit with zero stones.
     */
    public Pit() {
        numStones = 0;
    }
    
    /**
     * Adds one stone to the pit.
     */
    @Override
    public void addStone() {
        numStones++;
    }

    /**
     * Adds the given number of stones to the pit.
     * 
     * @param numToAdd  The number of stones to add.
     */
    @Override
    public void addStones(final int numToAdd) {
        numStones += numToAdd;
    }

    /**
     * Removes all stones from the pit.
     * 
     * @return  The number of stones that were in the pit.
     */
    @Override
    public int removeStones() {
        //copy over the number of stones, set the original to 0, and return the copy
        final int stones = numStones;
        numStones = 0;
        return stones;
    }

    /**
     * Gets the number of stones in the pit.
     * 
     * @return  The number of stones in the pit.
     */
    @Override
    public int getStoneCount() {
        return numStones;
    }

    /**
     * Gets a string representation of the pit.
     * 
     * @return  The string representation of the pit.
     */
    @Override
    public String toString() {
        return "" + getStoneCount();
    }
}
