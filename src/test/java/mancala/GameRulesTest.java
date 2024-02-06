package mancala;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Method;

//import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;

public class GameRulesTest {
    private GameRules gameRules;
    private MancalaDataStructure dataStructure;
    private Player player1;
    private Player player2;
    
    @BeforeEach
    public void setUp() {
        /*Use a KalahRules reference to test the generic methods*/
        gameRules = new KalahRules();
        dataStructure = gameRules.getDataStructure();
        dataStructure.setUpPits();
        player1 = new Player("Player1");
        player2 = new Player("Player2");
        gameRules.registerPlayers(player1, player2);
    }

    @Test
    public void testSetUpPits() {
        for (int i = 1; i < 12; i++) {
            assertEquals(4, dataStructure.getNumStones(i));
        }
    }

    @Test
    public void testGetDataStructure() {
        assertEquals(dataStructure, gameRules.getDataStructure());
    }

    @Test
    public void testGetNumStones() {
        for (int i = 1; i <= 12; i++) {
            assertEquals(4, gameRules.getNumStones(i));
        }
    }

    @Test
    public void testGetNumStones_AfterAddingStones() {
        for (int i = 1; i <= 12; i++) {
            dataStructure.addStones(i, 1);
            assertEquals(5, gameRules.getNumStones(i));
        }
    }

    @Test
    public void testGetStoreCount() {
        assertEquals(0, gameRules.getStoreCount(1));
        assertEquals(0, gameRules.getStoreCount(2));
    }

    @Test
    public void testGetStoreCount_AfterAddingStones() {
        dataStructure.addToStore(1, 3);
        dataStructure.addToStore(2, 7);
        assertEquals(3, gameRules.getStoreCount(1));
        assertEquals(7, gameRules.getStoreCount(2));
    }

    @Test
    public void testGetStoreCount_AfterAddingNegativeNumber() {
        dataStructure.addToStore(2, -9);
        assertEquals(-9, gameRules.getStoreCount(2));
    }

    @Test
    public void testIsSideEmpty_AtStartOfGame() {
        for (int i = 1; i <= 2; i++) {
            assertFalse(gameRules.isSideEmpty(i));
        }
    }

    @Test
    public void testIsSideEmpty_AfterEmptyingRandomPits() {
        for (int i = 0; i <= 5; i++) {
            dataStructure.removeStones(2*i + 1);
        }
        for (int i = 1; i <= 2; i++) {
            assertFalse(gameRules.isSideEmpty(i));
        }
    }

    @Test
    public void testIsSideEmpty_AfterEmptyingPlayerOneSide() {
        for (int i = 1; i <= 6; i++) {
            dataStructure.removeStones(i);
        }
        assertTrue(gameRules.isSideEmpty(1));
        assertFalse(gameRules.isSideEmpty(2));
    }

    @Test
    public void testIsSideEmpty_AfterEmptyingPlayerTwoSide() {
        for (int i = 7; i <= 12; i++) {
            dataStructure.removeStones(i);
        }
        assertFalse(gameRules.isSideEmpty(1));
        assertTrue(gameRules.isSideEmpty(2));
    }

    @Test
    public void testIsSideEmpty_AfterEmptyingBothSides() {
        for (int i = 1; i <= 12; i++) {
            dataStructure.removeStones(i);
        }
        for (int i = 1; i <= 2; i++) {
            assertTrue(gameRules.isSideEmpty(i));
        }
    }

    @Test
    public void testSetPlayer() {
        gameRules.setPlayer(2);
        assertEquals(2, gameRules.getCurrentPlayer());
    }

    @Test
    public void testRegisterPlayers() {
        player1.addToStore(10);
        assertEquals(player1.getStoreCount(), dataStructure.getStoreCount(1));
    }

    @Test
    public void testResetBoard_AtStartOfGame() {
        gameRules.resetBoard();
        for (int i = 1; i < 12; i++) {
            assertEquals(4, dataStructure.getNumStones(i));
        }
        assertEquals(0, gameRules.getStoreCount(1));
        assertEquals(0, gameRules.getStoreCount(2));
    }

