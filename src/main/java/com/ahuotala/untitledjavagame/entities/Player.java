package com.ahuotala.untitledjavagame.entities;

import com.ahuotala.untitledjavagame.Game;
import com.ahuotala.untitledjavagame.graphics.Renderer;
import com.ahuotala.untitledjavagame.Tickable;
import com.ahuotala.untitledjavagame.graphics.animation.Animation;
import com.ahuotala.untitledjavagame.map.GameMap;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aleksi Huotala
 */
public class Player extends Entity implements Tickable {

    /**
     *
     */
    public static int realX;

    /**
     *
     */
    public static int realY;

    /**
     *
     */
    public static int maxHealth = 120;

    /**
     *
     */
    public int lastX;

    /**
     *
     */
    public int lastY;

    private double xp;


    //Base stepping speed
    private int step = 1;

    private int health = 120;

    private Direction direction = Direction.DOWN;

    private boolean walking = false;
    private boolean swimming = false;

    private final int cY;
    private final int cX;

    /**
     *
     */
    public int offsetX = Game.CENTERX;

    /**
     *
     */
    public int offsetY = Game.CENTERY;

    /**
     *
     */
    public String currentTile = "";

    private int playerTicks = 0;

    //Animations
    private Animation playerWalkingUp;
    private Animation playerWalkingDown;
    private Animation playerWalkingLeft;
    private Animation playerWalkingRight;
    private Animation playerSwimmingUp;
    private Animation playerSwimmingDown;
    private Animation playerSwimmingLeft;
    private Animation playerSwimmingRight;

    //Bounds
    private final Rectangle bounds;
    private final int width = 16;
    private final int height = 16;
    
    
    private static final Logger LOG = Logger.getLogger(Player.class.getName());

    /**
     *
     */
    public Player() {
        //XP
        xp = 0.0;
        //Center Y
        this.cY = Game.CENTERY;
        //Center X
        this.cX = Game.CENTERX;
        //Real x & y
        realX = cX;
        realY = cY;
        bounds = new Rectangle(realX, realY, width, height);
    }

    /**
     * Initialize animations
     */
    public void initAnimations() {
        playerWalkingUp = new Animation("PlayerWalkingUp", 5);
        playerWalkingDown = new Animation("PlayerWalkingDown", 5);
        playerWalkingLeft = new Animation("PlayerWalkingLeft", 5);
        playerWalkingRight = new Animation("PlayerWalkingRight", 5);
        playerSwimmingUp = new Animation("PlayerSwimmingUp", 10);
        playerSwimmingDown = new Animation("PlayerSwimmingDown", 10);
        playerSwimmingLeft = new Animation("PlayerSwimmingLeft", 10);
        playerSwimmingRight = new Animation("PlayerSwimmingRight", 10);
        Game.ANIMATIONTICKER.register(playerWalkingUp);
        Game.ANIMATIONTICKER.register(playerWalkingDown);
        Game.ANIMATIONTICKER.register(playerWalkingLeft);
        Game.ANIMATIONTICKER.register(playerWalkingRight);
        Game.ANIMATIONTICKER.register(playerSwimmingUp);
        Game.ANIMATIONTICKER.register(playerSwimmingDown);
        Game.ANIMATIONTICKER.register(playerSwimmingLeft);
        Game.ANIMATIONTICKER.register(playerSwimmingRight);
    }

    /**
     *
     * @param x
     */
    @Override
    public void setX(int x) {
        super.setX(x);
        offsetX = cX - x;
    }

    /**
     *
     * @param y
     */
    @Override
    public void setY(int y) {
        super.setY(y);
        offsetY = cY - y;
    }

    /**
     *
     * @return
     */
    public double getXp() {
        return xp;
    }

    /**
     *
     * @param xp
     */
    public void setXp(double xp) {
        this.xp = xp;
    }

    /**
     *
     * @return
     */
    public int getRealX() {
        return realX;
    }

    /**
     *
     * @return
     */
    public int getRealY() {
        return realY;
    }

    /**
     *
     * @return
     */
    public int getOffsetX() {
        return offsetX;
    }

    /**
     *
     * @return
     */
    public int getOffsetY() {
        return offsetY;
    }

    /**
     * Damage the player
     * @param health Health
     */
    public void damagePlayer(int health) {
        if (this.health - health >= 0) {
            this.health -= health;
        }
    }

    /**
     * Increase the health of the player
     * @param health Health
     */
    public void increaseHealth(int health) {
        setHealth(getHealth() + health);
    }

    /**
     * Returns the health of the player
     * @return Health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Go up
     */
    public void goUp() {
        super.incrementY(step);
        offsetY += step;
    }

    /**
     * Go down
     */
    public void goDown() {
        super.incrementY(-step);
        offsetY -= step;
    }

    /**
     * Go left
     */
    public void goLeft() {
        super.incrementX(step);
        offsetX += step;
    }

