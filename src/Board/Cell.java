package Board;
import Enums.Color;

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

    @Override
    public String toString() {
        return String.valueOf(this.number) + letter + this.color;
    }
}
