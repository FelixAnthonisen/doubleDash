package no.uib.inf101.sem2.model.tiles;

public class DirtTile extends Tile {

    /**
     * Call to create a new dirt tile. Calls the default constructor of
     * {@link Tile} with offset to match the dirt sprite in the tilemap
     */
    public DirtTile() {
        super(7, 5);
    }

    @Override
    public Tile patternize(int patternCounter) {
        return new Tile(7 + patternCounter % 3, 5);
    }
}
