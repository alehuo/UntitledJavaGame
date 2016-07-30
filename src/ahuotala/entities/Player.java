/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.entities;

import ahuotala.game.Game;
import static ahuotala.game.Game.DEBUG_PLAYER;
import static ahuotala.game.Game.animationTicker;
import ahuotala.game.Tickable;
import ahuotala.graphics.animation.Animation;
import ahuotala.map.Map;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Aleksi Huotala
 */
public class Player implements Entity, Tickable {

    private int x;
    private int y;

    public static int realX;
    public static int realY;

    //Base stepping speed
    private int step = 1;

    private final String name;
    private int health = 120;
    public static int maxHealth = 120;

    private Direction direction = Direction.DOWN;

    private boolean walking = false;
    private boolean swimming = false;

    private final int cY;
    private final int cX;

    public int offsetX = Game.CENTERX;
    public int offsetY = Game.CENTERY;

    public String currentTile = "";

    private int playerTicks = 0;

    private Direction collisionDirection;

    //Animations
    private final Animation playerWalkingUp;
    private final Animation playerWalkingDown;
    private final Animation playerWalkingLeft;
    private final Animation playerWalkingRight;
    private final Animation playerSwimmingUp;
    private final Animation playerSwimmingDown;
    private final Animation playerSwimmingLeft;
    private final Animation playerSwimmingRight;

    public Player(String name) {
        this.name = name;
        //Center Y
        this.cY = Game.CENTERY;
        //Center X
        this.cX = Game.CENTERX;
        realX = cX;
        realY = cY;
//        radiusX = (int) Math.floor(0.2 * cX);
//        radiusY = (int) Math.floor(0.2 * cY);

        playerWalkingUp = new Animation("PlayerWalkingUp", 10);
        playerWalkingDown = new Animation("PlayerWalkingDown", 10);
        playerWalkingLeft = new Animation("PlayerWalkingLeft", 10);
        playerWalkingRight = new Animation("PlayerWalkingRight", 10);
        playerSwimmingUp = new Animation("PlayerSwimmingUp", 30);
        playerSwimmingDown = new Animation("PlayerSwimmingDown", 30);
        playerSwimmingLeft = new Animation("PlayerSwimmingLeft", 30);
        playerSwimmingRight = new Animation("PlayerSwimmingRight", 30);
        animationTicker.register(playerWalkingUp);
        animationTicker.register(playerWalkingDown);
        animationTicker.register(playerWalkingLeft);
        animationTicker.register(playerWalkingRight);
        animationTicker.register(playerSwimmingUp);
        animationTicker.register(playerSwimmingDown);
        animationTicker.register(playerSwimmingLeft);
        animationTicker.register(playerSwimmingRight);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public int getRealX() {
        return realX;
    }

    public int getRealY() {
        return realY;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void damagePlayer(int health) {
        if (health >= 0) {
            this.health -= health;
        }
    }

    public void increaseHealth(int health) {
        if (this.health + health <= maxHealth) {
            this.health += health;
        } else {
            health = maxHealth;
        }
    }

    public int getHealth() {
        return health;
    }

    public void goUp() {
        y -= step;
        offsetY += step;
    }

    public void goDown() {
        y += step;
        offsetY -= step;
    }

    public void goLeft() {
        x -= step;
        offsetX += step;
    }

    public void goRight() {
        x += step;
        offsetX -= step;
    }

    public void setCollisionDirection(Direction direction) {
        collisionDirection = direction;
    }

    public Direction getCollisionDirection() {
        return collisionDirection;
    }

    @Override
    public void setX(int x) {
        offsetX = cX - x;
        this.x = x;
    }

    @Override
    public void setY(int y) {
        offsetY = cY - y;
        this.y = y;
    }

    public void setHealth(int health) {
        if (health > maxHealth) {
            this.health = maxHealth;
        } else {
            this.health = health;
        }
    }

    public void setCurrentTile(String tile) {
        currentTile = tile;
    }

    public String getCurrentTile(String tile) {
        return currentTile;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean getWalkingState() {
        return walking;
    }

    public void setWalkingState(boolean walking) {
        this.walking = walking;
    }

    public void setSwimmingState(boolean swimming) {
        this.swimming = swimming;
    }

    public boolean isWalking() {
        return walking;
    }

    public boolean isSwimming() {
        return swimming;
    }

    @Override
    public String toString() {
        return this.getX() + "," + this.getY();
    }

    public void render(Graphics g, Map map) {
        //Debug
        if (Game.DEBUG) {
            g.setColor(Color.yellow);
            g.fill3DRect(realX, realY, 16, 16, true);
        }
        //Player shadow
//        Game.spriteSheet.paint(g, "player_shadow", realX - 8, realY - 13);

        //Player walking animation
        switch (this.getDirection()) {
            case DOWN:
                if (this.isWalking()) {
                    if (this.isSwimming()) {
                        playerSwimmingDown.nextFrame(g, realX, realY);
                    } else {
                        playerWalkingDown.nextFrame(g, realX, realY);
                    }
                } else if (this.isSwimming()) {
                    Game.spriteSheet.paint(g, "player_swimming_down", realX, realY);
                } else {
                    Game.spriteSheet.paint(g, "player_down", realX, realY);
                }
                break;
            case UP:
                if (this.isWalking()) {
                    if (this.isSwimming()) {
                        playerSwimmingUp.nextFrame(g, realX, realY);
                    } else {
                        playerWalkingUp.nextFrame(g, realX, realY);
                    }
                } else if (this.isSwimming()) {
                    Game.spriteSheet.paint(g, "player_swimming_up", realX, realY);
                } else {
                    Game.spriteSheet.paint(g, "player_up", realX, realY);
                }
                break;
            case LEFT:
                if (this.isWalking()) {
                    if (this.isSwimming()) {
                        playerSwimmingLeft.nextFrame(g, realX, realY);
                    } else {
                        playerWalkingLeft.nextFrame(g, realX, realY);
                    }
                } else if (this.isSwimming()) {
                    Game.spriteSheet.paint(g, "player_swimming_left", realX, realY);
                } else {
                    Game.spriteSheet.paint(g, "player_left", realX, realY);
                }
                break;
            case RIGHT:
                if (this.isWalking()) {
                    if (this.isSwimming()) {
                        playerSwimmingRight.nextFrame(g, realX, realY);
                    } else {
                        playerWalkingRight.nextFrame(g, realX, realY);
                    }
                } else if (this.isSwimming()) {
                    Game.spriteSheet.paint(g, "player_swimming_right", realX, realY);
                } else {
                    Game.spriteSheet.paint(g, "player_right", realX, realY);
                }
                break;
            default:
                break;
        }
        
        //Debug for player
        g.setColor(Color.white);
        if (DEBUG_PLAYER) {
            g.drawString("x " + this.getX(), 5, 15);
            g.drawString("y " + this.getY(), 5, 31);
            g.drawString("tileCount " + map.getRenderedTileCount(), 5, 47);
            g.drawString("tileX " + map.getCurrentTileX(), 5, 63);
            g.drawString("tileY " + map.getCurrentTileY(), 5, 79);
        }
    }

    @Override
    public void tick() {
        //Slow the player down if we are walking on a sand
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
                if (playerTicks % 40 == 0 && health > 0) {
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

    }

}
