package Abstracts;

import Board.Board;
import Board.Cell;
import Enums.Color;
import com.sun.xml.internal.xsom.impl.scd.Iterators;

import java.util.ArrayList;
import java.util.List;

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

    protected List<Cell> possiblePositions;

    public ChessPiece(Color color, char abbreviation, int value, int current_row, int current_name){
        this.color = color;
        this.abbreviation = abbreviation;
        this.value = value;
        this.current_row = current_row;
        this.current_column = current_name;
        this.possiblePositions = new ArrayList<>();
    }

    @Override
    public String toString() {
        //TODO: implementation is needed
        return "";
    }

    public List<Cell> getPossiblePositions(){
        return this.possiblePositions;
    }

    protected abstract void calculatePossibleMovements();
}
