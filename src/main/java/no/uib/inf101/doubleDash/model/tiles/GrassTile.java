package no.uib.inf101.doubleDash.model.tiles;

public class GrassTile extends Tile {

    /**
     * Call to create a new grass tile. Calls the default constructor of
     * {@link Tile} with offset to match the grass sprite in the tilemap
     */
    public GrassTile() {
        super(1, 6);
    }

    @Override
    public Tile patternize(int patternCounter) {
        return new Tile(1 + patternCounter % 2, 6);
    }

}
