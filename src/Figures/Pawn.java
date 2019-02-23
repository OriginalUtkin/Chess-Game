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

        // Calculate possible extra movement from start pawn position
        if (this.startedPosition)
            if (this.color == Color.BLACK)
                possibleMovements.add(new Movement(this.currentRow - 2, this.currentColumn, false));

            else
                possibleMovements.add(new Movement(this.currentRow + 2, this.currentColumn, false));

            // Common paw move set depends on paw color
        if (this.color == Color.WHITE){
            if (this.currentRow + 1 <= 7)
                possibleMovements.add(new Movement(this.currentRow + 1, this.currentColumn, false));

            if(this.currentRow + 1 <= 7 && this.currentColumn + 1 <= 7)
                possibleMovements.add(new Movement(this.currentRow + 1, this.currentColumn + 1, true));

            if(this.currentRow - 1 >= 0 && this.currentColumn + 1 <= 7)
                possibleMovements.add(new Movement(this.currentRow - 1, this.currentColumn + 1, true));
        }else{
            if (this.currentRow - 1 >= 0)
                possibleMovements.add(new Movement(this.currentRow - 1, this.currentColumn, false));

            if(this.currentRow - 1 >= 0 && this.currentColumn - 1 >= 0)
                possibleMovements.add(new Movement(this.currentRow - 1, this.currentColumn - 1, true));

            if(this.currentRow + 1 <= 7 && this.currentColumn - 1 >= 0)
                possibleMovements.add(new Movement(this.currentRow + 1, this.currentColumn - 1, true));
        }

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
