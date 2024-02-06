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

public class PlayerTest {
    private Player player;
    private Store store;

    @BeforeEach
    public void setUp() {
        player = new Player("Player One");
        store = new Store();
        player.setStore(store);
    }

    @Test
    public void testGetName() {
        assertEquals("Player One", player.getName());
    }

    @Test
    public void testSetName() {
        player.setName("New Name");
        assertEquals("New Name", player.getName());
    }

    @Test
    public void testGetStore() {
        assertSame(store, player.getStore());
    }

    @Test
    public void testSetStore() {
        Store newStore = new Store();
        player.setStore(newStore);
        assertSame(newStore, player.getStore());
    }

    //new test

    @Test
    public void testGetNameWithEmptyNameFromNameConstructor() {
        Player player2 = new Player("");
        assertEquals("", player2.getName());
    }

    @Test
    public void testGetNameWithNormalLengthedNameFromNameConstructor() {
        Player player2 = new Player("Some name");
        assertEquals("Some name", player2.getName());
    }

    @Test
    public void testGameNameWithVeryLongNameFromNameConstructor() {
        Player player2 = new Player("Some very long name for a player that doesn't make sense");
        assertEquals("Some very long name for a player that doesn't make sense", player2.getName());
    }

    @Test
    public void testGetNameFromSetNameWithNullString() {
        player.setName(null);
        assertEquals(null, player.getName());
    }

    @Test
    public void testGetNameFromSetNameWithEmptyString() {
        player.setName("");
        assertEquals("", player.getName());
    }

    @Test
    public void testGetNameFromSetNameWithNormalLengthedName() {
        player.setName("Player one");
        assertEquals("Player one", player.getName());
    }

    @Test
    public void testGetNameFromSetNameWithVeryLongName() {
        player.setName("Some very long name for a player that doesn't make sense");
        assertEquals("Some very long name for a player that doesn't make sense", player.getName());
    }

    @Test
    public void testGetStoreWithAStoreInitialized() {
        assertEquals(store, player.getStore());
    }

    @Test
    public void testGetStoreAfterSettingStoreToNull() {
        player.setStore(null);
        assertEquals(null, player.getStore());
    }

    @Test
    public void testGetStoreCountWithNewStore() {
        assertEquals(0, player.getStoreCount());
    }

    @Test
    public void testGetStoreCountWithStonesAddedFromStoreReference() {
        store.addStones(5);
        assertEquals(5, player.getStoreCount());
    }

    @Test
    public void testGetStoreCountWithStonesAddedViaPlayerReference() {
        player.getStore().addStones(5);
        assertEquals(5, player.getStoreCount());
    }
}
