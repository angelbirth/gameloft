/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobileapplication1;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.GameCanvas;

/**
 * @author ric
 */
public class MyCanvas extends GameCanvas implements Runnable {

    private static final int FPS = 60;

    /**
     * constructor
     */
    private MyCanvas() {
        super(false);
        x = y = 0;
        dx = dy = 6;
    }

    public static Canvas getInstance() {
        return CanvasHolder.INSTANCE;
    }
    private int x, y;
    private int dx, dy;
    private long startTime, endTime;

    /**
     * paint
     */
    public void paint(Graphics g) {

        g.setColor(0);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(0x00ff00ff);
        g.fillRect(x, y, 40, 40);

    }

    /**
     * Called when a key is pressed.
     */
    protected void keyPressed(int keyCode) {
    }

    /**
     * Called when a key is released.
     */
    protected void keyReleased(int keyCode) {
    }

    /**
     * Called when a key is repeated (held down).
     */
    protected void keyRepeated(int keyCode) {
    }

    /**
     * Called when the pointer is dragged.
     */
    protected void pointerDragged(int x, int y) {
    }

    /**
     * Called when the pointer is pressed.
     */
    protected void pointerPressed(int x, int y) {
    }

    /**
     * Called when the pointer is released.
     */
    protected void pointerReleased(int x, int y) {
    }

    public void run() {
        while (true) {
            startTime = System.currentTimeMillis();

            if (x > getWidth() - 40 || x < 0) {
                dx = -dx;
            }
            if (y > getHeight() - 40 || y < 0) {
                dy = -dy;
            }
            x += dx;
            y += dy;
            endTime = System.currentTimeMillis();
            long delta = (1000 / FPS) - (endTime - startTime);
            System.out.println(delta);
            if (delta < 10) {
                delta = 10;
            }
            try {
                Thread.sleep(delta);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            repaint();
        }
    }

    private static final class CanvasHolder {

        private static final MyCanvas INSTANCE = new MyCanvas();
    }
}
