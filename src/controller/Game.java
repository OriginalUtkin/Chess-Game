package controller;

import backend.Abstracts.ChessPiece;
import backend.Board.Board;
import backend.Enums.Color;
import backend.Enums.Direction;
import backend.Figures.Movement;
import backend.Board.Cell;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game {

    private Board gameBoard;
    private ChessPiece selectedPiece;
    private gui.Cell selectedCell;
    private gui.Cell destinationCell;


//    List<Board> gameStatements;

    public Game(boolean initFlag){
        this.gameBoard = new Board(initFlag);

        this.selectedPiece = null;

        this.selectedCell = null;
        this.destinationCell = null;
//        this.gameStatements = new ArrayList<Board>();

    }

    public void setSelected(gui.Cell selectedCell, ChessPiece selectedPiece){
        this.selectedCell = selectedCell;
        this.selectedPiece = selectedPiece;
    }

    public void setDestinationCell(gui.Cell dstCell){
        this.destinationCell = dstCell;
        this.destinationCell.setBorder(BorderFactory.createLineBorder(java.awt.Color.green));
    }

    public void dropDestinationCell(){
        this.destinationCell.setBorder(BorderFactory.createLineBorder(java.awt.Color.black));
        this.destinationCell = null;
    }

    public void changeTurn(){
        // TODO :Add turn stuff
    }

    public List<Movement> getPossibleMovements(){

        List<Movement> possibleMovements;

        if (this.selectedPiece != null) {
           possibleMovements  = this.selectedPiece.calculatePossibleMovements();
            this.applyRules(possibleMovements, this.selectedPiece.getColor());
        }
        else{
            possibleMovements = new ArrayList<>();
        }

        return possibleMovements;
    }

    public void dropSelected(){
        this.selectedCell.setBorder(BorderFactory.createLineBorder(java.awt.Color.black));
        this.selectedCell.repaint();

        this.selectedPiece = null;

        this.selectedCell = null;
    }


    public boolean isCellSelected(){
        return this.selectedPiece != null && this.selectedCell != null;
    }

    public boolean destinationSelected(){
        return this.destinationCell != null;
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

    private boolean isPossibleMovement(Movement movement, final Color pieceColor){
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
        /**
         * Check if after movement selected piece enemy peace was beaten.
         *
         * @param movement possible movement of selected chess piece
         * @param pieceColor color of selected chess piece
         *
         * @return true is enemy piece was beaten, else otherwise
         */

        final Cell dstCell = this.gameBoard.gameBoard[movement.get_x()][movement.get_y()];

        if (dstCell.isFree())
            return false;

        else{

            if (dstCell.getPiece().getColor() != pieceColor)
                return true;
        }

        return false;
    }

    // TODO: this method could be refactored somehow :/
    private void applyRules(List<Movement> allPossibleMovements, final Color pieceColor){
        /**
         * !!!---------------------WARNING----------------------!!!
         * This method is changing allPossibleMovements list parameter
         * !!!--------------------------------------------------!!!
         *
         * Recalculate possible piece moves on chess board;
         *
         * @see beatEnemy
         * @see isPossible
         *
         * @param allPossibleMovements all possible movements list of selected chess piece. This list will be changed
         *                             after applying rules.
         * @param boardPiece selected board piece. Moves are calculated depends on selected piece color
         *
         * @return void but input list will be changed in result
         */

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

            switch (currentMovement.getDirection()){

                case VERTICAL_UP:
                    if (!verticalTop) {

                        if (!this.isPossibleMovement(currentMovement, pieceColor)) {
                            verticalTop = true;
                            movementIterator.remove();
                        } else {
                            if (this.beatEnemy(currentMovement, pieceColor))
                                verticalTop = true;
                        }
                    } else {
                        if (verticalTop)
                            movementIterator.remove();
                    }
                    break;

                case VERTICAL_DOWN:
                    if (!verticalDown) {

                        if (!this.isPossibleMovement(currentMovement, pieceColor)) {
                            verticalDown = true;
                            movementIterator.remove();
                        }else {
                            if (this.beatEnemy(currentMovement, pieceColor))
                                verticalDown = true;
                        }

                    } else {
                        if (verticalDown)
                            movementIterator.remove();
                    }
                    break;

                case HORIZONTAL_RIGHT:
                    if (!horizontalRight) {

                        if (!this.isPossibleMovement(currentMovement, pieceColor)) {
                            horizontalRight = true;
                            movementIterator.remove();
                        }else {
                            if (this.beatEnemy(currentMovement, pieceColor))
                                horizontalRight = true;
                        }

                    } else {
                        if (horizontalRight)
                            movementIterator.remove();
                    }
                    break;

                case HORIZONTAL_LEFT:
                    if (!horizontalLeft) {

                        if (!this.isPossibleMovement(currentMovement, pieceColor)) {
                            horizontalLeft = true;
                            movementIterator.remove();
                        }else {
                            if (this.beatEnemy(currentMovement, pieceColor))
                                horizontalLeft = true;
                        }

                    } else {
                        if (horizontalLeft)
                            movementIterator.remove();
                    }
                    break;

                case DIAGONAL_UP_LEFT:
                    if (!diagonalTopLeft) {

                        if (!this.isPossibleMovement(currentMovement, pieceColor)) {
                            diagonalTopLeft = true;
                            movementIterator.remove();
                        }else {
                            if (this.beatEnemy(currentMovement, pieceColor))
                                diagonalTopLeft = true;
                        }

                    } else {
                        if (diagonalTopLeft)
                            movementIterator.remove();
                    }
                    break;

                case DIAGONAL_DOWN_RIGHT:
                    if (!diagonalDownRight) {

                        if (!this.isPossibleMovement(currentMovement, pieceColor)) {
                            diagonalDownRight = true;
                            movementIterator.remove();
                        }else {
                            if (this.beatEnemy(currentMovement, pieceColor))
                                diagonalDownRight = true;
                        }

                    } else {
                        if (diagonalDownRight)
                            movementIterator.remove();
                    }
                    break;

                case DIAGONAL_DOWN_LEFT:
                    if (!diagonalDownLeft) {

                        if (!this.isPossibleMovement(currentMovement, pieceColor)) {
                            diagonalDownLeft = true;
                            movementIterator.remove();
                        }else {
                            if (this.beatEnemy(currentMovement, pieceColor))
                                diagonalDownLeft = true;
                        }

                    } else {
                        if (diagonalDownLeft)
                            movementIterator.remove();
                    }
                    break;

                case DIAGONAL_UP_RIGHT:
                    if (!diagonalTopRight) {

                        if (!this.isPossibleMovement(currentMovement, pieceColor)) {
                            diagonalTopRight = true;
                            movementIterator.remove();
                        }else {
                            if (this.beatEnemy(currentMovement, pieceColor))
                                diagonalTopRight = true;
                        }

                    } else {
                        if (diagonalTopRight)
                            movementIterator.remove();
                    }
                    break;
            }
        }
    }

    public boolean isPossibleDestination(int srcX, int srcY){


        for (Movement movement : this.getPossibleMovements()){
            if (movement.get_x() == srcX && movement.get_y() == srcY)
                return true;
        }

        return false;
    }
}

