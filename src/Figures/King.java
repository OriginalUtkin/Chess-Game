package Figures;

import Abstracts.ChessPiece;
import Enums.Color;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author xutkin00, xpolis03
 */

public class King extends ChessPiece {

    public King(Color color, int current_row, int current_column){
        super(color,'K', 10, current_row, current_column);
        this.calculatePossibleMovements();
    }

    @Override
    public List<Movement> calculatePossibleMovements(){
        /**
         * Calculate possible movements from current position
         *
         * @return List of all possible movements
         */

        List<Movement> possibleMovements =  new ArrayList<>();
//        TODO: refactor this bullshit- > add new method for calculating horizontal/ vertical coordinates


        if (super.current_row - 1 >= 0)
            possibleMovements.add(new Movement(super.current_row - 1, super.current_column));

        if (super.current_row + 1 <= 7)
            possibleMovements.add(new Movement(super.current_row + 1, super.current_column));

        if (super.current_column - 1 >= 0)
            possibleMovements.add(new Movement(super.current_row , super.current_column - 1));

        if (super.current_row + 1 <= 7)
            possibleMovements.add(new Movement(super.current_row , super.current_column + 1));

        if (super.current_row + 1 <= 7 && super.current_column + 1 <=7)
            possibleMovements.add(new Movement(super.current_row + 1, super.current_column + 1));

        if (super.current_row + 1 <= 7 && super.current_column - 1 >= 0)
            possibleMovements.add(new Movement(super.current_row + 1, super.current_column - 1));

        if (super.current_row - 1 >= 0 && super.current_column + 1 <= 7)
            possibleMovements.add(new Movement(super.current_row - 1, super.current_column + 1));

        if (super.current_row - 1 >= 0 && super.current_column - 1 >= 0)
            possibleMovements.add(new Movement(super.current_row - 1, super.current_column - 1));

        return possibleMovements;

    }

}
