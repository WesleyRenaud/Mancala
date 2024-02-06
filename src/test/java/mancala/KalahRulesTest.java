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

public class KalahRulesTest {
    private KalahRules rules;
    private Player player1;
    private Player player2;
    private MancalaDataStructure data;
    private GameRules kalahRules;
    private MancalaDataStructure dataStructure;

    @BeforeEach
    public void setUp() {
        /*will be making the assumption that the constructor call
        sets everything up to call other methods */
        rules = new KalahRules(); 
        player1 = new Player();
        player2 = new Player();
        /*don't change the register players method signature
        because I need players to test */
        rules.registerPlayers(player1, player2); 
        data = rules.getDataStructure();
        kalahRules = rules;
        dataStructure = data;
    }


 
    @Test
    public void testMoveStonesOneMove() throws InvalidMoveException {
    
        //player one move stones starting at pit 6
        int num = rules.moveStones(6,1);
        assertEquals(1,num);  //number in players store
        //pit 6 has zero stones
        assertEquals(0,data.getNumStones(6));
        //pit 8 has 5 stones
        assertEquals(5,data.getNumStones(8));
}

    @Test
    public void testMoveStonesWithCapture() throws InvalidMoveException {
  
        /*
        The move we will be testing is to pull the stones from pit 6
        and distribute them.  We will set up so that there enough stones in 
        pit 6 to wrap around to pit 1, which we will empty before starting 

        */
        //add stones to pit 6 to total 8 stones
        data.addStones(6,4);
        //remove all stones from pit 1
        data.removeStones(1);
        //player one move stones starting at pit one
        int num = rules.moveStones(6,1);
       
       //player store should now have 7 stones
       assertEquals(7, player1.getStoreCount());    
       //pits 1 and 12 should be empty
       assertEquals(0,data.getNumStones(1));
       assertEquals(0,data.getNumStones(12));       
  }

    @Test
    public void testDistributeStonesSingleMove() {
  
        //distribute stones starting at pit 6
        int num = rules.distributeStones(6);
        assertEquals(4,num);  //number distributed
        //pit 6 has zero stones
        assertEquals(0,data.getNumStones(6));
        //pit 8 has 5 stones
        assertEquals(5,data.getNumStones(8));
}


    @Test
    public void testCaptureStonesNonEmptyTarget() {
  
         //remove all stones from pit 1
        data.removeStones(1);
        // add a single stone to pit 1 to simulate capture scenario
        data.addStones(1,1);
        //player one move stones starting at pit one
        rules.setPlayer(1);
        int num = rules.captureStones(1);

        /* return value should be 5 to include singleton
        stone on players side*/
        assertEquals(5,num);
       //pits 1 and 12 should be empty
       assertEquals(0,data.getNumStones(1));
       assertEquals(0,data.getNumStones(12)); 
       /*not testing the player's store as it is not 
       the responsibility of capture stones to put stones
       in the store*/
}

    @Test
    public void testSetUpPits() {
        for (int i = 1; i < 12; i++) {
            assertEquals(4, dataStructure.getNumStones(i));
        }
    }

    @Test
    public void testCaptureStones_WithPlayerOne_OnPlayerOneSide() {
        kalahRules.setPlayer(1);
        assertEquals(8, kalahRules.captureStones(1));
        assertEquals(0, dataStructure.getNumStones(1));
        assertEquals(0, dataStructure.getNumStones(12));
        assertEquals(8, dataStructure.getStoreCount(1));
    }

    @Test
    public void testCaptureStones_WithPlayerOne_OnPlayerTwoSide() {
        kalahRules.setPlayer(1);
        assertEquals(8, kalahRules.captureStones(10));
        assertEquals(0, dataStructure.getNumStones(10));
        assertEquals(0, dataStructure.getNumStones(3));
        assertEquals(8, dataStructure.getStoreCount(1));
    }

    @Test
    public void testCaptureStones_WithPlayerTwo_OnPlayerTwoSide() {
        kalahRules.setPlayer(2);
        assertEquals(8, kalahRules.captureStones(7));
        assertEquals(0, dataStructure.getNumStones(7));
        assertEquals(0, dataStructure.getNumStones(6));
        assertEquals(8, dataStructure.getStoreCount(2));
    }

    @Test
    public void testCaptureStones_WithPlayerTwo_OnPlayerOneSide() {
        kalahRules.setPlayer(2);
        assertEquals(8, kalahRules.captureStones(3));
        assertEquals(0, dataStructure.getNumStones(3));
        assertEquals(0, dataStructure.getNumStones(10));
        assertEquals(8, dataStructure.getStoreCount(2));
    }

    @Test
    public void testCaptureStones_WithInvalidStoppingPoint() {
        assertThrows(RuntimeException.class, () -> kalahRules.captureStones(0));
    }

    @Test
    public void testDistributeStones_WithPlayerOne_FromPlayerOneSide() {
        kalahRules.setPlayer(1);
        assertEquals(4, kalahRules.distributeStones(3));
        for (int i = 4; i <= 6; i++) {
            assertEquals(5, dataStructure.getNumStones(i));
        }
        assertEquals(1, dataStructure.getStoreCount(1));
    }
    
    @Test
    public void testDistributeStones_WithPlayerOne_FromPlayerTwoSide() {
        kalahRules.setPlayer(1);
        assertEquals(4, kalahRules.distributeStones(8));
        for (int i = 9; i <= 12; i++) {
            assertEquals(5, dataStructure.getNumStones(i));
        }
        assertEquals(0, dataStructure.getStoreCount(1));
    }

