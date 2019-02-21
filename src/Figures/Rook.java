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

    public Rook(Color color, int current_row, int current_column){
        super(color,'V', 5, current_row, current_column);
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
