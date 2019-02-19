package Abstracts;

import Enums.Color;

/**
 *
 * @author xutkin00, xpolis03
 */

abstract public class ChessPiece {

    private Color color;

    private char abbreviation;

    private int value;

    private int current_row;
    private int currnet_column;

    public ChessPiece(Color color, char abbreviation, int value, int current_row, int currnet_column){
        this.color = color;
        this.abbreviation = abbreviation;
        this.value = value;
        this.current_row = current_row;
        this.currnet_column = currnet_column;
    }

    @Override
    public String toString() {
        //TODO: implementation is needed
        return "";
    }

    public abstract boolean move(int new_row, int new_column);
}
