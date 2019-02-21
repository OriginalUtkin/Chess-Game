import Abstracts.ChessPiece;
import Board.Board;
import Enums.Color;
import Figures.King;
import Figures.Movement;

import java.util.ArrayList;
import java.util.List;

public class IJAProject {

    public static void main(String args[]) {
        Board gameBoard = new Board();

        gameBoard.gameBoard[0][0].setPiece(new King(Color.BLACK,0, 0));

        ChessPiece king_black = gameBoard.gameBoard[0][0].getPiece();
        List<Movement> possibleMovments = new ArrayList<>(king_black.getPossiblePositions());


        System.out.println();
    }
}
