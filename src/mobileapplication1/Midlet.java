/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobileapplication1;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.midlet.*;

/**
 * @author ric
 */
public class Midlet extends MIDlet {

    private final Form form;
    private Command quit;

    public Midlet() {
        form = new Form("Hello World");
        form.append("Hello Micro World");
        quit = new Command("Quit", Command.EXIT, 1);
        Command lala = new Command("Flee", Command.BACK, 2);
//        form.addCommand(lala);
        form.addCommand(quit);
        form.addCommand(lala);
        form.setCommandListener(new CommandListener() {

            public void commandAction(Command c, Displayable d) {
                try {
                    if (c == quit) {
                        destroyApp(true);
                        notifyDestroyed();
                    }
                } catch (Exception e) {

                }
            }
        });
    }

    public void startApp() {
        Display.getDisplay(this).setCurrent(form);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
}
