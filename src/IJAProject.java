import Abstracts.ChessPiece;
import Board.Board;
import Enums.Color;
import Figures.*;

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

        chessGame.setPiece(new Pawn(Color.BLACK), 1, 0);
        chessGame.setPiece(new Pawn(Color.BLACK), 0, 1);
        chessGame.setPiece(new Pawn(Color.BLACK), 1, 1);

        chessGame.setPiece(new King(Color.BLACK), 0, 0);

        // Simulate mouse button click. Now we have a chess piece from selected cell
        ChessPiece boardPiece = chessGame.getBoardPiece(0, 0);

        // Get all possible statements of current piece
        List<Movement> allPossibleMovements = new ArrayList<>(boardPiece.calculatePossibleMovements());

        List<Movement> horizontalLeft = new ArrayList<>();
        List<Movement> horizontalRight = new ArrayList<>();

        List<Movement> verticalTop = new ArrayList<>();
        List<Movement> verticalDown = new ArrayList<>();

        List<Movement> diagonalTopRight = new ArrayList<>();
        List<Movement> diagonalTopLeft = new ArrayList<>();
        List<Movement> diagonalDownRight = new ArrayList<>();
        List<Movement> diagonalDownLeft = new ArrayList<>();

        for (Movement movement : allPossibleMovements){

        }

//        chessGame.isPossible(possibleMovements, boardPiece.getColor());

//        // Remove moves from possible moves for currently selected chess piece which couldn't be done
//        for(int counter = 0; counter < possibleMovements.size(); counter++){
//
//            if (!chessGame.isPossible(possibleMovements.get(counter), boardPiece.getColor()))
//                possibleMovements.remove(counter);
//        }

        // Return PossibleMoves to GUI and show them on board. Now we're waiting for new input from player.

        // if player choose a cell which isn't represented in PossibleMovements List as dst cell -> clear selected cell and give him option to choose another cell
        // else change x and y coordinates for chess piece , set pointer to piece in src cell to null dst pointer to chessPiece. Current player Turn is done -> return Turn string

        /**
         * Move string:
         * if returned piece not Pawn -> To string
         * Before calling setPiece get dst cell and call toString (dst)
         */
        System.out.println();
    }

}
