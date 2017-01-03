package com.ahuotala.untitledjavagame.entities;

import com.ahuotala.untitledjavagame.game.Debug;
import com.ahuotala.untitledjavagame.game.Game;
import static com.ahuotala.untitledjavagame.game.Game.WINDOW_HEIGHT;
import static com.ahuotala.untitledjavagame.game.Game.spriteSheet;
import com.ahuotala.untitledjavagame.graphics.Renderer;
import com.ahuotala.untitledjavagame.game.Tickable;
import com.ahuotala.untitledjavagame.graphics.animation.Animation;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import static com.ahuotala.untitledjavagame.game.Game.ANIMATIONTICKER;

/**
 *
 * @author Aleksi
 */
public class InteractableNpc extends Entity implements Interactable, Tickable {

    //x
    private int x = 0;
    //y
    private int y = 0;
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

    //Npc id
    private int npcId;

    //Can the NPC move around?
    private boolean canMove = true;

    //Interaction radius
    private int rY;
    private int rX;

    //Animations
    private final Animation playerWalkingUp;
    private final Animation playerWalkingDown;
    private final Animation playerWalkingLeft;
    private final Animation playerWalkingRight;

    /**
     *
     * @param name
     */
    public InteractableNpc(String name) {
        this.interval = 100;
        this.name = name;
        random = new Random();
        playerWalkingUp = new Animation("PlayerWalkingUp", 10);
        playerWalkingDown = new Animation("PlayerWalkingDown", 10);
        playerWalkingLeft = new Animation("PlayerWalkingLeft", 10);
        playerWalkingRight = new Animation("PlayerWalkingRight", 10);
        ANIMATIONTICKER.register(playerWalkingUp);
        ANIMATIONTICKER.register(playerWalkingDown);
        ANIMATIONTICKER.register(playerWalkingLeft);
        ANIMATIONTICKER.register(playerWalkingRight);
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
     * @param x
     */
    @Override
    public void setX(int x) {
        super.setX(x);
        startX = x;
    }

    /**
     *
     * @param y
     */
    @Override
    public void setY(int y) {
        super.setY(y);
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
                                Debug.log("NPC boundary (tried to move up)");
                            }
                            break;
                        //Down
                        case 1:
                            if (y < startY + movingAreaY) {
                                direction = Direction.DOWN;
                                y += step;
                                isWalking = true;
                            } else {
                                Debug.log("NPC boundary (tried to move down)");
                            }
                            break;
                        //Left
                        case 2:
                            if (x > startX - movingAreaX) {
                                direction = Direction.LEFT;
                                x -= step;
                                isWalking = true;
                            } else {
                                Debug.log("NPC boundary (tried to move left)");
                            }
                            break;
                        //Right
                        case 3:
                            if (x < startX + movingAreaX) {
                                direction = Direction.RIGHT;
                                x += step;
                                isWalking = true;
                            } else {
                                Debug.log("NPC boundary (tried to move right)");
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
                                Debug.log("NPC boundary (tried to move up and left)");
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
                                Debug.log("NPC boundary (tried to move up and right)");
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
                                Debug.log("NPC boundary (tried to move down and left)");
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
                                Debug.log("NPC boundary (tried to move down and left)");
                            }
                            break;
                        default:
                            Debug.log("Unknown direction: " + randomDirection);
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
                    Debug.log("New interval:" + interval);
                    Debug.log("NPC tick");
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
//        spriteSheet.paint(g, "player_shadow", this.getX() + PLAYER.getOffsetX() - 8, this.getY() + PLAYER.getOffsetY() - 13);
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

        if (Game.DEBUG) {
            //Movement boundaries
            this.drawBoundaries(g, player.getOffsetX(), player.getOffsetY());
            //For debug; interaction boundaries
            this.drawInteractionBoundaries(g, player.getOffsetX(), player.getOffsetY());
        }
        //If we are within interaction distance
        if (this.isWithinInteractionDistance(player)) {
            g.drawString("Press E to talk with \"" + this.getName() + "\"", 40, WINDOW_HEIGHT - 32);
        }
    }

    /**
     *
     * @param g
     * @param oX
     * @param oY
     */
    public void drawBoundaries(Graphics g, int oX, int oY) {
        g.setColor(Color.yellow);
        g.draw3DRect(startX - movingAreaX + oX, startY - movingAreaY + oY, 2 * movingAreaX + 32, 2 * movingAreaY + 32, false);
    }

    /**
     *
     * @param g
     * @param oX
     * @param oY
     */
    public void drawInteractionBoundaries(Graphics g, int oX, int oY) {
        g.setColor(Color.blue);
        g.draw3DRect(x - rX + oX, y - rY + oY, 4 * rX, 4 * rY, false);
    }

    @Override
    public void setInteractionRadiusX(int rX) {
        this.rX = rX * Game.SCALE;
    }

    @Override
    public void setInteractionRadiusY(int rY) {
        this.rY = rY * Game.SCALE;
    }

    @Override
    public int getInteractionRadiusX() {
        return rX;
    }

    @Override
    public int getInteractionRadiusY() {
        return rY;
    }

    @Override
    public int getNpcId() {
        return npcId;
    }

    @Override
    public void setNpcId(int id) {
        this.npcId = id;
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

    @Override
    public boolean isWithinInteractionDistance(Player player) {
        int offsetX = 8;
        int offsetY = 2;
        int playerX = player.getX();
        int playerY = player.getY();
        return (playerX > x - rX - offsetX && playerX < x + 2 * rX + offsetX && playerY > y - rY - offsetY * 2 && playerY < y + 2 * rY);
    }
}
