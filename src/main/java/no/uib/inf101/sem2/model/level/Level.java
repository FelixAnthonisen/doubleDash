package no.uib.inf101.sem2.model.level;

import no.uib.inf101.sem2.grid.CellPosition;
import no.uib.inf101.sem2.grid.Grid;
import no.uib.inf101.sem2.model.tiles.Tile;

public class Level extends Grid<Tile> {

    private CellPosition spawnPos;

    /**
     * Call to create a new Level objects represented as a grid. Defaultvalue will
     * be set to null
     * 
     * @param rows the number of rows the grid should contain
     * @param cols the number of cols the grid should contain
     */
    Level(int rows, int cols) {
        super(rows, cols, null);
    }

    /**
     * 
     * @return the cell position the player should spawn and respawn at
     */
    public CellPosition getSpawnPos() {
        return spawnPos;
    }

    /**
     * Call to set the position where the player should spawn
     * 
     * @param spawnPos the position the player should spawn at
     */
    void setSpawnPos(CellPosition spawnPos) {
        this.spawnPos = spawnPos;
    }

}
