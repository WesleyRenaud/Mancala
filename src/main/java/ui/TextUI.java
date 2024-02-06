package ui;

import java.io.IOException;
import java.util.Scanner;

import mancala.MancalaGame;
import mancala.Player;
import mancala.Saver;
import mancala.GameNotOverException;
import mancala.InvalidMoveException;

/**
 * Class that provides a text inferface to play Mancala.
 */
public class TextUI {
    private Scanner input = new Scanner(System.in);
    private Saver saver = new Saver();
    private MancalaGame game;
    private InputAnalyzer analyzer = new InputAnalyzer();

    /**
     * Initializes a new TextUI with a new MancalaGame.
     */
    public TextUI() {
        game = new MancalaGame();
    }

    /**
     * Runs one or more mancala games with loading and saving options.
     * 
     * @param args  Default arguments for main method.
     */
    public static void main(String[] args) {
        TextUI ui = new TextUI();
        // if the game was not loaded we start a new one with new players
        if (ui.promptLoadGame()==false) {
            ui.pickRuleSet();
            ui.startFirstGame(); 
            ui.intializePlayers();
        } else {
            try {
                ui.loadGame();
            } catch (IOException e) {
                e.getMessage();
            }
            System.out.println("Loading game ...");
        }

        do { 
            while(!ui.game.isGameOver()) {
                ui.printBoard();
            
                // during the game we can prompt the user for a move, or for saving and quitting
                try {
                    ui.promptMoveOrQuit();
                } catch (IOException e) {
                    e.getMessage();
                }
            }

            //after the game is over check who won
            try {
                //print the winner
                ui.printWinner();
                ui.printBoard();
            } catch (GameNotOverException e) {
                System.out.println(e.getMessage());
            }
        } while (ui.willPlayAgain());
    }

    /**
     * Starts a new game by initializing the board.
     */
    private void startFirstGame() {
        game.startNewGame();
    }

    /**
     * Prompts the user for whether they would like to load the previous game.
     * 
     * @return  True if we are to load the game, and false otherwise.
     */
    private boolean promptLoadGame() {
        boolean loadGame = false;
        String loadGameString = "Enter 'L' or 'l' to load the previous game or anything else to start a new one >> ";
        String input = analyzer.promptUser(loadGameString);
        if((analyzer.checkSpecificInput(input, "L")) || (analyzer.checkSpecificInput(input, "l"))) {
            loadGame = true;
        }
        return loadGame;
    }

    /**
     * Loads the previous game.
     * 
     * @throws IOException  If the game cannot be read from the file. 
     */
    private void loadGame() throws IOException {
        String getFileName = "Enter the filename to load from >> ";
        game = (MancalaGame) saver.loadObject(analyzer.promptUser(getFileName));
    }

    /**
     * Picks the ruleset for the game by prompting the user and making a GameRules instance.
     */
    private void pickRuleSet() {
        boolean ruleSetPicked = false;
        while (ruleSetPicked == false) {
            String ruleSetPrompt = "Enter 'Kalah' to use Kalah rules and 'Ayo' to Ayo Rules >> ";
            String ruleSet = analyzer.promptUser(ruleSetPrompt);
            if((analyzer.checkSpecificInput(ruleSet, "Kalah")) || analyzer.checkSpecificInput(ruleSet, "Ayo")) {
                ruleSetPicked = true;
                setRuleSet(ruleSet);
            }
        }
    }

    /**
     * Finalizes the rule set for the current game.
     */
    private void setRuleSet(String ruleSet) {
        if (analyzer.checkSpecificInput(ruleSet, "Kalah")) {
            game.startKalahGame();
        } else {
            game.startAyoGame();
        }
    }

    /**
     * Prompts the user if they would like to continue playing (make a move) or quit.
     * 
     * @throws IOException  If the game cannot be saved.
     */
    private void promptMoveOrQuit() throws IOException {
        int minAcceptedValue = game.currPlayerStartPit();
        int maxAcceptedValue = game.currPlayerEndPit();
        String moveOrQuitString = "Enter a pit number to make the move from " + minAcceptedValue + " to " + maxAcceptedValue;
        moveOrQuitString += " or 'S' or 's' to save and quit >> ";
        String input = analyzer.promptUser(moveOrQuitString);

        // If the user enters 's' or 'S' we save the game and quit.
        if((analyzer.checkSpecificInput(input, "S")) || analyzer.checkSpecificInput(input, "s")) {
            saveGame();
        } else {
            int startPit = getStartingPitInput(input);
            try {
                game.move(startPit);
            } catch (InvalidMoveException e) {
                e.getMessage();
            }
        }
    }

    /**
     * Gets the starting input for a move.
     * 
     * @param given The initial pit input we are given.
     * @return  The parsed pit number given.
     */
    private int getStartingPitInput(String given) {
        int minAcceptedValue = game.currPlayerStartPit();
        int maxAcceptedValue = game.currPlayerEndPit();
        String num = given;
        while (!(analyzer.isIntegerInput(num) && isValidPitInput(minAcceptedValue, maxAcceptedValue, num))) {
            String promptNewPit = "Error - the pit number is invalid. Enter another pit number from ";
            promptNewPit += minAcceptedValue + " to " + maxAcceptedValue;
            num = analyzer.promptUser(promptNewPit);
        }

        return Integer.parseInt(num);
    }

    /**
     * Checks if a string is a number and is in between a minimum and maximum value.
     * 
     * @param min   The minimum value to compare against.
     * @param max   The maximum value to compare against.
     * @param num   The string to handle as a number.
     * @return  True if the number is in between the values, and false otherwise.
     */
    public boolean isValidPitInput(final int min, final int max, final String num) {
        int number;
        try {
            number = Integer.parseInt(num);
        } catch (Exception e) {
            throw new RuntimeException("Invalid Input Given");
        }

        if (number >= min && number <= max) {
            return true;
        }
        return false;
    }

    /**
     * Saves a game to a file.
     * 
     * @throws IOException  If the game cannot be saved.
     */
    private void saveGame() throws IOException {
        String filename = analyzer.promptUser("Enter the filename to save the game to >> ");
        saver.saveObject(game, filename);
        System.exit(0);
    }

    /**
     * Prints the boaard.
     */
    private void printBoard() {
        System.out.println(game);
    } 

    /**
     * Prints the winner of the game.
     * 
     * @throws GameNotOverException If the game is not over.
     */
    private void printWinner() throws GameNotOverException {
        Player winner = game.getWinner();
        if (winner != null) {
            System.out.printf("%s has won the game!\n", winner.getName());
        } else {
            System.out.println("The game has resulted in a tie");
        }
    }

    /**
     * Initializes the players for the game, and connects them to the game.
     */
    private void intializePlayers() {
        System.out.print("Enter a name for player 1: ");
        Player p1 = new Player(input.nextLine());
        System.out.print("Enter a name for player 2: ");
        Player p2 = new Player(input.nextLine());
       
        game.setPlayers(p1, p2);
    }

    /**
     * Prompts the user if they would like to play again.
     * 
     * @return  True if they enter yes, and false otherwise.
     */
    private boolean willPlayAgain() {
        String in = analyzer.promptUser("Would you like to play again? (Enter 'Y' or 'y' for yes) ");

        if (analyzer.checkSpecificInput(in, "Y") || analyzer.checkSpecificInput(in, "y")) {
            return true;
        }
        return false;
    }
}