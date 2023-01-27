package gfx;



public class Screen {
    
    int x = 0;
    int y = 1;

    int[] lineNumsY;
    int[] lineNumsX;
    int[] playerPos = new int[2];

    public Screen(int rows, int cols) {
        lineNumsX = new int[cols];
        lineNumsY = new int[rows];

        playerPos[x] = (int) cols / 2;
        playerPos[y] = (int) rows / 2;

        makeLines();
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
    }
}
