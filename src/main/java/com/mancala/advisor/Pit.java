package com.mancala.advisor;

/**
 * Represents a single pit on a Mancala board.
 * Responsible only for tracking its stone count.
 */
public class Pit {

    private int stoneCount;

    /** Creates an empty pit with zero stones. */
    public Pit() {
        this.stoneCount = 0;
    }

    /**
     * Creates a pit with the given initial stone count.
     *
     * @param initialStones the starting number of stones
     * @throws IllegalArgumentException if initialStones is negative
     */
    public Pit(int initialStones) {
        if (initialStones < 0) {
            throw new IllegalArgumentException("Stone count cannot be negative");
        }
        this.stoneCount = initialStones;
    }

    /** Adds one stone to this pit. */
    public void addStone() {
        stoneCount++;
    }

    /**
     * Removes all stones from this pit and returns how many were taken.
     *
     * @return the number of stones removed
     */
    public int removeAllStones() {
        int removed = stoneCount;
        stoneCount = 0;
        return removed;
    }

    /** Returns the current number of stones in this pit. */
    public int getStoneCount() {
        return stoneCount;
    }

    /** Returns {@code true} if this pit contains no stones. */
    public boolean isEmpty() {
        return stoneCount == 0;
    }
}
