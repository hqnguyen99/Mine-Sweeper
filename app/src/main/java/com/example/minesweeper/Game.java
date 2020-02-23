package com.example.minesweeper;


// handles all information regarding game features such as the board/grid and mines
public class Game {
    private int boardRow = 4;
    private int boardColumn = 6;
    private int numOfPikas = 6;
    private static Game instance;

    private Game(){

    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public int getBoardRow() {
        return boardRow;
    }

    public void setBoardRow(int boardRow) {
        this.boardRow = boardRow;
    }

    public int getBoardColumn() {
        return boardColumn;
    }

    public void setBoardColumn(int boardColumn) {
        this.boardColumn = boardColumn;
    }

    public int getNumOfPikas() {
        return numOfPikas;
    }

    public void setNumOfPikas(int numOfPikas) {
        this.numOfPikas = numOfPikas;
    }
    //
}
