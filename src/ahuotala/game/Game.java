/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.game;

import ahuotala.entities.InteractableNpc;
import ahuotala.entities.NpcTicker;
import ahuotala.entities.Player;
import ahuotala.graphics.Animation;
import ahuotala.graphics.AnimationTicker;
import ahuotala.graphics.SpriteSheet;
import ahuotala.map.Map;
import java.awt.*;
import java.awt.image.*;
import javax.swing.JFrame;

/**
 *
 * @author Aleksi Huotala
 */
public class Game extends Canvas implements Runnable, Tickable {

    private static final long serialVersionUID = 1L;
    public static final boolean DEBUG = false;
    //Window width
    public static final int WINDOW_WIDTH = 1280;
    //Window height
    public static final int WINDOW_HEIGHT = WINDOW_WIDTH / 16 * 9;
    //Tile scale
    public static final int SCALE = 2;
    //Font scale
    public static final double FONTSCALE = 0.7;
    //Center x coordinate
    public static final int CENTERX = (int) Math.floor(WINDOW_WIDTH / 2);
    //Center y coordinate
    public static final int CENTERY = (int) Math.floor(WINDOW_HEIGHT / 2);
    //Game name
    public static final String NAME = "Untitled Game";

    //JFrame object
    private final JFrame frame;

    //Game state
    public boolean running = false;
    //Tick count
    public int tickCount = 0;
    //Tickrate; amount of game updates per second
    public double tickrate = 120D;
    //Image
    private final BufferedImage image = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);
    //Sprite sheet
    public static SpriteSheet spriteSheet = new SpriteSheet("spriteSheet.png");
    //Font
    private Font currentFont;
    /**
     * ####################### Players and NPC's here #######################
     */
    //Player
    private final Player player = new Player("Aleksi");
    //NPC test
    private final InteractableNpc npc = new InteractableNpc("TestNPC");
    /**
     * #######################
     */
    //Animation ticker
    public static final AnimationTicker animationTicker = new AnimationTicker();
    //NPC ticker
    public static final NpcTicker npcTicker = new NpcTicker();

    //Animations
    private final Animation playerWalkingUp;
    private final Animation playerWalkingDown;
    private final Animation playerWalkingLeft;
    private final Animation playerWalkingRight;
    //Map
    Map map = new Map("map1");
    //Input handler
    private final PlayerInputHandler inputHandler = new PlayerInputHandler(player, map);

    /**
     * Constructor
     */
    public Game() {
        this.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setMaximumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        /**
         * Load sprites and animations here
         */
        //Player picture (up)
        spriteSheet.getSprite("player_up", 32, 32, 16);
        //Player picture (down)
        spriteSheet.getSprite("player_down", 32, 16, 16);
        //Player picture (left)
        spriteSheet.getSprite("player_left", 32, 48, 16);
        //Player picture (right)
        spriteSheet.getSprite("player_right", 32, 64, 16);

        //Animations
        playerWalkingUp = new Animation("PlayerWalkingUp", spriteSheet, 15);
        playerWalkingDown = new Animation("PlayerWalkingDown", spriteSheet, 15);
        playerWalkingLeft = new Animation("PlayerWalkingLeft", spriteSheet, 15);
        playerWalkingRight = new Animation("PlayerWalkingRight", spriteSheet, 15);

        //Register animations to be tickable
        animationTicker.register(playerWalkingUp);
        animationTicker.register(playerWalkingDown);
        animationTicker.register(playerWalkingLeft);
        animationTicker.register(playerWalkingRight);

        //Set player x and y
        player.setX(CENTERX);
        player.setY(CENTERY);
        //Set test NPC x and y
        npc.setX(160);
        npc.setY(300);
        npc.setInteractionRadiusX(16);
        npc.setInteractionRadiusY(16);
        //Register the new NPC to be tickable
        npcTicker.register(npc);

        //Initialize our JFrame
        frame = new JFrame(NAME);
        frame.addKeyListener(inputHandler);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void init() {

    }

    private synchronized void start() {
        running = true;
        new Thread(this).start();
    }

    private synchronized void stop() {
        running = false;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / tickrate;

        int frames = 0;
        int ticks = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        init();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            //Use false to limit fps to the number of ticks
            boolean shouldRender = false;
            while (delta >= 1) {
                //Tick
                ticks++;
                tick();
                delta -= 1;
                shouldRender = true;
            }
            if (shouldRender) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            if (shouldRender) {
                //Render a new frame
                frames++;
                render();
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                frame.setTitle(NAME + " (" + frames + " frames, " + ticks + " ticks)");
                frames = 0;
                ticks = 0;
            }
        }
    }

    @Override
    public void tick() {
        tickCount++;
        animationTicker.tick();
        inputHandler.tick();
        npcTicker.tick();
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            //Triple buffering
            createBufferStrategy(3);
            return;
        }

        //Get draw graphics
        Graphics g = bs.getDrawGraphics();

        //Font
        currentFont = g.getFont();
        g.setFont(new Font(currentFont.getName(), Font.BOLD, 18));

        //Black background
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

        //Map
        map.renderMap(g, player.getOffsetX(), player.getOffsetY(), player.getRealX(), player.getRealY());

        //Other objects
        map.renderObject(g, -100, -100, "house");
        map.renderObject(g, 4, -100, "house");

        //NPCs here
        //Movement boundaries
//        npc.drawBoundaries(g, player.getOffsetX(), player.getOffsetY());
        //For debug; interaction boundaries
//        npc.drawInteractionBoundaries(g, player.getOffsetX(), player.getOffsetY());
        //Draw npc
        spriteSheet.paint(g, "player_down", npc.getX() + player.getOffsetX(), npc.getY() + player.getOffsetY());

        //If we are within interaction distance
        if (npc.isWithinInteractionDistance(player)) {
            g.drawString("Press E to talk with \"" + npc.getName() + "\"", 40, WINDOW_HEIGHT - 32);
        }

        //Player walking animation
        switch (player.getDirection()) {
            case DOWN:
                if (player.getWalkingState() == true) {
                    playerWalkingDown.nextFrame(g, player.getX(), player.getY());
                } else {
                    spriteSheet.paint(g, "player_down", player.getX(), player.getY());
                }
                break;
            case UP:
                if (player.getWalkingState() == true) {
                    playerWalkingUp.nextFrame(g, player.getX(), player.getY());
                } else {
                    spriteSheet.paint(g, "player_up", player.getX(), player.getY());
                }
                break;
            case LEFT:
                if (player.getWalkingState() == true) {
                    playerWalkingLeft.nextFrame(g, player.getX(), player.getY());
                } else {
                    spriteSheet.paint(g, "player_right", player.getX(), player.getY());
                }
                break;
            default:
                if (player.getWalkingState() == true) {
                    playerWalkingRight.nextFrame(g, player.getX(), player.getY());
                } else {
                    spriteSheet.paint(g, "player_left", player.getX(), player.getY());
                }
                break;
        }

        //X & Y coords
        g.setColor(Color.white);
        g.drawString("x " + player.getRealX(), 1, 15);
        g.drawString("y " + player.getRealY(), 1, 31);
//        fontHandler.drawText(g, "x " + player.getRealX(), 5, 5);
//        fontHandler.drawText(g, "y " + player.getRealY(), 5, 21);

        //Empty buffer
        g.dispose();
        //Show frame
        bs.show();
    }

    public static void main(String[] args) {
        //Start the game
        new Game().start();
    }
}