    /**
     * Go right
     */
    public void goRight() {
        super.incrementX(-step);
        offsetX -= step;
    }

    /**
     *
     * @param health
     */
    public void setHealth(int health) {
        if (health > maxHealth) {
            this.health = maxHealth;
        } else {
            this.health = health;
        }
    }

    /**
     *
     * @return
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     *
     * @param tile
     */
    public void setCurrentTile(String tile) {
        currentTile = tile;
    }

    /**
     *
     * @param tile
     * @return
     */
    public String getCurrentTile(String tile) {
        return currentTile;
    }

    /**
     *
     * @param step
     */
    public void setStep(int step) {
        this.step = step;
    }

    /**
     *
     * @param direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     *
     * @return
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     *
     * @return
     */
    public boolean getWalkingState() {
        return walking;
    }

    /**
     *
     * @param walking
     */
    public void setWalkingState(boolean walking) {
        this.walking = walking;
    }

    /**
     *
     * @param swimming
     */
    public void setSwimmingState(boolean swimming) {
        this.swimming = swimming;
    }

    /**
     *
     * @return
     */
    public boolean isWalking() {
        return walking;
    }

    /**
     *
     * @return
     */
    public boolean isSwimming() {
        return swimming;
    }

    @Override
    public String toString() {
        return this.getX() + "," + this.getY();
    }

    /**
     *
     * @param x
     */
    public void setLastX(int x) {
        lastX = x;
    }

    /**
     *
     * @param y
     */
    public void setLastY(int y) {
        lastY = y;
    }

    /**
     *
     * @return
     */
    public int getLastX() {
        return lastX;
    }

    /**
     *
     * @return
     */
    public int getLastY() {
        return lastY;
    }

    /**
     *
     * @param r
     * @param g
     * @param map
     */
    public void render(Renderer r, Graphics g, GameMap map) {
        int playerX = super.getX();
        int playerY = super.getY();
        //Debug
        if (Game.DEBUG) {
            g.setColor(Color.yellow);
            g.fill3DRect(playerX + offsetX, playerY + offsetY, width, height, true);
        }
        //Player shadow
//        Game.spriteSheet.paint(g, "player_shadow", realX - 8, realY - 13);

        //Player walking animation
        switch (this.getDirection()) {
            case DOWN:
                if (this.isWalking()) {
                    if (this.isSwimming()) {
                        playerSwimmingDown.nextFrame(r, realX, realY);
                    } else {
                        playerWalkingDown.nextFrame(r, realX, realY);
                    }
                } else if (this.isSwimming()) {
                    Game.spriteSheet.paint(r, "player_swimming_down", realX, realY);
                } else {
                    Game.spriteSheet.paint(r, "player_down", realX, realY);
                }
                break;
            case UP:
                if (this.isWalking()) {
                    if (this.isSwimming()) {
                        playerSwimmingUp.nextFrame(r, realX, realY);
                    } else {
                        playerWalkingUp.nextFrame(r, realX, realY);
                    }
                } else if (this.isSwimming()) {
                    Game.spriteSheet.paint(r, "player_swimming_up", realX, realY);
                } else {
                    Game.spriteSheet.paint(r, "player_up", realX, realY);
                }
                break;
            case LEFT:
                if (this.isWalking()) {
                    if (this.isSwimming()) {
                        playerSwimmingLeft.nextFrame(r, realX, realY);
                    } else {
                        playerWalkingLeft.nextFrame(r, realX, realY);
                    }
                } else if (this.isSwimming()) {
                    Game.spriteSheet.paint(r, "player_swimming_left", realX, realY);
                } else {
                    Game.spriteSheet.paint(r, "player_left", realX, realY);
                }
                break;
            case RIGHT:
                if (this.isWalking()) {
                    if (this.isSwimming()) {
                        playerSwimmingRight.nextFrame(r, realX, realY);
                    } else {
                        playerWalkingRight.nextFrame(r, realX, realY);
                    }
                } else if (this.isSwimming()) {
                    Game.spriteSheet.paint(r, "player_swimming_right", realX, realY);
                } else {
                    Game.spriteSheet.paint(r, "player_right", realX, realY);
                }
                break;
            default:
                break;
        }

    }

    /**
     *
     */
    @Override
    public void tick() {
        //Slow the PLAYER down if we are walking on a sand
        switch (currentTile) {
            case "sand":
                swimming = false;
                step = 2;
                break;
            case "water_ani":
                swimming = true;
                step = 1;
                break;
            case "lava_ani":
                swimming = true;
                step = 1;
                if (playerTicks % (40 * (int) Math.ceil(Game.tickrate / 60)) == 0 && health > 0) {
                    this.damagePlayer(10);
                    playerTicks = 0;
                }
                playerTicks++;
                break;
            default:
                swimming = false;
                step = 3;
                break;
        }
        bounds.setBounds(x, y, width, height);
    }

    /**
     *
     * @return
     */
    public Rectangle getBounds() {
        return bounds;
    }

}
