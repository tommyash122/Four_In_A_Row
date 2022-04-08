package com.example.fourinarow;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javax.swing.*;

//this class will control over the graphic windows that contains our board game
public class HelloController {

    private Logical logic;//logical variable declaration
    private int INIT_NUM_OF_CHILDREN;//number of elements (children) that we are start with

    @FXML
    private GridPane myGrid;

    @FXML
    private ColumnConstraints col1;

    @FXML
    private RowConstraints row1;


    public void initialize() {
        logic = new Logical();
        //get the number of elements (children) that we are start with
        INIT_NUM_OF_CHILDREN = myGrid.getChildren().size();
    }


    @FXML
    void clearWindow() {

        //enable our board for use
        myGrid.setDisable(false);

        //clean only elements that added while the users play the game
        while (myGrid.getChildren().size() > INIT_NUM_OF_CHILDREN)
            myGrid.getChildren().remove(myGrid.getChildren().size() - 1);

        //initialize back our logical class (data structure)
        initialize();
    }

    @FXML
        //insert the current disk
    void placeDisk(MouseEvent event) {

        int currColClicked = getClickedCol(event.getX());//gets the col the user clicked on the board
        int lowestRow = logic.getLowestRow(currColClicked); // gets the lowest legal row to place the current disk

        if (lowestRow < 0)//if that row is already full lowestRow will be negative,we pop the massage for the user
            JOptionPane.showMessageDialog(null, "This col is full, please try others...");
        else {

            //a function that help as get in which col the user has been clicked
            logic.setIndexInMatrix(lowestRow, currColClicked);

            //add the disk to the right spot
            addToGrid(lowestRow, currColClicked, logic.getColor());
            if (logic.checkWinner(lowestRow, currColClicked)) {
                //we have got a winner,we lunch a window massage to the user
                JOptionPane.showMessageDialog(null, logic.getTurn() +
                        " is our Winner,Congratulations! Well done!\n\n" +
                        "You can start a new game by clearing the board...");
                myGrid.setDisable(true);//disable the board game
                return;
            }
            //switch the turn to the other player
            logic.setTurn();
        }
    }

    //create and insert a new circle to the board
    private void addToGrid(int lowestRow, int currColClicked, Color color) {

        Circle disk = new Circle();
        disk.setCenterX(col1.getPrefWidth() / 2);
        disk.setCenterY(row1.getPrefHeight() / 2);
        disk.setRadius(Math.min(col1.getPrefWidth(), row1.getPrefHeight()) / 2);
        disk.setFill(color);
        myGrid.add(disk, currColClicked, lowestRow);
    }

    //A function that helps us indicate which col has been clicked
    private int getClickedCol(double x) {
        int i = 0;
        for (; i <= Logical.getNumOfCols(); i++) {
            if (x >= col1.getPrefWidth() * i && x < col1.getPrefWidth() * (i + 1))
                break;
        }
        return i;

    }


}
