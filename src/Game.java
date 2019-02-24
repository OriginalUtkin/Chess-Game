import Abstracts.ChessPiece;
import Board.Board;
import Enums.Color;
import Enums.Direction;
import Figures.Movement;
import Board.Cell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game {

    Board gameBoard;

//    List<Board> gameStatements;

    public Game(){
        this.gameBoard = new Board();
//        this.gameStatements = new ArrayList<Board>();

    }

    public void gameState(){

    }

    public ChessPiece getBoardPiece(final int x, final int y){
        /**
         * Return chess piece that is staying on cell with coordinates x and y.
         *
         * @param int x - row coordinate; array index 0 - 7
         * @param int y - column coordinate; array index 0 - 7
         *
         * @return ChessPiece object if chess piece is staying on selected cell; null otherwise
         */
        return this.gameBoard.gameBoard[x][y].getPiece();
    }

    public void setPiece(ChessPiece piece, int x, int y){
        // TODO: save previous gameboard for redo / undo operation
        this.gameBoard.gameBoard[x][y].setPiece(piece);
    }

    private boolean isPossible(Movement movement, final Color pieceColor){
        /**
         * Check if movement of chess piece is possible.
         *
         * Chess piece can move to destination cell in case:
         * 1) Destination cell is free
         * 2) Destination cell isn't free but contains enemy player chess piece
         *
         * Check if additionalCheck flag is set. If so, that means that this movement should be
         * processed using other rules (pawn diagonal movement, pawn vertical movement):
         *
         * Diagonal:
         * 1) Destination cell shouldn't be free
         * 2) Same as 2 point above
         *
         * Vertical:
         * 1) Destination cell SHOULD be free
         *
         * @param movement - possible movement of chess piece
         * @param color - color of chess piece
         *
         * @return true if chess piece movement is possible, false otherwise
         */

        final Cell dstCell = this.gameBoard.gameBoard[movement.get_x()][movement.get_y()];

        if (movement.getAdditionalCheck()) {

            if (movement.getDirection() == Direction.DIAGONAL_UP_LEFT || movement.getDirection() == Direction.DIAGONAL_UP_RIGHT)
                return !dstCell.isFree() && dstCell.getPiece().getColor() != pieceColor;

            if (movement.getDirection() == Direction.VERTICAL_UP)
                return dstCell.isFree();
        }

        return dstCell.isFree() || dstCell.getPiece().getColor() != pieceColor;
    }

    private boolean beatEnemy(final Movement movement, final Color pieceColor){
        final Cell dstCell = this.gameBoard.gameBoard[movement.get_x()][movement.get_y()];

        if (dstCell.isFree())
            return false;
        else{
            if (dstCell.getPiece().getColor() != pieceColor)
                return true;
        }

        return false;
    }

    public void applyRules(List<Movement> allPossibleMovements, final ChessPiece boardPiece){

        boolean horizontalLeft = false;
        boolean horizontalRight = false;

        boolean verticalTop = false;
        boolean verticalDown = false;

        boolean diagonalTopRight = false;
        boolean diagonalTopLeft = false;
        boolean diagonalDownRight = false;
        boolean diagonalDownLeft = false;

        Iterator<Movement> movementIterator = allPossibleMovements.iterator();

        while (movementIterator.hasNext()) {

            Movement currentMovement = movementIterator.next();

            if (currentMovement.getDirection() == Direction.VERTICAL_UP) {

                if (!verticalTop) {

                    if (!this.isPossible(currentMovement, boardPiece.getColor())) {
                        verticalTop = true;
                        movementIterator.remove();
                    } else {
                        if (this.beatEnemy(currentMovement, boardPiece.getColor()))
                            verticalTop = true;
                    }

                } else {
                    if (verticalTop)
                        movementIterator.remove();
                }
            } else if (currentMovement.getDirection() == Direction.VERTICAL_DOWN) {

                if (!verticalDown) {

                    if (!this.isPossible(currentMovement, boardPiece.getColor())) {
                        verticalDown = true;
                        movementIterator.remove();
                    }else {
                        if (this.beatEnemy(currentMovement, boardPiece.getColor()))
                            verticalDown = true;
                    }

                } else {
                    if (verticalDown)
                        movementIterator.remove();
                }
            } else if (currentMovement.getDirection() == Direction.HORIZONTAL_RIGTH) {

                if (!horizontalRight) {

                    if (!this.isPossible(currentMovement, boardPiece.getColor())) {
                        horizontalRight = true;
                        movementIterator.remove();
                    }else {
                        if (this.beatEnemy(currentMovement, boardPiece.getColor()))
                            horizontalRight = true;
                    }

                } else {
                    if (horizontalRight)
                        movementIterator.remove();
                }
            } else if (currentMovement.getDirection() == Direction.HORIZONTAL_LEFT) {

                if (!horizontalLeft) {

                    if (!this.isPossible(currentMovement, boardPiece.getColor())) {
                        horizontalLeft = true;
                        movementIterator.remove();
                    }else {
                        if (this.beatEnemy(currentMovement, boardPiece.getColor()))
                            horizontalLeft = true;
                    }

                } else {
                    if (horizontalLeft)
                        movementIterator.remove();
                }
            } else if (currentMovement.getDirection() == Direction.DIAGONAL_UP_LEFT) {

                if (!diagonalTopLeft) {

                    if (!this.isPossible(currentMovement, boardPiece.getColor())) {
                        diagonalTopLeft = true;
                        movementIterator.remove();
                    }else {
                        if (this.beatEnemy(currentMovement, boardPiece.getColor()))
                            diagonalTopLeft = true;
                    }

                } else {
                    if (diagonalTopLeft)
                        movementIterator.remove();
                }
            } else if (currentMovement.getDirection() == Direction.DIAGONAL_DOWN_RIGHT) {

                if (!diagonalDownRight) {

                    if (!this.isPossible(currentMovement, boardPiece.getColor())) {
                        diagonalDownRight = true;
                        movementIterator.remove();
                    }else {
                        if (this.beatEnemy(currentMovement, boardPiece.getColor()))
                            diagonalDownRight = true;
                    }

                } else {
                    if (diagonalDownRight)
                        movementIterator.remove();
                }
            } else if (currentMovement.getDirection() == Direction.DIAGONAL_DOWN_LEFT) {

                if (!diagonalDownLeft) {

                    if (!this.isPossible(currentMovement, boardPiece.getColor())) {
                        diagonalDownLeft = true;
                        movementIterator.remove();
                    }else {
                        if (this.beatEnemy(currentMovement, boardPiece.getColor()))
                            diagonalDownLeft = true;
                    }

                } else {
                    if (diagonalDownLeft)
                        movementIterator.remove();
                }
            } else {
                if (!diagonalTopRight) {

                    if (!this.isPossible(currentMovement, boardPiece.getColor())) {
                        diagonalTopRight = true;
                        movementIterator.remove();
                    }else {
                        if (this.beatEnemy(currentMovement, boardPiece.getColor()))
                            diagonalTopRight = true;
                    }

                } else {
                    if (diagonalTopRight)
                        movementIterator.remove();
                }
            }
        }
    }
}

