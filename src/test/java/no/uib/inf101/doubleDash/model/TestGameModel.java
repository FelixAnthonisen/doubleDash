package no.uib.inf101.doubleDash.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.geom.Rectangle2D;

import org.junit.jupiter.api.Test;

import no.uib.inf101.doubleDash.model.direction.DashDirection;

public class TestGameModel {

    @Test
    void testGetDeathCount() {
        GameModel model = new GameModel();

        assertEquals(model.getDeathCount(), 0);
    }

    @Test
    void testTimeBetweenGameTicks() {
        GameModel model = new GameModel();

        assertEquals(model.timeBetweenGameTicks(), 1000 / 240);
    }

    @Test
    void testJumpPlayer() {
        GameModel model = new GameModel();

        // make sure the player is grounded
        for (int i = 0; i < 100; ++i) {
            model.clockTick();
        }
        double yBefore = model.getPlayerBounds().getY();
        model.jumpPlayer();
        model.clockTick();

        // check if player has moved up
        assertTrue(yBefore > model.getPlayerBounds().getY());
    }

    @Test
    void testDashPlayer() {
        GameModel model = new GameModel();
        model.clockTick();
        double yBefore = model.getPlayerBounds().getY();
        double xBefore = model.getPlayerBounds().getX();
        model.dashPlayer(DashDirection.EAST);
        model.clockTick();

        assertTrue(yBefore - model.getPlayerBounds().getY() < 0.001);
        assertTrue(xBefore < model.getPlayerBounds().getX());
    }

    @Test
    void testMoveHorisontal() {
        GameModel model = new GameModel();
        model.moveHorisontal(1);
        Rectangle2D boundsBefore = model.getPlayerBounds(), boundsAfter;
        for (int i = 0; i < 2; ++i) {
            model.clockTick();
        }
        boundsAfter = model.getPlayerBounds();
        assertTrue(boundsBefore.getX() - boundsAfter.getX() + 2 <= 0.001);

    }

    @Test
    void testMoveVertical() {
        GameModel model = new GameModel();
        model.moveHorisontal(1);
        // to get the player close to a wall
        for (int i = 0; i < 2000; ++i) {
            model.clockTick();
        }
        model.moveVertical(-1);
        Rectangle2D boundsBefore = model.getPlayerBounds(), boundsAfter;
        for (int i = 0; i < 2; ++i) {
            model.clockTick();
        }
        boundsAfter = model.getPlayerBounds();
        assertTrue(boundsBefore.getY() - boundsAfter.getY() - 2 <= 0.001);
    }

    @Test
    void testSetGameState() {
        GameModel model = new GameModel();
        assertEquals(model.getGameState(), GameState.GAME_NOT_STARTED);
        model.setGameState(GameState.GAME_STARTED);
        assertEquals(model.getGameState(), GameState.GAME_STARTED);
    }

    @Test
    void testRestartGame() {
        GameModel model = new GameModel();
        model.setGameState(GameState.GAME_STARTED);
        model.moveHorisontal(1);
        // some random input
        for (int i = 0; i < 100; ++i) {
            model.clockTick();
        }
        model.restartGame();

        assertEquals(model.getPlayerBounds().getX(), 35);
        assertEquals(model.getGameState(), GameState.GAME_NOT_STARTED);
    }

    @Test
    void testRespawnPlayer() {
        GameModel model = new GameModel();
        model.setGameState(GameState.GAME_STARTED);
        model.moveHorisontal(1);
        // some random input
        for (int i = 0; i < 100; ++i) {
            model.clockTick();
        }
        model.respawnPlayer();
        assertEquals(model.getPlayerBounds().getX(), 35);
    }
}
