/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.game;

import ahuotala.entities.InteractableNpc;
import ahuotala.entities.NpcTicker;
import ahuotala.entities.Player;
import ahuotala.graphics.animation.Animation;
import ahuotala.graphics.animation.AnimationTicker;
import ahuotala.graphics.SpriteSheet;
import ahuotala.map.Map;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.*;
import javax.swing.JFrame;

/**
 *
 * @author Aleksi Huotala
 */
public class Game extends Canvas implements Runnable, Tickable {
    
    private static final long serialVersionUID = 1L;
    public static boolean DEBUG = false;
    public static boolean DEBUG_PLAYER = false;
    //Window width
    public static final int WINDOW_WIDTH = 1600;
    //Window height
    public static final int WINDOW_HEIGHT = WINDOW_WIDTH / 16 * 9;
    //Tile scale
    public static final int SCALE = 1;
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
    public double tickrate = 60D;
    private final BufferedImage image;
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
    public static final AnimationTicker ANIMATIONTICKER = new AnimationTicker();
    //NPC ticker
    public static final NpcTicker NPCTICKER = new NpcTicker();

    //Animations
    private final Animation playerWalkingUp;
    private final Animation playerWalkingDown;
    private final Animation playerWalkingLeft;
    private final Animation playerWalkingRight;
    private final Animation playerSwimmingUp;
    private final Animation playerSwimmingDown;
    private final Animation playerSwimmingLeft;
    private final Animation playerSwimmingRight;
    private final Animation playerLowHealth;

    //Save
    private SaveGame save;

    //Map
    Map map = new Map("map2");
    //Input handler
    private final PlayerInputHandler inputHandler = new PlayerInputHandler(player, map);

    /**
     * Constructor
     */
    public Game() {
        this.image = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);
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
        spriteSheet.getSprite("player_left", 32, 64, 16);
        //Player picture (right)
        spriteSheet.getSprite("player_right", 32, 48, 16);
        //Swimming:
        //Player picture (up)
        spriteSheet.getSprite("player_swimming_up", 80, 32, 16);
        //Player picture (down)
        spriteSheet.getSprite("player_swimming_down", 80, 16, 16);
        //Player picture (left)
        spriteSheet.getSprite("player_swimming_left", 80, 64, 16);
        //Player picture (right)
        spriteSheet.getSprite("player_swimming_right", 80, 48, 16);
        //Full heart
        spriteSheet.getSprite("full_heart", 112, 96, 32);
        //Half heart
        spriteSheet.getSprite("half_a_heart", 144, 96, 32);

        //Animations
        playerWalkingUp = new Animation("PlayerWalkingUp", spriteSheet, 15);
        playerWalkingDown = new Animation("PlayerWalkingDown", spriteSheet, 15);
        playerWalkingLeft = new Animation("PlayerWalkingLeft", spriteSheet, 15);
        playerWalkingRight = new Animation("PlayerWalkingRight", spriteSheet, 15);
        playerSwimmingUp = new Animation("PlayerSwimmingUp", spriteSheet, 30);
        playerSwimmingDown = new Animation("PlayerSwimmingDown", spriteSheet, 30);
        playerSwimmingLeft = new Animation("PlayerSwimmingLeft", spriteSheet, 30);
        playerSwimmingRight = new Animation("PlayerSwimmingRight", spriteSheet, 30);
        playerLowHealth = new Animation("PlayerLowHealth", spriteSheet, 40);
        //Register animations to be tickable
        ANIMATIONTICKER.register(playerWalkingUp);
        ANIMATIONTICKER.register(playerWalkingDown);
        ANIMATIONTICKER.register(playerWalkingLeft);
        ANIMATIONTICKER.register(playerWalkingRight);
        ANIMATIONTICKER.register(playerSwimmingUp);
        ANIMATIONTICKER.register(playerSwimmingDown);
        ANIMATIONTICKER.register(playerSwimmingLeft);
        ANIMATIONTICKER.register(playerSwimmingRight);
        ANIMATIONTICKER.register(playerLowHealth);

