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


    /**
     * Main constructor for game cell object.
     *
     * @param color color of the current cell
     * @param piece chess piece that is staying on the cell
     * @param letter letter of the cell that represents cell column
     * @param number number of the cell that represents cell row
     * @param row cell row coordinate in the board array
     * @param column cell column coordinate in the board array
     */
    Cell(Color color, ChessPiece piece, char letter, int number, int row, int column){
        this.color = color;
        this.letter = letter;
        this.number = number;
        this.row = row;
        this.column = column;

        this.piece = piece;
    }



    /***
     * Return piece that is currently staying on cell object.
     *
     * @return ChessPiece object - piece on cell
     */
    public ChessPiece getPiece(){
        return this.piece;
    }



    /**
     * Set chess piece on cell. If piece is represented by ChessPiece object recalculate piece coordinates.
     *
     * @param piece object on the game board
     *
     */
    public void setPiece(ChessPiece piece){
        this.piece = piece;

        if (piece != null){
            this.piece.setRow(this.row);
            this.piece.setColumn(this.column);
        }
    }



    /**
     * Check if any chess piece is staying on the cell.
     *
     * @return boolean; True in case that cell is empty. Otherwise False is returned
     */
    public boolean isFree(){
        return this.piece == null;
    }



    /**
     * Return full representation of cell in string format.
     *
     * @return string in format cell_number + cell_letter + cell_color
     */
    public String getFullString() {
        return String.valueOf(this.number) + letter + this.color;
    }



    /**
     * Return representation of cell in string format.
     *
     * @return string in format cell_letter + cell_number
     */
    @Override
    public String toString(){
        return letter + String.valueOf(this.number);
    }


    /**
     * Getter for cell letter (row).
     *
     * @return char symbol which represents selected board cell
     */
    public char returnLetter(){
        return letter;
    }



    /**
     * Getter for cell number (column)
     *
     * @return cell number in String format which represents selected board cell
     */
    public String returnNumber(){
        return String.valueOf(this.number);
    }
}
