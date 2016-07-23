/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.entities;

import java.awt.Graphics;

/**
 *
 * @author Aleksi
 */
public class Object {

    public Object(int x, int y, int width, int height, String name) {
    }

    public boolean isCollidingWithPlayer(Player player) {
        return true;
    }
    
    public void render(Graphics g){
    
    }
}
