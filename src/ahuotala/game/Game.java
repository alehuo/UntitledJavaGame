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
    public double tickrate = 60D;
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
        spriteSheet.getSprite("full_heart", 112, 16, 16);
        //Half heart
        spriteSheet.getSprite("half_a_heart", 128, 16, 16);

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

        //Set player x and y
        player.setX(96);
        player.setY(-32);
        //Set test NPC x and y
        npc.setX(244);
        npc.setY(270);
        npc.setInteractionRadiusX(16);
        npc.setInteractionRadiusY(16);
        //Register the new NPC to be tickable
        NPCTICKER.register(npc);

        //Initialize our JFrame
        frame = new JFrame(NAME);
        frame.addKeyListener(inputHandler);
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
        spriteSheet.paint(g, "player_down", npc.getX() + player.getOffsetX(), npc.getY() + player.getOffsetY());
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
                    spriteSheet.paint(g, "player_swimming_right", playerX, playerY);
                } else {
                    spriteSheet.paint(g, "player_right", playerX, playerY);
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
                    spriteSheet.paint(g, "player_swimming_left", playerX, playerY);
                } else {
                    spriteSheet.paint(g, "player_left", playerX, playerY);
                }
                break;
            default:
                break;
        }

        //X & Y coords
        g.setColor(Color.white);
        g.drawString("x " + player.getX(), 1, 15);
        g.drawString("y " + player.getY(), 1, 31);
//        fontHandler.drawText(g, "x " + player.getRealX(), 5, 5);
//        fontHandler.drawText(g, "y " + player.getRealY(), 5, 21);
        //Player health
        int playerFullHearts = (int) Math.floor(player.getHealth() / 20);
        int playerHalfHearts = (int) Math.floor((player.getHealth() - playerFullHearts * 20) / 10);
        System.out.println(playerHalfHearts);
        int heartX = CENTERX;
        int heartY = 5;
        if (playerFullHearts == 0 && playerHalfHearts == 0 && player.getHealth() > 0) {
            playerLowHealth.nextFrame(g, heartX, heartY);
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
