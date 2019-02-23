import Abstracts.ChessPiece;
import Board.Board;
import Enums.Color;
import Figures.Movement;
import Board.Cell;

import java.util.ArrayList;
import java.util.List;

public class Game {

    Board gameBoard;

//    List<Board> gameStatements;

    public Game(){
        this.gameBoard = new Board();
//        this.gameStatements = new ArrayList<Board>();

    }

    public void gameState(){

    }

    public ChessPiece getBoardPiece(final int x, final int y){
        /**
         * Return chess piece that is staying on cell with coordinates x and y.
         *
         * @param int x - row coordinate; array index 0 - 7
         * @param int y - column coordinate; array index 0 - 7
         *
         * @return ChessPiece object if chess piece is staying on selected cell; null otherwise
         */
        return this.gameBoard.gameBoard[x][y].getPiece();
    }

    public void setPiece(ChessPiece piece, int x, int y){
        // TODO: save previous gameboard for redo / undo operation
        this.gameBoard.gameBoard[x][y].setPiece(piece);
    }

    public boolean isPossible(final Movement movement, final Color pieceColor){
        /**
         * Check if movement of chess piece is possible.
         *
         * Chess piece can move to destination cell in case:
         * 1) Destination cell is free
         * 2) Destination cell isn't free but contains enemy player chess piece
         *
         * @param Movement movement - possible movement of chess piece
         * @param Color color - color of chess piece
         *
         * @return true if chess piece movement is possible, false otherwise
         */
        final Cell dstCell = this.gameBoard.gameBoard[movement.get_x()][movement.get_y()];

        if (dstCell.isFree())
            return true;

        // Destination cell contains a chess piece of other player
        if (dstCell.getPiece().getColor() != pieceColor)
            return true;

        return false;
    }

    public Movement newPawnMovement(int currentPawnX, int currentPawnY){

        return new Movement(0, 0);

    }


}
