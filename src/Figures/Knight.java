package Figures;

import Abstracts.ChessPiece;
import Enums.Color;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xutkin00, xpolis03
 */

public class Knight extends ChessPiece {

    public Knight(Color color, int current_row, int current_column){
        super(color,'J', 3, current_row, current_column);
    }

    @Override
    public List<Movement> calculatePossibleMovements(){
        List<Movement> possibleMovements =  new ArrayList<>();

        return possibleMovements;
    }
}
