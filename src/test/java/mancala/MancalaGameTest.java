/**
 * These test cases are intended to ensure that the correct API has been implemented for the classes under test.
 * They do not provide exhaustive coverage and thorough testing of all possible scenarios.
 * Additional test cases should be added to cover  edge cases and behaviors.
 */

package mancala;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MancalaGameTest {
    /*
    private MancalaGame game;
    private Player player1;
    private Player player2;

    @BeforeEach
    public void setUp() {
        game = new MancalaGame();
        Board board = new Board();
        board.setUpPits();
        board.setUpStores();
        board.initializeBoard();
        game.setBoard(board);
        player1 = new Player("Player1");
        player2 = new Player("Player2");
        game.setPlayers(player1, player2);
        game.startNewGame();
    }

    private void emptyBoard(){
        for(Pit p: game.getBoard().getPits()){
            p.removeStones();
        }
    }

  @Test
    public void testGetPlayers() {

        boolean players = true;
        List<Player> actualPlayers = game.getPlayers();
        for(Player p:actualPlayers){
            if(p!=player1){
                if(p!=player2){
                    players = false;
                    break;
                }
            }
        }
        assertTrue(players);
    }

   @Test
    public void testGetNumStonesValidPit() throws PitNotFoundException {
        // Assuming you have a specific pit with a known number of stones (e.g., pit 1 has 5 stones)
        int pitNum = 1;
        int expectedStones = 4;

        assertEquals(expectedStones, game.getNumStones(pitNum));
    }

    @Test
    public void testGetNumStonesInvalidPit() {
        // Assuming you're testing an invalid pit number (e.g., pit 0, which is out of bounds)
        int invalidPitNum = 14;

        // The method should throw a PitNotFoundException for an invalid pit number
        assertThrows(PitNotFoundException.class, () -> game.getNumStones(invalidPitNum));
    }

    @Test
    public void testGetStoreCountValidPlayer() throws NoSuchPlayerException {
        // Assuming you have set up player stores with known stone counts
        player1.getStore().addStones(10);

        // Verify the store count for player1
        int expectedStoreCountPlayer1 = 10;
        int actualStoreCountPlayer1 = game.getStoreCount(player1);
        assertEquals(expectedStoreCountPlayer1, actualStoreCountPlayer1);
    }

    @Test
    public void testGetWinnerGameNotOver() {
        // Test that the method throws GameNotOverException when the game is not over
        assertThrows(GameNotOverException.class, () -> game.getWinner());
    }

    @Test
    public void testSetPlayers() {
        //assumption that Player One is current Player at start of game
        assertEquals(player1, game.getCurrentPlayer());
        assertSame(player1, game.getCurrentPlayer());
    }

    @Test
    public void testSetCurrentPlayer() {
        game.setCurrentPlayer(player2);

        assertEquals(player2, game.getCurrentPlayer());
        assertSame(player2, game.getCurrentPlayer());
    }

    @Test
    public void testSetAndGetBoard() {
        Board newBoard = new Board();
        game.setBoard(newBoard);

        assertEquals(newBoard, game.getBoard());
        assertSame(newBoard, game.getBoard());
    }

    @Test
    public void testGetNumStones() throws PitNotFoundException{
        int numStones = game.getNumStones(1);

        assertEquals(4, numStones); // Assuming initial setup
    }

    @Test
    public void testMove() throws InvalidMoveException {
        int stonesInPits = game.move(1);

        assertTrue(stonesInPits >= 0);
    }

    @Test
    public void testGetStoreCount() throws NoSuchPlayerException{
        int storeCount = game.getStoreCount(player1);

        assertEquals(0, storeCount); // Assuming initial setup
    }

    @Test
    public void testGetWinner() {
        assertThrows(GameNotOverException.class, () -> game.getWinner());
    }

    @Test
    public void testIsGameOver() {
        assertFalse(game.isGameOver());
    }

    public void testStartNewGame() throws PitNotFoundException, InvalidMoveException, NoSuchPlayerException {
        game.move(0);
        int stonesBeforeNewGame = game.getBoard().getNumStones(1);

        game.startNewGame();
        int stonesAfterNewGame = game.getBoard().getNumStones(1);

        assertEquals(player1, game.getCurrentPlayer());
        assertSame(player1, game.getCurrentPlayer());
        assertFalse(game.isGameOver());

        assertEquals(0, stonesBeforeNewGame);
        assertEquals(4, stonesAfterNewGame); // Assuming initial setup
    }

    @Test
    public void testToString() {
        assertNotNull(game.toString());
    }

    //new tests

    @Test
    public void testSetPlayersAndGetPlayers() {
        assertEquals(player1, game.getPlayers().get(0));
        assertEquals(player2, game.getPlayers().get(1));
        assertEquals(game.getPlayers().get(0), game.getBoard().getStores().get(0).getOwner());
        assertEquals(game.getPlayers().get(1), game.getBoard().getStores().get(1).getOwner());
    }

    @Test
    public void testGetCurrentPlayerAtStartOfGame() {
        assertEquals(player1, game.getCurrentPlayer());
    }

    @Test
    public void testGetCurrentPlayerWithSettingCurrentPlayerToNull() {
        game.setCurrentPlayer(null);
        assertNull(game.getCurrentPlayer());
    }

    @Test
    public void testGetCurrentPlayerAndSetCurrentPlayer() {
        game.setCurrentPlayer(player2);
        assertEquals(player2, game.getCurrentPlayer());
    }

    @Test
    public void testGetCurrentPlayerAndSetCurrentPlayerWithPlayerOutsideOfGame() {
        Player playerThree = new Player("Player three");
        game.setCurrentPlayer(playerThree);
        assertEquals(playerThree, game.getCurrentPlayer());
    }

    @Test
    public void testSetBoardAndGetBoard() throws PitNotFoundException {
        game.setBoard(game.getBoard());
        assertEquals(12, game.getBoard().getPits().size());
        assertEquals(2, game.getBoard().getStores().size());
        for (int i = 1; i <= 12; i++) {
            assertEquals(4, game.getNumStones(i));
        }
    }

    @Test
    public void testGetNumStonesAtStartOfGame() throws PitNotFoundException {
        for (int i = 1; i <= 12; i++) {
            assertEquals(4, game.getNumStones(i));
        }
        assertThrows(PitNotFoundException.class, () -> game.getNumStones(-1));
        assertThrows(PitNotFoundException.class, () -> game.getNumStones(0));
        assertThrows(PitNotFoundException.class, () -> game.getNumStones(13));
    }

    @Test
    public void testGetNumStonesAfterAddingStoneToPit() throws PitNotFoundException {
        game.getBoard().getPits().get(6).addStone();
        for (int i = 1; i <= 6; i++) {
            assertEquals(4, game.getNumStones(i));
        }
        assertEquals(5, game.getNumStones(7));
        for (int i = 8; i <= 12; i++) {
            assertEquals(4, game.getNumStones(i));
        }
    }

    @Test
    public void testGetNumStonesAfterEmptyingPit() throws PitNotFoundException {
        game.getBoard().getPits().get(3).removeStones();
        for (int i = 1; i <= 3; i++) {
            assertEquals(4, game.getNumStones(i));
        }
        assertEquals(0, game.getNumStones(4));
        for (int i = 5; i <= 12; i++) {
            assertEquals(4, game.getNumStones(i));
        }
    }

    @Test
    public void testGetNumStonesAfteEmptyingAllPits() throws PitNotFoundException {
        for (int i = 1; i <= 12; i++) {
            game.getBoard().getPits().get(i-1).removeStones();
            assertEquals(0, game.getNumStones(i));
        }
    }

    @Test
    public void testMoveFromInvalidStartingPit() {
        game.setCurrentPlayer(player1);
        assertThrows(InvalidMoveException.class, () -> game.move(0));
        assertThrows(InvalidMoveException.class, () -> game.move(13));
    }

    @Test
    public void testMoveFromEmptyStartingPit() {
        game.setCurrentPlayer(player1);
        game.getBoard().getPits().get(0).removeStones();
        assertThrows(InvalidMoveException.class, () -> game.move(0));
    }

    @Test
    public void testMoveWithPlayerOneOnPlayerOneSideStoreNotReached() 
        throws InvalidMoveException, PitNotFoundException, NoSuchPlayerException {
        game.setCurrentPlayer(player1);
        assertEquals(24, game.move(1));
        assertEquals(0, game.getNumStones(1));
        for (int i = 2; i <= 5; i++) {
            assertEquals(5, game.getNumStones(i));
        }
        assertEquals(4, game.getNumStones(6));
    }

    @Test
    public void testMoveWithPlayerOneOnPlayerOneSideStoreReached() 
        throws InvalidMoveException, PitNotFoundException, NoSuchPlayerException {
        game.setCurrentPlayer(player1);
        assertEquals(22, game.move(4));
        assertEquals(0, game.getNumStones(4));
        for (int i = 5; i <= 7; i++) {
            assertEquals(5, game.getNumStones(i));
        }
        assertEquals(4, game.getNumStones(8));
        assertEquals(1, player1.getStoreCount());
    }

    @Test
    public void testMoveStonesWithPlayerOneFromPlayerTwosSide() {
        game.setCurrentPlayer(player1);
        assertThrows(InvalidMoveException.class, () -> game.move(8));
    }

    @Test
    public void testMoveWithPlayerTwoOnPlayerTwoSideStoreNotReached() 
        throws InvalidMoveException, PitNotFoundException {
        game.setCurrentPlayer(player2);
        assertEquals(24, game.move(8));
        assertEquals(0, game.getNumStones(8));
        for (int i = 9; i <= 12; i++) {
            assertEquals(5, game.getNumStones(i));
        }
        assertEquals(4, game.getNumStones(1));
        assertEquals(0, player2.getStoreCount());
    }

    @Test
    public void testMoveWithPlayerTwoOnPlayerTwoSideStoreReached() throws InvalidMoveException,
        PitNotFoundException {
        game.setCurrentPlayer(player2);
        assertEquals(20, game.move(12));
        assertEquals(0, game.getNumStones(12));
        for (int i = 1; i <= 3; i++) {
            assertEquals(5, game.getNumStones(i));
        }
        assertEquals(4, game.getNumStones(4));
        assertEquals(1, player2.getStoreCount());
    }

    @Test
    public void testMoveStonesWithPlayerTwoFromPlayerOnesSide() {
        game.setCurrentPlayer(player2);
        assertThrows(InvalidMoveException.class, () -> game.move(2));
    }

    @Test
    public void testGetStoreCountWithNullPlayer() {
        assertThrows(NoSuchPlayerException.class, () -> game.getStoreCount(null));
    }

    @Test
    public void testGetStoreCountWithPlayerOutsideOfGame() {
        Player playerThree = new Player("Player three");
        assertEquals(0, playerThree.getStoreCount());
    }

    @Test
    public void testGetStoreCountAtStartOfGame() {
        assertEquals(0, player1.getStoreCount());
        assertEquals(0, player2.getStoreCount());
    }

    @Test
    public void testGetStoreCountAfterAddingStones() {
        player1.getStore().addStones(-1);
        assertEquals(-1, player1.getStoreCount());
        assertEquals(0, player2.getStoreCount());
    }

    @Test
    public void testGetStoreCountAfterMove() throws InvalidMoveException, NoSuchPlayerException {
        game.setCurrentPlayer(player2);
        game.move(11);
        assertEquals(1, player2.getStoreCount());
    }

    @Test
    public void testGetWinnerAtStartOfGame() {
        assertThrows(GameNotOverException.class, () -> game.getWinner());
    }

    @Test
    public void testGetWinnerAfterEmptyingSomePits() {
        for (int i = 0; i <= 3; i++) {
            game.getBoard().getPits().get(i).removeStones();
        }
        game.getBoard().getPits().get(6).removeStones();
        for (int i = 9; i <= 11; i++) {
            game.getBoard().getPits().get(i).removeStones();
        }
        assertThrows(GameNotOverException.class, () -> game.getWinner());
    }

    @Test
    public void testGetWinnerWithPlayerOnePitsEmptyAndPlayerOneWinner() throws GameNotOverException {
        for (int i = 0; i <= 5; i++) {
            game.getBoard().getPits().get(i).removeStones();
        }
        player1.getStore().addStones(25);
        //player one has 4 stones; player 2 has 4 * 6 = 24 stones
        assertEquals(player1, game.getWinner());
        assertEquals(25, player1.getStoreCount());
        assertEquals(24, player2.getStoreCount());
    }

    @Test
    public void testGetWinnerWithPlayerOnePitsEmptyAndPlayerTwoWinner() throws GameNotOverException {
        for (int i = 0; i <= 5; i++) {
            game.getBoard().getPits().get(i).removeStones();
        }
        player2.getStore().addStones(6);
        assertEquals(player2, game.getWinner());
        assertEquals(0, player1.getStoreCount());
        assertEquals(30, player2.getStoreCount());
    }

    @Test
    public void testGetWinnerWithPlayerTwoPitsEmptyAndPlayerOneWinner() throws GameNotOverException {
        for (int i = 6; i <= 11; i++) {
            game.getBoard().getPits().get(i).removeStones();
        }
        player1.getStore().addStones(2);
        assertEquals(player1, game.getWinner());
    }

    @Test
    public void testGetWinnerWithPlayerTwoPitsEmptyAndPlayerTwoWinner() throws GameNotOverException {
        for (int i = 6; i <= 11; i++) {
            game.getBoard().getPits().get(i).removeStones();
        }
        player2.getStore().addStones(29);
        assertEquals(player2, game.getWinner());
        assertEquals(29, player2.getStoreCount());
        assertEquals(24, player1.getStoreCount());
    }

    @Test
    public void testGetWinnerWithPlayerOnePitsEmptyAndTieGame() throws GameNotOverException {
        for (int i = 0; i <= 5; i++) {
            game.getBoard().getPits().get(i).removeStones();
        }
        player1.getStore().addStones(25);
        player2.getStore().addStones(1);
        assertEquals(null, game.getWinner());
    }

    @Test
    public void testGetWinnerWithPlayerTwoPitsEmptyAndTieGame() throws GameNotOverException {
        for (int i = 6; i <= 11; i++) {
            game.getBoard().getPits().get(i).removeStones();
        }
        player1.getStore().addStones(10);
        player2.getStore().addStones(34);
        assertEquals(null, game.getWinner());
    }

    @Test
    public void testIsGameOverAtStartOfGame() {
        assertFalse(game.isGameOver());
    }

    @Test
    public void testIsGameOverWithSomeEmptyPits() {
        for (int i = 0; i <= 2; i++) {
            game.getBoard().getPits().get(i).removeStones();
        }
        for (int i = 4; i <= 7; i++) {
            game.getBoard().getPits().get(i).removeStones();
        }
        assertFalse(game.isGameOver());
    }

    @Test
    public void testIsGameOverWithPlayerOneEmptyPits() {
        for (int i = 0; i <= 5; i++) {
            game.getBoard().getPits().get(i).removeStones();
        }
        assertTrue(game.isGameOver());
    }

    @Test
    public void testIsGameOverWithPlayerTwoEmptyPits() {
        for (int i = 6; i <= 11; i++) {
            game.getBoard().getPits().get(i).removeStones();
        }
        assertTrue(game.isGameOver());
    }

    @Test
    public void testIsGameOverWithAllEmptyPits() {
        for (int i = 0; i <= 11; i++) {
            game.getBoard().getPits().get(i).removeStones();
        }
        assertTrue(game.isGameOver());
        
    }

    @Test
    public void testStartNewGameII() throws PitNotFoundException, NoSuchPlayerException {
        game.startNewGame();
        for (int i = 1; i <= 12; i++) {
            assertEquals(4, game.getNumStones(i));
        }
        assertEquals(0, game.getStoreCount(player1));
        assertEquals(0, game.getStoreCount(player2));
    }

    @Test
    public void testStartNewGameDuringGame() throws InvalidMoveException,
        PitNotFoundException, NoSuchPlayerException {
        game.move(4);
        game.move(11);
        game.startNewGame();
        for (int i = 1; i <= 12; i++) {
            assertEquals(4, game.getNumStones(i));
        }
        assertEquals(0, game.getStoreCount(player1));
        assertEquals(0, game.getStoreCount(player2));
    }

    @Test
    public void testStartNewGameAtEndOfGame() throws PitNotFoundException, NoSuchPlayerException {
        for (int i = 0; i <= 5; i++) {
            game.getBoard().getPits().get(i).removeStones();
        }
        game.startNewGame();
        for (int i = 1; i <= 12; i++) {
            assertEquals(4, game.getNumStones(i));
        }
        assertEquals(0, game.getStoreCount(player1));
        assertEquals(0, game.getStoreCount(player2));
    }

    @Test
    public void testStartNewGameTwice() throws PitNotFoundException, NoSuchPlayerException {
        game.startNewGame();
        for (int i = 1; i <= 12; i++) {
            assertEquals(4, game.getNumStones(i));
        }
        assertEquals(0, game.getStoreCount(player1));
        assertEquals(0, game.getStoreCount(player2));
        game.startNewGame();
        for (int i = 1; i <= 12; i++) {
            assertEquals(4, game.getNumStones(i));
        }
        assertEquals(0, game.getStoreCount(player1));
        assertEquals(0, game.getStoreCount(player2));
    }
    */
}
