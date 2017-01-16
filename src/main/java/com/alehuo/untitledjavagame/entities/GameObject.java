/*
 * Copyright 2017 alehuo.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alehuo.untitledjavagame.entities;

import com.alehuo.untitledjavagame.Game;
import com.alehuo.untitledjavagame.graphics.Renderer;
import com.alehuo.untitledjavagame.graphics.Sprite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author alehuo
 */
public class GameObject extends Entity {

    private final int width;
    private final int height;
    private final Sprite sprite;
    private final Rectangle bounds;

    /**
     *
     * @param x
     * @param y
     * @param sprite
     */
    public GameObject(int x, int y, Sprite sprite) {
        super(x, y);
        this.sprite = sprite;
        width = sprite.getWidth();
        height = sprite.getHeight();
        bounds = new Rectangle(x, y, width, height);
    }

    /**
     *
     * @param player
     * @return
     */
    public boolean collidesWithPlayer(Player player) {
        if (!Game.COLLISIONDECECTION) {
            return false;
        }
        if (bounds.intersects(player.getBounds())) {
            player.setX(player.getLastX());
            player.setY(player.getLastY());
            return true;
        }
        player.setLastX(player.getX());
        player.setLastY(player.getY());
        return false;
    }

    /**
     *
     * @param r
     * @param p
     */
    public void render(Renderer r, Player p) {
        r.renderSprite(sprite, getX() + p.getOffsetX(), getY() + p.getOffsetY());
    }

    /**
     *
     * @param g
     * @param p
     */
    public void drawBoundaries(Graphics g, Player p) {
        g.setColor(Color.MAGENTA);
        g.draw3DRect(getX() + p.getOffsetX(), getY() + p.getOffsetY(), width, height, false);
    }
}
