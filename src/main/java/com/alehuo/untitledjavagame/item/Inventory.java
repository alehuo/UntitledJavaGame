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
package com.alehuo.untitledjavagame.item;

import com.alehuo.untitledjavagame.Game;
import com.alehuo.untitledjavagame.input.MouseHandler;
import com.alehuo.untitledjavagame.graphics.Renderer;
import com.alehuo.untitledjavagame.graphics.Sprite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aleksi
 */
public class Inventory {

    /**
     * Inventory columns
     */
    public static final int COLS = 6;

    /**
     * Inventory rows
     */
    public static final int ROWS = 8;
    /**
     * Logger
     */
    private static final Logger LOG = Logger.getLogger(Inventory.class.getName());

    /**
     * Slot amount
     */
    private final int slots = COLS * ROWS;

    /**
     * ItemStack
     */
    private ItemStack[] inventory;

    /**
     * Slot to move to
     */
    private int movingSlot = -1;

    /**
     * Temporary itemStack
     */
    private ItemStack tmpStack;

    /**
     * Temporary itemStack 2
     */
    private ItemStack tmpStack2;


    /**
     * Constructor
     */
    public Inventory() {
        //Initialize the INVENTORY, an array of ItemStacks
        inventory = new ItemStack[COLS * ROWS];
    }

    /**
     * Returns the Inventory array
     *
     * @return Inventory
     */
    public ItemStack[] getInventory() {
        return inventory;
    }

    /**
     * Sets the INVENTORY object
     *
     * @param inventory Inventory
     */
    public void setInventory(ItemStack[] inventory) {
        this.inventory = inventory;
    }

    /**
     * Add an itemStack to INVENTORY. Stacks combine automatically if there are
     * stacks that are not full.
     *
     * @param stack
     */
    public void addStack(ItemStack stack) {
        for (int i = 0; i < inventory.length; i++) {
            //We have the same item in the stack and the stack is not full
            if (inventory[i] != null && inventory[i].equals(stack) && inventory[i].getAmount() < inventory[i].getMaxSize()) {
                //Check for the quantity and add to it if possible
                int inventoryAmount = inventory[i].getAmount();
                int inventoryMaxAmount = inventory[i].getMaxSize();
                //Amount if items in stack
                int stackAmount = stack.getAmount();
                //Available space in INVENTORY (current stack)
                int availableSpace = inventoryMaxAmount - inventoryAmount;
                //Leftover; amount of items that don't fit
                int leftoverSpace = stackAmount - availableSpace;
                //If the leftoverSpace is greater than zero, we need to create a new itemStack.
                if (leftoverSpace > 0) {
                    inventory[i].addToStack(availableSpace);
                    int stackId = this.addNewStack(stack);
                    if (stackId != -1) {
                        inventory[stackId].takeFromStack(availableSpace);
                    }
                    break;
                } else {
                    //If the leftoverSpace is negative, all items fit the stack.
                    inventory[i].addToStack(stackAmount);
                    break;
                }
            } else if (inventory[i] == null) {
                //If the stack is empty
                inventory[i] = stack;
                break;
            }
        }
    }

