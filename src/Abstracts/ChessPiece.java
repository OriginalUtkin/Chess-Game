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


    public ChessPiece(Color color, char abbreviation, int value, int current_row, int current_name){
        this.color = color;
        this.abbreviation = abbreviation;
        this.value = value;
        this.current_row = current_row;
        this.current_column = current_name;
    }

    @Override
    public String toString() {
        //TODO: implementation is needed
        return "";
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

        this.current_row = new_x;
        this.current_column = new_y;
    }

    abstract public List<Movement> calculatePossibleMovements();

    // TODO: Calculate path from current position to dst
//    abstract public List<Movement> calculatePath();
}
