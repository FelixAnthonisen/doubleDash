package no.uib.inf101.sem2.model.tiles;

import java.awt.image.BufferedImage;

import no.uib.inf101.sem2.view.ImageParser;

public class Tile {

    private final boolean conflictsDamage, isPassable;
    private final BufferedImage tileImage;

    /**
     * Call to create a new Tile object. Variables conflictsDamage and isPassable
     * will be set to false by default
     * 
     * @param offsetX how many cols the image should be offsetted in the tilemap
     * @param offsetY how many rows the image should be offsetted in the tilemap
     */
    Tile(int offsetX, int offsetY) {
        conflictsDamage = false;
        this.isPassable = false;
        this.tileImage = ImageParser.parseTiles(offsetX, offsetY);
    }

    /**
     * Call to create a new Tile object
     * 
     * @param offsetX         how many cols the image should be offsetted in the
     *                        tilemap
     * @param offsetY         how many rows the image should be offsetted in the
     *                        tilemap
     * @param conflictsDamage true if the tile conflicts damage, false if not
     * @param isPassable      true if the tile is passable, false if not
     */
    Tile(int offsetX, int offsetY, boolean conflictsDamage, boolean isPassable) {
        this.conflictsDamage = conflictsDamage;
        this.isPassable = isPassable;
        this.tileImage = ImageParser.parseTiles(offsetX, offsetY);
    }

    /**
     * 
     * @return the the sprite of the tile
     */
    public BufferedImage getTileImage() {
        return tileImage;
    }

    /**
     * Call to create a new instance of the tile with all attributers remaining the
     * same except a new sprite of the same type
     * 
     * @param patternCounter how much the sprite should be offsetted on the tilemap
     * @return the new Tile instance with new sprite
     */
    public Tile patternize(int patternCounter) {
        return this;
    }

    /**
     * 
     * @return true if a tile conflicts damage, false if not
     */
    public boolean conflictsDamage() {
        return conflictsDamage;
    }

    /**
     * 
     * @return true if a tile is passable, false if not
     */
    public boolean isPassable() {
        return isPassable;
    }
}
