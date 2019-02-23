package Abstracts;


import Enums.Color;
import Figures.Movement;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author xutkin00, xpolis03
 */

abstract public class ChessPiece implements Serializable {

    final protected Color color;

    final protected char abbreviation;

    final protected int value;

    protected int currentRow;
    protected int currentColumn;

    public ChessPiece(Color color, char abbreviation, int value){
        this.color = color;
        this.abbreviation = abbreviation;
        this.value = value;
    }

    @Override
    public String toString() {
        return Character.toString(this.abbreviation);
    }

    final public void setRow(int x){
        this.currentRow = x;
    }

    final public void setColumn(int y){
        this.currentColumn = y;
    }

    final public char getAbbreviation(){return this.abbreviation;}

    final void movePiece(int new_x, int new_y){
        /**
         * Move piece from current position to new. Change current piece coordinates
         *
         * @param int new_x new row coordinate
         * @param int new_y new column coordinate
         *
         * @return void
         */

        this.setRow(new_x);
        this.setColumn(new_y);
    }

    final public Color getColor(){
        /**
         * Piece color getter
         *
         * @return color of piece
         */
        return this.color;
    }

    abstract public List<Movement> calculatePossibleMovements();

    // TODO: Calculate path from current position to dst
//    abstract public List<Movement> calculatePath();
}
