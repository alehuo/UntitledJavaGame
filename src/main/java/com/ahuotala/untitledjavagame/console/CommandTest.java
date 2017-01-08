/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahuotala.untitledjavagame.console;

import com.ahuotala.untitledjavagame.console.commands.AddStack;

/**
 *
 * @author alehuo
 */
public class CommandTest {

    public static void main(String[] args) {
//        cmds[0] = null;
//        System.out.println(cmds.length);
        String[] commandString = "FOOD 5".split(" ");
        Command c = new AddStack();
        System.out.println(c.execute(commandString));
//        Object[] cmds = c.parseCommands(commandString);
//        c.execute(1, " ");
    }
}
