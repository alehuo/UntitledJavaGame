package ahuotala.game;

import static ahuotala.game.Game.FONTSCALE;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Aleksi Huotala
 */
public class Menu {

    //Game object
    private final Game game;
    //File choose
    private final JFileChooser fc = new JFileChooser();

    public Menu(Game game) {
        this.game = game;
    }

    public void render(Graphics g) {
        Font currentFont = g.getFont();
        g.setFont(new Font(currentFont.getName(), Font.BOLD, (int) Math.floor(36 * FONTSCALE)));
//        g.setColor(Color.white);

        //Title
        renderCenterText(g, Game.NAME, 32, false);

        g.setFont(new Font(currentFont.getName(), Font.BOLD, (int) Math.floor(25 * FONTSCALE)));
        //Main menu
        if (Game.menuState == MenuState.MAINMENU) {
            //Singleplayer
            if (renderCenterText(g, "Singleplayer", 128, true)) {
                System.out.println("Clicked singleplayer");
                Game.menuState = MenuState.SINGLEPLAYER;
            }
            //Multiplayer
            if (renderCenterText(g, "Multiplayer", 150, true)) {
                System.out.println("Clicked multiplayer");
                Game.menuState = MenuState.MULTIPLAYER_CONNECT;
                //Connection prompt
                if (!Game.isConnectedToServer) {
                    String[] data = JOptionPane.showInputDialog(game, "Please enter the IP address and the port of the host to connect to\r\nFor example, 127.0.0.1:1337").split(":");
                    if (data.length == 2) {
                        game.connectToServer(data[0], Integer.parseInt(data[1]));
                        Game.isConnectedToServer = true;
                    } else {
                        Game.menuState = MenuState.MAINMENU;
                    }
                }
            }
            //Exit game
            if (renderCenterText(g, "Exit game", 173, true)) {
                System.out.println("Clicked exit game");
                System.exit(1);
            }
        } else if (Game.menuState == MenuState.SINGLEPLAYER) {
            //Load game
            if (renderCenterText(g, "Load game", 128, true)) {
                System.out.println("Clicked load game");
                int returnValue = fc.showOpenDialog(game);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File tmpFile = fc.getSelectedFile();
                    game.loadSaveFile(tmpFile);
                    Game.menuState = MenuState.NONE;
                    Game.isInMenu = false;
                }
            }
            //New game
            if (renderCenterText(g, "New game", 150, true)) {
                System.out.println("Clicked new game");
                game.newGame();
                Game.menuState = MenuState.NONE;
                Game.isInMenu = false;
            }
        }

    }

    public boolean renderCenterText(Graphics g, String text, int y, boolean hover) {
        boolean hoverEffect = false;
        hoverEffect = hover;
        FontMetrics fm = g.getFontMetrics();
        int x = ((Game.WINDOW_WIDTH - fm.stringWidth(text)) / 2);

        if (hoverEffect && ((fm.getStringBounds(text, g).getBounds().getX() + x) < MouseHandler.x) && (fm.getStringBounds(text, g).getBounds().getX() + x + fm.stringWidth(text)) > MouseHandler.x && (fm.getStringBounds(text, g).getBounds().getY() + y) < MouseHandler.y && (fm.getStringBounds(text, g).getBounds().getY() + y + fm.getHeight()) > MouseHandler.y) {
            //We know that the mouse is inside the button area.
            if (MouseHandler.mouseClicked && hoverEffect) {
                MouseHandler.mouseClicked = false;
                g.setColor(Color.blue);
                g.drawString(text, x, y);
                return true;
            }
            g.setColor(Color.blue);
            g.drawString(text, x, y);
        } else {
            g.setColor(Color.white);
        }
        g.drawString(text, x, y);
        return false;
    }

    public boolean clicked() {
        return false;
    }
}
