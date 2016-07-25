/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.entities;

/**
 *
 * @author Aleksi
 */
interface Entity {

    int getX();

    int getY();
    
    int getZ();

    void setX(int x);

    void setY(int y);
    
    void setZ(int z);
}
