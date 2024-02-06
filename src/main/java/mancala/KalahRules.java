package mancala;

/**
 * Class that implements the mechanics of the game using Kalah rules.
 */
public class KalahRules extends GameRules {
    private static final long serialVersionUID = 6590317064585867117L;

    /**
     * Makes a KalahRules objects by initializing the data structure.
     */
    public KalahRules() {
        super();
    }

    /**
     * Completes a move using Kalah rules.
     * 
     * @param startPit  The pit to start the move from,
     * @param playerNum The current player (1 or 2).
     */
    @Override
    public int moveStones(final int startPit, final int playerNum) throws InvalidMoveException {
        checkForInvalidMove(startPit, playerNum);
        setPlayer(playerNum);

        final int initialStoneCount = getDataStructure().getStoreCount(playerNum);
        distributeStones(startPit);
        
        return getDataStructure().getStoreCount(playerNum) - initialStoneCount;
    }

    /**
     * Distributes stones using Kalah rules.
     * 
     * @param startPit  The pit to start distributing from.
     * @return  The stones being moved.
     */
    @Override
    int distributeStones(final int startPit) {
        if (startPit < 1 || startPit > 12) {
            throw new RuntimeException("Pit Number Out of Range");
        }

        // take the stones out of the starting pit
        final int stoneToMove = getDataStructure().removeStones(startPit);
        getDataStructure().setIterator(startPit, getPlayer(), false);

        // physically distribute the stones across the board
        Countable currentSpot = null;
        for (int i = 0; i<stoneToMove; i++) {
            currentSpot = getDataStructure().next();
            currentSpot.addStone();
        }
        
        // check for a steal
        checkForSteal(currentSpot);
        if (doPerformSteal()) {
            setPerformSteal(false);
            captureStones(getLastPitIndex());
        }
        // check for giving an extra turn
        checkForExtraTurn(currentSpot);
        
        return stoneToMove;
    }

    /**
     * Captures stones from the across pit using Kalah rules, and puts them in the player's store.
     * 
     * @param stoppindPoint The pit to capture across from.
     * @return  The number of stones that were captured.
     */
    @Override
    int captureStones(final int stoppingPoint) {
        int stonesCaptured = getDataStructure().removeStones(13-stoppingPoint);
        stonesCaptured += getDataStructure().removeStones(stoppingPoint);

        getDataStructure().addToStore(getPlayer(), stonesCaptured);
        
        return stonesCaptured;
    }
}
