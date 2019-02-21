import Abstracts.ChessPiece;
import Board.Board;
import Enums.Color;
import Figures.King;

public class IJAProject {

    public static void main(String args[]) {
        Board gameBoard = new Board();

        gameBoard.gameBoard[0][0].setPiece(new King(Color.BLACK,0, 0));
        ChessPiece king_black = gameBoard.gameBoard[0][0].getPiece();
        System.out.println();
    }
}
