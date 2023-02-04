package gui;

import java.io.IOException;
import java.util.HashMap;

import main.Main;
import main.Settings.SettingToggle;
import utils.KeyInput;
import utils.ControlThread;

public class Menu {
    public static HashMap<String, Menu> menus = new HashMap<>();
    public String[] rows;
    public Option[] options;
    private String header;

    public Menu(String header, Object[] options) {
        this.header = header;
        this.rows = new String[Main.height];
        for (Object obj : options) if (!(obj instanceof Option)) return;
        this.options = (Option[]) options;

        makeLines();
        menus.put(header, this);
    }

    public void setHeader(String header) {
        this.header = header;
    }
    public String getHeader() {
        return header;
    }
    public String getLine(int line) {
        return rows[line];
    }

    public void focus() {
        Main.input.setRunnable(new MenuControl());
    }



    public String makeOptionRow(boolean highlight, int index) {
        String row = "";

        String option = "      ";

        int length = (option + options[index].getLabel(index)).length();

        if (highlight) option += Main.screen.highlight(options[index].getLabel(index));
        else option += options[index].getLabel(index);

        row = option;
        for (int i = 0; i < (29 - length); i++) row += " ";

        row += "|";

        return row;
    }

    private void makeLines() {
        String row = "";
        String blankRow = "";

        for (int i = 0; i < 29; i++) {
            if (i == (15 - (int)(header.length()/2)-1)) { row += header; i += header.length(); }
            row += " ";
        }
        row += "|";
        rows[0] = row;
        row = "";

        for (int i = 0; i < 29; i++) row += " ";
        row += "|";
        rows[1] = row;
        row = "";
        for (int i = 0; i < 29; i++) row += "-";
        row += "+";
        rows[2] = row;
        for (int i = 0; i < 29; i++) blankRow += " ";
        blankRow += "|";
        rows[3] = blankRow;

        int optionIndex = 0;
        for (int r = 4; r < Main.height-2; r++) {

            if (!(optionIndex < options.length)) { rows[r] = blankRow; continue; }

            row = makeOptionRow(false, optionIndex);
            rows[r] = row;

            optionIndex++;
        }

        row = "";
        for (int i = 0; i < 29; i++) row += "-";
        row += "+";
        rows[Main.height-2] = row;

    }


    public static class MenuControl implements ControlThread.BetterRunnable {

        public boolean detect = true;

        public void run() {

            Main.screen.focus = 2;
            int optionIndex = 0;
            Main.currMenu.rows[optionIndex+4] = Main.currMenu.makeOptionRow(true, optionIndex);
            Main.screen.printRows();

            KeyInput.startCharInput();
            try {
                while(detect) {
                    if (System.in.available() == 0) continue;
                    char c = (char) System.in.read();
                    switch(c) {
                        case 27: // esc
                            Main.screen.swapFocus();
                            break;
                        case 'e': 
                            if (Main.currMenu.options[optionIndex] instanceof SettingToggle) {
                                ((SettingToggle) Main.currMenu.options[optionIndex]).toggle(optionIndex);
                                break;
                            }
                            Main.currMenu.options[optionIndex].run();
                            break;
                        case 'j':
                            if (!(optionIndex < Main.currMenu.options.length-1)) break;
                            optionIndex++;

                            Main.currMenu.rows[optionIndex+3] = Main.currMenu.makeOptionRow(false, optionIndex-1);
                            Main.currMenu.rows[optionIndex+4] = Main.currMenu.makeOptionRow(true, optionIndex);
                            Main.screen.printRows();
                            break;
                        case 'k':
                            if (!(optionIndex > 0)) break;
                            optionIndex--;

                            Main.currMenu.rows[optionIndex+4] = Main.currMenu.makeOptionRow(true, optionIndex);
                            Main.currMenu.rows[optionIndex+5] = Main.currMenu.makeOptionRow(false, optionIndex+1);
                            Main.screen.printRows();
                            break;
                    }
                }
                KeyInput.stopCharInput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void stop() {
            detect = false;
        }

    }

}

