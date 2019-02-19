package Board;
import Abstracts.Figure;
import Enums.Color;


/**
 *
 * @author xutkin00, xpolis03
 */

public class Cell {

    private Color color;
    private Figure figure;
    private char letter;
    private int number;

    Cell(Color color, Figure figure, char letter, int number){
        this.color = color;
        this.figure = figure;
        this.letter = letter;
        this.number = number;

    }

    public Figure getFigure(){
        return this.figure;
    }

    public void setFigure(Figure figure){
        this.figure = figure;
    }

    public boolean isFree(){
        /**
         * Check if any chess piece is staying on the cell
         *
         * @return boolean; True in case that cell is empty. Otherwise False is returned
         */
        return this.figure == null ? true : false;
    }

    @Override
    public String toString() {
        return String.valueOf(this.number) + letter + this.color;
    }
}
