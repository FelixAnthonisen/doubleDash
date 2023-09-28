package no.uib.inf101.sem2.model.tiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestTile {
    @Test
    void spikeTileConflictsDamage() {
        SpikeTile tile = new SpikeTile();
        assertTrue(tile.conflictsDamage());
    }

    @Test
    void signTileIsPassable() {
        SignTile tile = new SignTile();
        assertTrue(tile.isPassable());
    }

    @Test
    void brickTileIsSolid() {
        BrickTile tile = new BrickTile();
        assertTrue(!tile.isPassable());

    }

    @Test
    void dirtTileDontConflictDamage() {
        DirtTile tile = new DirtTile();
        assertTrue(!tile.conflictsDamage());

    }
}
