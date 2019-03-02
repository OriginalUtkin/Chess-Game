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
    private Color currentTurn;
    private List<Turn> gameTurns;
    private int turnNumber;


//    List<Board> gameStatements;

    public Game(boolean initFlag){
        this.gameBoard = new Board(initFlag);

        this.selectedPiece = null;

        this.selectedCell = null;
        this.destinationCell = null;

        this.turnNumber = 1;
        this.gameTurns = new ArrayList<>();

        this.currentTurn = Color.WHITE;

//        this.gameStatements = new ArrayList<Board>();

    }

    public void setSelected(gui.Cell selectedCell, ChessPiece selectedPiece){
        /**
         * Set selectedCell and selectedPiece variables to particular values (Set selection of src cell).
         *
         * @param selectedCell is a cell from gui which was selected
         * @param selectedPiece chess piece which is staying on selected cell
         */

        this.selectedCell = selectedCell;
        this.selectedPiece = selectedPiece;
    }

    public void dropSelected(){
        /**
         * Set selectedCell and selectedPiece variables to null (Drop selection). Is used after turn is done or turn
         * isn't even possible.
         */

        this.selectedCell.setBorder(BorderFactory.createLineBorder(java.awt.Color.black));
        this.selectedCell.repaint();

        this.selectedPiece = null;
        this.selectedCell = null;
    }

    public void setDestinationCell(gui.Cell dstCell){
        /**
         * Set destinationCell variable to (gui) Cell object from GUI (Set selection of dst cell).
         */

        this.destinationCell = dstCell;
    }

    public void dropDestinationCell(){
        /**
         * Set destinationCell variable tu null (Drop selection). Is used after turn is done or turn
         * isn't even possible.
         */

        this.destinationCell = null;
    }

    public void changeTurn(){
        if (this.currentTurn == Color.WHITE)
            this.currentTurn = Color.BLACK;
        else
            this.currentTurn = Color.WHITE;
    }

    public List<Movement> getPossibleMovements(){
        /**
         * Calculate all possible movements for selected cell. Calculation method depends on object (polimorphic method).
         *
         * @see applyRules
         * @see calculatePossibleMovements calculation movements method for particular chess piece
         *
         * @return list of all possible movements. One movement is represented by Movement object
         */

        List<Movement> possibleMovements;

        possibleMovements  = this.selectedPiece.calculatePossibleMovements();
        this.applyRules(possibleMovements, this.selectedPiece.getColor());

        return possibleMovements;
    }

    public boolean isCellSelected(){
        /**
         * Check if any cell is selected
         *
         * @return true if cell is selected, false otherwise
         */

        return this.selectedPiece != null && this.selectedCell != null;
    }

    public boolean destinationSelected(){
        /**
         * Check if any destination cell is selected
         *
         * @return true if cell is selected, false otherwise
         */

        return this.destinationCell != null;
    }

    public ChessPiece getBoardPiece(final int row, final int column){
        /**
         * Return chess piece that is staying on cell with coordinates x and y.
         *
         * @param row - row coordinate; array index 0 - 7
         * @param column - column coordinate; array index 0 - 7
         *
         * @return ChessPiece object if chess piece is staying on selected cell; null otherwise
         */

        return this.gameBoard.gameBoard[row][column].getPiece();
    }

    public void setPiece(ChessPiece piece, int row, int column){
        /**
         * set chess piece object to particular position
         *
         * @param piece which will be set to the board
         * @param row where piece will be set
         * @param column where piece will be set
         */

        this.gameBoard.gameBoard[row][column].setPiece(piece);
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

        final Cell dstCell = this.gameBoard.gameBoard[movement.getRow()][movement.getColumn()];

        if (movement.getAdditionalCheck()) {

            if ((pieceColor == Color.WHITE) && (movement.getDirection() == Direction.DIAGONAL_UP_LEFT || movement.getDirection() == Direction.DIAGONAL_UP_RIGHT))
                return !dstCell.isFree() && dstCell.getPiece().getColor() != pieceColor;


            if ((pieceColor == Color.BLACK) && (movement.getDirection() == Direction.DIAGONAL_DOWN_LEFT || movement.getDirection() == Direction.DIAGONAL_DOWN_RIGHT))
                return !dstCell.isFree() && dstCell.getPiece().getColor() != pieceColor;

            if (movement.getDirection() == Direction.VERTICAL_UP || movement.getDirection() == Direction.VERTICAL_DOWN)
                return dstCell.isFree();

            return false;
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

        final Cell dstCell = this.gameBoard.gameBoard[movement.getRow()][movement.getColumn()];

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

            // Rules for all type of pieces except Knight
            if (currentMovement.getDirection() != null){
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
            }else{
                if (!this.isPossibleMovement(currentMovement, pieceColor))
                    movementIterator.remove();
            }
        }
    }

    public boolean isPossibleDestination(int dstRow, int dstColumn){
        /**
         * Calculate possible movements for currently selected chess piece and check if destination cell
         * is possible movement for selected piece
         *
         * @param srcX destination row coordinate
         * @param srcY destination column coordinate
         *
         * @return true in case if destination cell is a possible movement and piece could be moved. False otherwise
         */

        for (Movement movement : this.getPossibleMovements()){
            if (movement.getRow() == dstRow && movement.getColumn() == dstColumn)
                return true;
        }

        return false;
    }

    public void movePiece(){
        // TODO : change void -> Turn, object which contains info about turn. and push made turn to some list in game object
        if (this.selectedPiece.getStartedPosition())
            this.selectedPiece.changeStartedPosition();
        
        this.destinationCell.setAbbreviation(this.selectedCell.getAbbreviation());
        this.selectedCell.setAbbreviation("");

        this.setPiece(this.selectedPiece, this.destinationCell.getRow(), this.destinationCell.getColumn());

        this.setPiece(null, this.selectedCell.getRow(), this.selectedCell.getColumn());

        this.dropSelected();
        this.dropDestinationCell();

        this.turnNumber += 1;
    }

    private String getFullNotation(){
        char abbreviation = this.selectedPiece.getAbbreviation();

        if (abbreviation == 'p')
            abbreviation = '\0';

        int roundNum = this.currentRound();

        String turnNotation = abbreviation  +
                this.gameBoard.gameBoard[selectedCell.getRow()][selectedCell.getColumn()].toString() +
                this.gameBoard.gameBoard[destinationCell.getRow()][destinationCell.getColumn()].toString() +
                Integer.valueOf(roundNum).toString();

        return turnNotation;

    }

    private int currentRound(){
        return this.turnNumber % 2 == 0 ? this.turnNumber : this.turnNumber - 1;
    }

    private boolean isMate(){
        return false;
    }

}

