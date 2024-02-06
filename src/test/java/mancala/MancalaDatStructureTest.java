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

public class MancalaDatStructureTest {
    private MancalaDataStructure dataStructure;
    private Player player1;
    private Player player2;
    
    @BeforeEach
    public void setUp() {
        dataStructure = new MancalaDataStructure();
        dataStructure.setUpPits();
        player1 = new Player("Player1");
        player2 = new Player("Player2");
    }

    @Test
    public void testSetUpPits() {
        for (int i = 1; i <= 12; i++) {
            assertEquals(4, dataStructure.getNumStones(i));
        }
    }

    @Test
    public void testAddStones_WithPositiveNumber() {
        dataStructure.addStones(1, 5);
        assertEquals(9, dataStructure.getNumStones(1));
    }

    @Test
    public void testAddStones_WithNegativeNumber() {
        dataStructure.addStones(3, -8);
        assertEquals(-4, dataStructure.getNumStones(3));
    }

    @Test
    public void testAddStones_WithInvalidPitIndex() {
        assertThrows(RuntimeException.class, () -> dataStructure.addStones(13, 1));
    }

    @Test
    public void testRemoveStones() {
        dataStructure.removeStones(7);
        assertEquals(0, dataStructure.getNumStones(7));
    }

    @Test
    public void testRemoveStonesTwice() {
        dataStructure.removeStones(5);
        dataStructure.removeStones(5);
        assertEquals(0, dataStructure.getNumStones(5));
    }

    @Test
    public void testRemoveStones_WithInvalidPitIndex() {
        assertThrows(RuntimeException.class, () -> dataStructure.removeStones(0));
    }

    @Test
    public void testAddToStore() {
        dataStructure.addToStore(1, 10);
        assertEquals(10, dataStructure.getStoreCount(1));
        assertEquals(0, dataStructure.getStoreCount(2));
    }

    @Test
    public void testAddToStore_WithInvalidPlayerNumber() {
        assertThrows(RuntimeException.class, () -> dataStructure.addToStore(3, 2));
    }

    @Test
    public void testEmptyStores() {
        dataStructure.addToStore(1, 8);
        dataStructure.addToStore(2, 5);
        dataStructure.emptyStores();
    }

    @Test
    public void testSetStore() {
        Store store = new Store();
        store.addStones(5);
        dataStructure.setStore(store, 1);
        assertEquals(5, dataStructure.getStoreCount(1));
    }

    @Test
    public void testIsSideEmpty_WithNoEmptyPits() {
        assertFalse(dataStructure.isSideEmpty(1));
        assertFalse(dataStructure.isSideEmpty(2));
    }

    @Test
    public void testIsSideEmpty_WithInvalidPitNumber() {
        assertThrows(RuntimeException.class, () -> dataStructure.isSideEmpty(3));
    }

    @Test
    public void testIsSideEmpty_AfterEmptyingSideOne() {
        for (int i = 1; i <= 6; i++) {
            dataStructure.removeStones(i);
        }
        assertTrue(dataStructure.isSideEmpty(1));
        assertFalse(dataStructure.isSideEmpty(2));
    }

    @Test
    public void testIsSideEmpty_AfterEmptyingSideTwo() {
        for (int i = 7; i <= 12; i++) {
            dataStructure.removeStones(i);
        }
        assertFalse(dataStructure.isSideEmpty(1));
        assertTrue(dataStructure.isSideEmpty(2));
    }

    @Test
    public void testIsSideEmpty_AfterEmptyingBothSides() {
        for (int i = 1; i <= 12; i++) {
            dataStructure.removeStones(i);
        }
        assertTrue(dataStructure.isSideEmpty(1));
        assertTrue(dataStructure.isSideEmpty(2));
    }
    
}
