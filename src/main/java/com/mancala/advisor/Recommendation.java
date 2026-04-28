package com.mancala.advisor;

/**
 * Immutable result of a move recommendation.
 * Carries the suggested pit index and the expected stones that will reach the player's store.
 */
public class Recommendation {

    private final int recommendedPit;
    private final int expectedStonesGained;

    /**
     * @param recommendedPit      index of the pit the player should sow from
     * @param expectedStonesGained stones the player will add to their store by playing this pit
     */
    public Recommendation(int recommendedPit, int expectedStonesGained) {
        this.recommendedPit      = recommendedPit;
        this.expectedStonesGained = expectedStonesGained;
    }

    /** Returns the index of the recommended pit to sow from. */
    public int getRecommendedPit() {
        return recommendedPit;
    }

    /** Returns the number of stones expected to reach the player's store this turn. */
    public int getExpectedStonesGained() {
        return expectedStonesGained;
    }
}
