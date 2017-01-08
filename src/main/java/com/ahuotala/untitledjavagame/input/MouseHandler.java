package com.ahuotala.untitledjavagame.input;

import com.ahuotala.untitledjavagame.Game;
import com.ahuotala.untitledjavagame.item.Inventory;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 *
 * @author Aleksi Huotala
 */
public class MouseHandler extends MouseAdapter {

    /**
     *
     */
    public static int x = 0;

    /**
     *
     */
    public static int y = 0;

    /**
     *
     */
    public static boolean mouseClicked = false;

    /**
     *
     */
    public static boolean pressed = false;

    /**
     *
     */
    public static int slot = -1;

    /**
     *
     * @param slot
     */
    public static void setClickedSlot(int slot) {
        MouseHandler.slot = slot;
    }

    /**
     *
     * @return
     */
    public static int getClickedSlot() {
        return slot;
    }
    private final Inventory i;

    /**
     *
     * @param i
     */
    public MouseHandler(Inventory i) {
        this.i = i;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        if (Game.SHOW_INVENTORY) {
//            mouseClicked = !mouseClicked;
//        }
    }


    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (Game.SHOW_INVENTORY || Game.isInMenu) {
            mouseClicked = !mouseClicked;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        e.getScrollAmount();
    }

}
