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

        List<Movement> possibleMovements = new ArrayList<>();
        int maxPossibleMovements = 1;

        // Calculate possible extra movement from start pawn position
        if (this.startedPosition)
            maxPossibleMovements = 2;

        possibleMovements.addAll(super.getVerticalMovements(maxPossibleMovements));
        possibleMovements.addAll(super.getDiagonalMovements(1));

        return possibleMovements;
    }
}
