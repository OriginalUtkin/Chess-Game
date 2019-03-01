package controller;

import backend.Abstracts.ChessPiece;

public class Turn {

    private int sourceRow;
    private int sourceColumn;

    private int destinationRow;
    private int destinationColumn;

    private ChessPiece movedPiece;
    private ChessPiece beatenPeace;

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

    @Override
    public String toString(){
        return "Turn representation";
    }
}
