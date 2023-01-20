/**
 * @author Mohit Masand
 * @since October 18, 2022
 *
 * Board class represents a board of one game played of size selected in the
 * options menu. Consists of Board Elements and sets up the board by randomly
 * choosing locations on the board to put mines. Functionality to find all
 * un-found mines in the given row and column is also implemented here
 */

package com.example.assignment3.Game;

import java.util.Random;

public class Board {
    public com.example.assignment3.Game.BoardElement[][] board;
    int rows;
    int cols;
    int numMines;
    int minesFound;
    int usedScans;
    boolean foundAllMines;
    private static Board instance;


    private Board() {
        this.usedScans = 0;
        this.minesFound = 0;
        this.foundAllMines = false;
    }

    // Singleton Support
    public static Board getInstance() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }
    //

    public void setUpBoard(int rows, int cols, int numMines) {
        this.usedScans = 0;
        this.foundAllMines = false;
        this.minesFound = 0;
        this.rows = rows;
        this.cols = cols;
        this.numMines = numMines;
        board = new com.example.assignment3.Game.BoardElement[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++){
                board[i][j] = new com.example.assignment3.Game.BoardElement(0, false);
            }
        }
        for (int i = 0; i < numMines; i++) {
            Random rand = new Random();
            int randRow = rand.nextInt(rows);
            int randCol = rand.nextInt(cols);
            if (!board[randRow][randCol].isMine()){
                board[randRow][randCol].setMine(true);
                board[randRow][randCol].setStatus(1);
            }
            else { i--; }
        }
        updateBoardValues();
    }

    public boolean checkAllMinesFound() {
        return minesFound == numMines;
    }

    public void spotSearched(int row, int col) {
        board[row][col].setStatus(-1);
        usedScans++;
        updateBoardValues();
    }

    public void mineFound(int row, int col) {
        board[row][col].setStatus(2);
        minesFound++;
        updateBoardValues();
    }

    public void updateBoardValues() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                board[row][col].setValue(numUnFoundMinesInRow(row) + numUnFoundMinesInCol(col));
            }
        }
    }

    private int numUnFoundMinesInRow(int row) {
        int mines = 0;
        for (int i = 0; i < cols; i++) {
            if (board[row][i].getStatus() == 1) {
                mines++;
            }
        }
        return mines;
    }

    private int numUnFoundMinesInCol(int col) {
        int mines = 0;
        for (int i = 0; i < rows; i++) {
            if (board[i][col].getStatus() == 1) {
                mines++;
            }
        }
        return mines;
    }

    public int minesFound() {
        return minesFound;
    }

    public int getNumMines() {
        return numMines;
    }

    public int getUsedScans() {
        return usedScans;
    }
}
