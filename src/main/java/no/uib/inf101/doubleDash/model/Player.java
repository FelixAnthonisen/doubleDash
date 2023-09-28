package no.uib.inf101.doubleDash.model;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import no.uib.inf101.doubleDash.grid.CellPosition;
import no.uib.inf101.doubleDash.model.direction.ClimbDirection;
import no.uib.inf101.doubleDash.model.direction.DashDirection;
import no.uib.inf101.doubleDash.view.ImageParser;

class Player {
    static final int MOVE_SPEED = 1;
    private double x, y, width = 14, height = 30;
    private double xVel = 0, yVel = 0;
    private Map<String, Queue<BufferedImage>> animatedSprites = new HashMap<>();
    private boolean isFalling = false, canDash = true;
    private ClimbDirection climbDirection;
    private DashDirection dashDirection;
    private int dashCounter = 0, jumpBuffer = 0, coyoteTime = 0;

    /**
     * Call to create a new Player with width of 14 and height of 30. xVel, yVel,
     * dashCounter, jumpBuffer and coyoteTime is initially set to 0
     * 
     * @param spawnPos the pixel position the player should spawn at
     */
    public Player(CellPosition spawnPos) {
        x = spawnPos.col() * GameModel.TILE_SIZE;
        y = spawnPos.row() * GameModel.TILE_SIZE;
        initAnimatedSprites();
    }

    /**
     * 
     * @return the x pixel position of the player
     */
    double getX() {
        return x;
    }

    /**
     * 
     * @return the y pixel position of the player
     */
    double getY() {
        return y;
    }

    /**
     * 
     * @param x the x pixel position the player should move to
     */
    void moveX(double x) {
        this.x += x;
    }

    /**
     * 
     * @param y the y pixel position the player should move to
     */
    void moveY(double y) {
        this.y += y;
    }

    /**
     * 
     * @return the pixel width of the player hitbox
     */
    double getWidth() {
        return width;
    }

    /**
     * 
     * @return the pixel height of the player hitbox
     */
    double getHeight() {
        return height;
    }

    /**
     * 
     * @return the x velocity of the player in pixels
     */
    double getXVel() {
        return xVel;
    }

    /**
     * 
     * @param xVel the value xVel should be set to
     */
    void setXVel(double xVel) {
        this.xVel = xVel;
    }

    /**
     * 
     * @return the y velocity of the player in pixels
     */
    double getYVel() {
        return yVel;
    }

    /**
     * 
     * @param yVel the value yVel should be set to
     */
    void setYVel(double yVel) {
        this.yVel = yVel;
    }

    /**
     * 
     * @return true if the player is falling, false if not
     */
    boolean isFalling() {
        return isFalling;
    }

    /**
     * 
     * @return true if the player can dash, false if not
     */
    boolean canDash() {
        return canDash;
    }

    /**
     * 
     * @param isFalling true if the player should be able to fall, false if not
     */
    void setIsFalling(boolean isFalling) {
        this.isFalling = isFalling;
    }

    /**
     * 
     * @param canDash true if the player should be able to dash, false if not
     */
    void setCanDash(boolean canDash) {
        this.canDash = canDash;
    }

    /**
     * 
     * @return the direction the player is dashing in. Returns null if the player
     *         isn't dashing in any direction
     */
    DashDirection getDashDirection() {
        return dashDirection;
    }

    /**
     * 
     * @param dashDirection the direction the player should dash in. Should be null
     *                      if the player shouldn't dashing in any direction
     */
    void setDashDirection(DashDirection dashDirection) {
        this.dashDirection = dashDirection;
    }

    /**
     * 
     * @return the direction the player is climbing towards. Returns null if the
     *         player isn't climbing towards any direction
     */
    ClimbDirection getClimbDirection() {
        return climbDirection;
    }

    /**
     * 
     * @param climbDirection the direction the player should climb towards. Should
     *                       be null if the player shouldn't climb towards any
     *                       direction
     */
    void setClimbDirection(ClimbDirection climbDirection) {
        this.climbDirection = climbDirection;
    }

    /**
     * 
     * @return how many ticks are left of the dash counter
     */
    int getDashCounter() {
        return dashCounter;
    }

