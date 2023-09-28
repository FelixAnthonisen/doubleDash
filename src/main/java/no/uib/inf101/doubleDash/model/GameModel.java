package no.uib.inf101.doubleDash.model;

import java.util.List;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import no.uib.inf101.doubleDash.Main;
import no.uib.inf101.doubleDash.controller.ControllableGameModel;
import no.uib.inf101.doubleDash.grid.CellPosition;
import no.uib.inf101.doubleDash.grid.GridCell;
import no.uib.inf101.doubleDash.grid.GridDimension;
import no.uib.inf101.doubleDash.model.direction.ClimbDirection;
import no.uib.inf101.doubleDash.model.direction.DashDirection;
import no.uib.inf101.doubleDash.model.level.Level;
import no.uib.inf101.doubleDash.model.level.LevelLoader;
import no.uib.inf101.doubleDash.model.tiles.PortalTile;
import no.uib.inf101.doubleDash.model.tiles.PowerupTile;
import no.uib.inf101.doubleDash.model.tiles.SpikeTile;
import no.uib.inf101.doubleDash.model.tiles.Tile;
import no.uib.inf101.doubleDash.view.ViewableGameModel;
import no.uib.inf101.doubleDash.wave.PlayWave;
import no.uib.inf101.util.Pair;
import no.uib.inf101.util.linearAlgebra.Vector;

public class GameModel implements ControllableGameModel, ViewableGameModel {
    public static final int LEVEL_ROWS = 24, LEVEL_COLS = 18;
    static final int TILE_SIZE = Main.WIDTH / LEVEL_COLS;
    private static final double TERMINAL_VEL = 4, GRAVITY = 0.1;
    private int levelNum = 0, deathCount = 0, idleScreenTickCounter = 0;
    private LevelLoader levelLoader = new LevelLoader();
    private Level l = levelLoader.loadLevel(levelNum);
    private Player p = new Player(l.getSpawnPos());
    private Camera camera = new Camera();
    private Queue<Pair<Integer, CellPosition>> powerupBuffer = new LinkedList<>();
    private PlayWave hurtSound = new PlayWave("hurt.wav"),
            powerupSound = new PlayWave("powerup.wav"),
            jumpSound = new PlayWave("jump.wav"),
            dashSound = new PlayWave("dash.wav"),
            gameSong = new PlayWave("doubleDashOST.wav");
    private GameState gameState = GameState.GAME_NOT_STARTED;
    private Random random = new Random(123456);

    public GameModel() {
        gameSong.play(true, 31);
    }

