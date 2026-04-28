package com.mancala.advisor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void newBoardHasFourteenPositions() {
        assertEquals(14, board.size());
    }

    @Test
    void newBoardAllPitsHaveZeroStones() {
        for (int i = 0; i < 14; i++) {
            assertEquals(0, board.getStones(i), "Position " + i + " should start at 0");
        }
    }

    @Test
    void canSetStonesAtPosition() {
        board.setStones(3, 5);
        assertEquals(5, board.getStones(3));
    }

    @Test
    void canGetStonesAtPosition() {
        board.setStones(3, 7);
        assertEquals(7, board.getStones(3));
    }

    @Test
    void canAddStoneAtPosition() {
        board.setStones(3, 2);
        board.addStone(3);
        assertEquals(3, board.getStones(3));
    }

    @Test
    void positionSixIsPlayerOneStore() {
        assertTrue(board.isStore(6));
    }

    @Test
    void positionThirteenIsPlayerTwoStore() {
        assertTrue(board.isStore(13));
    }

    @Test
    void positionZeroIsNotStore() {
        assertFalse(board.isStore(0));
    }

    @Test
    void playerOneOwnsPositionsZeroToFive() {
        for (int i = 0; i <= 5; i++) {
            assertTrue(board.isPlayerOnePit(i), "Position " + i + " should belong to player one");
        }
    }

    @Test
    void playerOneDoesNotOwnPositionSeven() {
        assertFalse(board.isPlayerOnePit(7));
    }

    @Test
    void playerTwoOwnsPositionsSevenToTwelve() {
        for (int i = 7; i <= 12; i++) {
            assertTrue(board.isPlayerTwoPit(i), "Position " + i + " should belong to player two");
        }
    }

    @Test
    void playerOneStoreIndexIsSix() {
        assertEquals(6, board.getPlayerOneStoreIndex());
    }

    @Test
    void playerTwoStoreIndexIsThirteen() {
        assertEquals(13, board.getPlayerTwoStoreIndex());
    }

    @Test
    void playerOneSideEmptyWhenAllPitsZero() {
        assertTrue(board.isPlayerOneSideEmpty());
    }

    @Test
    void playerOneSideNotEmptyWhenSomeHaveStones() {
        board.setStones(2, 3);
        assertFalse(board.isPlayerOneSideEmpty());
    }

    @Test
    void playerTwoSideEmptyWhenAllPitsZero() {
        assertTrue(board.isPlayerTwoSideEmpty());
    }

    @Test
    void invalidPositionThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> board.getStones(-1));
        assertThrows(IllegalArgumentException.class, () -> board.getStones(14));
    }

    @Test
    void negativeStonesThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> board.setStones(0, -1));
    }

    @Test
    void copyHasSameStoneCounts() {
        board.setStones(0, 4);
        board.setStones(6, 7);
        board.setStones(13, 3);

        Board copy = board.copy();

        for (int i = 0; i < 14; i++) {
            assertEquals(board.getStones(i), copy.getStones(i), "Position " + i + " differs");
        }
    }

    @Test
    void copyIsIndependent() {
        board.setStones(0, 4);
        Board copy = board.copy();

        copy.setStones(0, 99);

        assertEquals(4, board.getStones(0));
    }

    @Test
    void copyIsIndependentReverse() {
        board.setStones(0, 4);
        Board copy = board.copy();

        board.setStones(0, 99);

        assertEquals(4, copy.getStones(0));
    }
}
