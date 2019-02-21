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

    protected List<Movement> possibleMovements;

    public ChessPiece(Color color, char abbreviation, int value, int current_row, int current_name){
        this.color = color;
        this.abbreviation = abbreviation;
        this.value = value;
        this.current_row = current_row;
        this.current_column = current_name;
        this.possibleMovements = new ArrayList<Movement>();
    }

    @Override
    public String toString() {
        //TODO: implementation is needed
        return "";
    }

    public List<Movement> getPossiblePositions(){
        return this.possibleMovements;
    }

    public void move_piece(int new_x, int new_y){
        this.current_row = new_x;
        this.current_column = new_y;

        this.calculatePossibleMovements();
    }

    abstract protected void calculatePossibleMovements();
}
