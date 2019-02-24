import Abstracts.ChessPiece;
import Board.Board;
import Enums.Color;
import Enums.Direction;
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

    public boolean isPossible(Movement movement, final Color pieceColor){
        /**
         * Check if movement of chess piece is possible.
         *
         * Chess piece can move to destination cell in case:
         * 1) Destination cell is free
         * 2) Destination cell isn't free but contains enemy player chess piece
         *
         * Check if additionalCheck flag is set. If so, that means that this movement should be
         * processed using other rules (pawn diagonal movement, pawn vertical movement):
         *
         * Diagonal:
         * 1) Destination cell shouldn't be free
         * 2) Same as 2 point above
         *
         * Vertical:
         * 1) Destination cell SHOULD be free
         *
         * @param movement - possible movement of chess piece
         * @param color - color of chess piece
         *
         * @return true if chess piece movement is possible, false otherwise
         */

        final Cell dstCell = this.gameBoard.gameBoard[movement.get_x()][movement.get_y()];

        if (movement.getAdditionalCheck()) {

            if (movement.getDirection() == Direction.DIAGONAL_UP_LEFT || movement.getDirection() == Direction.DIAGONAL_UP_RIGHT)
                return !dstCell.isFree() && dstCell.getPiece().getColor() != pieceColor;

            if (movement.getDirection() == Direction.VERTICAL_UP)
                return dstCell.isFree();
        }

        return dstCell.isFree() || dstCell.getPiece().getColor() != pieceColor;
    }

//    public boolean isPossibleSpecial(Movement movement, final Color pieceColor){
//        return false;
//    }
}

