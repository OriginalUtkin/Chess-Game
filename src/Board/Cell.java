package Board;
import Abstracts.ChessPiece;
import Enums.Color;


/**
 *
 * @author xutkin00, xpolis03
 */

public class Cell {

    private Color color;
    private ChessPiece piece;
    private char letter;
    private int number;
    private int row;
    private int column;

    Cell(Color color, ChessPiece piece, char letter, int number, int row, int column){
        this.color = color;
        this.piece = piece;
        this.letter = letter;
        this.number = number;
        this.row = row;
        this.column = column;

    }

    public ChessPiece getPiece(){
        return this.piece;
    }

    public void setPiece(ChessPiece piece){

        this.piece = piece;

        this.piece.setRow(this.row);
        this.piece.setColumn(this.column);
    }

    public boolean isFree(){
        /**
         * Check if any chess piece is staying on the cell
         *
         * @return boolean; True in case that cell is empty. Otherwise False is returned
         */
        return this.piece == null ? true : false;
    }

    @Override
    public String toString() {
        return String.valueOf(this.number) + letter + this.color;
    }
}
