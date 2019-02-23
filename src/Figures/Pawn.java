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

        List<Movement> possibleMovements =  new ArrayList<>();

        if (this.startedPosition)
            possibleMovements.add(new Movement(this.currentRow + 2, this.currentColumn));

        if (this.currentRow + 1 <= 7)
            possibleMovements.add(new Movement(this.currentRow + 1, this.currentColumn));

        if(this.currentRow + 1 <= 7 && this.currentColumn + 1 <= 7)
            possibleMovements.add(new Movement(this.currentRow + 1, this.currentColumn + 1));

        if(this.currentRow - 1 >= 0 && this.currentColumn + 1 <= 7)
            possibleMovements.add(new Movement(this.currentRow - 1, this.currentColumn + 1));

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
