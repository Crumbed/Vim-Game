package main;

import java.io.IOException;

import utils.KeyInput;
import utils.RowPrinter;
import gfx.Screen;



public class Main {

    public static Thread movement = new Thread(new Movement());
    public static RowPrinter rp;
    public static Screen screen;
    
    public static int height = 35;

    public static void main(String[] args) {
        rp = new RowPrinter(height);
        screen = new Screen(height-5, height-5);
        
        movement.start();
        
    }

    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}



class Movement implements Runnable {
    
    private boolean detect = true;
    
    public void run() {
        KeyInput.startCharInput();
        try {
            while(detect) {
                if (System.in.available() == 0 ) continue;
                char c = (char) System.in.read();
                switch(c) {
                    case 'q':
                        System.exit(1);
                    case 'j':
                        if (Main.screen.playerPos[1] == 1) break;
                        Main.screen.down();
                        break;
                    case 'k':
                        if (Main.screen.playerPos[1] == Main.height-5) break;
                        Main.screen.up();
                        break;
                    case 'h':
                        if (Main.screen.playerPos[0] == Main.height-5) break;
                        Main.screen.left();
                        break;
                    case 'l':
                        if (Main.screen.playerPos[0] == 1) break;
                        Main.screen.right();
                        break;

                }
            }
        } catch (IOException e) {
            Main.rp.writeRow(39, e.toString());
        }
    }

    public void stop() {
        KeyInput.stopCharInput();
        detect = false;
    }
}
