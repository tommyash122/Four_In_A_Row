package com.example.fourinarow;

import javafx.scene.paint.Color;

//this class will hold the logical functionality of our board game
public class Logical {
    private static final int NUM_OF_ROWS = 6;
    private static final int NUM_OF_COLS = 7;
    private static final int FOUR_IN_A_ROW = 4;
    private static int countDisks;

    private static Player[][] matrix;
    private static Player turn;//represents who's turn is it first/second player

    protected enum Player {
        NO_PLAYER,
        FIRST_PLAYER,
        SECOND_PLAYER
    }

    //logical constructor
    public Logical() {
        matrix = new Player[NUM_OF_ROWS][NUM_OF_COLS];

        //At first, we initialize our indicator matrix to be no player's at all cells
        for (int i = 0; i < NUM_OF_ROWS; i++) {
            for (int j = 0; j < NUM_OF_COLS; j++)
                matrix[i][j] = Player.NO_PLAYER;
        }

        turn = Player.FIRST_PLAYER;//init the first turn to the first player
        countDisks = 0;
    }

    //function that checks if we got a winner
    public boolean checkWinner(int row, int col) {

        if (checkHorizontally(row, col))
            return true;
        if (checkVertically(row, col))
            return true;
        return checkDiagonally(row, col);

    }

    //checks the conditions, if we still on the board and the jointed disk is the same color
    public boolean checkFourInARow(int row, int col) {
        return (inBounds(row, col) && getIndexInMatrix(row, col) == getTurn());
    }

    //a function that checks the diagonals for a sequence
    private boolean checkDiagonally(int row, int col) {

        //Same diagonal, set the count back to ONE (counting the one we just added)
        setCountDisks(1);
        if (checkDiagRightDown(row + 1, col + 1))
            return true;
        if (checkDiagLeftUp(row - 1, col - 1))
            return true;

        //Same diagonal, set the count back to ONE (counting the one we just added)
        setCountDisks(1);
        if (checkDiagLeftDown(row + 1, col - 1))
            return true;
        return checkDiagRightUp(row - 1, col + 1);
    }

    private boolean checkDiagRightDown(int row, int col) {

        while (checkFourInARow(row++, col++))
            setCountDisks(getCountDisks() + 1);

        return getCountDisks() >= FOUR_IN_A_ROW;
    }

    private boolean checkDiagLeftUp(int row, int col) {

        while (checkFourInARow(row--, col--))
            setCountDisks(getCountDisks() + 1);

        return getCountDisks() >= FOUR_IN_A_ROW;
    }

    private boolean checkDiagLeftDown(int row, int col) {

        while (checkFourInARow(row++, col--))
            setCountDisks(getCountDisks() + 1);

        return getCountDisks() >= FOUR_IN_A_ROW;
    }

    private boolean checkDiagRightUp(int row, int col) {

        while (checkFourInARow(row--, col++))
            setCountDisks(getCountDisks() + 1);

        return getCountDisks() >= FOUR_IN_A_ROW;
    }

    //a function that checks the vertical cells of the board for a sequence
    private boolean checkVertically(int row, int col) {

        //Same diagonal, set the count back to ONE (counting the one we just added)
        setCountDisks(1);
        if (checkVerticallyDown(row + 1, col))
            return true;
        return checkVerticallyUp(row - 1, col);

    }

    private boolean checkVerticallyUp(int row, int col) {

        while (checkFourInARow(row--, col))
            setCountDisks(getCountDisks() + 1);

        return getCountDisks() >= FOUR_IN_A_ROW;
    }

    private boolean checkVerticallyDown(int row, int col) {

        while (checkFourInARow(row++, col))
            setCountDisks(getCountDisks() + 1);

        return getCountDisks() >= FOUR_IN_A_ROW;
    }

    //a function that checks the horizontal cells of the board for a sequence
    private boolean checkHorizontally(int row, int col) {

        //Same diagonal, set the count back to ONE (counting the one we just added)
        setCountDisks(1);
        if (checkHorizontallyRight(row, col + 1))
            return true;
        return checkHorizontallyLeft(row, col - 1);
    }

    private boolean checkHorizontallyLeft(int row, int col) {

        while (checkFourInARow(row, col--))
            setCountDisks(getCountDisks() + 1);

        return getCountDisks() >= FOUR_IN_A_ROW;
    }

    private boolean checkHorizontallyRight(int row, int col) {

        while (checkFourInARow(row, col++))
            setCountDisks(getCountDisks() + 1);

        return getCountDisks() >= FOUR_IN_A_ROW;
    }

    //checks if we in the board bounds
    private boolean inBounds(int row, int col) {
        return (row >= 0 && row < getNumOfRows() && col >= 0 && col < getNumOfCols());
    }

    //a function that checks and return the lowest legal non taken spot to place the new disk
    public int getLowestRow(int col) {
        int currRow = 0;

        while (currRow < getNumOfRows() && getIndexInMatrix(currRow, col) == Player.NO_PLAYER)
            currRow++;

        return currRow - 1;
    }


    public Player getIndexInMatrix(int row, int col) {
        return matrix[row][col];
    }

    //switch the turn between the players
    public void setTurn() {
        if (turn == Player.FIRST_PLAYER)
            turn = Player.SECOND_PLAYER;
        else
            turn = Player.FIRST_PLAYER;

    }

    public Color getColor() {
        if (getTurn() == Player.FIRST_PLAYER)
            return Color.BLUE;
        return Color.RED;
    }

    public Player getTurn() {
        return turn;
    }

    public void setIndexInMatrix(int row, int col) {
        matrix[row][col] = getTurn();
    }

    public static int getNumOfRows() {
        return NUM_OF_ROWS;
    }

    public static int getNumOfCols() {
        return NUM_OF_COLS;
    }

    public static int getCountDisks() {
        return countDisks;
    }

    //counts the disks that we have at each point in time
    public static void setCountDisks(int countDisks) {
        Logical.countDisks = countDisks;
    }
}
