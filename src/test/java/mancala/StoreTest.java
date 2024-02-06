/**
 * These test cases are intended to ensure that the correct API has been implemented for the classes under test.
 * They do not provide exhaustive coverage and thorough testing of all possible scenarios.
 * Additional test cases should be added to cover  edge cases and behaviors.
 */
package mancala;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StoreTest {
    private Store store;
    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player("Player One");
        store = new Store();
        store.setOwner(player);
    }

    @Test
    public void testSetOwner() {
        assertEquals(player, store.getOwner());
    }

    @Test
    public void testGetOwnerName() {
        assertEquals("Player One", store.getOwnerName());
    }

    @Test
    public void testAddStone() {
        store.addStone();
        assertEquals(1, store.getStoneCount());
    }

    @Test
    public void testAddStones() {
        store.addStones(5);
        assertEquals(5, store.getStoneCount());
    }

    @Test
    public void testGetTotalStones() {
        store.addStones(3);
        assertEquals(3, store.getStoneCount());
    }

    @Test
    public void testEmptyStore() {
        store.addStones(4);
        int emptiedStones = store.removeStones();
        assertEquals(4, emptiedStones);
        assertEquals(0, store.getStoneCount());
    }

    @Test
    public void testGetTotalStones_AtStartOfGame() {
        assertEquals(0, store.getStoneCount());
    }

    @Test
    public void testGetTotalStones_AfterAddingPositiveNumber() {
        store.addStones(3);
        assertEquals(3, store.getStoneCount());
    }

    @Test
    public void testGetTotalStones_AfterAddingNegativeNumber() {
        store.addStones(-4);
        assertEquals(-4, store.getStoneCount());
    }

    @Test
    public void testEmptyStore_AtStartOfGame() {
        assertEquals(0, store.removeStones());
        assertEquals(0, store.getStoneCount());
    }

    @Test
    public void testToString() {
        assertNotNull(store.toString());
    }
}
