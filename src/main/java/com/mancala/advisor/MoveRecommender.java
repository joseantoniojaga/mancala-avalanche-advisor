package com.mancala.advisor;

/**
 * Strategy for recommending the best pit to play in a Mancala Avalanche position.
 * Implementations evaluate available moves for the given player and return the
 * one considered optimal according to their strategy.
 */
public interface MoveRecommender {

    /**
     * Returns a {@link Recommendation} for the best pit the given player should sow from.
     *
     * @param board  the current board state (must not be modified)
     * @param player {@code 1} or {@code 2}
     * @return a recommendation identifying the pit and expected stones gained in store
     * @throws IllegalArgumentException if {@code player} is not 1 or 2
     * @throws IllegalStateException    if no legal moves are available for the player
     */
    Recommendation recommendMove(Board board, int player);
}
