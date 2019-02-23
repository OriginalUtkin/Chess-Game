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

    public King(Color color){
        super(color,'K', 10);
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

        if (super.currentRow - 1 >= 0)
            possibleMovements.add(new Movement(super.currentRow - 1, super.currentColumn));

        if (super.currentRow + 1 <= 7)
            possibleMovements.add(new Movement(super.currentRow + 1, super.currentColumn));

        if (super.currentColumn - 1 >= 0)
            possibleMovements.add(new Movement(super.currentRow, super.currentColumn - 1));

        if (super.currentRow + 1 <= 7)
            possibleMovements.add(new Movement(super.currentRow, super.currentColumn + 1));

        if (super.currentRow + 1 <= 7 && super.currentColumn + 1 <=7)
            possibleMovements.add(new Movement(super.currentRow + 1, super.currentColumn + 1));

        if (super.currentRow + 1 <= 7 && super.currentColumn - 1 >= 0)
            possibleMovements.add(new Movement(super.currentRow + 1, super.currentColumn - 1));

        if (super.currentRow - 1 >= 0 && super.currentColumn + 1 <= 7)
            possibleMovements.add(new Movement(super.currentRow - 1, super.currentColumn + 1));

        if (super.currentRow - 1 >= 0 && super.currentColumn - 1 >= 0)
            possibleMovements.add(new Movement(super.currentRow - 1, super.currentColumn - 1));

        return possibleMovements;

    }

}
