package controller;

import backend.Abstracts.ChessPiece;
import backend.Enums.Color;

public class Turn {

    final private int sourceRow;
    final private int sourceColumn;

    final private int destinationRow;
    final private int destinationColumn;

    final private ChessPiece movedPiece;
    final private ChessPiece beatenPeace;

    final private Color playerColor;

    public Turn(
            int sourceRow,
            int sourceColumn,
            int destinationRow,
            int destinationColumn,
            ChessPiece movedPiece,
            ChessPiece beatenPeace
    ){
        this.sourceRow = sourceRow;
        this.sourceColumn = sourceColumn;
        this.destinationRow = destinationRow;
        this.destinationColumn = destinationColumn;
        this.movedPiece = movedPiece;
        this.beatenPeace = beatenPeace;

        this.playerColor = movedPiece.getColor();
    }

    public ChessPiece getBeatenPeace() {
        return beatenPeace;
    }

    public ChessPiece getMovedPiece() {
        return movedPiece;
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

    public Color getPlayerColor(){
        return this.playerColor;
    }

    @Override
    public String toString(){
        return "Turn representation";
    }
}
