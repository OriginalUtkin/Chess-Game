import Abstracts.ChessPiece;
import Board.Board;
import Enums.Color;
import Enums.Direction;
import Figures.*;

import java.util.ArrayList;
import java.util.Iterator;
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

        chessGame.setPiece(new King(Color.BLACK), 0, 0);

        // Simulate mouse button click. Now we have a chess piece from selected cell
        ChessPiece boardPiece = chessGame.getBoardPiece(0, 0);

        // Get all possible statements of current piece
        List<Movement> allPossibleMovements = new ArrayList<>(boardPiece.calculatePossibleMovements());

        // Sort all movements


        boolean horizontalLeft = false;
        boolean horizontalRight = false;

        boolean verticalTop = false;
        boolean verticalDown = false;

        boolean diagonalTopRight = false;
        boolean diagonalTopLeft = false;
        boolean diagonalDownRight = false;
        boolean diagonalDownLeft = false;

        Iterator<Movement> movementIterator = allPossibleMovements.iterator();

        while(movementIterator.hasNext()){

            Movement currentMovement = movementIterator.next();

            if (currentMovement.getDirection() == Direction.VERTICAL_UP){

                if (!verticalTop){

                    if (!chessGame.isPossible(currentMovement, boardPiece.getColor())){
                        verticalTop = true;
                        movementIterator.remove();
                    }

                }else{
                    if (verticalTop)
                        movementIterator.remove();
                }
            }

            else if (currentMovement.getDirection() == Direction.VERTICAL_DOWN){

                if (!verticalDown){

                    if (!chessGame.isPossible(currentMovement, boardPiece.getColor())){
                        verticalDown = true;
                        movementIterator.remove();
                    }

                }else{
                    if (verticalDown)
                        movementIterator.remove();
                }
            }

            else if (currentMovement.getDirection() == Direction.HORIZONTAL_RIGTH){

                if (!horizontalRight){

                    if (!chessGame.isPossible(currentMovement, boardPiece.getColor())){
                        horizontalRight = true;
                        movementIterator.remove();
                    }

                }else{
                    if (horizontalRight)
                        movementIterator.remove();
                }
            }

            else if (currentMovement.getDirection() == Direction.HORIZONTAL_LEFT){

                if (!horizontalLeft){

                    if (!chessGame.isPossible(currentMovement, boardPiece.getColor())){
                        horizontalLeft = true;
                        movementIterator.remove();
                    }

                }else{
                    if (horizontalLeft)
                        movementIterator.remove();
                }
            }

            else if (currentMovement.getDirection() == Direction.DIAGONAL_UP_LEFT){

                if (!diagonalTopLeft){

                    if (!chessGame.isPossible(currentMovement, boardPiece.getColor())){
                        diagonalTopLeft = true;
                        movementIterator.remove();
                    }

                }else{
                    if (diagonalTopLeft)
                        movementIterator.remove();
                }
            }

            else if (currentMovement.getDirection() == Direction.DIAGONAL_DOWN_RIGHT){

                if (!diagonalDownRight){

                    if (!chessGame.isPossible(currentMovement, boardPiece.getColor())){
                        diagonalDownRight = true;
                        movementIterator.remove();
                    }

                }else{
                    if (diagonalDownRight)
                        movementIterator.remove();
                }
            }

            else if (currentMovement.getDirection() == Direction.DIAGONAL_DOWN_LEFT){

                if (!diagonalDownLeft){

                    if (!chessGame.isPossible(currentMovement, boardPiece.getColor())){
                        diagonalDownLeft = true;
                        movementIterator.remove();
                    }

                }else{
                    if (diagonalDownLeft)
                        movementIterator.remove();
                }
            }

            else{
                if (!diagonalTopRight){

                    if (!chessGame.isPossible(currentMovement, boardPiece.getColor())){
                        diagonalTopRight = true;
                        movementIterator.remove();
                    }

                }else{
                    if (diagonalTopRight)
                        movementIterator.remove();
                }
            }
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
