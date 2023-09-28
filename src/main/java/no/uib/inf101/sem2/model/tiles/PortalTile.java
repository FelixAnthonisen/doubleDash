package no.uib.inf101.sem2.model.tiles;

public class PortalTile extends Tile {

    /**
     * Call to create a new portal tile. Calls the default constructor of
     * {@link Tile} with offset to match the portal sprite in the tilemap
     */
    public PortalTile() {
        super(15, 1);
    }

}
