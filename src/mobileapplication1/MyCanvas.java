/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobileapplication1;

import java.io.IOException;
import java.util.Random;
import javax.microedition.lcdui.*;

/**
 * @author ric
 */
public class MyCanvas extends Canvas implements Runnable {

    private int _nextState;
    private static final int FPS = 60;
    private int _state;
    private boolean _exit = false;
    private boolean s_stateEnterNext = false;
    private boolean s_stateExitCurrent = false;
    private Graphics _g;
    private int _counter;
    private Image mLogo;

    /**
     * constructor
     */
    private MyCanvas() {
        super();
        stateSwitch(Constants.STATE_LOGO);
    }

    public static MyCanvas getInstance() {
        return CanvasHolder.INSTANCE;
    }
    private int x, y;
    private int dx, dy;
    private long startTime, endTime;

    /**
     * paint
     */
    public void paint(Graphics g) {
        _g = g;
        _counter++;
        StateMachine_sendMessage(Constants.MESSAGE_PAINT);
    }

    private void draw(Graphics g) {
        g.setColor(0);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(0x00ff00ff);
        g.fillRect(x, y, 40, 40);
    }

    public void StateMachine_Update() {
        if (s_stateExitCurrent) {
            StateMachine_sendMessage(Constants.MESSAGE_DESTROY);
            s_stateExitCurrent = false;
            s_stateEnterNext = true;
            _state = _nextState;
            _nextState = -1;
        }

        if (s_stateEnterNext) {
            s_stateEnterNext = false;
            _counter = 0;
            StateMachine_sendMessage(Constants.MESSAGE_INIT);
        }
        StateMachine_sendMessage(Constants.MESSAGE_UPDATE);
    }

    public void run() {
        while (!_exit) {
            startTime = System.currentTimeMillis();

            StateMachine_Update();

            endTime = System.currentTimeMillis();
            long delta = (1000 / FPS) - (endTime - startTime);
            //System.out.println(delta);
            if (delta < 10) {
                delta = 10;
            }
            try {
                Thread.sleep(delta);
            } catch (InterruptedException ex) {
            }
            repaint();
        }
    }

    protected void stateSwitch(int nextState) {
        if (nextState == -1) {
            return;
        }
        _nextState = nextState;
        s_stateExitCurrent = true;
    }

    public void StateMachine_sendMessage(int msg) {
        switch (_state) {
            case Constants.STATE_LOGO:
                updateLogo(msg);
                break;
            case Constants.STATE_GAMEPLAY:
                updateGameplay(msg);
                break;
        }
    }

    private void updateGameplay(int msg) {
        switch (msg) {
            case Constants.MESSAGE_UPDATE: {
                if (x > getWidth() - 40 || x < 0) {
                    dx = -dx;
                }
                if (y > getHeight() - 40 || y < 0) {
                    dy = -dy;
                }
                x += dx;
                y += dy;
            }
            break;
            case Constants.MESSAGE_PAINT:
                draw(_g);
                break;
            case Constants.MESSAGE_INIT:
                x = y = 100;
                dx = dy = 6;
            default:
                break;
        }
    }

    private void updateLogo(int message) {
        switch (message) {
            case Constants.MESSAGE_PAINT: {
                paintLogo(_g);
            }
            break;
            case Constants.MESSAGE_UPDATE:
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                stateSwitch(Constants.STATE_GAMEPLAY);
                break;
            case Constants.MESSAGE_INIT:
            case Constants.MESSAGE_DESTROY:
                break;
        }
    }

    private void paintLogo(Graphics g) {
        g.drawImage(mLogo, 0, 0, 0);
    }

    void loadResource() {
        try {
            mLogo = Image.createImage("/hotot.PNG");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private static final class CanvasHolder {

        private static final MyCanvas INSTANCE = new MyCanvas();
    }
}
