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

            if (this.current_row + counter <= 7)
                possibleMovements.add(new Movement(current_row + counter, current_column));

            if (this.current_row - counter >= 0)
                possibleMovements.add(new Movement(current_row - counter, current_column));

            if (this.current_column + counter <= 7)
                possibleMovements.add(new Movement(current_row, current_column + counter));

            if (this.current_column - counter  >= 0)
                possibleMovements.add(new Movement(current_row, current_column - counter));
        }

        return possibleMovements;
    }
}
