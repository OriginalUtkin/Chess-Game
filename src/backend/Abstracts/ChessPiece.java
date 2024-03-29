package backend.Abstracts;


import backend.Enums.Color;
import backend.Enums.Direction;
import backend.Figures.Movement;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: Chess game IJA project
 * File: ChessPiece.java
 * Date: 27.04.2019
 * Authors: xutkin00 <xutkin00@stud.fit.vutbr.cz>
 *          xpolis03 <xpolis03@stud.fit.vutbr.cz>
 * Description: Abstract class that used for extends by other chess pieces
 */

abstract public class ChessPiece{

    final protected Color color;

    final private char abbreviation;


    final private int value;

    protected boolean startedPosition;
    protected int currentRow;
    protected int currentColumn;


    public ChessPiece(Color color, char abbreviation, int value){
        this.color = color;
        this.abbreviation = abbreviation;
        this.value = value;
        this.startedPosition = true;
    }

    public void changeStartedPosition(){
        this.startedPosition = !this.startedPosition;
    }


    final public boolean getStartedPosition(){
        return this.startedPosition;
    }


    @Override
    public String toString() { return Character.toString(this.abbreviation); }


    final public void setRow(int x){
        this.currentRow = x;
    }


    final public void setColumn(int y){
        this.currentColumn = y;
    }

    final public String getFullPieceString(){return this.color.toString() + this.abbreviation;}


    /**
     * Piece color getter
     *
     * @return color of piece
     */
    final public Color getColor(){

        return this.color;
    }


    /**
     * Return all possible diagonal movements for chess piece.
     *
     * @param maxDiagonalStep maximum step of piece on the chess board.
     *
     * @return list of all diagonal movements.
     */
    final protected List<Movement> getDiagonalMovements(final int maxDiagonalStep){

        List<Movement> diagonalMovements = new ArrayList<>();

        boolean additionalFlag = false;

        if (this.abbreviation == 'p')
            additionalFlag = true;

        for (int counter = 1; counter < maxDiagonalStep + 1; counter++){

            if (this.currentRow + counter <= 7 && this.currentColumn + counter <=7)
                diagonalMovements.add(new Movement(
                        this.currentRow + counter,
                        this.currentColumn + counter,
                        additionalFlag,
                        Direction.DIAGONAL_UP_RIGHT)
                );

            if (this.currentRow + counter <= 7 && this.currentColumn - counter >= 0)
                diagonalMovements.add(new Movement(
                        this.currentRow + counter,
                        this.currentColumn - counter,
                        additionalFlag,
                        Direction.DIAGONAL_UP_LEFT)
                );

            if (this.currentRow - counter >= 0 && this.currentColumn + counter <= 7)
                diagonalMovements.add(new Movement(
                        this.currentRow - counter,
                        this.currentColumn + counter,
                        additionalFlag,
                        Direction.DIAGONAL_DOWN_RIGHT)
                );

            if (this.currentRow - counter >= 0 && this.currentColumn - counter >= 0)
                diagonalMovements.add(new Movement(
                        this.currentRow - counter,
                        this.currentColumn - counter,
                        additionalFlag,
                        Direction.DIAGONAL_DOWN_LEFT)
                );
        }

        return diagonalMovements;
    }


    /**
     * Return all possible vertical movements for chess piece.
     *
     * @param maxVerticalStep maximum step of piece on the chess board.
     *
     * @return list of all vertical movements.
     */
    final protected List<Movement> getVerticalMovements(final int maxVerticalStep){


        List<Movement> verticalMovements = new ArrayList<>();

        boolean additionalFlag = false;

        if (this.abbreviation == 'p')
            additionalFlag = true;

        for (int counter = 1; counter < maxVerticalStep + 1; counter++){

            if (this.abbreviation != 'p' || this.color == Color.BLACK){

                if (this.currentRow - counter >= 0) {
                    verticalMovements.add(new Movement(
                            this.currentRow - counter,
                            this.currentColumn,
                            additionalFlag,
                            Direction.VERTICAL_DOWN)
                    );
                }
            }

            if (this.abbreviation != 'p' || this.color == Color.WHITE){
                if (this.currentRow + counter <= 7) {
                    verticalMovements.add(new Movement(
                            this.currentRow + counter,
                            this.currentColumn,
                            additionalFlag,
                            Direction.VERTICAL_UP)
                    );
                }
            }
        }

        return verticalMovements;
    }


    /**
     * Return all possible horizontal movements for chess piece.
     *
     * @param maxHorizontalStep maximum step of piece on the chess board.
     *
     * @return list of all horizontal movements.
     */
    final protected List<Movement> getHorizontalMovements(final int maxHorizontalStep){


        List<Movement> horizontalMovements = new ArrayList<>();

        for (int counter = 1; counter < maxHorizontalStep + 1; counter++){

            if (this.currentColumn - counter >= 0)
                horizontalMovements.add(new Movement(
                        this.currentRow,
                        this.currentColumn - counter,
                        false,
                        Direction.HORIZONTAL_LEFT)
                );

            if (this.currentColumn + counter <= 7)
                horizontalMovements.add(new Movement(
                        this.currentRow,
                        this.currentColumn + counter,
                        false,
                        Direction.HORIZONTAL_RIGHT)
                );
        }

        return horizontalMovements;
    }

    abstract public List<Movement> calculatePossibleMovements();

}
