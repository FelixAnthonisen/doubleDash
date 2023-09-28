package no.uib.inf101.sem2.model.tiles;

public class SpikeTile extends Tile {

    /**
     * Call to create a new spike tile. Calls the constructor of
     * {@link Tile} with offset to match the spike sprite in the tilemap,
     * conflictsDamage as true and isPassable as false
     */
    public SpikeTile() {
        super(0, 14, true, false);
    }

}
