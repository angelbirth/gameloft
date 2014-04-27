/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobileapplication1;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.*;

/**
 * @author ric
 */
public class PaintBox extends MIDlet {
    
    private final Command quit = new Command("Quit", Command.EXIT, 2);
    Canvas canvas ;
    public PaintBox() {
        canvas = MyCanvas.getInstance();
        canvas.addCommand(quit);
        canvas.setCommandListener(new CommandListener() {
            
            public void commandAction(Command c, Displayable d) {
                if (c == quit) {
                    destroyApp(true);
                    notifyDestroyed();
                }
            }
        });
    }
    
    public void startApp() {
        Display.getDisplay(this).setCurrent(canvas);
        Thread t=new Thread((Runnable)canvas);
        t.start();
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
    
}
