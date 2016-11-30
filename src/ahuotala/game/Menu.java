package ahuotala.game;

import static ahuotala.game.Game.FONTSCALE;
import static ahuotala.game.Game.isConnectedToServer;
import ahuotala.net.Client;
import ahuotala.net.ClientStatus;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.SocketException;
import java.net.UnknownHostException;
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
        if (null != Game.menuState) //Main menu
        {
            switch (Game.menuState) {
                case MAINMENU:
                    //Singleplayer
                    if (renderCenterText(g, "Singleplayer", 128, true)) {
//                        System.out.println("Clicked singleplayer");
                        Game.menuState = MenuState.SINGLEPLAYER;
                    }   //Multiplayer
                    if (renderCenterText(g, "Multiplayer", 150, true)) {
//                        System.out.println("Clicked multiplayer");
                        Game.menuState = MenuState.MULTIPLAYER_CONNECT;
                        //Connection prompt
                        if (!Game.isConnectedToServer) {
                            String data = JOptionPane.showInputDialog(game, "Please enter the IP address and the port of the host to connect to\r\nFor example, localhost:9876");
                            if (data != null) {
                                String[] tmpData = data.split(":");
                                if (tmpData.length == 2) {
                                    try {
                                        game.connectToServer(tmpData[0].trim(), Integer.parseInt(tmpData[1].trim()));
                                        Game.isConnectedToServer = true;
                                        Game.menuState = MenuState.NONE;
                                    } catch (NumberFormatException | UnknownHostException | SocketException e) {
                                        JOptionPane.showMessageDialog(game, "Error connecting to server: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                        Game.menuState = MenuState.MAINMENU;
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(game, "Error connecting to server: malformed server address", "Error", JOptionPane.ERROR_MESSAGE);
                                    Game.menuState = MenuState.MAINMENU;
                                }
                            } else {
                                JOptionPane.showMessageDialog(game, "Error connecting to server: server address can't be null", "Error", JOptionPane.ERROR_MESSAGE);
                                Game.menuState = MenuState.MAINMENU;
                            }
                        }
                    }
                    //Exit game
                    if (renderCenterText(g, "Exit game", 173, true)) {
                        game.save();
                        System.exit(1);
                    }
                    break;

                case SINGLEPLAYER:
                    //Load game
                    if (renderCenterText(g, "Load game", 128, true)) {
                        int returnValue = fc.showOpenDialog(game);
                        if (returnValue == JFileChooser.APPROVE_OPTION) {
                            File tmpFile = fc.getSelectedFile();
                            game.loadSaveFile(tmpFile);
                            Game.menuState = MenuState.NONE;
                            Game.playing = true;
                        }
                    }
                    //New game
                    if (renderCenterText(g, "New game", 150, true)) {
                        String tmpName = JOptionPane.showInputDialog(game, "Please give your save a name.");
                        if (tmpName != null) {
                            if (!tmpName.isEmpty()) {
                                game.newGame(tmpName);
                                Game.menuState = MenuState.NONE;
                                Game.playing = true;
                            } else {
                                Game.menuState = MenuState.SINGLEPLAYER;
                            }

                        } else {
                            Game.menuState = MenuState.SINGLEPLAYER;
                        }

                    }
                    if (renderCenterText(g, "Back", 173, true)) {
                        Game.menuState = MenuState.MAINMENU;
                    }
                    break;
                case PAUSED:
                    //Continue
                    if (renderCenterText(g, "Continue", 128, true)) {
                        Game.menuState = MenuState.NONE;
                    }
//Save game
                    if (renderCenterText(g, "Save game", 150, true)) {
                        game.save();
                    }
//Exit
                    if (renderCenterText(g, "Exit game", 173, true)) {
                        if (Game.isConnectedToServer) {
                            //Close the server
                            Game.frame.dispatchEvent(new WindowEvent(Game.frame, WindowEvent.WINDOW_CLOSING));
                        }
                        game.save();
                        System.exit(1);
                    }
                    break;
                default:
                    break;
            }
        }

    }

    public boolean renderCenterText(Graphics g, String text, int y, boolean hover) {

        FontMetrics fm = g.getFontMetrics();
        int x = ((Game.WINDOW_WIDTH * Game.SCALE - fm.stringWidth(text)) / 2);

        if (hover && ((fm.getStringBounds(text, g).getBounds().getX() + x) < MouseHandler.x) && (fm.getStringBounds(text, g).getBounds().getX() + x + fm.stringWidth(text)) > MouseHandler.x && (fm.getStringBounds(text, g).getBounds().getY() + y) < MouseHandler.y && (fm.getStringBounds(text, g).getBounds().getY() + y + fm.getHeight()) > MouseHandler.y) {
            //We know that the mouse is inside the button area.
            if (MouseHandler.mouseClicked && hover) {
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
