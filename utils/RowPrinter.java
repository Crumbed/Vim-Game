package utils;

import java.util.ArrayList;


public class RowPrinter {


    private int currentRow;
    private int maxRows;
    private String[] textRows;
    public boolean rawMode;
    

    public RowPrinter(int height) {
        currentRow = 0;
        maxRows = height;
        textRows = new String[maxRows];
        for (int i = 0; i < maxRows; i++) {
            textRows[i] = "";
        }
        rawMode = false;
    }
    

    
    public void writeRow(int rowIndex, String rowContents) {
        if (rowIndex >= maxRows) {
            throw new IllegalArgumentException("rowIndex too high");
        }
        textRows[rowIndex] = rowContents;
        navigateToRow(rowIndex);
        // clear the existing line
        System.out.print("\u001B[2K");
        // write the new text
        System.out.print(rowContents);
        // go to the last row
        navigateToRow(maxRows);

        System.out.flush();
    }

    /*
     * Bring a row back to its cached value
     */
    public void restoreRow(int rowIndex) {
        if (rowIndex >= maxRows) {
            throw new IllegalArgumentException("rowIndex too high");
        }
        navigateToRow(rowIndex);
        // clear the existing line
        System.out.print("\u001B[2K");
        // write the text
        System.out.print(textRows[rowIndex]);
        System.out.flush();
    }

    /*
     * Go to a row and wipe away text
     */
    public void clearRow(int rowIndex) {
        if (rowIndex < 0) {
            throw new IllegalArgumentException("rowIndex too low");
        }
        if (rowIndex < maxRows) {
            // only clear cache if cache exists, but allow navigation
            textRows[rowIndex] = "";
        }

        navigateToRow(rowIndex);
        // clear the existing line
        System.out.print("\u001B[2K");
        System.out.flush();
    }

    /*
     * Uses escape sequence to place cursor on a row,
     * at the beginning of the row
     */
    public void navigateToRow(int rowIndex) {
        if (rowIndex >= maxRows + 1) {
            throw new IllegalArgumentException("rowIndex of " + rowIndex + " >= maxRows + 1 of " + (maxRows + 1));
        }
        if (rowIndex != currentRow) {
            // need to move up or down
            currentRow = rowIndex;
            System.out.print("\u001B[" + (rowIndex + 1) + ";1H");
            // System.out.flush();
        } else {
            // go to beginning of line
            System.out.print("\u001B[1000D");
            // System.out.flush();
        }

    }
}
