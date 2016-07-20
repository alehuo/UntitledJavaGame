/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.entities;

import ahuotala.game.Tickable;
import java.util.Random;

/**
 *
 * @author Aleksi
 */
public class Npc implements Entity, Tickable {

    private int x;
    private int y;
    private int interval = 600;
    private int count = 0;
    private String name;
    private int step = 2;

    //Moving algorithm
    private Random random;
    private boolean move = true;
    private int randomDirection;
    private int moveAmount;
    private boolean moveTicks = false;
    private int movingAreaX = 30;
    private int movingAreaY = 30;
    private int startX;
    private int startY;

    public Npc(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        random = new Random();
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
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void tick() {
        if (moveTicks) {
            if (moveAmount >= 0) {
                switch (randomDirection) {
                    //Up
                    case 0:
                        if (y > startY - movingAreaY) {
                            y -= step;
                        } else {
                            System.out.println("NPC boundary (tried to move up)");
                        }
                        break;
                    //Down
                    case 1:
                        if (y < startY + movingAreaY) {
                            y += step;
                        } else {
                            System.out.println("NPC boundary (tried to move down)");
                        }
                        break;
                    //Left
                    case 2:
                        if (x > startX - movingAreaX) {
                            x -= step;
                        } else {
                            System.out.println("NPC boundary (tried to move left)");
                        }
                        break;
                    //Right
                    case 3:
                        if (x < startX + movingAreaX) {
                            x += step;
                        } else {
                            System.out.println("NPC boundary (tried to move right)");
                        }
                        break;
                }
                moveAmount--;
            } else {
                moveTicks = false;
            }

        } else {
            if (count >= interval) {
                interval = random.nextInt(400) + 200;
                randomDirection = random.nextInt(4);
                moveAmount = random.nextInt(22) + 13;
                moveTicks = true;
                System.out.println("New interval:" + interval);
                System.out.println("NPC tick");
                count = 0;
            }
            count++;

        }

    }

}
