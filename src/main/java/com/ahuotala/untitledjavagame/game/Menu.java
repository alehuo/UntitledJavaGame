package com.ahuotala.untitledjavagame.game;

import static com.ahuotala.untitledjavagame.game.Game.SCALE;
import static com.ahuotala.untitledjavagame.game.Game.WINDOW_HEIGHT;
import static com.ahuotala.untitledjavagame.game.Game.WINDOW_WIDTH;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Aleksi Huotala
 */
public class Menu extends JPanel implements ActionListener {

    //Game object
    private final Game game;
    //File choose
    private final JFileChooser fc = new JFileChooser();
    private final JButton spButton;
    private final JButton mpButton;
    private final JButton exitButton;
    private final JButton ngButton;
    private final JButton lgButton;
    private final JButton bButton;
    private final JButton saveButton;
    private final JButton continueButton;

    public Menu(Game game) {
        this.game = game;
        super.setLayout(new FlowLayout());
        super.setMinimumSize(new Dimension(WINDOW_WIDTH * SCALE, WINDOW_HEIGHT * SCALE));
        super.setMaximumSize(new Dimension(WINDOW_WIDTH * SCALE, WINDOW_HEIGHT * SCALE));
        super.setPreferredSize(new Dimension(WINDOW_WIDTH * SCALE, WINDOW_HEIGHT * SCALE));

        spButton = new JButton("Singleplayer");
        spButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        spButton.setMargin(new Insets(10, 10, 10, 10));

        ngButton = new JButton("New game");
        ngButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        ngButton.setMargin(new Insets(10, 10, 10, 10));

        lgButton = new JButton("Load game");
        lgButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        lgButton.setMargin(new Insets(10, 10, 10, 10));

        bButton = new JButton("Back");
        bButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bButton.setMargin(new Insets(10, 10, 10, 10));

        mpButton = new JButton("Multiplayer");
        mpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mpButton.setMargin(new Insets(10, 10, 10, 10));

        exitButton = new JButton("Exit game");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setMargin(new Insets(10, 10, 10, 10));

        saveButton = new JButton("Save game");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setMargin(new Insets(10, 10, 10, 10));

        continueButton = new JButton("Continue game");
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueButton.setMargin(new Insets(10, 10, 10, 10));

        spButton.addActionListener(this);
        mpButton.addActionListener(this);
        exitButton.addActionListener(this);
        ngButton.addActionListener(this);
        lgButton.addActionListener(this);
        bButton.addActionListener(this);
        saveButton.addActionListener(this);
        continueButton.addActionListener(this);

        super.add(spButton);
        super.add(mpButton);
        super.add(exitButton);
    }

//    public void render(Graphics g) {
//        Font currentFont = g.getFont();
//        g.setFont(new Font(currentFont.getName(), Font.BOLD, (int) Math.floor(36 * FONTSCALE)));
////        g.setColor(Color.white);
//
//        //Title
//        renderCenterText(g, Game.NAME, 32, false);
//
//        g.setFont(new Font(currentFont.getName(), Font.BOLD, (int) Math.floor(25 * FONTSCALE)));
//        if (null != Game.menuState) //Main menu
//        {
//            switch (Game.menuState) {
//                case MAINMENU:
//                    //Singleplayer
//                    if (renderCenterText(g, "Singleplayer", 128, true)) {
////                        System.out.println("Clicked singleplayer");
//                        Game.menuState = MenuState.SINGLEPLAYER;
//                    }   //Multiplayer
//                    if (renderCenterText(g, "Multiplayer", 150, true)) {
////                        System.out.println("Clicked multiplayer");
//                        Game.menuState = MenuState.MULTIPLAYER_CONNECT;
//                        //Connection prompt
//                        if (!Game.isConnectedToServer) {
//                            String data = JOptionPane.showInputDialog(game, "Please enter the IP address and the port of the host to connect to\r\nFor example, localhost:9876");
//                            if (data != null) {
//                                String[] tmpData = data.split(":");
//                                if (tmpData.length == 2) {
//                                    try {
//                                        game.connectToServer(tmpData[0].trim(), Integer.parseInt(tmpData[1].trim()));
//                                        Game.isConnectedToServer = true;
//                                        Game.menuState = MenuState.NONE;
//                                    } catch (NumberFormatException | UnknownHostException | SocketException e) {
//                                        JOptionPane.showMessageDialog(game, "Error connecting to server: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//                                        Game.menuState = MenuState.MAINMENU;
//                                    }
//                                } else {
//                                    JOptionPane.showMessageDialog(game, "Error connecting to server: malformed server address", "Error", JOptionPane.ERROR_MESSAGE);
//                                    Game.menuState = MenuState.MAINMENU;
//                                }
//                            } else {
//                                JOptionPane.showMessageDialog(game, "Error connecting to server: server address can't be null", "Error", JOptionPane.ERROR_MESSAGE);
//                                Game.menuState = MenuState.MAINMENU;
//                            }
//                        }
//                    }
//                    //Exit game
//                    if (renderCenterText(g, "Exit game", 173, true)) {
//                        game.save();
//                        System.exit(1);
//                    }
//                    break;
//
//                case SINGLEPLAYER:
//                    //Load game
//                    if (renderCenterText(g, "Load game", 128, true)) {
//                        int returnValue = fc.showOpenDialog(game);
//                        if (returnValue == JFileChooser.APPROVE_OPTION) {
//                            File tmpFile = fc.getSelectedFile();
//                            game.loadSaveFile(tmpFile);
//                            Game.menuState = MenuState.NONE;
//                            Game.playing = true;
//                        }
//                    }
//                    //New game
//                    if (renderCenterText(g, "New game", 150, true)) {
//                        String tmpName = JOptionPane.showInputDialog(game, "Please give your save a name.");
//                        if (tmpName != null) {
//                            if (!tmpName.isEmpty()) {
//                                game.newGame(tmpName);
//                                Game.menuState = MenuState.NONE;
//                                Game.playing = true;
//                            } else {
//                                Game.menuState = MenuState.SINGLEPLAYER;
//                            }
//
//                        } else {
//                            Game.menuState = MenuState.SINGLEPLAYER;
//                        }
//
//                    }
//                    if (renderCenterText(g, "Back", 173, true)) {
//                        Game.menuState = MenuState.MAINMENU;
//                    }
//                    break;
//                case PAUSED:
//                    //Continue
//                    if (renderCenterText(g, "Continue", 128, true)) {
//                        Game.menuState = MenuState.NONE;
//                    }
////Save game
//                    if (renderCenterText(g, "Save game", 150, true)) {
//                        game.save();
//                    }
////Exit
//                    if (renderCenterText(g, "Exit game", 173, true)) {
//                        if (Game.isConnectedToServer) {
//                            //Close the server
//                            Game.frame.dispatchEvent(new WindowEvent(Game.frame, WindowEvent.WINDOW_CLOSING));
//                        }
//                        game.save();
//                        System.exit(1);
//                    }
//                    break;
//                default:
//                    break;
//            }
//        }
//
//    }
//
//    public boolean renderCenterText(Graphics g, String text, int y, boolean hover) {
//
//        FontMetrics fm = g.getFontMetrics();
//        int x = ((Game.WINDOW_WIDTH * Game.SCALE - fm.stringWidth(text)) / 2);
//
//        if (hover && ((fm.getStringBounds(text, g).getBounds().getX() + x) < MouseHandler.x) && (fm.getStringBounds(text, g).getBounds().getX() + x + fm.stringWidth(text)) > MouseHandler.x && (fm.getStringBounds(text, g).getBounds().getY() + y) < MouseHandler.y && (fm.getStringBounds(text, g).getBounds().getY() + y + fm.getHeight()) > MouseHandler.y) {
//            //We know that the mouse is inside the button area.
//            if (MouseHandler.mouseClicked && hover) {
//                MouseHandler.mouseClicked = false;
//                g.setColor(Color.blue);
//                g.drawString(text, x, y);
//                return true;
//            }
//            g.setColor(Color.blue);
//            g.drawString(text, x, y);
//        } else {
//            g.setColor(Color.white);
//        }
//        g.drawString(text, x, y);
//        return false;
//    }
//
//    public boolean clicked() {
//        return false;
//    }
    @Override
    public void actionPerformed(ActionEvent action) {
        if (action.getSource() == exitButton) {
            //EXIT
            if (game.getMp().isConnected()) {
                //Close the server
                Game.frame.dispatchEvent(new WindowEvent(Game.frame, WindowEvent.WINDOW_CLOSING));
            }
            System.exit(1);
        } else if (action.getSource() == spButton) {
            loadMenuState(MenuState.SINGLEPLAYER);
            Game.menuState = MenuState.SINGLEPLAYER;
        } else if (action.getSource() == mpButton) {
//            loadMenuState(MenuState.MULTIPLAYER);
//            Game.menuState = MenuState.MULTIPLAYER;
            //Connection prompt
            if (!game.getMp().isConnected()) {
                String data = JOptionPane.showInputDialog(game, "Please enter the IP address and the port of the host to connect to\r\nFor example, localhost:9876");
                if (data != null) {
                    String[] tmpData = data.split(":");
                    if (tmpData.length == 2) {
                        try {
                            game.getMp().connect(InetAddress.getByName(tmpData[0].trim()), Integer.parseInt(tmpData[1].trim()));
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
        } else if (action.getSource() == bButton) {
            loadMenuState(MenuState.MAINMENU);
            Game.menuState = MenuState.MAINMENU;
        } else if (action.getSource() == lgButton) {
            //LOAD GAME
            Game.menuState = MenuState.SINGLEPLAYER_LOADLEVEL;
            //Load game
            int returnValue = fc.showOpenDialog(game);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File tmpFile = fc.getSelectedFile();
                game.getSp().loadSaveFile(tmpFile);
                Game.menuState = MenuState.NONE;
                Game.playing = true;
            }
        } else if (action.getSource() == ngButton) {
            //NEW GAME
            String tmpName = JOptionPane.showInputDialog(game, "Please give your save a name.");
            if (tmpName != null) {
                if (!tmpName.isEmpty()) {
                    game.getSp().newGame(tmpName);
                    Game.menuState = MenuState.NONE;
                    Game.playing = true;
                } else {
                    Game.menuState = MenuState.SINGLEPLAYER;
                }

            } else {
                Game.menuState = MenuState.SINGLEPLAYER;
            }
        } else if (action.getSource() == saveButton) {
            game.getSp().save();
        }

        validate();
        super.repaint();
    }

    public void loadMenuState(MenuState m) {
        if (null != m) {
            switch (m) {
                case SINGLEPLAYER:
                    //SINGLEPLAYER
                    super.removeAll();
                    super.add(ngButton);
                    super.add(lgButton);
                    super.add(bButton);
                    break;
                case MULTIPLAYER:
                    //MULTIPLAYER
                    super.removeAll();
                    super.add(bButton);
                    break;
                case MAINMENU:
                    //MAIN MENU
                    super.removeAll();
                    super.add(spButton);
                    super.add(mpButton);
                    super.add(exitButton);
                    break;
                case PAUSED:
                    //PAUSED
                    super.removeAll();
                    super.add(continueButton);
                    super.add(saveButton);
                    super.add(exitButton);
                    break;
                case NONE:
                    //NONE
                    super.removeAll();
                    break;
                default:
                    break;
            }
        }
    }
}
