/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.entities;

import ahuotala.game.Game;
import static ahuotala.game.Game.WINDOW_HEIGHT;
import static ahuotala.game.Game.animationTicker;
import static ahuotala.game.Game.spriteSheet;
import ahuotala.game.Tickable;
import ahuotala.graphics.animation.Animation;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 *
 * @author Aleksi
 */
public class InteractableNpc implements Entity, Interactable, Tickable {

    //X
    private int x = 0;
    //Y
    private int y = 0;
    //Ticking interval
    private int interval;
    //Tick count
    private int count = 0;
    //NPC name
    private final String name;
    //Movement step
    private final int step = 1*Game.SCALE;
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

    public InteractableNpc(String name) {
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

    public String getName() {
        return name;
    }

    public boolean isWalking() {
        return isWalking;
    }

    public void setWalkingState(boolean isWalking) {
        this.isWalking = isWalking;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setX(int x) {
        this.x = x*Game.SCALE;
        startX = x*Game.SCALE;
    }

    @Override
    public void setY(int y) {
        this.y = y*Game.SCALE;
        startY = y*Game.SCALE;
    }

    public void setMovementState(boolean state) {
        canMove = state;
    }

    @Override
    /**
     * Use the tick() -method to add special features to npcs.
     */
    public void tick() {
//        For debug only
//        moveTicks = true;
//        moveAmount = 800;
//        randomDirection = 2;
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
//                            System.out.println("NPC boundary (tried to move up)");
                            }
                            break;
                        //Down
                        case 1:
                            if (y < startY + movingAreaY) {
                                direction = Direction.DOWN;
                                y += step;
                                isWalking = true;
                            } else {
//                            System.out.println("NPC boundary (tried to move down)");
                            }
                            break;
                        //Left
                        case 2:
                            if (x > startX - movingAreaX) {
                                direction = Direction.LEFT;
                                x -= step;
                                isWalking = true;
                            } else {
//                            System.out.println("NPC boundary (tried to move left)");
                            }
                            break;
                        //Right
                        case 3:
                            if (x < startX + movingAreaX) {
                                direction = Direction.RIGHT;
                                x += step;
                                isWalking = true;
                            } else {
//                            System.out.println("NPC boundary (tried to move right)");
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
//                            System.out.println("NPC boundary (tried to move up and left)");
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
//                            System.out.println("NPC boundary (tried to move up and right)");
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
//                            System.out.println("NPC boundary (tried to move down and left)");
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
//                            System.out.println("NPC boundary (tried to move down and left)");
                            }
                            break;
                        default:
                            System.err.println("Unknown direction: " + randomDirection);
                            break;
                    }
                    moveAmount--;
                } else {
                    moveTicks = false;
                    isWalking = false;
                }

            } else {
                if (count >= interval) {
                    interval = random.nextInt(400) + 50;
                    randomDirection = random.nextInt(7);
                    moveAmount = random.nextInt(15)*Game.SCALE + 19*Game.SCALE;
                    moveTicks = true;
//                System.out.println("New interval:" + interval);
//                System.out.println("NPC tick");
                    count = 0;
                }
                count++;

            }

        }

    }

    public void renderNpc(Graphics g, Player player) {
//        spriteSheet.paint(g, "player_shadow", this.getX() + player.getOffsetX() - 8, this.getY() + player.getOffsetY() - 13);
        if (this.isWalking()) {
            switch (this.getDirection()) {
                case UP:
                    playerWalkingUp.nextFrame(g, this.getX() + player.getOffsetX(), this.getY() + player.getOffsetY());
                    break;
                case DOWN:
                    playerWalkingDown.nextFrame(g, this.getX() + player.getOffsetX(), this.getY() + player.getOffsetY());
                    break;
                case LEFT:
                    playerWalkingLeft.nextFrame(g, this.getX() + player.getOffsetX(), this.getY() + player.getOffsetY());
                    break;
                case RIGHT:
                    playerWalkingRight.nextFrame(g, this.getX() + player.getOffsetX(), this.getY() + player.getOffsetY());
                    break;
                default:
                    break;
            }
        } else {
            switch (this.getDirection()) {
                case UP:
                    spriteSheet.paint(g, "player_up", this.getX() + player.getOffsetX(), this.getY() + player.getOffsetY());
                    break;
                case DOWN:
                    spriteSheet.paint(g, "player_down", this.getX() + player.getOffsetX(), this.getY() + player.getOffsetY());
                    break;
                case LEFT:
                    spriteSheet.paint(g, "player_left", this.getX() + player.getOffsetX(), this.getY() + player.getOffsetY());
                    break;
                case RIGHT:
                    spriteSheet.paint(g, "player_right", this.getX() + player.getOffsetX(), this.getY() + player.getOffsetY());
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

    public void drawBoundaries(Graphics g, int oX, int oY) {
        g.setColor(Color.yellow);
        g.draw3DRect(startX - movingAreaX + oX, startY - movingAreaY + oY, 2 * movingAreaX + 32, 2 * movingAreaY + 32, false);
    }

    public void drawInteractionBoundaries(Graphics g, int oX, int oY) {
        g.setColor(Color.blue);
        g.draw3DRect(x - rX + oX, y - rY + oY, 4 * rX, 4 * rY, false);
    }

    @Override
    public void setInteractionRadiusX(int rX) {
        this.rX = rX*Game.SCALE;
    }

    @Override
    public void setInteractionRadiusY(int rY) {
        this.rY = rY*Game.SCALE;
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

    public Direction getDirection() {
        return direction;
    }

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