    /**
     * 
     * @param dashCounter the value the dashCounter should be set to
     */
    void setDashCounter(int dashCounter) {
        this.dashCounter = dashCounter;
    }

    /**
     * 
     * @return how many ticks are left of the jump buffer
     */
    int getJumpBuffer() {
        return jumpBuffer;
    }

    /**
     * 
     * @param jumpBuffer the value the jumpBuffer should be set to
     */
    void setJumpBuffer(int jumpBuffer) {
        this.jumpBuffer = jumpBuffer;
    }

    /**
     * 
     * @return how may ticks are left of the coyoteTime
     */
    int getCoyoteTime() {
        return coyoteTime;
    }

    /**
     * 
     * @param coyoteTime the value the coyoteTime should be set to
     */
    void setCoyoteTime(int coyoteTime) {
        this.coyoteTime = coyoteTime;
    }

    /**
     * call to get the current sprite of the player. Which sprite is selected
     * depends on
     * wether the player is falling, which direction it's facing and if it's
     * climbing. The sprites are animated by cycling through a set of
     * sprites for each action
     * 
     * @return the sprite
     */
    public BufferedImage getSprite() {
        String action;
        if (climbDirection == ClimbDirection.EAST) {
            if (yVel != 0) {
                action = "climbRight";
            } else {
                action = "idleClimbRight";
            }
        } else if (climbDirection == ClimbDirection.WEST) {
            if (yVel != 0) {
                action = "climbLeft";
            } else {
                action = "idleClimbLeft";
            }
        } else if (xVel > 0) {
            if (yVel < 0) {
                action = "jumpRight";
            } else if (yVel > 0) {
                action = "fallRight";
            } else {
                action = "walkingRight";
            }

        } else if (xVel < 0) {
            if (yVel < 0) {
                action = "jumpLeft";
            } else if (yVel > 0) {
                action = "fallLeft";
            } else {
                action = "walkingLeft";
            }
        } else {
            if (yVel < 0) {
                action = "jumpFront";
            } else if (yVel > 0) {
                action = "fallFront";
            } else {
                action = "idle";
            }
        }
        BufferedImage sprite = animatedSprites.get(action).poll();
        animatedSprites.get(action).add(sprite);
        return sprite;
    }

    private void initAnimatedSprites() {
        animatedSprites.put("idle", new LinkedList<>(Arrays.asList(ImageParser.parseSprites(1, 0))));
        animatedSprites.put("jumpRight", new LinkedList<>(Arrays.asList(ImageParser.parseSprites(3, 1))));
        animatedSprites.put("jumpLeft", new LinkedList<>(Arrays.asList(ImageParser.parseSprites(3, 2))));
        animatedSprites.put("jumpFront", new LinkedList<>(Arrays.asList(ImageParser.parseSprites(2, 0))));
        animatedSprites.put("fallRight", new LinkedList<>());
        animatedSprites.put("fallLeft", new LinkedList<>());
        animatedSprites.put("fallFront", new LinkedList<>());
        animatedSprites.put("walkingRight", new LinkedList<>());
        animatedSprites.put("walkingLeft", new LinkedList<>());
        animatedSprites.put("climbRight", new LinkedList<>());
        animatedSprites.put("climbLeft", new LinkedList<>());
        animatedSprites.put("idleClimbRight", new LinkedList<>(Arrays.asList(ImageParser.parseSprites(4, 3))));
        animatedSprites.put("idleClimbLeft", new LinkedList<>(Arrays.asList(ImageParser.parseSprites(1, 3))));
        for (int i = 0; i < 2; ++i) {
            animatedSprites.get("fallRight").add(ImageParser.parseSprites(4 + i, 1));
            animatedSprites.get("fallLeft").add(ImageParser.parseSprites(4 + i, 2));
        }
        for (int i = 0; i < 3; ++i) {
            animatedSprites.get("walkingRight").add(ImageParser.parseSprites(i, 1));
            animatedSprites.get("walkingLeft").add(ImageParser.parseSprites(i, 2));
            animatedSprites.get("fallFront").add(ImageParser.parseSprites(3 + i, 0));
            animatedSprites.get("climbRight").add(ImageParser.parseSprites(3 + i, 3));
            animatedSprites.get("climbLeft").add(ImageParser.parseSprites(i, 3));
        }
    }
}
