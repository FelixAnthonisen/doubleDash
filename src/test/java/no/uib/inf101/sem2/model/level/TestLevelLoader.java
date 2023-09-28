package no.uib.inf101.sem2.model.level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sem2.grid.CellPosition;
import no.uib.inf101.sem2.model.tiles.PortalTile;
import no.uib.inf101.sem2.model.tiles.SpikeTile;

public class TestLevelLoader {
    @Test
    void testSpawnPos() {
        LevelLoader levelLoader = new LevelLoader();
        Level l = levelLoader.loadLevel(0);

        assertEquals(l.getSpawnPos(), new CellPosition(20, 1));
    }

    @Test
    void testRandomBlocks() {
        LevelLoader levelLoader = new LevelLoader();
        Level l = levelLoader.loadLevel(2);

        assertInstanceOf(SpikeTile.class, l.get(new CellPosition(9, 0)));
        assertInstanceOf(PortalTile.class, l.get(new CellPosition(0, 13)));
    }
}
