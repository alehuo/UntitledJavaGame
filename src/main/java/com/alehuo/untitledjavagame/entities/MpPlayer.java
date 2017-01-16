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

import static com.alehuo.untitledjavagame.Game.spriteSheet;
import com.alehuo.untitledjavagame.graphics.Renderer;
import java.awt.Graphics;

/**
 *
 * @author alehuo
 */
public class MpPlayer extends Entity implements java.io.Serializable {

    private int x;
    private int y;
    private Direction direction;
    private String uuid;
    private transient long lastUpdate;

    /**
     *
     * @param uuid
     * @param x
     * @param y
     * @param direction
     */
    public MpPlayer(String uuid, int x, int y, Direction direction) {
        this.uuid = uuid;
        this.x = x;
        this.y = y;
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
     * @param direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     *
     * @param uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     *
     * @return
     */
    public String getUuid() {
        return uuid;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     *
     * @param g
     * @param r
     * @param player
     */
    public void renderMpPlayer(Graphics g, Renderer r, Player player) {
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
