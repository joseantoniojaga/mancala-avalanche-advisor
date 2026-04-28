package com.mancala.advisor;

/**
 * Recommends the pit that maximises stones added to the player's store in a single turn.
 * When two pits produce the same score the one with the lower index is preferred,
 * giving deterministic output. No game-rule logic beyond turn simulation is performed here.
 */
public class GreedyRecommender implements MoveRecommender {

    private final TurnSimulator simulator;

    /**
     * @param simulator the simulator used to evaluate each candidate pit;
     *                  injected so the dependency can be replaced in tests
     */
    public GreedyRecommender(TurnSimulator simulator) {
        this.simulator = simulator;
    }

    /**
     * Evaluates every non-empty pit belonging to {@code player}, simulates the resulting
     * turn, and returns the pit that delivers the most stones to the player's store.
     *
     * @param board  the current board state; never mutated
     * @param player {@code 1} or {@code 2}
     * @return recommendation for the greedy-best pit
     * @throws IllegalArgumentException if {@code player} is not 1 or 2
     * @throws IllegalStateException    if all of the player's pits are empty
     */
    @Override
    public Recommendation recommendMove(Board board, int player) {
        if (player != 1 && player != 2) {
            throw new IllegalArgumentException("Player must be 1 or 2, got: " + player);
        }

        int pitStart = (player == 1) ? 0 : 7;
        int pitEnd   = (player == 1) ? 5 : 12;

        int bestPit    = -1;
        int bestStones = -1;

        for (int pit = pitStart; pit <= pitEnd; pit++) {
            if (board.getStones(pit) == 0) {
                continue;
            }

            int gained = simulator.simulateTurn(board, pit, player).getStonesGainedInStore();

            if (gained > bestStones) {
                bestStones = gained;
                bestPit    = pit;
            }
        }

        if (bestPit == -1) {
            throw new IllegalStateException("No legal moves available");
        }

        return new Recommendation(bestPit, bestStones);
    }
}
