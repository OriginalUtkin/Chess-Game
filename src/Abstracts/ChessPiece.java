package Abstracts;


import Enums.Color;
import Enums.Direction;
import Figures.Movement;

import java.io.Serializable;
import java.util.ArrayList;
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


    public void movePiece(int new_x, int new_y){
        /**
         * Move piece from current position to new. Change current piece coordinates
         *
         * @param int new_x new row coordinate
         * @param int new_y new column coordinate
         *
         * @return void
         */

        this.currentRow = new_x;
        this.currentColumn = new_y;
    }


    final public Color getColor(){
        /**
         * Piece color getter
         *
         * @return color of piece
         */
        return this.color;
    }


    final public List<Movement> getDiagonalMovements(final int maxDiagonalStep){

        List<Movement> diagonalMovements = new ArrayList<>();

        boolean additionalFlag = false;

        if (this.abbreviation == 'p')
            additionalFlag = true;

        for (int counter = 1; counter < maxDiagonalStep + 1; counter++){

            if (this.currentRow + counter <= 7 && this.currentColumn + counter <=7)
                diagonalMovements.add(new Movement(
                        this.currentRow + 1,
                        this.currentColumn + 1,
                        additionalFlag,
                        Direction.DIAGONAL)
                );

            if (this.currentRow + counter <= 7 && this.currentColumn - counter >= 0)
                diagonalMovements.add(new Movement(
                        this.currentRow + 1,
                        this.currentColumn - 1,
                        additionalFlag,
                        Direction.DIAGONAL)
                );

            if (this.currentRow - counter >= 0 && this.currentColumn + counter <= 7)
                diagonalMovements.add(new Movement(
                        this.currentRow - 1,
                        this.currentColumn + 1,
                        additionalFlag,
                        Direction.DIAGONAL)
                );

            if (this.currentRow - counter >= 0 && this.currentColumn - counter >= 0)
                diagonalMovements.add(new Movement(
                        this.currentRow - 1,
                        this.currentColumn - 1,
                        additionalFlag,
                        Direction.DIAGONAL)
                );
        }

        return diagonalMovements;
    }


    final public List<Movement> getVerticalMovements(final int maxVerticalStep){

        List<Movement> verticalMovements = new ArrayList<>();

        boolean additionalFlag = false;

        if (this.abbreviation == 'p')
            additionalFlag = true;

        for (int counter = 1; counter < maxVerticalStep + 1; counter++){

            if (this.currentRow - counter >= 0) {
                verticalMovements.add(new Movement(
                        this.currentRow - counter,
                        this.currentColumn,
                        additionalFlag,
                        Direction.VERTICAL)
                );

                if (this.abbreviation == 'p' && this.color == Color.BLACK)
                    continue;
            }

            if (this.currentRow + counter <= 7) {
                verticalMovements.add(new Movement(
                        this.currentRow + counter,
                        this.currentColumn,
                        additionalFlag,
                        Direction.VERTICAL)
                );

                if (this.abbreviation == 'p' && this.color == Color.WHITE)
                    continue;
            }
        }

        return verticalMovements;
    }


    final public List<Movement> getHorizontalMovements(final int maxHorizantalStep){

        List<Movement> horizontalMovements = new ArrayList<>();

        for (int counter = 1; counter < maxHorizantalStep + 1; counter++){

            if (this.currentColumn - counter >= 0)
                horizontalMovements.add(new Movement(this.currentRow
                        , this.currentColumn - counter,
                        false,
                        Direction.HORIZONTAL)
                );

            if (this.currentRow + counter <= 7)
                horizontalMovements.add(new Movement(
                        this.currentRow,
                        this.currentColumn + counter,
                        false,
                        Direction.HORIZONTAL)
                );
        }

        return horizontalMovements;
    }

    abstract public List<Movement> calculatePossibleMovements();

    // TODO: Calculate path from current position to dst
//    abstract public List<Movement> calculatePath();
}