    @Test
    public void testDistributeStones_WithPlayerOne_FromPlayerTwoSide_PassingPlayerTwoStore() {
        kalahRules.setPlayer(1);
        assertEquals(4, kalahRules.distributeStones(12));
        for (int i = 1; i <= 4; i++) {
            assertEquals(5, dataStructure.getNumStones(i));
        }
        assertEquals(0, dataStructure.getStoreCount(1));
        assertEquals(0, dataStructure.getStoreCount(2));
    }

    @Test
    public void testDistributeStones_WithPlayerTwo_FromPlayerTwoSide() {
        kalahRules.setPlayer(2);
        assertEquals(4, kalahRules.distributeStones(10));
        for (int i = 11; i <= 12; i++) {
            assertEquals(5, dataStructure.getNumStones(i));
        }
        assertEquals(5, dataStructure.getNumStones(1));
        assertEquals(1, dataStructure.getStoreCount(2));
    }
    
    @Test
    public void testDistributeStones_WithPlayerTwo_FromPlayerOneSide() {
        kalahRules.setPlayer(2);
        assertEquals(4, kalahRules.distributeStones(2));
        for (int i = 3; i <= 6; i++) {
            assertEquals(5, dataStructure.getNumStones(i));
        }
        assertEquals(0, dataStructure.getStoreCount(2));
    }

    @Test
    public void testDistributeStones_WithPlayerTwo_FromPlayerOneSide_PassingPlayerOneStore() {
        kalahRules.setPlayer(2);
        assertEquals(4, kalahRules.distributeStones(4));
        for (int i = 5; i <= 8; i++) {
            assertEquals(5, dataStructure.getNumStones(i));
        }
        assertEquals(0, dataStructure.getStoreCount(1));
        assertEquals(0, dataStructure.getStoreCount(2));
    }

    @Test
    public void testDistributeStones_WithInvalidStartPit() {
        assertThrows(RuntimeException.class, () -> kalahRules.distributeStones(50));
    }

    @Test
    public void testMoveStones_WithPlayerOne_FromPlayerOneSide_StoreNotReached() throws InvalidMoveException {
        assertEquals(0, kalahRules.moveStones(2, 1));
        assertEquals(0, dataStructure.getNumStones(2));
        for (int i = 3; i <= 6; i++) {
            assertEquals(5, dataStructure.getNumStones(i));
        }
        assertEquals(4, dataStructure.getNumStones(7));
        assertEquals(0, dataStructure.getStoreCount(1));
    }

    @Test
    public void testMoveStones_WithPlayerOne_FromPlayerOneSide_StoreReached() throws InvalidMoveException {
        assertEquals(1, kalahRules.moveStones(4, 1));
        assertEquals(0, dataStructure.getNumStones(4));
        for (int i = 5; i <= 7; i++) {
            assertEquals(5, dataStructure.getNumStones(i));
        }
        assertEquals(4, dataStructure.getNumStones(8));
        assertEquals(1, dataStructure.getStoreCount(1));
    }

    @Test
    public void testMoveStones_WithPlayerOne_FromPlayerTwoSide() throws InvalidMoveException {
        assertEquals(0, kalahRules.moveStones(10, 1));
        assertEquals(0, dataStructure.getNumStones(10));
        for (int i = 11; i <= 12; i++) {
            assertEquals(5, dataStructure.getNumStones(i));
        }
        for (int i = 1; i <= 2; i++) {
            assertEquals(5, dataStructure.getNumStones(i));
        }
        assertEquals(4, dataStructure.getNumStones(3));
        assertEquals(0, dataStructure.getStoreCount(1));
        assertEquals(0, dataStructure.getStoreCount(2));
    }

    @Test
    public void testMoveStones_WithPlayerTwo_FromPlayerTwoSide_StoreNotReached() throws InvalidMoveException {
        assertEquals(0, kalahRules.moveStones(7, 2));
        assertEquals(0, dataStructure.getNumStones(7));
        for (int i = 8; i <= 11; i++) {
            assertEquals(5, dataStructure.getNumStones(i));
        }
        assertEquals(4, dataStructure.getNumStones(12));
        assertEquals(0, dataStructure.getStoreCount(2));
    }

    @Test
    public void testMoveStones_WithPlayerTwo_FromPlayerTwoSide_StoreReached() throws InvalidMoveException {
        assertEquals(1, kalahRules.moveStones(12, 2));
        assertEquals(0, dataStructure.getNumStones(12));
        for (int i = 1; i <= 3; i++) {
            assertEquals(5, dataStructure.getNumStones(i));
        }
        assertEquals(4, dataStructure.getNumStones(4));
        assertEquals(1, dataStructure.getStoreCount(2));
    }

    @Test
    public void testMoveStones_WithPlayerTwo_FromPlayerOneSide() throws InvalidMoveException {
        assertEquals(0, kalahRules.moveStones(3, 2));
        assertEquals(0, dataStructure.getNumStones(3));
        for (int i = 4; i <= 7; i++) {
            assertEquals(5, dataStructure.getNumStones(i));
        }
        assertEquals(4, dataStructure.getNumStones(8));
        assertEquals(0, dataStructure.getStoreCount(1));
        assertEquals(0, dataStructure.getStoreCount(2));
    }

    @Test
    public void testMoveStone_WithSteal() throws InvalidMoveException {
        dataStructure.removeStones(6);
        kalahRules.moveStones(2, 1);
        //assertEquals(5, kalahRules.moveStones(2, 1));
        for (int i = 3; i <= 5; i++) {
            assertEquals(5, dataStructure.getNumStones(i));
        }
        assertEquals(0, dataStructure.getNumStones(6));
        assertEquals(0, dataStructure.getNumStones(7));
        assertEquals(5, dataStructure.getStoreCount(1));
    }
}