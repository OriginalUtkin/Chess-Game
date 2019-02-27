package backend.Figures;

import backend.Abstracts.ChessPiece;
import backend.Enums.Color;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xutkin00, xpolis03
 */

public class Rook extends ChessPiece {

    public Rook(Color color){
        super(color,'V', 5);
    }

    @Override
    public List<Movement> calculatePossibleMovements(){

        List<Movement> possibleMovements =  new ArrayList<>();

        possibleMovements.addAll(super.getVerticalMovements(7));
        possibleMovements.addAll(super.getHorizontalMovements(7));

        return possibleMovements;
    }
}
