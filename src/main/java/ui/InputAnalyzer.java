package ui;

import java.util.Scanner;

/**
 * A class used to compare and interpret strings of input.
 */
public class InputAnalyzer {
    Scanner input = new Scanner(System.in);

    /**
     * Prompts a user with a string and gets the input.
     * 
     * @param promptString  The string to prompt the user with.
     * @return  The string given as input.
     */
    public String promptUser(String promptString) {
        System.out.print(promptString);
        return input.nextLine();
    }


    /**
     * Checks if a string is exactly equal to another.
     * @param input     The first string to compare.
     * @param toCompare The second string to compare.
     * @return  True if the strings are equal, and false otherwise.
     */
    public boolean checkSpecificInput(String input, String toCompare) {
        input.trim();
        toCompare.trim();
        boolean isSame = false;
        if (input.compareTo(toCompare)==0) {
            isSame = true;
        }
        return isSame;
    }
    

    /**
     * Checks if a string can be converted into an integer.
     * 
     * @param line  The string to check.
     * @return  True if the input is an integer, and false otherwise.
     */
    public boolean isIntegerInput(String line) {
        //try to parse the String to an int
        try {
            Integer.parseInt(line);
            return true;
        } catch (Exception e) {
            //if there was an error, we don't have an int
            return false;
        }
    }
}