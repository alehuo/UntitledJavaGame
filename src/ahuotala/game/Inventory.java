/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Aleksi
 */
public class Inventory {

    public static final int cols = 6;
    public static final int rows = 8;
    private final int slots = cols * rows;
    private ItemStack[] inventory;
    private int movingSlot = -1;
    private ItemStack tmpStack;
    private ItemStack tmpStack2;

    public Inventory() {
        //Initialize the inventory, an array of ItemStacks
        inventory = new ItemStack[cols * rows];
    }

    public ItemStack[] getInventory() {
        return inventory;
    }

    public void setInventory(ItemStack[] inventory) {
        this.inventory = inventory;
    }

    public void addStack(ItemStack stack) {
        for (int i = 0; i < inventory.length; i++) {
            //We have the same item in the stack and the stack is not full
            if (inventory[i] != null && inventory[i].equals(stack) && inventory[i].getAmount() < inventory[i].getMaxSize()) {
                //Check for the quantity and add to it if possible
                int inventoryAmount = inventory[i].getAmount();
                int inventoryMaxAmount = inventory[i].getMaxSize();
                //Amount if items in stack
                int stackAmount = stack.getAmount();
                //Available space in inventory (current stack)
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

    public int addNewStack(ItemStack stack) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                inventory[i] = stack;
                return i;
            }
        }
        return -1;
    }

    public int addToStack(int stackId, ItemStack stack) {
        return -1;
    }

    public void removeStack(int stackId) {
        inventory[stackId] = null;
    }

    public ItemStack getItemStack(int stackId) {
        if (inventory.length - 1 > stackId) {
            return inventory[stackId];
        }
        return inventory[-1];
    }

    public int getStackCount() {
        int itemCount = 0;
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                itemCount++;
            }
        }
        return itemCount;
    }

    public void renderInventory(Graphics g) {
        //Base image
        BufferedImage inventoryImage = Game.spriteSheet.getInventory();
        int x = (int) Math.floor((Game.WINDOW_WIDTH - inventoryImage.getWidth()) / 2);
        int y = (int) Math.floor((Game.WINDOW_HEIGHT - inventoryImage.getHeight()) / 2);
        g.drawImage(inventoryImage, x, y, null);
        //Tooltip
        g.setColor(Color.white);
        g.drawString("[F] Use     [R] Drop         " + this.getStackCount() + " / " + slots + " inventory slots in use", x + 8, y + inventoryImage.getHeight() + 16);
        //Item stacks
        //Each inventory slot is 72x72 pixels
        //First inv slot has an x-offset of 4 and an y-offset of 4.
        //X-offset of 80 afterwards.
        //1: (4,4) , 2: (84,4), 3: (164,4), ...
        //9: (4,84), 10: (84,84), 11: (164,84), ...
        int slot = 0;
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                int mouseX = MouseHandler.x;
                int mouseY = MouseHandler.y;
                if (mouseX >= x + 4 + 80 * row && mouseX < x + 4 + 80 * row + 72 && mouseY >= y + 4 + 80 * col && mouseY < y + 4 + 80 * col + 72) {
                    if (Game.DEBUG) {
                        g.setColor(Color.LIGHT_GRAY);
                        g.fill3DRect(x + 4 + 80 * row, y + 4 + 80 * col, 72, 72, false);
                    }
                }
                //If the slot is not empty
                if (inventory[slot] != null) {
                    int slotX = x + 4 + 80 * row;
                    int slotY = y + 4 + 80 * col;
                    //If we clicked a slot
                    if (MouseHandler.mouseClicked && mouseX >= x + 4 + 80 * row && mouseX < x + 4 + 80 * row + 72 && mouseY >= y + 4 + 80 * col && mouseY < y + 4 + 80 * col + 72) {
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
                    g.drawImage(Game.spriteSheet.getItemIcon(inventory[slot].getItemId()), slotX, slotY, 72, 72, null);
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
                                System.out.println("Slot " + slot + " is interactable");
                            } else {
                                System.out.println("Slot " + slot + " is not interactable");
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

    public void setMovingSlot(int slot) {
        movingSlot = slot;
    }

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
