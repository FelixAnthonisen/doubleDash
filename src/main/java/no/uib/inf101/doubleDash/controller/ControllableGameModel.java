package no.uib.inf101.doubleDash.controller;

import no.uib.inf101.doubleDash.model.GameState;
import no.uib.inf101.doubleDash.model.direction.DashDirection;

public interface ControllableGameModel {

    /**
     * Call to move the player horisontally in a direction
     * 
     * @param dir -1, 0 or 1 to move the player left, no direction or right
     */
    void moveHorisontal(int dir);

    /**
     * Call to move the player vertically in a direction
     * 
     * @param dir -1, 0 or 1 to move the player up, no direction or down
     */
    void moveVertical(int dir);

    /**
     * Call to make the player jump
     */
    void jumpPlayer();

    /**
     * Call to dash the player in a direction
     * 
     * @param dashDirection the direction the player should dash. Should be null if
     *                      the player should dash in no direction
     */
    void dashPlayer(DashDirection dashDirection);

    /**
     * 
     * @return the time between game ticks in miliseconds
     */
    int timeBetweenGameTicks();

    /**
     * Call to process one game tick
     */
    void clockTick();

    /**
     * Call to respawn the player in the same level
     */
    void respawnPlayer();

    /**
     * 
     * @return the current state of the game
     */
    GameState getGameState();

    /**
     * 
     * @param gameState the state the game should be set to
     */
    void setGameState(GameState gameState);

    /**
     * Call to restart the game
     */
    void restartGame();

}
