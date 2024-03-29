package backend.Board;
import backend.Enums.Color;
import backend.Figures.*;

/**
 * Project: Chess game IJA project
 * File: Board.java
 * Date: 27.04.2019
 * Authors: xutkin00 <xutkin00@stud.fit.vutbr.cz>
 *          xpolis03 <xpolis03@stud.fit.vutbr.cz>
 * Description: Class that represents a game board for the backend.
 */

public class Board {
    public Cell[][] gameBoard;

    /**
     * Main Board object constructor.
     *
     * @param initCells indicate if chess pieces will be initialised on the game board after creating.
     */
    public Board(boolean initCells){
        this.gameBoard = new Cell[8][8];
        this.initBoardCells();
        if (initCells)
            this.initFigures();
    }



    /**
     * Initialize the board game object without pieces on it.
     */
    private void initBoardCells(){
        char[] cellLetters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

        for (int row = 0; row < 8; row++){

            Color current_cell_color = (row+1)%2 == 0 ? Color.WHITE : Color.BLACK;

            for (int column = 0; column < 8; column++){

                this.gameBoard[row][column] = new Cell(current_cell_color, null, cellLetters[column], row+1, row, column);
                current_cell_color = current_cell_color == Color.WHITE ? Color.BLACK : Color.WHITE;
            }
        }

    }



    /**
     * Initialise chess pieces on the created game board.
     */
    private void initFigures(){

        for (int row = 0; row < 8; row++){

            if (row > 1 && row < 6)
                continue;

            Color currentColor = (row < 3) ? Color.WHITE : Color.BLACK;

            for (int column = 0; column < 8; column++){
                if (row == 1 || row == 6){
                    this.gameBoard[row][column].setPiece(new Pawn(currentColor));
                    continue;
                }

                if (column == 0 || column == 7){
                    this.gameBoard[row][column].setPiece(new Rook(currentColor));
                    continue;
                }

                if (column == 1 || column == 6){
                    this.gameBoard[row][column].setPiece(new Knight(currentColor));
                    continue;
                }

                if (column == 2 || column == 5){
                    this.gameBoard[row][column].setPiece(new Bishop(currentColor));
                    continue;
                }

                if (column == 3){
                    this.gameBoard[row][column].setPiece(new Queen(currentColor));
                    continue;
                }

                this.gameBoard[row][column].setPiece(new King(currentColor));
            }
        }
    }
}
