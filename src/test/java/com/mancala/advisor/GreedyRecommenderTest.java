package com.mancala.advisor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GreedyRecommenderTest {

    private TurnSimulator simulator;
    private GreedyRecommender recommender;
    private Board board;

    @BeforeEach
    void setUp() {
        simulator  = new TurnSimulator();
        recommender = new GreedyRecommender(simulator);
        board      = new Board();
    }

    @Test
    void recommendsPitThatGivesMostStones() {
        board.setStones(0, 3); // sows to pits 1,2,3 — scores 0
        board.setStones(5, 1); // sows to store(6)  — scores 1

        Recommendation result = recommender.recommendMove(board, 1);

        assertEquals(5, result.getRecommendedPit());
        assertEquals(1, result.getExpectedStonesGained());
    }

    @Test
    void recommendsFreeTurnPit() {
        board.setStones(0, 1); // sows to pit 1 (empty) — scores 0
        board.setStones(5, 1); // sows to store(6)       — scores 1

        Recommendation result = recommender.recommendMove(board, 1);

        assertEquals(5, result.getRecommendedPit());
        assertEquals(1, result.getExpectedStonesGained());
    }

    /**
     * If empty pits were not skipped, pit 0 (empty) would be simulated and return
     * 0 stones gained. Because the loop starts at index 0 and uses strictly-greater
     * comparison, pit 0 would win the tie over pit 1 — returning a recommendation
     * for an empty pit. Skipping empty pits means pit 1 is the first (and only)
     * candidate, so it must be returned.
     */
    @Test
    void skipsEmptyPits() {
        board.setStones(1, 1); // only non-empty P1 pit; sows to pit 2 — scores 0
        // pits 0, 2, 3, 4, 5 are all 0 (empty)

        Recommendation result = recommender.recommendMove(board, 1);

        assertEquals(1, result.getRecommendedPit());
    }

    @Test
    void tiePicksLowerIndex() {
        // pit 0 with 6 stones: sows 1,2,3,4,5,store(6) — scores 1
        // pit 5 with 1 stone:  sows store(6)            — scores 1
        // both score 1; lower index (pit 0) must win
        board.setStones(0, 6);
        board.setStones(5, 1);

        Recommendation result = recommender.recommendMove(board, 1);

        assertEquals(0, result.getRecommendedPit());
        assertEquals(1, result.getExpectedStonesGained());
    }

    @Test
    void allPitsEmptyThrowsException() {
        // board is all zeros by default — no legal moves for player 1
        assertThrows(IllegalStateException.class,
                () -> recommender.recommendMove(board, 1));
    }

    @Test
    void invalidPlayerThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> recommender.recommendMove(board, 0));
        assertThrows(IllegalArgumentException.class,
                () -> recommender.recommendMove(board, 3));
    }

    @Test
    void doesNotMutateInputBoard() {
        board.setStones(0, 3);

        recommender.recommendMove(board, 1);

        assertEquals(3, board.getStones(0));
    }

    @Test
    void worksForPlayer2() {
        board.setStones(7,  3); // sows to pits 8,9,10       — scores 0
        board.setStones(12, 1); // sows to store(13)          — scores 1

        Recommendation result = recommender.recommendMove(board, 2);

        assertEquals(12, result.getRecommendedPit());
        assertEquals(1,  result.getExpectedStonesGained());
    }
}
