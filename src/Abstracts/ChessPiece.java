package Abstracts;


import Enums.Color;
import Figures.Movement;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xutkin00, xpolis03
 */

abstract public class ChessPiece {

    protected Color color;

    protected char abbreviation;

    protected int value;

    protected int current_row;
    protected int current_column;


    public ChessPiece(Color color, char abbreviation, int value){
        this.color = color;
        this.abbreviation = abbreviation;
        this.value = value;
    }

    @Override
    public String toString() {
        //TODO: implementation is needed
        return "";
    }

    public void setRow(int x){
        this.current_row = x;
    }

    public void setColumn(int y){
        this.current_column = y;
    }

    public void movePiece(int new_x, int new_y){
        /**
         * Move piece from current position to new. Change current piece coordinates
         *
         * @param int new_x new row coordinate
         * @param int new_y new column coordinate
         *
         * @return void
         */

        /**
         * TODO: Calculate path from src do dst
         */

        this.setRow(new_x);
        this.setColumn(new_y);
    }

    abstract public List<Movement> calculatePossibleMovements();

    // TODO: Calculate path from current position to dst
//    abstract public List<Movement> calculatePath();
}
