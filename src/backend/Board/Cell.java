package backend.Board;
import backend.Abstracts.ChessPiece;
import backend.Enums.Color;


/**
 *
 * @author xutkin00, xpolis03
 */

public class Cell {

    final private Color color;
    final private char letter;
    final private int number;
    final private int row;
    final private int column;

    private ChessPiece piece;


    Cell(Color color, ChessPiece piece, char letter, int number, int row, int column){
        this.color = color;
        this.letter = letter;
        this.number = number;
        this.row = row;
        this.column = column;

        this.piece = piece;

    }

    public ChessPiece getPiece(){
        /***
         * Return piece that is currently staying on cell object.
         *
         * @return ChessPiece object - piece on cell
         */
        return this.piece;
    }


    public void setPiece(ChessPiece piece){
        /**
         * Set chess piece on cell. If piece is represented by ChessPiece object recalculate piece coordinates.
         *
         * @param piece object on the game board
         *
         * @return void
         */
        this.piece = piece;

        if (piece != null){
            this.piece.setRow(this.row);
            this.piece.setColumn(this.column);
        }
    }

    public boolean isFree(){
        /**
         * Check if any chess piece is staying on the cell.
         *
         * @return boolean; True in case that cell is empty. Otherwise False is returned
         */
        return this.piece == null;
    }

    public String getFullString() {
        /**
         * Return full representation of cell in string format.
         *
         * @return string in format cell_number + cell_letter + cell_color
         */
        return String.valueOf(this.number) + letter + this.color;
    }

    @Override
    public String toString(){
        /**
         * Return representation of cell in string format.
         *
         * @return string in format cell_letter + cell_number
         */
        return letter + String.valueOf(this.number);
    }

    public char returnLetter(){
        return letter;
    }
    public String returnNumber(){
        return String.valueOf(this.number);
    }
}
