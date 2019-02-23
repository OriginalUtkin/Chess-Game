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

        List<Movement> possibleMovements = new ArrayList<>();

        possibleMovements.addAll(super.getVerticalMovements(1));
        possibleMovements.addAll(super.getHorizontalMovements(1));
        possibleMovements.addAll(super.getDiagonalMovements(1));

        return possibleMovements;
    }
}
