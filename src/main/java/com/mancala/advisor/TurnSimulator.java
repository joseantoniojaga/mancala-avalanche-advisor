package com.mancala.advisor;

/**
 * Simulates a single Mancala Avalanche turn for one player.
 * Responsible only for turn mechanics; no recommendation logic, no UI.
 *
 * <p>Rules implemented:
 * <ul>
 *   <li>Stones are sown in ascending index order, wrapping 13→0.</li>
 *   <li>The opponent's store is always skipped.</li>
 *   <li>AVALANCHE: if the last stone lands in a non-empty pit, all stones there are picked up and sowing continues.</li>
 *   <li>FREE TURN: if the last stone lands in the player's own store, {@code grantsFreeTurn} is {@code true}.</li>
 *   <li>TURN ENDS: when the last stone lands in an empty pit (not a store).</li>
 * </ul>
 */
public class TurnSimulator {

    private static final int BOARD_SIZE     = 14;
    private static final int MAX_ITERATIONS = 10_000;

    /**
     * Simulates one full turn, including any avalanche chain, and returns the result.
     * The original {@code board} is never modified.
     *
     * @param board       the current board state
     * @param startingPit the pit the active player chose to sow from
     * @param player      {@code 1} or {@code 2}
     * @return a {@link MoveResult} describing the post-turn board, stones gained, and free-turn flag
     * @throws IllegalArgumentException if {@code startingPit} does not belong to {@code player}
     * @throws IllegalStateException    if sowing iterations exceed the safety limit (cycle guard)
     */
    public MoveResult simulateTurn(Board board, int startingPit, int player) {
        validateOwnership(board, startingPit, player);

        Board copy = board.copy();

        if (copy.getStones(startingPit) == 0) {
            return new MoveResult(copy, 0, false);
        }

        int playerStore   = (player == 1) ? board.getPlayerOneStoreIndex() : board.getPlayerTwoStoreIndex();
        int opponentStore = (player == 1) ? board.getPlayerTwoStoreIndex() : board.getPlayerOneStoreIndex();

        int stonesInHand        = copy.getStones(startingPit);
        int stonesGainedInStore = 0;
        int currentPos          = startingPit;
        int iterations          = 0;

        copy.setStones(startingPit, 0);

        while (stonesInHand > 0) {
            if (++iterations > MAX_ITERATIONS) {
                throw new IllegalStateException("Possible infinite loop in simulation");
            }

            currentPos = nextPosition(currentPos, opponentStore);
            copy.addStone(currentPos);
            stonesInHand--;

            if (currentPos == playerStore) {
                stonesGainedInStore++;
                if (stonesInHand == 0) {
                    return new MoveResult(copy, stonesGainedInStore, true);
                }
            } else if (stonesInHand == 0) {
                if (copy.getStones(currentPos) > 1) {
                    // Pit was non-empty before this stone arrived; avalanche
                    stonesInHand = copy.getStones(currentPos);
                    copy.setStones(currentPos, 0);
                }
                // else: landed in an empty pit — turn ends, fall through to return below
            }
        }

        return new MoveResult(copy, stonesGainedInStore, false);
    }

    private void validateOwnership(Board board, int startingPit, int player) {
        boolean valid = (player == 1) ? board.isPlayerOnePit(startingPit)
                                      : board.isPlayerTwoPit(startingPit);
        if (!valid) {
            throw new IllegalArgumentException(
                "Pit " + startingPit + " does not belong to player " + player);
        }
    }

    private int nextPosition(int current, int opponentStore) {
        int next = (current + 1) % BOARD_SIZE;
        return (next == opponentStore) ? (next + 1) % BOARD_SIZE : next;
    }
}
