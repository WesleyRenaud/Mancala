package mancala;

import java.io.Serializable;

/**
 * A class used to hold the stores in the board.
 */
public class Store implements Serializable, Countable {
    private static final long serialVersionUID = 1954504290949908055L;

    /**
     * The number of stones in the store.
     */
    private int numStones;
    /**
     * The owner of the store.
     */
    private Player owner;
    /**
     * The name of the owner of the store.
     */
    private String ownerName;

    /**
     * Initializes a store with zero stones.
     */
    public Store() {
        numStones = 0;
    }

    /**
     * Initializes a store with zero stones and an owner.
     * 
     * @param storeOwner    The owner of the store.
     */
    public Store(final Player storeOwner) {
        owner = storeOwner;
        numStones = 0;
    }

    /**
     * Adds one stone to the store.
     */
    @Override
    public void addStone() {
        numStones++;
    }

    /**
     * Adds the given number of stones to the store.
     * 
     * @param amount    The amount of stones to add.
     */
    @Override
    public void addStones(final int amount) {
        numStones += amount;
    }

    /**
     * Removes all stones from the store.
     * 
     * @return  The number of stones that were in the store.
     */
    @Override
    public int removeStones() {
        final int stones = numStones;
        numStones = 0;
        return stones;
    }

    /**
     * Gets the number of stones in the store.
     * 
     * @return  The number of stones in the store.
     */
    @Override
    public int getStoneCount() {
        return numStones;
    }

    /**
     * Sets the owner (player) of the store.
     * 
     * @param player    The owner for the store.
     */
    protected void setOwner(final Player player) {
        owner = player;
        ownerName = player.getName();
    }

    /**
     * Gets the owner of the store.
     * 
     * @return  The owner of the store.
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Gets the name of the owner of the store.
     * 
     * @return  The name of the owner of the store.
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * Gets a string representation of the player.
     * 
     * @return  The string representation of the player.
     */
    @Override
    public String toString() {
        return getOwnerName() + "\'s store with " + numStones + " stones";
    }
}