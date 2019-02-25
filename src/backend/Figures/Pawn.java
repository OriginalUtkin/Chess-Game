package backend.Figures;

import backend.Abstracts.ChessPiece;
import backend.Enums.Color;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xutkin00, xpolis03
 */

public class Pawn extends ChessPiece {
    private boolean startedPosition;

    public Pawn(Color color){
        super(color,'p', 1);
        this.startedPosition = true;
    }

    @Override
    public List<Movement> calculatePossibleMovements(){
        /**
         *  Pawn can only move forward one square
         *  Pawn can move forward two squares (if desired) on it's first move of the game
         *  When capturing other pieces, a pawn can only move forward diagonally one square
         */

        List<Movement> possibleMovements = new ArrayList<>();

        int maxPossibleMovements = 1;

        // Calculate possible extra movement from start pawn position
        if (this.startedPosition)
            maxPossibleMovements = 2;

        possibleMovements.addAll(super.getVerticalMovements(maxPossibleMovements));
        possibleMovements.addAll(super.getDiagonalMovements(1));

        return possibleMovements;
    }

    @Override
    public void movePiece(int new_x, int new_y){
        super.movePiece(new_x, new_y);
        this.startedPosition = false;
    }
}