    @Test
    public void testResetBoard_AfterMoves() throws InvalidMoveException {
        gameRules.moveStones(3, 1);
        gameRules.moveStones(7, 2);
        gameRules.moveStones(4, 1);
        gameRules.resetBoard();
        for (int i = 1; i < 12; i++) {
            assertEquals(4, dataStructure.getNumStones(i));
        }
        assertEquals(0, gameRules.getStoreCount(1));
        assertEquals(0, gameRules.getStoreCount(2));
    }

    @Test
    public void testForInvalidMoveException_DueToInvalidStartPit() {
        assertThrows(InvalidMoveException.class, () -> gameRules.moveStones(-1, 1));
        assertThrows(InvalidMoveException.class, () -> gameRules.moveStones(0, 1));
        assertThrows(InvalidMoveException.class, () -> gameRules.moveStones(13, 1));
    }

    @Test
    public void testForInvalidMoveException_DueToInvalidPlayerNum() {
        assertThrows(InvalidMoveException.class, () -> gameRules.moveStones(1, 0));
        assertThrows(InvalidMoveException.class, () -> gameRules.moveStones(1, 3));
    }

    @Test
    public void testForInvalidMoveException_DueToEmptyStartPit() {
        dataStructure.removeStones(2);
        assertThrows(InvalidMoveException.class, () -> gameRules.moveStones(2, 1));
    }

    @Test
    public void testSwapPlayers_FromPlayerOne() {
        gameRules.setPlayer(1);
        gameRules.swapPlayers();
        assertEquals(2, gameRules.getCurrentPlayer());
    }

    @Test
    public void testSwapPlayers_FromPlayerTwo() {
        gameRules.setPlayer(2);
        gameRules.swapPlayers();
        assertEquals(1, gameRules.getCurrentPlayer());
    }

    @Test
    public void testMoveFinalStonesIntoStores() {
        gameRules.moveFinalStonesIntoStores();
        for (int i = 1; i <= 12; i++) {
            assertEquals(0, gameRules.getNumStones(i));
        }
        assertEquals(24, gameRules.getStoreCount(1));
        assertEquals(24, gameRules.getStoreCount(2));
    }

    @Test
    public void testGetWinner_WithGameNotOver() {
        assertThrows(GameNotOverException.class, () -> gameRules.getWinner());
    }

    @Test
    public void testGetWinner_WithPlayerOneWinner() throws GameNotOverException {
        for (int i = 7; i <= 12; i++) {
            dataStructure.removeStones(i);
        }
        assertEquals(1, gameRules.getWinner());
    }

    @Test
    public void testGetWinner_WithPlayerTwoWinner() throws GameNotOverException {
        for (int i = 1; i <= 6; i++) {
            dataStructure.removeStones(i);
        }
        assertEquals(2, gameRules.getWinner());
    }

    @Test
    public void testGetWinner_WithTiedGame() throws GameNotOverException {
        for (int i = 1; i <= 12; i++) {
            dataStructure.removeStones(i);
        }
        assertEquals(0, gameRules.getWinner());
    }

    @Test
    public void testSumStoresInPlayerPits() {
        assertEquals(24, gameRules.sumStonesInPlayersPits(1));
        assertEquals(24, gameRules.sumStonesInPlayersPits(2));
    }

    @Test
    public void testCurrPlayerStartPit_WithPlayerOneCurrentPlayer() {
        gameRules.setPlayer(1);
        assertEquals(1, gameRules.currPlayerStartPit());
    }

    @Test
    public void testCurrPlayerStartPit_WithPlayerTwoCurrentPlayer() {
        gameRules.setPlayer(2);
        assertEquals(7, gameRules.currPlayerStartPit());
    }

    @Test
    public void testCurrPlayerEndPit_WithPlayerOneCurrentPlayer() {
        gameRules.setPlayer(1);
        assertEquals(6, gameRules.currPlayerEndPit());
    }

    @Test
    public void testCurrPlayerEndPit_WithPlayerTwoCurrentPlayer() {
        gameRules.setPlayer(2);
        assertEquals(12, gameRules.currPlayerEndPit());
    }

}
