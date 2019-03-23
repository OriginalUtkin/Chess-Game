package controller;

import backend.Enums.Color;

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
