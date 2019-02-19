package Figures;

import Abstracts.ChessPiece;
import Enums.Color;

/**
 *
 * @author xutkin00, xpolis03
 */

public class Pawn extends ChessPiece {

    public Pawn(Color color, int current_row, int current_column){
        super(color,'p', 1, current_row, current_column);
    }

    @Override
    protected void calculatePossibleMovements(){
    }
}
