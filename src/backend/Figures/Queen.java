package backend.Figures;

import backend.Abstracts.ChessPiece;
import backend.Enums.Color;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xutkin00, xpolis03
 */

public class Queen extends ChessPiece {

    public Queen(Color color){
        super(color,'D', 9);
    }

    @Override
    public List<Movement> calculatePossibleMovements(){

        List<Movement> possibleMovements =  new ArrayList<>();

        possibleMovements.addAll(super.getDiagonalMovements(7));
        possibleMovements.addAll(super.getVerticalMovements(7));
        possibleMovements.addAll(super.getHorizontalMovements(7));

        return possibleMovements;
    }
}
