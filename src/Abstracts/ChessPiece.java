package Abstracts;

import Enums.Color;

/**
 *
 * @author xutkin00, xpolis03
 */

abstract public class ChessPiece {

    protected Color color;

    protected char abbreviation;

    protected int value;

    protected int current_row;
    protected int current_column;

    public ChessPiece(Color color, char abbreviation, int value, int current_row, int currnet_column){
        this.color = color;
        this.abbreviation = abbreviation;
        this.value = value;
        this.current_row = current_row;
        this.current_column = currnet_column;
    }

    @Override
    public String toString() {
        //TODO: implementation is needed
        return "";
    }

    public abstract boolean move(int new_row, int new_column);
}
