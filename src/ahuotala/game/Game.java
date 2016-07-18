/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.game;


import ahuotala.entities.Direction;
import ahuotala.entities.Player;
import ahuotala.graphics.Animation;
import ahuotala.graphics.AnimationTicker;
import ahuotala.graphics.FontHandler;
import ahuotala.graphics.SpriteSheet;
import java.awt.*;
import java.awt.image.*;
import javax.swing.JFrame;

/**
 *
 * @author Aleksi Huotala
 */
public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;

    public static final int WINDOW_WIDTH = 1280;
    //16:9 aspect ratio
    public static final int WINDOW_HEIGHT = WINDOW_WIDTH / 16 * 9;
    public static final int SCALE = 2;
    public static final String NAME = "Untitled Game";

    private final JFrame frame;

    public boolean running = false;
    public int tickCount = 0;
    public double tickrate = 120D;
    //Pohja
    private final BufferedImage image = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);
    //Sprite sheet
    private final SpriteSheet spriteSheet = new SpriteSheet("spriteSheet.png", SCALE);
    //Pixel data
    private final int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    //Font
    private final FontHandler fontHandler = new FontHandler("spriteSheet.png");
    //Player
    private final Player player = new Player("Aleksi", WINDOW_WIDTH - 22, WINDOW_HEIGHT - 22);
    //Animaations
    private final AnimationTicker animationTicker = new AnimationTicker();
    private final Animation water;
    private final Animation lava;
    private final Animation playerWalkingUp;
    private final Animation playerWalkingDown;
    private final Animation playerWalkingLeft;
    private final Animation playerWalkingRight;

    //Input handler
    private final PlayerInputHandler inputHandler = new PlayerInputHandler(player);

    public Game() {

        this.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setMaximumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        /**
         * Load sprites and animations here
         */
        //Pelaajan kuva (ylös), width & height = 16. Tälle indeksiksi 0
        spriteSheet.getSprite(32, 32, 16);
        //Pelaajan kuva (alas), width & height = 16. Tälle indeksiksi 1
        spriteSheet.getSprite(32, 16, 16);
        //Pelaajan kuva (vasen), width & height = 16. Tälle indeksiksi 2
        spriteSheet.getSprite(32, 48, 16);
        //Pelaajan kuva (oikea), width & height = 16. Tälle indeksiksi 3
        spriteSheet.getSprite(32, 64, 16);

        //Animations
        water = new Animation("Water", spriteSheet, 30, 1);
        lava = new Animation("Lava", spriteSheet, 120, 1);
        playerWalkingUp = new Animation("PlayerWalkingUp", spriteSheet, 15, SCALE);
        playerWalkingDown = new Animation("PlayerWalkingDown", spriteSheet, 15, SCALE);
        playerWalkingLeft = new Animation("PlayerWalkingLeft", spriteSheet, 15, SCALE);
        playerWalkingRight = new Animation("PlayerWalkingRight", spriteSheet, 15, SCALE);

        //Register animations to be tickable
        animationTicker.register(water);
        animationTicker.register(lava);
        animationTicker.register(playerWalkingUp);
        animationTicker.register(playerWalkingDown);
        animationTicker.register(playerWalkingLeft);
        animationTicker.register(playerWalkingRight);

        //Set player x and y
        player.setX(0);
        player.setY(0);

        frame = new JFrame(NAME);
        frame.addKeyListener(inputHandler);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
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

    public void tick() {
        tickCount++;
        animationTicker.tick();
        if (inputHandler.up || inputHandler.down || inputHandler.left || inputHandler.right) {
            if (inputHandler.up) {
                player.setDirection(Direction.UP);
                player.setWalkingState(true);
                player.goUp();
            }
            if (inputHandler.down) {
                player.setDirection(Direction.DOWN);
                player.setWalkingState(true);
                player.goDown();
            }
            if (inputHandler.left) {
                player.setDirection(Direction.LEFT);
                player.setWalkingState(true);
                player.goLeft();
            }
            if (inputHandler.right) {
                player.setDirection(Direction.RIGHT);
                player.setWalkingState(true);
                player.goRight();
            }
        } else {
            player.setWalkingState(false);
        }

    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            //Triple buffering
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

        for (int i = 10; i <= 30; i++) {
            for (int a = 10; a <= 30; a++) {
                water.nextFrame(g, i * 16, a * 16);
            }
        }
        for (int i = 31; i <= 40; i++) {
            for (int a = 10; a <= 30; a++) {
                lava.nextFrame(g, i * 16, a * 16);
            }
        }
        //X&Y
        fontHandler.drawText(g, "x " + player.getX(), 20, 20);
        fontHandler.drawText(g, "y " + player.getY(), 20, 36);
        //Work in progress
        fontHandler.drawText(g, "Work in progress", 20, 56);
        g.setColor(Color.red);
        g.fill3DRect(player.getX(), player.getY(), 32, 32, false);
        switch (player.getDirection()) {
            case DOWN:
                if (player.getWalkingState() == true) {
                    playerWalkingDown.nextFrame(g, player.getX(), player.getY());
                } else {
                    spriteSheet.paint(g, 1, player.getX(), player.getY());
                }
                break;
            case UP:
                if (player.getWalkingState() == true) {
                    playerWalkingUp.nextFrame(g, player.getX(), player.getY());
                } else {
                    spriteSheet.paint(g, 0, player.getX(), player.getY());
                }
                break;
            case LEFT:
                if (player.getWalkingState() == true) {
                    playerWalkingLeft.nextFrame(g, player.getX(), player.getY());
                } else {
                    spriteSheet.paint(g, 3, player.getX(), player.getY());
                }
                break;
            default:
                if (player.getWalkingState() == true) {
                    playerWalkingRight.nextFrame(g, player.getX(), player.getY());
                } else {
                    spriteSheet.paint(g, 2, player.getX(), player.getY());
                }
                break;
        }
        g.dispose();
        bs.show();

    }

    public static void main(String[] args) {
        new Game().start();
    }
}
