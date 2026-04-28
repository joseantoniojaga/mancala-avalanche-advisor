package com.mancala.advisor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PitTest {

    @Test
    void newPitStartsEmpty() {
        Pit pit = new Pit();
        assertEquals(0, pit.getStoneCount());
    }

    @Test
    void newPitCanStartWithStones() {
        Pit pit = new Pit(4);
        assertEquals(4, pit.getStoneCount());
    }

    @Test
    void addStoneIncreasesCount() {
        Pit pit = new Pit();
        pit.addStone();
        assertEquals(1, pit.getStoneCount());
    }

    @Test
    void addStoneMultipleTimes() {
        Pit pit = new Pit();
        pit.addStone();
        pit.addStone();
        pit.addStone();
        assertEquals(3, pit.getStoneCount());
    }

    @Test
    void removeAllStonesReturnsCount() {
        Pit pit = new Pit(5);
        int removed = pit.removeAllStones();
        assertEquals(5, removed);
    }

    @Test
    void removeAllStonesLeavesPitEmpty() {
        Pit pit = new Pit(5);
        pit.removeAllStones();
        assertEquals(0, pit.getStoneCount());
    }

    @Test
    void emptyPitIsEmpty() {
        Pit pit = new Pit();
        assertTrue(pit.isEmpty());
    }

    @Test
    void pitWithStonesIsNotEmpty() {
        Pit pit = new Pit(1);
        assertFalse(pit.isEmpty());
    }

    @Test
    void cannotCreatePitWithNegativeStones() {
        assertThrows(IllegalArgumentException.class, () -> new Pit(-1));
    }
}
