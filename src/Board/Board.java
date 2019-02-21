package Board;
import Enums.Color;

/**
 *
 * @author xutkin00, xpolis03
 */

public class Board {
    public Cell[][] gameBoard;

    public Board(){
        this.gameBoard = new Cell[8][8];
        this.initBoardCells();
    }

    private void initBoardCells(){
        /**
         * Initialize the board game object without figures on it.
         */


        char[] cellLetters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

        for (int row = 0; row < 8; row++){

            Color current_cell_color = (row+1)%2 == 0 ? Color.WHITE : Color.BLACK;

            for (int column = 0; column < 8; column++){

                this.gameBoard[row][column] = new Cell(current_cell_color, null, cellLetters[column], row+1, row, column);
                current_cell_color = current_cell_color == Color.WHITE ? Color.BLACK : Color.WHITE;
            }
        }

    }

    private void initFigures(){

    }

}
