package mancala;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class AyoRulesTest {
    private AyoRules rules;
    private Player player1;
    private Player player2;
    private MancalaDataStructure data;
    private AyoRules ayoRules;
    private MancalaDataStructure dataStructure;

    @BeforeEach
    public void setUp() {
        /* the constructor calls whatever methods
        are needed to set up the board data structure */
        rules = new AyoRules(); 
        player1 = new Player();
        player2 = new Player();
        rules.registerPlayers(player1, player2); 
        data = rules.getDataStructure();
        ayoRules = rules;
        dataStructure = data;

    }
 
    @Test
    public void testMoveStonesOneMove() throws InvalidMoveException {
  
          /*
         pull the stones from pit 4
        and distribute them.  
        We are emptying pit 7 first so that the turn
        ends after one move
        */

        data.removeStones(7);
        //player one move stones starting at pit four
        int num = rules.moveStones(4,1);
        assertEquals(1,num);  //number in players store
        /*other tests.   
        Pit 7 should also have 1 stone,  
        pit 5 should have 5 */

        assertEquals(1,data.getNumStones(7));
        assertEquals(5,data.getNumStones(5));
}

    @Test
    public void testMoveStonesMultiMove() throws InvalidMoveException {
  
        /*  
        set up so that there are three moves
          - add 2 stones to pit 12
        start at pit 4 - end at pit 7 - 
          pit 4 is empty and skipped for the rest of the turn
        continue from pit 7 (now empty) - end at pit 12
        continue from pit 12 (now empty)- end at pit 7 
            because pit 4 must be skipped.   
        */

        data.addStones(12,2);
        //player one move stones starting at pit one
        int num = rules.moveStones(4,1);
        // did we skip pit 4?
        assertEquals(0,data.getNumStones(4));
        // is pit 12 empty?
        assertEquals(0,data.getNumStones(12));
         //number in players store
        assertEquals(2,num); 
        // does pit 6 have 6 stones?
        assertEquals(6,data.getNumStones(6));
        // does pit 9 have 1 stone?
        assertEquals(1,data.getNumStones(7));                
  }

    @Test
    public void testDistributeStonesSingleMove() {
        /*empty pit 9  setup*/
        data.removeStones(9);
        assertEquals(0,data.getNumStones(9));


        //distribute stones starting at pit 6
        int num = rules.distributeStones(6);
        assertEquals(4,num);  //number distributed
        //pit 6 has zero stones
        assertEquals(0,data.getNumStones(6));
        //pit 8 has 5 stones
        assertEquals(5,data.getNumStones(8));
        //pit 9 has 1 stones
        assertEquals(1,data.getNumStones(9));
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
        // return value should be 4 as it is only stones captured
        assertEquals(4,num);
       //pit 12 should be empty
       assertEquals(0,data.getNumStones(12)); 
       //pit 1 should have a single stone
        assertEquals(1,data.getNumStones(1));
       /*not testing the player's store as it is not 
       the responsibility of capture stones to put stones
       in the store*/
}  

    @Test
    public void testCaptureStones_WithPlayerOne_OnPlayerOneSide() {
        ayoRules.setPlayer(1);
        assertEquals(4, ayoRules.captureStones(1));
        assertEquals(4, dataStructure.getNumStones(1));
        assertEquals(0, dataStructure.getNumStones(12));
        assertEquals(4, dataStructure.getStoreCount(1));
    }

    @Test
    public void testCaptureStones_WithPlayerOne_OnPlayerTwoSide() {
        ayoRules.setPlayer(1);
        assertEquals(4, ayoRules.captureStones(10));
        assertEquals(4, dataStructure.getNumStones(10));
        assertEquals(0, dataStructure.getNumStones(3));
        assertEquals(4, dataStructure.getStoreCount(1));
    }

    @Test
    public void testCaptureStones_WithPlayerTwo_OnPlayerTwoSide() {
        ayoRules.setPlayer(2);
        assertEquals(4, ayoRules.captureStones(7));
        assertEquals(4, dataStructure.getNumStones(7));
        assertEquals(0, dataStructure.getNumStones(6));
        assertEquals(4, dataStructure.getStoreCount(2));
    }

    @Test
    public void testCaptureStones_WithPlayerTwo_OnPlayerOneSide() {
        ayoRules.setPlayer(2);
        assertEquals(4, ayoRules.captureStones(3));
        assertEquals(4, dataStructure.getNumStones(3));
        assertEquals(0, dataStructure.getNumStones(10));
        assertEquals(4, dataStructure.getStoreCount(2));
    }

    @Test
    public void testCaptureStones_WithInvalidStoppingPoint() {
        assertThrows(RuntimeException.class, () -> ayoRules.captureStones(0));
    }

    /*
    @Test
    public void testDistributeStones_WithPlayerOne_FromPlayerOneSide() {
        dataStructure.setIterator(3, 1, true);
        ayoRules.setPlayer(1);
        assertEquals(4, ayoRules.distributeStones(3));
        for (int i = 4; i <= 6; i++) {
            assertEquals(5, dataStructure.getNumStones(i));
        }
        assertEquals(1, dataStructure.getStoreCount(1));
    }
    
    @Test
    public void testDistributeStones_WithPlayerOne_FromPlayerTwoSide() {
        dataStructure.setIterator(8, 1, true);
        ayoRules.setPlayer(1);
        assertEquals(4, ayoRules.distributeStones(8));
        for (int i = 9; i <= 12; i++) {
            assertEquals(5, dataStructure.getNumStones(i));
        }
        assertEquals(0, dataStructure.getStoreCount(1));
    }

    @Test
    public void testDistributeStones_WithPlayerOne_FromPlayerTwoSide_PassingPlayerTwoStore() {
        dataStructure.setIterator(12, 1, true);
        ayoRules.setPlayer(1);
        assertEquals(4, ayoRules.distributeStones(12));
        for (int i = 1; i <= 4; i++) {
            assertEquals(5, dataStructure.getNumStones(i));
        }
        assertEquals(0, dataStructure.getStoreCount(1));
        assertEquals(0, dataStructure.getStoreCount(2));
    }

    @Test
    public void testDistributeStones_WithPlayerTwo_FromPlayerTwoSide() {
        dataStructure.setIterator(10, 2, true);
        ayoRules.setPlayer(2);
        assertEquals(4, ayoRules.distributeStones(10));
        for (int i = 11; i <= 12; i++) {
            assertEquals(5, dataStructure.getNumStones(i));
        }
        assertEquals(5, dataStructure.getNumStones(1));
        assertEquals(1, dataStructure.getStoreCount(2));
    }
    
    @Test
    public void testDistributeStones_WithPlayerTwo_FromPlayerOneSide() {
        dataStructure.setIterator(2, 2, true);
        ayoRules.setPlayer(2);
        assertEquals(4, ayoRules.distributeStones(2));
        for (int i = 3; i <= 6; i++) {
            assertEquals(5, dataStructure.getNumStones(i));
        }
        assertEquals(0, dataStructure.getStoreCount(2));
    }

    @Test
    public void testDistributeStones_WithPlayerTwo_FromPlayerOneSide_PassingPlayerOneStore() {
        dataStructure.setIterator(4, 2, true);
        ayoRules.setPlayer(2);
        assertEquals(4, ayoRules.distributeStones(4));
        for (int i = 5; i <= 8; i++) {
            assertEquals(5, dataStructure.getNumStones(i));
        }
        assertEquals(0, dataStructure.getStoreCount(1));
        assertEquals(0, dataStructure.getStoreCount(2));
        assertEquals(8, ayoRules.getLastPitIndex());
    }

    @Test
    public void testDistributeStones_WhenPassingStartingPit() {
        dataStructure.setIterator(3, 1, true);
        ayoRules.setPlayer(1);
        dataStructure.addStones(3, 10);
        assertEquals(14, ayoRules.distributeStones(3));
        assertEquals(6, dataStructure.getNumStones(4));
        assertEquals(6, dataStructure.getNumStones(5));
        for (int i = 1; i <= 2; i++) {
            assertEquals(5, dataStructure.getNumStones(i));
        }
        for (int i = 6; i <= 12; i++) {
            assertEquals(5, dataStructure.getNumStones(i));
        }
        assertEquals(1, dataStructure.getStoreCount(1));
        assertEquals(0, dataStructure.getStoreCount(2));
        assertEquals(5, ayoRules.getLastPitIndex());
    }
    */

    @Test
    public void testDistributeStones_WithInvalidStartPit() {
        dataStructure.setIterator(1, 1, true);
        assertThrows(RuntimeException.class, () -> ayoRules.distributeStones(50));
    }

    @Test
    public void testMoveStones_WithRepetition() throws InvalidMoveException {
        dataStructure.setIterator(2, 1, true);
        for (int i = 7; i <= 12; i++) {
            dataStructure.removeStones(i);
        }
        assertEquals(1, ayoRules.moveStones(2, 1));
        assertEquals(0, dataStructure.getNumStones(2));
        for (int i = 3; i <= 5; i++) {
            assertEquals(5, dataStructure.getNumStones(i));
        }
        assertEquals(0, dataStructure.getNumStones(6));
        for (int i = 7; i <= 10; i++) {
            assertEquals(1, dataStructure.getNumStones(i));
        }
        assertEquals(1, dataStructure.getStoreCount(1));
        assertEquals(10, ayoRules.getLastPitIndex());
    }
}