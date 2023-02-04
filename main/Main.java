package main;

import java.io.IOException;
import java.util.ArrayList;

import main.*;
import utils.*;
import gui.*;


public class Main {
    public static Settings settings = new Settings();
    public static int height = 35;
    public static RowPrinter rp = new RowPrinter(height);

    public static ControlThread input = new ControlThread(new Movement());
    
    public static Menu currMenu = new Menu("Paused", new Option[] {new Option(1, "Settings"), new Option(0, "Quit")});
    public static ArrayList<String> lastMenus = new ArrayList<>();

    public static Screen screen = new Screen(height-5, height-5);

    public static void main(String[] args) {
        init();
        input.start();
    }

    private static void init() {

        new Menu("Settings", new Option[] {
            new Option(2, "Back"),
            new Settings.SettingToggle("Dev mode", Settings.DEVMODE),
            new Settings.SettingToggle("Show action", Settings.SHOWACTION)
        });

    }




    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


    public static class Movement implements ControlThread.BetterRunnable {

        public boolean detect = true;
        public Action currAction = new Action();

        public void run() {
            screen.focus = 1;

            for (int i = 0; i < currMenu.options.length; i++) {
                currMenu.rows[i+4] = currMenu.makeOptionRow(false, i);
            }

            screen.printRows();


            currAction = new Action();

            KeyInput.startCharInput();
            try {
                while(detect) {
                    if (System.in.available() == 0 ) continue;
                    char c = (char) System.in.read();
                    switch(c) {
                        case ':': // esc
                            Main.screen.swapFocus();
                            break;
                        case 'j':
                            if (Main.screen.playerPos[1] == Main.height-6) break;
                            currAction.down();
                            currAction = new Action();
                            break;
                        case 'k':
                            if (Main.screen.playerPos[1] == 0) break;
                            currAction.up();
                            currAction = new Action();
                            break;
                        case 'h':
                            if (Main.screen.playerPos[0] == 0) break;
                            currAction.left();
                            currAction = new Action();
                            break;
                        case 'l':
                            if (Main.screen.playerPos[0] == Main.height-6) break;
                            currAction.right();
                            currAction = new Action();
                            break;

                    }
                    if (Character.isDigit(c)) {
                        currAction.addCount(c - '0');
                    }
                }
                KeyInput.stopCharInput();
            } catch (IOException e) {
                Main.rp.writeRow(39, e.toString());
            }
        }

        public void stop() {
            detect = false;
        }

    }
}



