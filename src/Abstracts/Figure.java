package Abstracts;

import Enums.Color;

/**
 *
 * @author xutkin00, xpolis03
 */

abstract public class Figure {

    private Color color;

    private char abbreviation;

    private int value;

    private int current_row;
    private int currnet_column;

    @Override
    public String toString() {
        //TODO: implementation is needed
        return "";
    }

    public abstract boolean move(int new_row, int new_column);
}
