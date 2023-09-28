package no.uib.inf101.sem2.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import no.uib.inf101.sem2.model.GameState;
import no.uib.inf101.sem2.model.direction.DashDirection;
import no.uib.inf101.sem2.view.GameView;

public class Controller implements KeyListener {
    private static int TICKS_PER_FRAME = 4;
    private ControllableGameModel model;
    private GameView view;
    private Timer gameTimer;
    private int activeHKey = -1, activeVKey = -1, frameCounter = 0;

    /**
     * Call to instantiate a new Controller object. The controller handles input
     * from the keyboard and has a timer which handles and call ingame clock ticks.
     * 
     * @param model the GameModel object the controller should inact on
     * @param view  the ViewModel object the conttroller should inact on
     */
    public Controller(ControllableGameModel model, GameView view) {
        this.model = model;
        this.view = view;
        this.view.addKeyListener(this);
        gameTimer = new Timer(model.timeBetweenGameTicks(), this::gameClockTick);
        gameTimer.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        GameState gameState = model.getGameState();
        int keyCode = e.getKeyCode();
        if (gameState == GameState.GAME_NOT_STARTED) {
            if (keyCode == KeyEvent.VK_ENTER) {
                model.setGameState(GameState.GAME_STARTED);
                model.respawnPlayer();
            }
            return;
        }
        if (gameState == GameState.GAME_WON) {
            if (keyCode == KeyEvent.VK_ENTER) {
                model.restartGame();
            }
            return;
        }
        switch (keyCode) {
            case KeyEvent.VK_W:
                activeVKey = keyCode;
                break;
            case KeyEvent.VK_A:
                activeHKey = keyCode;
                break;
            case KeyEvent.VK_S:
                activeVKey = keyCode;
                break;
            case KeyEvent.VK_D:
                activeHKey = keyCode;
                break;
            case KeyEvent.VK_SPACE:
                model.jumpPlayer();
                break;
            case KeyEvent.VK_M:
                dash();
                break;
            case KeyEvent.VK_R:
                model.respawnPlayer();
                break;
        }
        view.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == activeHKey) {
            activeHKey = -1;
        }
        if (keyCode == activeVKey) {
            activeVKey = -1;
        }
    }

    private void gameClockTick(ActionEvent e) {
        if (model.getGameState() == GameState.GAME_STARTED) {
            int hDir = 0, vDir = 0;
            switch (activeHKey) {
                case KeyEvent.VK_A:
                    hDir = -1;
                    break;
                case KeyEvent.VK_D:
                    hDir = 1;
                    break;
            }
            switch (activeVKey) {
                case KeyEvent.VK_W:
                    vDir = -1;
                    break;
                case KeyEvent.VK_S:
                    vDir = 1;
                    break;
            }
            model.moveHorisontal(hDir);
            model.moveVertical(vDir);
        }
        if (model.getGameState() != GameState.GAME_WON) {
            model.clockTick();
        }
        ++frameCounter;
        frameCounter %= TICKS_PER_FRAME;
        if (frameCounter == 0) {
            view.repaint();
        }
    }

    private void dash() {
        DashDirection dashDirection;
        switch (activeHKey) {
            case KeyEvent.VK_A:
                switch (activeVKey) {
                    case KeyEvent.VK_W:
                        dashDirection = DashDirection.NORTH_WEST;
                        break;
                    case KeyEvent.VK_S:
                        dashDirection = DashDirection.SOUTH_WEST;
                        break;
                    default:
                        dashDirection = DashDirection.WEST;
                        break;
                }
                break;
            case KeyEvent.VK_D:
                switch (activeVKey) {
                    case KeyEvent.VK_W:
                        dashDirection = DashDirection.NORTH_EAST;
                        break;
                    case KeyEvent.VK_S:
                        dashDirection = DashDirection.SOUTH_EAST;
                        break;
                    default:
                        dashDirection = DashDirection.EAST;
                        break;
                }
                break;

            default:
                switch (activeVKey) {
                    case KeyEvent.VK_W:
                        dashDirection = DashDirection.NORTH;
                        break;
                    case KeyEvent.VK_S:
                        dashDirection = DashDirection.SOUTH;
                        break;
                    default:
                        return;
                }

        }
        model.dashPlayer(dashDirection);
    }
}