        //Initialize our JFrame
        frame = new JFrame(NAME);
        frame.addKeyListener(inputHandler);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
//                save.saveState(player.getX(), player.getY(), player.getHealth());
            }
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.requestFocusInWindow();
    }
    
    public void init() {
        /**
         * TEMPORARY
         */
//        //Savegame object
//        save = new SaveGame("save.sav");
//        //Set player x,y and health
//        player.setX(save.getX());
//        player.setY(save.getY());
//        player.setHealth(save.getHealth());
        player.setX(100);
        player.setY(100);
        player.setHealth(120);
        //Set test NPC x and y
        npc.setX(244);
        npc.setY(270);
        npc.setInteractionRadiusX(16);
        npc.setInteractionRadiusY(16);
        //Register the new NPC to be tickable
        NPCTICKER.register(npc);
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
        ANIMATIONTICKER.tick();
        inputHandler.tick();
        NPCTICKER.tick();
        player.tick();
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
        map.renderMap(g, player);

        //Other objects
        map.renderObject(g, -100, -100, "house");
        map.renderObject(g, 4, -100, "house");

        //NPCs here
        //Draw npc
        if (npc.isWalking()) {
            switch (npc.getDirection()) {
                case UP:
                    playerWalkingUp.nextFrame(g, npc.getX() + player.getOffsetX(), npc.getY() + player.getOffsetY());
                    break;
                case DOWN:
                    playerWalkingDown.nextFrame(g, npc.getX() + player.getOffsetX(), npc.getY() + player.getOffsetY());
                    break;
                case LEFT:
                    playerWalkingLeft.nextFrame(g, npc.getX() + player.getOffsetX(), npc.getY() + player.getOffsetY());
                    break;
                case RIGHT:
                    playerWalkingRight.nextFrame(g, npc.getX() + player.getOffsetX(), npc.getY() + player.getOffsetY());
                    break;
                default:
                    break;
            }
        } else {
            switch (npc.getDirection()) {
                case UP:
                    spriteSheet.paint(g, "player_up", npc.getX() + player.getOffsetX(), npc.getY() + player.getOffsetY());
                    break;
                case DOWN:
                    spriteSheet.paint(g, "player_down", npc.getX() + player.getOffsetX(), npc.getY() + player.getOffsetY());
                    break;
                case LEFT:
                    spriteSheet.paint(g, "player_left", npc.getX() + player.getOffsetX(), npc.getY() + player.getOffsetY());
                    break;
                case RIGHT:
                    spriteSheet.paint(g, "player_right", npc.getX() + player.getOffsetX(), npc.getY() + player.getOffsetY());
                    break;
                default:
                    break;
            }
        }
        
        if (Game.DEBUG) {
            //Movement boundaries
            npc.drawBoundaries(g, player.getOffsetX(), player.getOffsetY());
            //For debug; interaction boundaries
            npc.drawInteractionBoundaries(g, player.getOffsetX(), player.getOffsetY());
        }
        //If we are within interaction distance
        if (npc.isWithinInteractionDistance(player)) {
            g.drawString("Press E to talk with \"" + npc.getName() + "\"", 40, WINDOW_HEIGHT - 32);
        }

        //Player x and y
        int playerX = player.getRealX();
        int playerY = player.getRealY();

        //Player walking animation
        switch (player.getDirection()) {
            case DOWN:
                if (player.isWalking()) {
                    if (player.isSwimming()) {
                        playerSwimmingDown.nextFrame(g, playerX, playerY);
                    } else {
                        playerWalkingDown.nextFrame(g, playerX, playerY);
                    }
                } else if (player.isSwimming()) {
                    spriteSheet.paint(g, "player_swimming_down", playerX, playerY);
                } else {
                    spriteSheet.paint(g, "player_down", playerX, playerY);
                }
                break;
            case UP:
                if (player.isWalking()) {
                    if (player.isSwimming()) {
                        playerSwimmingUp.nextFrame(g, playerX, playerY);
                    } else {
                        playerWalkingUp.nextFrame(g, playerX, playerY);
                    }
                } else if (player.isSwimming()) {
                    spriteSheet.paint(g, "player_swimming_up", playerX, playerY);
                } else {
                    spriteSheet.paint(g, "player_up", playerX, playerY);
                }
                break;
            case LEFT:
                if (player.isWalking()) {
                    if (player.isSwimming()) {
                        playerSwimmingLeft.nextFrame(g, playerX, playerY);
                    } else {
                        playerWalkingLeft.nextFrame(g, playerX, playerY);
                    }
                } else if (player.isSwimming()) {
                    spriteSheet.paint(g, "player_swimming_left", playerX, playerY);
                } else {
                    spriteSheet.paint(g, "player_left", playerX, playerY);
                }
                break;
            case RIGHT:
                if (player.isWalking()) {
                    if (player.isSwimming()) {
                        playerSwimmingRight.nextFrame(g, playerX, playerY);
                    } else {
                        playerWalkingRight.nextFrame(g, playerX, playerY);
                    }
                } else if (player.isSwimming()) {
                    spriteSheet.paint(g, "player_swimming_right", playerX, playerY);
                } else {
                    spriteSheet.paint(g, "player_right", playerX, playerY);
                }
                break;
            default:
                break;
        }

        //X & Y coords
        g.setColor(Color.white);
        if (DEBUG_PLAYER) {
            g.drawString("x " + player.getX(), 1, 15);
            g.drawString("y " + player.getY(), 1, 31);
            g.drawString("z " + player.getZ(), 1, 47);
            g.drawString("tileCount " + map.getRenderedTileCount(), 1, 63);
        }
//        fontHandler.drawText(g, "x " + player.getRealX(), 5, 5);
//        fontHandler.drawText(g, "y " + player.getRealY(), 5, 21);

        //Player health system
        int playerFullHearts = (int) Math.floor(player.getHealth() / 20);
        int playerHalfHearts = (int) Math.floor((player.getHealth() - playerFullHearts * 20) / 10);
        int heartX = CENTERX - 256;
        int heartY = 5;
        if (playerFullHearts == 0 && playerHalfHearts == 0 && player.getHealth() > 0) {
            playerLowHealth.nextFrame(g, heartX, heartY);
        } else if (player.getHealth() == 0) {
            g.setColor(Color.red);
            //Black background
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            g.drawString("YOU DIED", CENTERX - 48, CENTERY);
        } else {
            for (int hearts = 0; hearts < playerFullHearts; hearts++) {
                spriteSheet.paint(g, "full_heart", heartX, heartY);
                heartX += 26;
            }
            if (playerHalfHearts > 0) {
                spriteSheet.paint(g, "half_a_heart", heartX, heartY);
            }
        }

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