    /**
     * Add a new stack NOTE: This method is used in the addStack() -method. Use
     * that method instead of this!
     *
     * @param stack ItemStack
     * @return Inventory slot ID
     */
    public int addNewStack(ItemStack stack) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                inventory[i] = stack;
                return i;
            }
        }
        return -1;
    }

    /**
     * Add items to stack
     *
     * @param stackId
     * @param stack
     * @return Itemstack ID
     */
    public int addToStack(int stackId, ItemStack stack) {
        return -1;
    }

    /**
     * Remove stack from INVENTORY
     *
     * @param stackId Inventory slot ID
     */
    public void removeStack(int stackId) {
        inventory[stackId] = null;
    }

    /**
     * Get itemStack by ID
     *
     * @param stackId
     * @return
     */
    public ItemStack getItemStack(int stackId) {
        if (inventory.length - 1 > stackId) {
            return inventory[stackId];
        }
        return null;
    }

    /**
     *
     * @return
     */
    public int getStackCount() {
        int itemCount = 0;
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                itemCount++;
            }
        }
        return itemCount;
    }

    /**
     * Renders the INVENTORY
     *
     * @param g
     * @param r
     */
    public void renderInventory(Graphics g, Renderer r) {
        //Base image
        BufferedImage inventoryImage = Game.spriteSheet.getInventory();
        Sprite inventorySprite = Game.spriteSheet.getInventorySprite();
        //X and Y coordinate
        int x = (int) Math.floor((Game.WINDOW_WIDTH - inventorySprite.getWidth()) / 2);
        int y = (int) Math.floor((Game.WINDOW_HEIGHT - inventorySprite.getHeight()) / 2);
        //Draw the INVENTORY background
//        g.drawImage(inventoryImage, x, y, null);
        r.renderSprite(inventorySprite, x, y);
        //Tooltip
        g.setColor(Color.white);
        g.drawString("[F] Use     [R] Drop         " + this.getStackCount() + " / " + slots + " inventory slots in use", x + 8, y + inventorySprite.getHeight() + 16);
        //Item stacks
        //Each INVENTORY slot is 41x41 pixels
        //First inv slot has an x-offset of 4 and an y-offset of 4.
        //X-offset of 80 afterwards.
        //1: (4,4) , 2: (84,4), 3: (164,4), ...
        //9: (4,84), 10: (84,84), 11: (164,84), ...
        int slot = 0;
        int slotWidthHeight = 41;
        int xOffset = 46;
        for (int col = 0; col < COLS; col++) {
            for (int row = 0; row < ROWS; row++) {
                int mouseX = MouseHandler.x;
                int mouseY = MouseHandler.y;
                if (mouseX >= x + 4 + xOffset * row && mouseX < x + 4 + xOffset * row + slotWidthHeight && mouseY >= y + 4 + xOffset * col && mouseY < y + 4 + xOffset * col + slotWidthHeight) {
                    if (Game.DEBUG) {
                        g.setColor(Color.LIGHT_GRAY);
                        g.fill3DRect(x + 4 + xOffset * row, y + 4 + xOffset * col, slotWidthHeight, slotWidthHeight, false);
                    }
                }
                //If the slot is not empty
                if (inventory[slot] != null) {
                    int slotX = x + 3 + xOffset * row;
                    int slotY = y + 3 + xOffset * col;
                    //If we clicked a slot
                    if (MouseHandler.mouseClicked && mouseX >= x + 4 + xOffset * row && mouseX < x + 4 + xOffset * row + slotWidthHeight && mouseY >= y + 4 + xOffset * col && mouseY < y + 4 + xOffset * col + slotWidthHeight) {
                        //If we have clicked in before
                        if (this.getMovingSlot() != -1) {
                            //Move the ItemStack to another slot
                            //If the slot we are trying to move the ItemStack is empty
                            if (inventory[slot] == null) {
                                inventory[this.getMovingSlot()] = null;
                                inventory[slot] = tmpStack;
                                this.setMovingSlot(-1);
                                tmpStack = null;
                            } else {
                                //If it's not empty
                                tmpStack2 = inventory[slot];
                                inventory[this.getMovingSlot()] = tmpStack2;
                                inventory[slot] = tmpStack;
                                this.setMovingSlot(-1);
                                tmpStack2 = null;
                                tmpStack = null;
                            }
                        } else {
                            tmpStack = inventory[slot];
                            this.setMovingSlot(slot);
                        }
                        MouseHandler.mouseClicked = false;
                    }
                    if (this.getMovingSlot() != -1 && this.getMovingSlot() == slot) {
                        slotX = mouseX - 36;
                        slotY = mouseY - 36;
                    }
                    //Item icon
                    //TODO
                    r.renderSprite(Game.spriteSheet.getItemIcon(inventory[slot].getItemId()), slotX, slotY);
//                    g.drawImage(Game.spriteSheet.getItemIcon(INVENTORY[slot].getItemId()), slotX, slotY, 72, 72, null);
                    g.setColor(Color.YELLOW);
                    int fontOffsetX = 2;
                    int fontOffsetY = 68;
                    //Amount
                    g.drawString(inventory[slot].getAmount() + "", slotX + fontOffsetX, slotY + fontOffsetY);

                    //Item data
                    if (mouseX >= x + 4 + 80 * row && mouseX < x + 4 + 80 * row + 72 && mouseY >= y + 4 + 80 * col && mouseY < y + 4 + 80 * col + 72 && this.getMovingSlot() == -1) {
                        g.setColor(Color.YELLOW);
                        if (!inventory[slot].getLabel().isEmpty()) {
                            String tmp = "";
                            if (inventory[slot].getAmount() > 1) {
                                tmp = " (" + inventory[slot].getAmount() + ")";
                            }
                            g.drawString(inventory[slot].getLabel() + tmp, mouseX - 32 + 4, mouseY + 64);
                        } else {
                            String tmp = "";
                            if (inventory[slot].getAmount() > 1) {
                                tmp = " (" + inventory[slot].getAmount() + ")";
                            }
                            g.drawString(inventory[slot].getItemId() + tmp, mouseX - 32 + 4, mouseY + 64);
                        }
                        if (inventory[slot].getEffect() != Effect.NONE) {
                            g.drawString(inventory[slot].getEffectDesc(), mouseX - 32 + 4, mouseY + 78);
                        }
                        if (Game.DEBUG) {
                            if (inventory[slot] != null && inventory[slot].isInteractable()) {
                                LOG.log(Level.INFO, "Slot " + slot + " is interactable");
                            } else {
                                LOG.log(Level.INFO, "Slot " + slot + " is not interactable");
                            }
                        }

                    }
                } else if (MouseHandler.mouseClicked && mouseX >= x + 4 + 80 * row && mouseX < x + 4 + 80 * row + 72 && mouseY >= y + 4 + 80 * col && mouseY < y + 4 + 80 * col + 72) {
                    //If the slot is empty & we are moving an item
                    if (this.getMovingSlot() != -1) {
                        inventory[this.getMovingSlot()] = null;
                        inventory[slot] = tmpStack;
                        this.setMovingSlot(-1);
                        tmpStack = null;
                    }
                    MouseHandler.mouseClicked = false;
                }
                slot++;
            }
        }
    }

    /**
     * Set the moving slot
     * @param slot Slot id
     */
    public void setMovingSlot(int slot) {
        movingSlot = slot;
    }

    /**
     * Returns the moving slot
     * @return Slot id
     */
    public int getMovingSlot() {
        return movingSlot;
    }

    @Override
    public String toString() {
        String tmp = "";
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                tmp += i + ": " + inventory[i].toString() + "\r\n";
            } else {
                tmp += i + ": empty\r\n";
            }

        }
        return tmp;
    }
}
