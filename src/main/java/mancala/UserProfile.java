package mancala;

import java.io.Serializable;

/**
 * A class used to concatenate stats of concurrent mancala games.
 */
public class UserProfile implements Serializable {
    private static final long serialVersionUID = 1391759965656690201L;

    /**
     * The name of the user.
     */
    private String name;
    /**
     * The number of kalah games played by the user.
     */
    private int kalahPlayed;
    /**
     * The number of ayo games played by the user.
     */
    private int ayoPlayed;
    /**
     * The number of kalah games won by the user.
     */
    private int kalahWon;
    /**
     * The number of ayo games won by the user.
     */
    private int ayoWon;


    /**
     * Creates a new UserProfile with blank stats and a given name.
     * 
     * @param userName  The name of the user.
     */
    public UserProfile(final String userName) {
        name = userName;
        kalahPlayed = 0;
        ayoPlayed = 0;
        kalahWon = 0;
        ayoWon = 0;
    }

    /**
     * Creates a new UserProfile with blank stats.
     */
    public UserProfile() {
        kalahPlayed = 0;
        ayoPlayed = 0;
        kalahWon = 0;
        ayoWon = 0;
    }

    /**
     * Sets the name of the user.
     * 
     * @param newName   The name for the user.
     */
    public void setName(final String newName) {
        name = newName;
    }

    /**
     * Gets the name of the user.
     * 
     * @return  The name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the number of kalah games played by the user.
     * 
     * @return  The number of kalah games played.
     */
    public int getKalahPlayed() {
        return kalahPlayed;
    }

    /**
     * Gets the number of kalah games won by the user.
     * 
     * @return  The number of kalah games won.
     */
    public int getKalahWon() {
        return kalahWon;
    }

    /**
     * Gets the number of ayo games played by the user.
     * 
     * @return  The number of ayo games played.
     */
    public int getAyoPlayed() {
        return ayoPlayed;
    }

    /**
     * Gets the number of ayo games won by the user.
     * 
     * @return  The number of ayo games won.
     */
    public int getAyoWon() {
        return ayoWon;
    }


    /**
     * Increments the number of kalah games played by one.
     */
    public void addKalahPlayed() {
        kalahPlayed++;
    }

    /**
     * Increments the number of ayo games played by one.
     */
    public void addAyoPlayed() {
        ayoPlayed++;
    }

    /**
     * Increments the number of kalah games played by one.
     */
    public void addKalahWon() {
        kalahWon++;
    }

    /**
     * Increments the number of kalah games played by two.
     */
    public void addAyoWon() {
        ayoWon++;
    }

    /**
     * Calculates the percentage of kalah games the user has won.
     * 
     * @return  The percentage of kalah games won.
     */    
    public double percentageKalahWon() {
        if (getKalahPlayed() != 0) {
            return 100 * (double)(getKalahWon() / getKalahPlayed());
        }
        return 0;
    }

    /**
     * Calculates the percentage of ayo games the user has won.
     * 
     * @return  The percentage of ayo games won.
     */
    public double percentageAyoWon() {
        if (getAyoPlayed() != 0) {
            return 100 * (double)(getAyoWon() / getAyoPlayed());
        }
        return 0;
    }
    
    /**
     * Generates a String representation of the user and their stats.
     */
    @Override
    public String toString() {
        String userString = "";

        userString += "\t\t\t\t\t" + getName() + "\t\t\t\t\t\n";
        for (int i = 0; i < 80; i++) {
            userString += "-";
        }
        userString += "Kalah Played: " + getKalahPlayed() + "   Kalah Won: " + getKalahWon();
        userString += "Ayo Played: " + getAyoPlayed() + "   Ayo Won: " + getAyoWon();
        return userString;
    }
}
