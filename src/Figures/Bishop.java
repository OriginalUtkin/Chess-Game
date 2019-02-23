package Figures;

import Abstracts.ChessPiece;
import Enums.Color;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xutkin00, xpolis03
 */

public class Bishop extends ChessPiece {

    public Bishop(Color color){
        super(color,'S', 3);
    }

    @Override
    public List<Movement> calculatePossibleMovements(){
        List<Movement> possibleMovements =  new ArrayList<>(super.getDiagonalMovements(7));

        return possibleMovements;
    }

}
