package no.uib.inf101.sem2.view;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import no.uib.inf101.sem2.grid.GridCell;
import no.uib.inf101.sem2.grid.GridDimension;
import no.uib.inf101.sem2.model.GameState;
import no.uib.inf101.sem2.model.tiles.Tile;
import no.uib.inf101.util.linearAlgebra.Vector;

public interface ViewableGameModel {

    /**
     * 
     * @return the current state of the game
     */
    GameState getGameState();

    /**
     * Call to get the bounds of the player as a Rectangle2D object
     * 
     * @return the bounds of the player
     */
    Rectangle2D getPlayerBounds();

    /**
     * 
     * @return the dimensions of the level
     */
    GridDimension getLevelDimension();

    /**
     * 
     * @return All tiles in the level as an iterable
     */
    Iterable<GridCell<Tile>> getLevelTiles();

    /**
     * 
     * @return the current sprite of the player, based on which action the player is
     *         doing
     */
    BufferedImage getPlayerSprite();

    /**
     * 
     * @return the eye vector of the camera
     */
    Vector getEye();

    /**
     * 
     * @return how many times the player has died in the current run
     */
    int getDeathCount();
}
