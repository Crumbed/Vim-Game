package main;

import java.io.IOException;

import utils.KeyInput;
import utils.RowPrinter;




public class Main {

    static Thread movement = new Thread(new Movement());
    static RowPrinter rp = new RowPrinter(40);

    public static void main(String[] args){
        movement.start();
        
        rp.writeRow(39, "running");
        
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
                        Main.rp.writeRow(39, "DOWN");
                        break;
                    case 'k':
                        Main.rp.writeRow(39, "UP");
                        break;
                    case 'h':
                        Main.rp.writeRow(39, "LEFT");
                        break;
                    case 'l':
                        Main.rp.writeRow(39, "RIGHT");
                        break;

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        KeyInput.stopCharInput();
        detect = false;
    }
}
