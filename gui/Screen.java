package gui;


import main.Main;
import main.Settings;

public class Screen {
    int x = 0;
    int y = 1;

    public int focus = 1;

    int[] lineNumsY;
    int[] lineNumsX;
    public int[] playerPos = new int[2];

    public Screen(int cols, int rows) {
        lineNumsX = new int[cols];
        lineNumsY = new int[rows];

        playerPos[x] = (int) cols / 2;
        playerPos[y] = (int) rows / 2;

        makeLines();
        printRows();
    }

    public void makeLines() {
        int currNum = lineNumsY.length - playerPos[y];
        for (int i = 0; i < lineNumsY.length; i++) {
            lineNumsY[i] = Math.abs(currNum);
            currNum--;
        }
        currNum = lineNumsX.length - playerPos[x];
        for (int i = 0; i < lineNumsX.length; i++) {
            lineNumsX[i] = Math.abs(currNum);
            currNum--;
        }
        printRows();
    }

    public void up(int count) {
        boolean past0 = false;
        playerPos[y] -= count;
        for (int i = 0; i < lineNumsY.length; i++) {
            if (i == playerPos[y]) {
                lineNumsY[i] = 0;
                past0 = true;
                for (int j = 0; j < count; j++) {
                    lineNumsY[i] -= (count-j);
                    i++;
                }
                continue;
            }
            if (past0) { lineNumsY[i] += count; continue; }
            lineNumsY[i] -= count;
        }
        printRows();
    }
    public void down(int count) {
        boolean past0 = false;
        playerPos[y] += count;
        for (int i = 0; i < lineNumsY.length; i++) {
            if (i == playerPos[y]) {
                lineNumsY[i] = 0;
                past0 = true;
                continue;
            }
            if (past0) { lineNumsY[i] -= count; continue; }
            lineNumsY[i] += count;
        }
        printRows();
    }
    public void left(int count) {
        boolean past0 = false;
        playerPos[x] -= count;
        for (int i = 0; i < lineNumsX.length; i++) {
            if (lineNumsX[i] == 0) {
                lineNumsX[i] = 1;
                past0 = true;
                continue;
            }
            if (past0) { lineNumsX[i] += count; continue; }
            lineNumsX[i] -= count;
        }
        printRows();
    }
    public void right(int count) {
        boolean past0 = false;
        playerPos[x] += count;
        for (int i = 0; i < lineNumsX.length; i++) {
            if (lineNumsX[i] == 0) {
                lineNumsX[i] = 1;
                past0 = true;
                continue;
            }
            if (past0) { lineNumsX[i] -= count; continue; }
            lineNumsX[i] += count;
        }
        printRows();
    }

    public String highlight(String text) {
        return "\033[30;47;1m"+text+"\u001B[0m";
    }
    public String dimText(int id, String text) {
        if (!(id == focus)) return "\033[90m"+text+"\u001B[0m";
        else return text;
    }



    public void swapFocus() {
        if (focus == 1) Main.currMenu.focus(); 
        else if (focus == 2) focus(); 
    }

    public void focus() {
        Main.input.setRunnable(new Main.Movement());
    }


    public void printRows() {
        String row0 = "  |";
        String row1 = "  |";
        String row2 = "--+";
        
        for (int i = 0; i < lineNumsX.length; i++) {
            String blank = "  ";
            String num = ""+lineNumsX[i];
            if (lineNumsX[i] < 10) num += " ";
            if (lineNumsX[i] == 0) { num = highlight(num); blank = highlight(blank); }

            if (i % 2 == 0) { row1 += blank; row0 += num; }
            else { row0 += blank; row1 += num; }

            row2 += "--";
        }

        Main.rp.writeRow(0, dimText(1, row0) + "|" + dimText(2, Main.currMenu.getLine(0)));
        Main.rp.writeRow(1, dimText(1, row1) + "|" + dimText(2, Main.currMenu.getLine(1)));
        Main.rp.writeRow(2, dimText(1, row2) + "+" + dimText(2, Main.currMenu.getLine(2)));

        for (int i = 0; i < lineNumsY.length; i++) {

            String row = ""+lineNumsY[i];
            if (lineNumsY[i] < 10) row += " ";
            if (lineNumsY[i] == 0) row = highlight(row);
            row += "|";

            for (int j = 0; j < lineNumsX.length; j++) {
                if ((lineNumsY[i] == 0) && (lineNumsX[j] == 0)) { row += highlight("  "); continue; }
                row += "  ";
            }
            
            Main.rp.writeRow(i+3, dimText(1, row) + "|" + dimText(2, Main.currMenu.getLine(i+3)));
        }
        Main.rp.writeRow(33, dimText(1, row2) + "+" + dimText(2, Main.currMenu.getLine(33)));

        makeInfoBar();
    }



    public void makeInfoBar() {
        String infoBar = "";

        if (Settings.SHOWACTION.isEnabled()) infoBar += "Action: "+ Main.input.getAction();
        if (Settings.DEVMODE.isEnabled()) infoBar += "("+playerPos[x]+", "+playerPos[y]+") Focus: "+focus;

        Main.rp.writeRow(34, infoBar);

    }
}