    @Override
    public void restartGame() {
        gameSong.play(true, 31);
        gameState = GameState.GAME_NOT_STARTED;
        levelNum = 0;
        deathCount = 0;
        l = levelLoader.loadLevel(levelNum);
        clearPowerupBuffer();
        respawnPlayer();
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public Rectangle2D getPlayerBounds() {
        return new Rectangle2D.Double(p.getX(), p.getY(), p.getWidth(), p.getHeight());
    }

    @Override
    public BufferedImage getPlayerSprite() {
        return p.getSprite();
    }

    @Override
    public GridDimension getLevelDimension() {
        return l;
    }

    @Override
    public Iterable<GridCell<Tile>> getLevelTiles() {
        return l;
    }

    @Override
    public Vector getEye() {
        return camera.getEye();
    }

    @Override
    public int getDeathCount() {
        return deathCount;
    }

    @Override
    public void moveHorisontal(int dir) {
        p.setXVel(dir * Player.MOVE_SPEED);
    }

    @Override
    public void moveVertical(int dir) {
        if (p.getClimbDirection() == null) {
            return;
        }
        p.setYVel(dir * Player.MOVE_SPEED);
    }

    @Override
    public void jumpPlayer() {
        if (p.getClimbDirection() == ClimbDirection.EAST) {
            p.setDashCounter(32);
            p.setDashDirection(DashDirection.NORTH_WEST);
            jumpSound.play(false);
            return;
        } else if (p.getClimbDirection() == ClimbDirection.WEST) {
            p.setDashCounter(32);
            p.setDashDirection(DashDirection.NORTH_EAST);
            jumpSound.play(false);
            return;
        } else if (!p.isFalling() || p.getCoyoteTime() > 0) {
            p.setYVel(-5);
            p.setCoyoteTime(0);
            jumpSound.play(false);
        } else {
            p.setJumpBuffer(20);
        }
        p.setIsFalling(true);
        ;
    }

    @Override
    public void dashPlayer(DashDirection dashDirection) {
        if (p.getDashCounter() != 0 || !p.canDash() || !p.isFalling() || dashDirection == null) {
            return;
        }
        p.setDashDirection(dashDirection);
        p.setDashCounter(48);
        p.setCanDash(false);
        camera.shake();
        dashSound.play(false);
    }

    @Override
    public int timeBetweenGameTicks() {
        return 1000 / 240;
    }

    @Override
    public void clockTick() {
        if (gameState == GameState.GAME_NOT_STARTED) {
            idleScreenTick();
        }
        if (p.getDashCounter() != 0) {
            p.setDashCounter(p.getDashCounter() - 1);
            if (p.getDashCounter() == 0) {
                p.setYVel(0);
            }
        }
        if (p.isFalling()) {
            gravity();

        } else if (p.getJumpBuffer() > 0) {
            p.setJumpBuffer(0);
            p.setYVel(-5);
            jumpSound.play(false);
        }
        p.setJumpBuffer(Math.max(0, p.getJumpBuffer() - 1));
        p.setCoyoteTime(Math.max(0, p.getCoyoteTime() - 1));
        handleCollisions();
        updatePlayer();
        handlePowerupBuffer();
        if (!p.isFalling() && p.getClimbDirection() == null) {
            p.setCanDash(true);
        }
        camera.tick();
    }

    @Override
    public void respawnPlayer() {
        p = new Player(l.getSpawnPos());
    }

    private void updatePlayer() {
        if (p.getDashCounter() == 0) {
            p.moveX(p.getXVel());
            p.moveY(p.getYVel());
            return;
        }
        p.moveX(p.getDashDirection().getXVel(p.getDashCounter()));
        p.moveY(p.getDashDirection().getYVel(p.getDashCounter()));
    }

    private void gravity() {
        p.setYVel(Math.min(p.getYVel() + GRAVITY, TERMINAL_VEL));
    }

    private boolean legalMove(int left, int right, int top, int bot) {
        if (bot >= LEVEL_ROWS) {
            killPlayer();
            return true;
        }
        Queue<Tile> corners = new LinkedList<>(
                Arrays.asList(l.get(new CellPosition(top, left)), l.get(new CellPosition(top, right)),
                        l.get(new CellPosition(bot, left)), l.get(new CellPosition(bot, right))));
        for (int i = 0; i < 4; ++i) {
            Tile t = corners.poll();
            if (t != null && !t.isPassable()) {
                corners.add(t);
            }
        }
        if (corners.isEmpty()) {
            return true;
        }
        while (!corners.isEmpty()) {
            Tile t = corners.poll();
            if (t.conflictsDamage()) {
                killPlayer();
                return false;
            }
        }
        return false;
    }

    private boolean handleSpecialBlockTouch(int left, int right, int top, int bot) {
        List<GridCell<Tile>> corners = new ArrayList<>();
        corners.add(new GridCell<>(new CellPosition(top, left), l.get(new CellPosition(top, left))));
        corners.add(new GridCell<>(new CellPosition(top, right), l.get(new CellPosition(top, right))));
        corners.add(new GridCell<>(new CellPosition(bot, left), l.get(new CellPosition(bot, left))));
        corners.add(new GridCell<>(new CellPosition(bot, right), l.get(new CellPosition(bot, right))));
        for (int i = 0; i < 4; ++i) {
            Tile t = corners.get(i).value();
            if (t == null) {
                continue;
            } else if (t instanceof PortalTile) {
                nextLevel();
                return true;
            } else if (t instanceof PowerupTile) {
                CellPosition pos = corners.get(i).pos();
                p.setCanDash(true);
                l.set(pos, null);
                powerupBuffer.add(new Pair<Integer, CellPosition>(500, pos));
                powerupSound.play(false);
                return true;
            }
        }
        return false;
    }

    private void handleCollisions() {
        double x = p.getX(), y = p.getY(), w = p.getWidth(), h = p.getHeight();
        double xVel = p.getDashCounter() == 0 ? p.getXVel() : p.getDashDirection().getXVel(p.getDashCounter()),
                yVel = p.getDashCounter() == 0 ? p.getYVel() : p.getDashDirection().getYVel(p.getDashCounter());
        // row / col index of each side of the player on the grid
        int left = (int) (x + xVel) / TILE_SIZE, right = (int) (x + w + xVel - 1) / TILE_SIZE,
                top = (int) y / TILE_SIZE,
                bot = (int) (y + h - 1) / TILE_SIZE;

        if ((double) x + xVel - 1 / TILE_SIZE < 0 || right >= LEVEL_COLS) {
            p.setXVel(0);
            p.setDashCounter(0);
        } else if (!legalMove(left, right, top, bot)) {
            if (handleSpecialBlockTouch(left, right, top, bot)) {
                return;
            }
            if (p.getClimbDirection() == null) {
                p.setClimbDirection(p.getXVel() > 0 ? ClimbDirection.EAST : ClimbDirection.WEST);
                p.setYVel(0);
                p.setIsFalling(false);
            }
            p.setXVel(0);
            p.setDashCounter(0);
        } else {
            p.setClimbDirection(null);
        }

        // start coyote time if player starts falling
        left = (int) (x) / TILE_SIZE;
        right = (int) (x + w - 1) / TILE_SIZE;
        bot = (int) (y + h + p.getYVel()) / TILE_SIZE;

        if (bot < LEVEL_ROWS && l.get(new CellPosition(bot, left)) == null && l.get(new CellPosition(bot,
                right)) == null) {
            if (p.getClimbDirection() == null) {
                if (!p.isFalling()) {
                    p.setCoyoteTime(20);
                }
            }
        }

        top = (int) (y + yVel) / TILE_SIZE;
        bot = (int) (y + h + yVel - 1) / TILE_SIZE;

        // vertical
        if (!legalMove(left, right, top, bot)) {
            if (handleSpecialBlockTouch(left, right, top, bot)) {
                return;
            }
            p.setYVel(0);
            p.setIsFalling(false);
            p.setDashCounter(0);
        }

        bot = (int) (y + h + p.getYVel()) / TILE_SIZE;
        if (bot < LEVEL_ROWS && l.get(new CellPosition(bot, left)) == null
                && l.get(new CellPosition(bot, right)) == null) {
            if (p.getClimbDirection() == null) {
                p.setIsFalling(true);
            }
        } else if (bot >= LEVEL_ROWS || l.get(new CellPosition(bot, left)) instanceof SpikeTile
                || l.get(new CellPosition(bot, right)) instanceof SpikeTile) {
            killPlayer();
        }
    }

    private void handlePowerupBuffer() {
        for (int i = 0; i < powerupBuffer.size(); ++i) {
            Pair<Integer, CellPosition> p = powerupBuffer.poll();
            if (p.first() - 1 == 0) {
                l.set(p.second(), new PowerupTile());
                continue;
            }
            powerupBuffer.add(new Pair<Integer, CellPosition>(p.first() - 1, p.second()));
        }
    }

    private void clearPowerupBuffer() {
        while (!powerupBuffer.isEmpty()) {
            l.set(powerupBuffer.poll().second(), new PowerupTile());
        }
    }

    private void nextLevel() {
        if (++levelNum == 8) {

            gameState = GameState.GAME_WON;
            return;
        }
        clearPowerupBuffer();
        l = levelLoader.loadLevel(levelNum);
        respawnPlayer();
    }

    private void killPlayer() {
        hurtSound.play(false);
        camera.shake();
        clearPowerupBuffer();
        respawnPlayer();
        ++deathCount;
    }

    private void idleScreenTick() {
        ++idleScreenTickCounter;
        idleScreenTickCounter %= 240;
        if (idleScreenTickCounter == 0) {
            int[] dirs = { -1, 0, 0, 1 };
            moveHorisontal(dirs[random.nextInt(3)]);
        }
        if (p.getX() < 5) {
            moveHorisontal(1);
        } else if (p.getX() > 670) {
            moveHorisontal(-1);
        }
    }

}
