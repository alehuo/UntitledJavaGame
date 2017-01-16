/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahuotala.untitledjavagame.test;

import com.alehuo.untitledjavagame.entities.Direction;
import com.alehuo.untitledjavagame.entities.Player;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author alehuo
 */
public class PlayerTest {

    private Player p;

    /**
     *
     */
    @Before
    public void init() {
        p = new Player();
    }

    /**
     *
     */
    @Ignore
    @Test
    public void testSettingUpCoordinatesWorks() {
        int x = 15;
        int y = 18;

        p.setX(x);
        p.setY(y);

        assertEquals("X-coordinate is incorrect", x, p.getX());
        assertEquals("Y-coordinate is incorrect", y, p.getY());
    }

    /**
     *
     */
    @Ignore
    @Test
    public void testSettingDirectionWorks() {
        Direction d = Direction.DOWN;

        p.setDirection(d);

        assertEquals("Direction is incorrect", d, p.getDirection());
    }
}
