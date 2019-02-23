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

    public ChessPiece getBoardCell(int x, int y){

        if (!this.gameBoard.gameBoard[x][y].isFree())
            return this.gameBoard.gameBoard[x][y].getPiece();

        else
            return null;
    }

    public void setPiece(ChessPiece piece, int x, int y){
        // TODO: save previous gameboard for redo / undo operation
        this.gameBoard.gameBoard[x][y].setPiece(piece);
    }

    public boolean isPossible(final Movement movement, final Color pieceColor){

        final Cell dstCell = this.gameBoard.gameBoard[movement.get_x()][movement.get_y()];

        if (dstCell.isFree())
            return true;

        // Destination cell contains a chess piece of other player
        if (dstCell.getPiece().getColor() != pieceColor)
            return true;

        return false;
    }


}
