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
public class Npc implements Entity, Tickable {

    private int x = 0;
    private int y = 0;
    private int interval = 600;
    private int count = 0;
    private final String name;
    private final int step = 1;

    //Moving algorithm
    private final Random random;
    private int randomDirection;
    private int moveAmount;
    private boolean moveTicks = false;
    private final int movingAreaX = 70;
    private final int movingAreaY = 70;
    private int startX;
    private int startY;

    public Npc(String name) {
        this.name = name;
        random = new Random();
    }

    public String getName() {
        return name;
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

    @Override
    /**
     * Use the tick() -method to add special features to npcs.
     */
    public void tick() {
//        For debug only
//        moveTicks = true;
//        moveAmount = 800;
//        randomDirection = 2;
        if (moveTicks) {
            if (moveAmount >= 0) {
                switch (randomDirection) {
                    //Up
                    case 0:
                        if (y > startY - movingAreaY) {
                            y -= step;
                        } else {
//                            System.out.println("NPC boundary (tried to move up)");
                        }
                        break;
                    //Down
                    case 1:
                        if (y < startY + movingAreaY) {
                            y += step;
                        } else {
//                            System.out.println("NPC boundary (tried to move down)");
                        }
                        break;
                    //Left
                    case 2:
                        if (x > startX - movingAreaX) {
                            x -= step;
                        } else {
//                            System.out.println("NPC boundary (tried to move left)");
                        }
                        break;
                    //Right
                    case 3:
                        if (x < startX + movingAreaX) {
                            x += step;
                        } else {
//                            System.out.println("NPC boundary (tried to move right)");
                        }
                        break;
                    //Up and left
                    case 4:
                        if (x > startX - movingAreaX && y > startY - movingAreaY) {
                            x -= step;
                            y -= step;
                        } else {
//                            System.out.println("NPC boundary (tried to move up and left)");
                        }
                        break;
                    //Up and right
                    case 5:
                        if (x < startX + movingAreaX && y > startY - movingAreaY) {
                            x += step;
                            y -= step;
                        } else {
//                            System.out.println("NPC boundary (tried to move up and right)");
                        }
                        break;
                    //Down and left
                    case 6:
                        if (x > startX - movingAreaX && y < startY + movingAreaY) {
                            x -= step;
                            y += step;
                        } else {
//                            System.out.println("NPC boundary (tried to move down and left)");
                        }
                        break;
                    //Down and right
                    case 7:
                        if (x < startX + movingAreaX && y < startY + movingAreaY) {
                            x += step;
                            y += step;
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

    public void drawBoundaries(Graphics g, int oX, int oY) {
        g.setColor(Color.red);
        g.draw3DRect(startX - movingAreaX + oX, startY - movingAreaY + oY, 2 * movingAreaX + 32, 2 * movingAreaY + 32, false);
    }

}
