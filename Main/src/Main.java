import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.image.BufferStrategy;

import java.awt.Image;

//Main game loop with extra funnies
public class Main extends Canvas implements Runnable {

    private static final int WIDTH = 1200;//Widht
    private static final int HEIGHT = 600;//Height
    private Thread gameThread; //Used for starting the whole ordeal
    private boolean running = false; //checks if the window is running

    public Main() {
        Dimension size = new Dimension(WIDTH, HEIGHT);
        setPreferredSize(size);

        //load background and spritesheet stuff
        setFocusable(true);

    }

    public synchronized void start() {
        if (running) return;
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public synchronized void stop() {
        if (!running) return;
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        final double nsPerUpdate = 1000000000.0 / 60.0; // 60 FPS
        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerUpdate;
            lastTime = now;

            while (delta >= 1) {
                delta--;
            }

            render();

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
            }
        }
    }

    private void render() { // You render and put stuff here
        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(3);
            return;
        }



        //Draws the background

        // Renders objects

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Registan Balls");
        Main game = new Main();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        game.start();
    }
}
