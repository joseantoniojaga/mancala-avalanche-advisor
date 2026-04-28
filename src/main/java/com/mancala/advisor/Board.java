package com.mancala.advisor;

/**
 * Represents the physical layout of a Mancala board.
 * Manages 14 positions: pits and stores for two players.
 * Responsible only for board state — no game rules.
 *
 * <pre>
 *   P2 pits : [12][11][10][ 9][ 8][ 7]
 *   Stores  : [13]                  [ 6]
 *   P1 pits :  [ 0][ 1][ 2][ 3][ 4][ 5]
 * </pre>
 */
public class Board {

    private static final int BOARD_SIZE              = 14;
    private static final int PLAYER_ONE_STORE_INDEX  = 6;
    private static final int PLAYER_TWO_STORE_INDEX  = 13;
    private static final int PLAYER_ONE_PITS_START   = 0;
    private static final int PLAYER_ONE_PITS_END     = 5;
    private static final int PLAYER_TWO_PITS_START   = 7;
    private static final int PLAYER_TWO_PITS_END     = 12;

    private final Pit[] positions;

    /** Creates a board with all 14 positions initialised to zero stones. */
    public Board() {
        positions = new Pit[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            positions[i] = new Pit();
        }
    }

    /** Returns the total number of positions on this board (always 14). */
    public int size() {
        return BOARD_SIZE;
    }

    /**
     * Returns the number of stones at the given position.
     *
     * @param position index 0–13
     * @throws IllegalArgumentException if position is out of range
     */
    public int getStones(int position) {
        validatePosition(position);
        return positions[position].getStoneCount();
    }

    /**
     * Sets the number of stones at the given position.
     *
     * @param position index 0–13
     * @param count    number of stones; must be non-negative
     * @throws IllegalArgumentException if position is out of range or count is negative
     */
    public void setStones(int position, int count) {
        validatePosition(position);
        if (count < 0) {
            throw new IllegalArgumentException("Stone count cannot be negative");
        }
        positions[position].removeAllStones();
        for (int i = 0; i < count; i++) {
            positions[position].addStone();
        }
    }

    /**
     * Adds one stone to the given position.
     *
     * @param position index 0–13
     * @throws IllegalArgumentException if position is out of range
     */
    public void addStone(int position) {
        validatePosition(position);
        positions[position].addStone();
    }

    /**
     * Returns {@code true} if the given position is a store (index 6 or 13).
     *
     * @param position index 0–13
     */
    public boolean isStore(int position) {
        return position == PLAYER_ONE_STORE_INDEX || position == PLAYER_TWO_STORE_INDEX;
    }

    /**
     * Returns {@code true} if the given position is one of Player 1's pits (0–5).
     *
     * @param position index 0–13
     */
    public boolean isPlayerOnePit(int position) {
        return position >= PLAYER_ONE_PITS_START && position <= PLAYER_ONE_PITS_END;
    }

    /**
     * Returns {@code true} if the given position is one of Player 2's pits (7–12).
     *
     * @param position index 0–13
     */
    public boolean isPlayerTwoPit(int position) {
        return position >= PLAYER_TWO_PITS_START && position <= PLAYER_TWO_PITS_END;
    }

    /** Returns the index of Player 1's store (always 6). */
    public int getPlayerOneStoreIndex() {
        return PLAYER_ONE_STORE_INDEX;
    }

    /** Returns the index of Player 2's store (always 13). */
    public int getPlayerTwoStoreIndex() {
        return PLAYER_TWO_STORE_INDEX;
    }

    /**
     * Returns {@code true} if all of Player 1's pits (positions 0–5) are empty.
     */
    public boolean isPlayerOneSideEmpty() {
        for (int i = PLAYER_ONE_PITS_START; i <= PLAYER_ONE_PITS_END; i++) {
            if (!positions[i].isEmpty()) return false;
        }
        return true;
    }

    /**
     * Returns {@code true} if all of Player 2's pits (positions 7–12) are empty.
     */
    public boolean isPlayerTwoSideEmpty() {
        for (int i = PLAYER_TWO_PITS_START; i <= PLAYER_TWO_PITS_END; i++) {
            if (!positions[i].isEmpty()) return false;
        }
        return true;
    }

    /**
     * Returns a deep copy of this board. The copy is fully independent:
     * modifications to either board will not affect the other.
     *
     * @return a new {@link Board} with identical stone counts at every position
     */
    public Board copy() {
        Board copy = new Board();
        for (int i = 0; i < BOARD_SIZE; i++) {
            copy.setStones(i, this.getStones(i));
        }
        return copy;
    }

    private void validatePosition(int position) {
        if (position < 0 || position >= BOARD_SIZE) {
            throw new IllegalArgumentException(
                "Position " + position + " is out of range [0, " + (BOARD_SIZE - 1) + "]"
            );
        }
    }
}
