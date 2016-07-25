/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.entities;

import ahuotala.game.Tickable;
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
    //Z
    private int z = 0;
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
    //Is walking?
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

    public InteractableNpc(String name) {
        this.interval = 100;
        this.name = name;
        random = new Random();
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
        this.x = x;
        startX = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
        startY = y;
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
                    moveAmount = random.nextInt(15) + 19;
                    moveTicks = true;
//                System.out.println("New interval:" + interval);
//                System.out.println("NPC tick");
                    count = 0;
                }
                count++;

            }

        }

    }

    public void renderNpc(Graphics g) {

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
        this.rX = rX;
    }

    @Override
    public void setInteractionRadiusY(int rY) {
        this.rY = rY;
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

    @Override
    public int getZ() {
        return z;
    }

    @Override
    public void setZ(int z) {
        this.z = z;
    }

}
