package no.uib.inf101.sem2.model.tiles;

public class BrickTile extends Tile {

    /**
     * Call to create a new brick tile. Calls the default constructor of
     * {@link Tile} with offset to match the brick sprite in the tilemap
     */
    public BrickTile() {
        super(9, 0);
    }

    @Override
    public Tile patternize(int patternCounter) {
        return new Tile(8 + patternCounter % 2, 3);
    }
}
