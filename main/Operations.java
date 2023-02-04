package main;

import gui.Menu;
import utils.KeyInput;

public class Operations {

    public static void runTask(int id) {

        switch(id) {
            case 2: // back
                Main.currMenu = Menu.menus.get(Main.lastMenus.get(Main.lastMenus.size()-1));
                Main.lastMenus.remove(Main.lastMenus.size()-1);
                Main.screen.printRows();
                break;
            case 1: // open settings
                Main.lastMenus.add(Main.currMenu.getHeader());
                Main.currMenu = Menu.menus.get("Settings");
                Main.screen.printRows();
                break;
            case 0:
                KeyInput.stopCharInput();
                System.exit(0);
                break;
        }

    }

}
