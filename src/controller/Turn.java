package controller;

import backend.Abstracts.ChessPiece;
import backend.Enums.Color;

public class Turn {

    final private int sourceRow;
    final private int sourceColumn;

    final private int destinationRow;
    final private int destinationColumn;

    final private String abbreviation;

    public Turn(
            int sourceRow,
            int sourceColumn,
            int destinationRow,
            int destinationColumn,
            String abbreviation
    ){
        this.sourceRow = sourceRow;
        this.sourceColumn = sourceColumn;
        this.destinationRow = destinationRow;
        this.destinationColumn = destinationColumn;
        this.abbreviation = abbreviation;
    }

    public int getDestinationColumn() {
        return destinationColumn;
    }

    public int getDestinationRow() {
        return destinationRow;
    }

    public int getSourceColumn() {
        return sourceColumn;
    }

    public int getSourceRow() {
        return sourceRow;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    @Override
    public String toString(){
        return "[" + abbreviation + "][SRC] row:" + sourceRow + " column:" + sourceColumn + "  [DST] row:" + destinationRow
                + " column: " + destinationColumn;
    }
}
