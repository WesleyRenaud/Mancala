package mancala;

/**
 * Class that implements the mechanics of the game using Ayo rules.
 */
public class AyoRules extends GameRules {
    private static final long serialVersionUID = 8868613842003379656L;

    /**
     * Creates an AyoRules object by initializing the data structure.
     */
    public AyoRules() {
        super();
    }

   /**
     * Completes a move using Ayo rules, until the final pit is empty.
     * 
     * @param startPit  The pit to start the move from,
     * @param playerNum The current player (1 or 2).
     */
    @Override
    public int moveStones(final int startPit, final int playerNum) throws InvalidMoveException {
        checkForInvalidMove(startPit, playerNum);
        setPlayer(playerNum);

        final int initialStoneCount = getDataStructure().getStoreCount(playerNum);
        // we distriubte stones until the starting pit has zero stones
        distributeStones(startPit);

        swapPlayers();
        return getDataStructure().getStoreCount(playerNum) - initialStoneCount;
    }

    /**
     * Distributes stones using Ayo rules, skipping the starting pit.
     * 
     * @param startPit  The pit to start distributing from.
     * @return  The number of stones being moved.
     */
    @Override
    int distributeStones(final int startPit) {
        if (startPit < 1 || startPit > 12) {
            throw new RuntimeException("Pit Number Out of Range");
        }
        getDataStructure().setIterator(startPit, getPlayer(), true);
        
        int stonesMoved = 0;
        int currStartPit = startPit;
        Countable currentSpot = null;
        do {
            final int stonesToMove = getDataStructure().removeStones(currStartPit);
            for (int i = 0; i< stonesToMove; i++) {
               
                stonesMoved++;
                currentSpot = getDataStructure().next();
                currentSpot.addStone();
                if (getDataStructure().isPit(currentSpot)) {
                    setLastPitIndex(currentSpot);
                }
            }
            currStartPit = getLastPitIndex();
        } while (getNumStones(currStartPit) > 1 && !getDataStructure().isStore(currentSpot));

        checkForSteal(currentSpot);
        if (doPerformSteal()) {
            captureStones(getLastPitIndex());
        }
        
        return stonesMoved;
    }

    /**
     * Captures stones from the across pit, using Ayo rules, and puts them in the player's store.
     * 
     * @param stoppingPoint The pit to capture across from.
     * @return  The number of stones that were captured.
     */
    @Override
    int captureStones(final int stoppingPoint) {
        final int stoneCaptured = getDataStructure().removeStones(13-stoppingPoint);
        // with the ayo rules we don't remvove the stones from the stopping point
        getDataStructure().addToStore(getPlayer(), stoneCaptured);
        
        return stoneCaptured;
    }
}