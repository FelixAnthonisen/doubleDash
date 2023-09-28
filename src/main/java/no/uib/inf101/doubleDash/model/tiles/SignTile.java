package no.uib.inf101.doubleDash.model.tiles;

public class SignTile extends Tile {

    /**
     * Call to create a new sign tile. Calls the constructor of
     * {@link Tile} with offset to match the sign sprite in the tilemap,
     * conflictsdamage as false and passable as true
     */
    public SignTile() {
        super(5, 2, false, true);
    }
}
