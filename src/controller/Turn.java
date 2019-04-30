package controller;

import backend.Enums.Color;

/**
 * Project: Chess game IJA project
 * File: Turn.java
 * Date: 27.04.2019
 * Authors: xutkin00 <xutkin00@stud.fit.vutbr.cz>
 *          xpolis03 <xpolis03@stud.fit.vutbr.cz>
 * Description: Class that represents a Turn object. This class is used for recreate made turn for redo and undo
 * operations
 */

public class Turn {

    final private int sourceRow;
    final private int sourceColumn;

    final private int destinationRow;
    final private int destinationColumn;

    final private String abbreviation;
    final private String beaten;

    private Color color;
    private boolean transform;
    private String transformTo;

    /**
     * Base constructor of the turn object.
     * @param sourceRow previous row position of the chess piece
     * @param sourceColumn previous column position of the chess piece
     * @param destinationRow new row position of the chess piece
     * @param destinationColumn new column position of the chess piece
     * @param abbreviation abbreviation of the chess piece which was moved
     * @param beaten abbreviation of the chess piece which was beaten during the turn
     */
    Turn(
            int sourceRow,
            int sourceColumn,
            int destinationRow,
            int destinationColumn,
            String abbreviation,
            String beaten
    ){
        this.sourceRow = sourceRow;
        this.sourceColumn = sourceColumn;
        this.destinationRow = destinationRow;
        this.destinationColumn = destinationColumn;
        this.abbreviation = abbreviation;
        this.beaten = beaten;
        this.transform = false;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public Color getColor(){
        return this.color;
    }

    void setTransform() {
        this.transform = true;
    }

    public String getTransformTo() {
        return transformTo;
    }

    public boolean isTransform() {
        return transform;
    }

    void setTransformTo(String transformTo) {
        this.transformTo = transformTo;
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

    public String getBeaten() {
        return this.beaten;
    }

    @Override
    public String toString(){
        return "[" + abbreviation + "][SRC] row:" + sourceRow + " column:" + sourceColumn + "  [DST] row:" + destinationRow
                + " column: " + destinationColumn;
    }
}
