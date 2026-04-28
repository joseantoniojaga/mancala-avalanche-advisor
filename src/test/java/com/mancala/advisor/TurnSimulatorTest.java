package com.mancala.advisor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnSimulatorTest {

    private TurnSimulator simulator;
    private Board board;

    @BeforeEach
    void setUp() {
        simulator = new TurnSimulator();
        board = new Board();
    }

    @Test
    void simpleSowingNoAvalanche() {
        board.setStones(0, 3);

        MoveResult result = simulator.simulateTurn(board, 0, 1);
        Board b = result.getBoard();

        assertEquals(0, b.getStones(0));
        assertEquals(1, b.getStones(1));
        assertEquals(1, b.getStones(2));
        assertEquals(1, b.getStones(3));
        assertEquals(0, b.getStones(6));
        assertEquals(0, result.getStonesGainedInStore());
        assertFalse(result.isGrantsFreeTurn());
    }

    @Test
    void lastStoneInOwnStoreGrantsFreeTurn() {
        board.setStones(5, 1);

        MoveResult result = simulator.simulateTurn(board, 5, 1);

        assertEquals(1, result.getBoard().getStones(6));
        assertEquals(1, result.getStonesGainedInStore());
        assertTrue(result.isGrantsFreeTurn());
    }

    @Test
    void lastStoneInOwnEmptyPitEndsTurn() {
        board.setStones(0, 1);

        MoveResult result = simulator.simulateTurn(board, 0, 1);

        assertEquals(0, result.getBoard().getStones(0));
        assertEquals(1, result.getBoard().getStones(1));
        assertEquals(0, result.getStonesGainedInStore());
        assertFalse(result.isGrantsFreeTurn());
    }

    /**
     * Trace: pit0=1, pit1=2.
     * Pick up 1 from pit0. Sow→pit1: pit1 becomes 3 (non-empty before) → avalanche.
     * Pick up all 3 from pit1. Sow→pit2, pit3, pit4. Last stone lands in pit4 (was empty) → turn ends.
     */
    @Test
    void avalancheTriggers() {
        board.setStones(0, 1);
        board.setStones(1, 2);

        MoveResult result = simulator.simulateTurn(board, 0, 1);
        Board b = result.getBoard();

        assertEquals(0, b.getStones(0));
        assertEquals(0, b.getStones(1));
        assertEquals(1, b.getStones(2));
        assertEquals(1, b.getStones(3));
        assertEquals(1, b.getStones(4));
        assertEquals(0, b.getStones(5));
        assertEquals(0, b.getStones(6));
        assertEquals(0, result.getStonesGainedInStore());
        assertFalse(result.isGrantsFreeTurn());
    }

    /**
     * Trace: pit5=9. Sow: 6(store+1), 7, 8, 9, 10, 11, 12, SKIP 13, 0, 1.
     * Last stone at pit1 (was empty) → no avalanche, no free turn.
     */
    @Test
    void skipsOpponentStore() {
        board.setStones(5, 9);

        MoveResult result = simulator.simulateTurn(board, 5, 1);
        Board b = result.getBoard();

        assertEquals(0, b.getStones(5));
        assertEquals(1, b.getStones(6));
        assertEquals(1, b.getStones(7));
        assertEquals(1, b.getStones(8));
        assertEquals(1, b.getStones(9));
        assertEquals(1, b.getStones(10));
        assertEquals(1, b.getStones(11));
        assertEquals(1, b.getStones(12));
        assertEquals(0, b.getStones(13));
        assertEquals(1, b.getStones(0));
        assertEquals(1, b.getStones(1));
        assertEquals(1, result.getStonesGainedInStore());
        assertFalse(result.isGrantsFreeTurn());
    }

    @Test
    void emptyStartingPitReturnsUnchanged() {
        MoveResult result = simulator.simulateTurn(board, 0, 1);

        assertEquals(0, result.getBoard().getStones(0));
        assertEquals(0, result.getStonesGainedInStore());
        assertFalse(result.isGrantsFreeTurn());
    }

    @Test
    void wrongOwnerThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> simulator.simulateTurn(board, 7, 1));
    }

    @Test
    void inputBoardIsNotMutated() {
        board.setStones(0, 3);

        simulator.simulateTurn(board, 0, 1);

        assertEquals(3, board.getStones(0));
    }

    @Test
    void player2BasicSowing() {
        board.setStones(7, 3);

        MoveResult result = simulator.simulateTurn(board, 7, 2);
        Board b = result.getBoard();

        assertEquals(0, b.getStones(7));
        assertEquals(1, b.getStones(8));
        assertEquals(1, b.getStones(9));
        assertEquals(1, b.getStones(10));
        assertEquals(0, result.getStonesGainedInStore());
        assertFalse(result.isGrantsFreeTurn());
    }

    @Test
    void player2LastStoneInOwnStore() {
        board.setStones(12, 1);

        MoveResult result = simulator.simulateTurn(board, 12, 2);

        assertEquals(0, result.getBoard().getStones(12));
        assertEquals(1, result.getBoard().getStones(13));
        assertEquals(1, result.getStonesGainedInStore());
        assertTrue(result.isGrantsFreeTurn());
    }
}
