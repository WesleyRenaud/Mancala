package mancala;

import java.io.Serializable;

/**
 * A class used to hold one of the player's in the game
 */
public class Player implements Serializable {
    private static final long serialVersionUID = -8847226214285638613L;

    /**
     * The name of the player.
     */
    private String playerName;
    /**
     * The store of the player.
     */
    private Store playerStore;
    /**
     * The userprofile of the player.
     */
    private UserProfile profile;

    /**
     * Creates a player with no name, but a store.
     */
    public Player() {
        playerStore = new Store();
        profile = new UserProfile();
    }

    /**
     * Creates a player with a given name and a store.
     * 
     * @param name  The name for the player.
     */
    public Player(final String name) {
        playerStore = new Store();
        playerName = name;
        profile = new UserProfile(name);
    }

    /**
     * Sets the name of the player.
     * 
     * @param name  The new name for the player.
     */
    protected void setName(final String name) {
        playerName = name;
    }

    /**
     * Sets the store of the player.
     * 
     * @param store The new store for the player.
     */
    protected void setStore(final Store store) {
        playerStore = store;
    }

    /**
     * Gets the player's name.
     * 
     * @return  The player's name.
     */
    public String getName() {
        return playerName;
    }

    /**
     * Returns the player's store.
     * 
     * @return  The player's store.
     */
    public Store getStore() {
        return playerStore;
    }

    /**
     * Gets the number of stones in a player's store.
     * 
     * @return  The count of the player's store.
     */
    public int getStoreCount() {
        return playerStore.getStoneCount();
    }

    /**
     * Adds a given number of stones to the player's store.
     * 
     * @param amount    The amount to add.
     */
    protected void addToStore(final int amount) {
        playerStore.addStones(amount);
    }
    
    /**
     * Gets a string representation of the player.
     * 
     * @return  The string representation of the player.
     */
    @Override
    public String toString() {
        return "Player: " + getName() + " has " + getStoreCount() + " stones in thier pit";
    }
}