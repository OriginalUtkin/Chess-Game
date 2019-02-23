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

            if (this.currentRow + counter <= 7)
                possibleMovements.add(new Movement(currentRow + counter, currentColumn));

            if (this.currentRow - counter >= 0)
                possibleMovements.add(new Movement(currentRow - counter, currentColumn));

            if (this.currentColumn + counter <= 7)
                possibleMovements.add(new Movement(currentRow, currentColumn + counter));

            if (this.currentColumn - counter  >= 0)
                possibleMovements.add(new Movement(currentRow, currentColumn - counter));
        }

        return possibleMovements;
    }
}
