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
    private boolean startedPosition;

    public Pawn(Color color){
        super(color,'p', 1);
        this.startedPosition = true;
    }

    @Override
    public List<Movement> calculatePossibleMovements(){

        List<Movement> possibleMovements =  new ArrayList<>();

        // Calculate possible extra movement from start pawn position
        if (this.startedPosition)
            if (super.color == Color.BLACK)
                possibleMovements.add(new Movement(super.currentRow - 2, super.currentColumn, false));

            else
                possibleMovements.add(new Movement(super.currentRow + 2, super.currentColumn, false));

            // Common paw move set depends on paw color
        if (super.color == Color.WHITE){
            if (super.currentRow + 1 <= 7)
                possibleMovements.add(new Movement(super.currentRow + 1, super.currentColumn, false));

            if(super.currentRow + 1 <= 7 && super.currentColumn + 1 <= 7)
                possibleMovements.add(new Movement(super.currentRow + 1, super.currentColumn + 1, true));

            if(super.currentRow - 1 >= 0 && super.currentColumn + 1 <= 7)
                possibleMovements.add(new Movement(super.currentRow - 1, super.currentColumn + 1, true));
        }else{
            if (super.currentRow - 1 >= 0)
                possibleMovements.add(new Movement(super.currentRow - 1, super.currentColumn, false));

            if(super.currentRow - 1 >= 0 && super.currentColumn - 1 >= 0)
                possibleMovements.add(new Movement(super.currentRow - 1, super.currentColumn - 1, true));

            if(super.currentRow + 1 <= 7 && super.currentColumn - 1 >= 0)
                possibleMovements.add(new Movement(super.currentRow + 1, super.currentColumn - 1, true));
        }

        return possibleMovements;
    }
}
