package com.ahuotala.untitledjavagame.entities;

import com.ahuotala.untitledjavagame.game.Game;
import static com.ahuotala.untitledjavagame.game.Game.animationTicker;
import static com.ahuotala.untitledjavagame.game.Game.spriteSheet;
import com.ahuotala.untitledjavagame.game.Renderer;
import com.ahuotala.untitledjavagame.game.Tickable;
import com.ahuotala.untitledjavagame.graphics.animation.Animation;
import java.awt.Graphics;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Basic NPC
 *
 * @author Aleksi Huotala
 */
public class Npc extends Entity implements Tickable {
    private static final Logger LOG = Logger.getLogger(Npc.class.getName());

    //Ticking interval
    private int interval;
    //Tick count
    private int count = 0;
    //NPC name
    private final String name;
    //Movement step
    private final int step = 1;
    //Direction
    private Direction direction = Direction.DOWN;
    //Is the NPC walking?
    private boolean isWalking = false;

    //Moving algorithm
    private final Random random;
    private int randomDirection;
    private int moveAmount;
    private boolean moveTicks = false;
    private final int movingAreaX = 70;
    private final int movingAreaY = 70;
    private int startX;
    private int startY;


    //Can the NPC move around?
    private boolean canMove = true;

    //Animations
    private final Animation playerWalkingUp;
    private final Animation playerWalkingDown;
    private final Animation playerWalkingLeft;
    private final Animation playerWalkingRight;

    /**
     *
     * @param name
     */
    public Npc(String name) {
        this.interval = 100;
        this.name = name;
        random = new Random();
        playerWalkingUp = new Animation("PlayerWalkingUp", 10);
        playerWalkingDown = new Animation("PlayerWalkingDown", 10);
        playerWalkingLeft = new Animation("PlayerWalkingLeft", 10);
        playerWalkingRight = new Animation("PlayerWalkingRight", 10);
        animationTicker.register(playerWalkingUp);
        animationTicker.register(playerWalkingDown);
        animationTicker.register(playerWalkingLeft);
        animationTicker.register(playerWalkingRight);
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public boolean isWalking() {
        return isWalking;
    }

    /**
     *
     * @param isWalking
     */
    public void setWalkingState(boolean isWalking) {
        this.isWalking = isWalking;
    }

    /**
     *
     * @return
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     *
     * @return
     */
    @Override
    public int getY() {
        return y;
    }

    /**
     *
     * @param x
     */
    @Override
    public void setX(int x) {
        this.x = x;
        startX = x;
    }

    /**
     *
     * @param y
     */
    @Override
    public void setY(int y) {
        this.y = y;
        startY = y;
    }

    /**
     *
     * @param state
     */
    public void setMovementState(boolean state) {
        canMove = state;
    }

    /**
     *
     */
    @Override
    /**
     * Use the tick() -method to add special features to npcs.
     */
    public void tick() {
        if (canMove) {
            if (moveTicks) {
                if (moveAmount >= 0) {
                    switch (randomDirection) {
                        //Up
                        case 0:
                            if (y > startY - movingAreaY) {
                                direction = Direction.UP;
                                isWalking = true;
                                y -= step;
                            } else {
                                LOG.log(Level.INFO, "NPC boundary (tried to move up)");
                            }
                            break;
                        //Down
                        case 1:
                            if (y < startY + movingAreaY) {
                                direction = Direction.DOWN;
                                y += step;
                                isWalking = true;
                            } else {
                                LOG.log(Level.INFO, "NPC boundary (tried to move down)");
                            }
                            break;
                        //Left
                        case 2:
                            if (x > startX - movingAreaX) {
                                direction = Direction.LEFT;
                                x -= step;
                                isWalking = true;
                            } else {
                                LOG.log(Level.INFO, "NPC boundary (tried to move left)");
                            }
                            break;
                        //Right
                        case 3:
                            if (x < startX + movingAreaX) {
                                direction = Direction.RIGHT;
                                x += step;
                                isWalking = true;
                            } else {
                                LOG.log(Level.INFO, "NPC boundary (tried to move right)");
                            }
                            break;
                        //Up and left
                        case 4:
                            if (x > startX - movingAreaX && y > startY - movingAreaY) {
                                direction = Direction.LEFT;
                                x -= step;
                                y -= step;
                                isWalking = true;
                            } else {
                                LOG.log(Level.INFO, "NPC boundary (tried to move up and left)");
                            }
                            break;
                        //Up and right
                        case 5:
                            if (x < startX + movingAreaX && y > startY - movingAreaY) {
                                direction = Direction.RIGHT;
                                x += step;
                                y -= step;
                                isWalking = true;
                            } else {
                                LOG.log(Level.INFO, "NPC boundary (tried to move up and right)");
                            }
                            break;
                        //Down and left
                        case 6:
                            if (x > startX - movingAreaX && y < startY + movingAreaY) {
                                direction = Direction.LEFT;
                                x -= step;
                                y += step;
                                isWalking = true;
                            } else {
                                LOG.log(Level.INFO, "NPC boundary (tried to move down and left)");
                            }
                            break;
                        //Down and right
                        case 7:
                            if (x < startX + movingAreaX && y < startY + movingAreaY) {
                                direction = Direction.RIGHT;
                                x += step;
                                y += step;
                                isWalking = true;
                            } else {
                                LOG.log(Level.INFO, "NPC boundary (tried to move down and left)");
                            }
                            break;
                        default:
                            LOG.log(Level.INFO, "Unknown direction: " + randomDirection);
                            break;
                    }
                    moveAmount--;
                } else {
                    moveTicks = false;
                    isWalking = false;
                }

            } else {
                if (count >= interval) {
                    interval = (random.nextInt(400) + 50) * (int) Math.ceil(Game.tickrate / 60);
                    randomDirection = random.nextInt(7);
                    moveAmount = random.nextInt(15) + 19;
                    moveTicks = true;
                    LOG.log(Level.INFO, "New interval:" + interval);
                    LOG.log(Level.INFO, "NPC tick");
                    count = 0;
                }
                count++;

            }

        }

    }

    /**
     *
     * @param g
     * @param r
     * @param player
     */
    public void renderNpc(Graphics g, Renderer r, Player player) {

        if (this.isWalking()) {
            switch (this.getDirection()) {
                case UP:
                    playerWalkingUp.nextFrame(r, this.getX() + player.getOffsetX(), getY() + player.getOffsetY());
                    break;
                case DOWN:
                    playerWalkingDown.nextFrame(r, this.getX() + player.getOffsetX(), getY() + player.getOffsetY());
                    break;
                case LEFT:
                    playerWalkingLeft.nextFrame(r, this.getX() + player.getOffsetX(), getY() + player.getOffsetY());
                    break;
                case RIGHT:
                    playerWalkingRight.nextFrame(r, this.getX() + player.getOffsetX(), getY() + player.getOffsetY());
                    break;
                default:
                    break;
            }
        } else {
            switch (this.getDirection()) {
                case UP:
                    spriteSheet.paint(r, "player_up", this.getX() + player.getOffsetX(), getY() + player.getOffsetY());
                    break;
                case DOWN:
                    spriteSheet.paint(r, "player_down", this.getX() + player.getOffsetX(), getY() + player.getOffsetY());
                    break;
                case LEFT:
                    spriteSheet.paint(r, "player_left", this.getX() + player.getOffsetX(), getY() + player.getOffsetY());
                    break;
                case RIGHT:
                    spriteSheet.paint(r, "player_right", this.getX() + player.getOffsetX(), getY() + player.getOffsetY());
                    break;
                default:
                    break;
            }
        }

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
        return moveTicks;
    }

}
