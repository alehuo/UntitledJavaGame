/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.levels;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Aleksi
 */
public class Level {

    private ArrayList<String> lines;

    public Level(String name) {
        lines = new ArrayList<>();
        try {
            File levelFile = new File("src/ahuotala/levels/" + name + ".lev");
            Scanner sc = new Scanner(levelFile);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                //Skip comments
                if (line.contains("#")) {
                    continue;
                }
                lines.add(line);
            }
            sc.close();
            for (String line : lines) {
                String[] lineData = line.split(",");
                //Block per block, add things
                int x = Integer.parseInt(lineData[0]);
                int y = Integer.parseInt(lineData[1]);
                int type = Integer.parseInt(lineData[2]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void renderLevel(){
    
    }

}
