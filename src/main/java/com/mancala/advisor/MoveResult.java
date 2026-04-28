package com.mancala.advisor;

/**
 * Immutable result of simulating one Mancala turn.
 * Carries the post-turn board state and outcome flags; no game-rule logic.
 */
public class MoveResult {

    private final Board board;
    private final int stonesGainedInStore;
    private final boolean grantsFreeTurn;

    /**
     * @param board               the board state after the turn completes
     * @param stonesGainedInStore total stones the active player added to their store this turn
     * @param grantsFreeTurn      {@code true} if the last stone landed in the player's own store
     */
    public MoveResult(Board board, int stonesGainedInStore, boolean grantsFreeTurn) {
        this.board = board;
        this.stonesGainedInStore = stonesGainedInStore;
        this.grantsFreeTurn = grantsFreeTurn;
    }

    /** Returns the board state after the turn. */
    public Board getBoard() {
        return board;
    }

    /** Returns the number of stones the active player added to their store during this turn. */
    public int getStonesGainedInStore() {
        return stonesGainedInStore;
    }

    /** Returns {@code true} if the last stone landed in the active player's own store. */
    public boolean isGrantsFreeTurn() {
        return grantsFreeTurn;
    }
}
