package Figures;

import Abstracts.ChessPiece;
import Enums.Color;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xutkin00, xpolis03
 */

public class Pawn extends ChessPiece {
    boolean startedPosition;

    public Pawn(Color color){
        super(color,'p', 1);
        this.startedPosition = true;
    }

    @Override
    public List<Movement> calculatePossibleMovements(){
        // TODO: Implementation stuff -> Pawn could move to 1 or 2 cells from start position
        List<Movement> possibleMovements =  new ArrayList<>();

        return possibleMovements;
    }

    @Override
    public void movePiece(int new_x, int new_y){
        // TODO: Tests are needed

        /**
         * Move function for Pawn piece.
         */
        if (this.startedPosition)
            this.startedPosition = false;

        super.movePiece(new_x, new_y);

    }
}
