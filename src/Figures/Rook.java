package Figures;

import Abstracts.ChessPiece;
import Enums.Color;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xutkin00, xpolis03
 */

public class Rook extends ChessPiece {

    public Rook(Color color){
        super(color,'V', 5);
        this.calculatePossibleMovements();
    }

    @Override
    public List<Movement> calculatePossibleMovements(){

        List<Movement> possibleMovements =  new ArrayList<>();

        for(int counter = 1; counter < 8; counter++){

            if (super.currentRow + counter <= 7)
                possibleMovements.add(new Movement(super.currentRow + counter, super.currentColumn, false));

            if (super.currentRow - counter >= 0)
                possibleMovements.add(new Movement(super.currentRow - counter, super.currentColumn, false));

            if (super.currentColumn + counter <= 7)
                possibleMovements.add(new Movement(super.currentRow, super.currentColumn + counter, false));

            if (super.currentColumn - counter  >= 0)
                possibleMovements.add(new Movement(super.currentRow, super.currentColumn - counter, false));
        }

        return possibleMovements;
    }
}
