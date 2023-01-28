package gfx;


import main.Main;

public class Screen {
    
    int x = 0;
    int y = 1;

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

    public void up() {
        boolean past0 = false;
        playerPos[y]++;
        for (int i = 0; i < lineNumsY.length; i++) {
            if (lineNumsY[i] == 0) {
                lineNumsY[i] = 1;
                past0 = true;
                continue;
            }
            if (past0) { lineNumsY[i] += 1; continue; }
            lineNumsY[i] -= 1;
        }
        printRows();
    }
    public void down() {
        boolean past0 = false;
        playerPos[y]--;
        for (int i = 0; i < lineNumsY.length; i++) {
            if (lineNumsY[i] == 0) {
                lineNumsY[i] = 1;
                past0 = true;
                continue;
            }
            if (past0) { lineNumsY[i] -= 1; continue; }
            lineNumsY[i] += 1;
        }
        printRows();
    }
    
    public void left() {
        boolean past0 = false;
        playerPos[x]++;
        for (int i = 0; i < lineNumsX.length; i++) {
            if (lineNumsX[i] == 0) {
                lineNumsX[i] = 1;
                past0 = true;
                continue;
            }
            if (past0) { lineNumsX[i] += 1; continue; }
            lineNumsX[i] -= 1;
        }
        printRows();
    }

    public void right() {
        boolean past0 = false;
        playerPos[x]--;
        for (int i = 0; i < lineNumsX.length; i++) {
            if (lineNumsX[i] == 0) {
                lineNumsX[i] = 1;
                past0 = true;
                continue;
            }
            if (past0) { lineNumsX[i] -= 1; continue; }
            lineNumsX[i] += 1;
        }
        printRows();
    }

    private void printRows() {
        String row0 = "  ";
        String row1 = "  ";
        for (int i = 0; i < lineNumsX.length; i++) {
            if (i % 2 == 0) {
                row0 += lineNumsX[i];
                if (lineNumsX[i] < 10) row0 += " ";
                row1 += "  ";
            } else {
                row1 += lineNumsX[i];
                if (lineNumsX[i] < 10) row1 += " ";
                row0 += "  ";
            }
        }
        Main.rp.writeRow(0, row0);
        Main.rp.writeRow(1, row1);
        for (int i = 0; i < lineNumsY.length; i++) {
            Main.rp.writeRow(i+2, ""+lineNumsY[i]);
        }

        Main.rp.writeRow(34, "("+playerPos[x]+", "+playerPos[y]+")");
    }
}
