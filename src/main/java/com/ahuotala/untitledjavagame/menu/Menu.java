package com.ahuotala.untitledjavagame.menu;

import com.ahuotala.untitledjavagame.Game;
import static com.ahuotala.untitledjavagame.Game.SCALE;
import static com.ahuotala.untitledjavagame.Game.WINDOW_HEIGHT;
import static com.ahuotala.untitledjavagame.Game.WINDOW_WIDTH;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Aleksi Huotala
 */
public class Menu extends JPanel implements ActionListener {

    private final Game game;
    private final JFileChooser fc = new JFileChooser();
    private final JButton spButton;
    private final JButton mpButton;
    private final JButton exitButton;
    private final JButton ngButton;
    private final JButton lgButton;
    private final JButton bButton;
    private final JButton saveButton;
    private final JButton continueButton;

    /**
     *
     * @param game
     */
    public Menu(Game game) {
        this.game = game;
        super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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

        super.add(Box.createRigidArea(new Dimension(0, 5)));
        super.add(spButton);
        super.add(Box.createRigidArea(new Dimension(0, 5)));
        super.add(mpButton);
        super.add(Box.createRigidArea(new Dimension(0, 5)));
        super.add(exitButton);
    }

    @Override
    public void actionPerformed(ActionEvent action) {
        if (action.getSource() == exitButton) {
            //EXIT
            if (game.getMp().isConnected()) {
                //Disconnect from server
                game.getMp().disconnect();
                Game.frame.dispatchEvent(new WindowEvent(Game.frame, WindowEvent.WINDOW_CLOSING));
            }
            System.exit(1);
        } else if (action.getSource() == spButton) {
            loadMenuState(MenuState.SINGLEPLAYER);
            game.setMenuState(MenuState.SINGLEPLAYER);
        } else if (action.getSource() == mpButton) {
            //Connection prompt
            if (!game.getMp().isConnected()) {
                String data = JOptionPane.showInputDialog(game, "Please enter the IP address and the port of the host to connect to\r\nFor example, localhost:9876");
                if (data != null) {
                    String[] tmpData = data.split(":");
                    if (tmpData.length == 2) {
                        try {
                            game.getMp().connect(InetAddress.getByName(tmpData[0].trim()), Integer.parseInt(tmpData[1].trim()));
                            game.setMenuState(MenuState.NONE);
                        } catch (NumberFormatException | UnknownHostException e) {
                            JOptionPane.showMessageDialog(game, "Error connecting to server: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            game.setMenuState(MenuState.MAINMENU);
                        }
                    } else {
                        JOptionPane.showMessageDialog(game, "Error connecting to server: malformed server address", "Error", JOptionPane.ERROR_MESSAGE);
                        game.setMenuState(MenuState.MAINMENU);
                    }
                } else {
                    JOptionPane.showMessageDialog(game, "Error connecting to server: server address can't be null", "Error", JOptionPane.ERROR_MESSAGE);
                    game.setMenuState(MenuState.MAINMENU);
                }
            }
        } else if (action.getSource() == bButton) {
            loadMenuState(MenuState.MAINMENU);
            game.setMenuState(MenuState.MAINMENU);
        } else if (action.getSource() == lgButton) {
            //LOAD GAME
            game.setMenuState(MenuState.SINGLEPLAYER_LOADLEVEL);
            //Load game
            int returnValue = fc.showOpenDialog(game);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File tmpFile = fc.getSelectedFile();
                game.getSp().loadSaveFile(tmpFile);
                game.setMenuState(MenuState.NONE);
                //Load singleplayer
                game.loadSp();
                game.setPlaying(true);
            }
        } else if (action.getSource() == ngButton) {
            //NEW GAME
            String tmpName = JOptionPane.showInputDialog(game, "Please give your save a name.");
            if (tmpName != null) {
                if (!tmpName.isEmpty()) {
                    game.getSp().newGame(tmpName);
                    game.setMenuState(MenuState.NONE);
                    game.setPlaying(true);
                    //Load singleplayer
                    game.loadSp();
                } else {
                    game.setMenuState(MenuState.SINGLEPLAYER);
                }

            } else {
                game.setMenuState(MenuState.SINGLEPLAYER);
            }
        } else if (action.getSource() == saveButton) {
            game.getSp().save();
        } else if (action.getSource() == continueButton) {
            game.loadSp();
        }

        validate();
        super.repaint();
    }

    /**
     *
     * @param m
     */
    public void loadMenuState(MenuState m) {
        if (null != m) {
            switch (m) {
                case SINGLEPLAYER:
                    //SINGLEPLAYER
                    super.removeAll();
                    super.add(Box.createRigidArea(new Dimension(0, 5)));
                    super.add(ngButton);
                    super.add(Box.createRigidArea(new Dimension(0, 5)));
                    super.add(lgButton);
                    super.add(Box.createRigidArea(new Dimension(0, 5)));
                    super.add(bButton);
                    break;
                case MULTIPLAYER:
                    //MULTIPLAYER
                    super.removeAll();
                    super.add(Box.createRigidArea(new Dimension(0, 5)));
                    super.add(bButton);
                    break;
                case MAINMENU:
                    //MAIN MENU
                    super.removeAll();
                    super.add(Box.createRigidArea(new Dimension(0, 5)));
                    super.add(spButton);
                    super.add(Box.createRigidArea(new Dimension(0, 5)));
                    super.add(mpButton);
                    super.add(Box.createRigidArea(new Dimension(0, 5)));
                    super.add(exitButton);
                    break;
                case PAUSED:
                    //PAUSED
                    super.removeAll();
                    super.add(Box.createRigidArea(new Dimension(0, 5)));
                    super.add(continueButton);
                    super.add(Box.createRigidArea(new Dimension(0, 5)));
                    super.add(saveButton);
                    super.add(Box.createRigidArea(new Dimension(0, 5)));
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
