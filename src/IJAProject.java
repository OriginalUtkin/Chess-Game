import Abstracts.ChessPiece;
import Board.Board;
import Enums.Color;
import Figures.King;
import Figures.Movement;
import Figures.Rook;

import java.util.ArrayList;
import java.util.List;

public class IJAProject {

    public static void main(String args[]) {

        /**
         * PSEUDO CODE:
         * Create chess piece (just for debugging. Pieces will be created during board init)
         * Get possible moves
         * Remove from possible moves variable cells which has been already obtained by other piece with the same color or add new possible movements in case of pawn
         * Call move function with new position parameters
         * Render frontend when it is done
         */

        Game chessGame = new Game();

        /** TODO seems as fuck. refactor it. Coordinates will be set right from Cell */
        chessGame.setPiece(new King(Color.BLACK), 0, 0);

        // Simulate mouse button click
        ChessPiece boardPiece = chessGame.getBoardCell(0, 0);

        // Get all possible statements of current piece and show them on GUI
        List<Movement> possibleMovements = new ArrayList<>(boardPiece.calculatePossibleMovements()) ;

        if (possibleMovements.size() == 0){
            System.out.println("Imma so stoned and cannot move. Help me pls");
        }else{
            System.out.println("Move me");
        }

        /**
         * Move string:
         * getBoardCell -> to string (src)
         * if returned piece not Pawn -> To string
         * Before calling setPiece get dst cell and call toString (dst)
         */
        System.out.println();
    }
}
