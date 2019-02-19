package Figures;

import Abstracts.ChessPiece;
import Enums.Color;

/**
 *
 * @author xutkin00, xpolis03
 */

public class Queen extends ChessPiece {

    public Queen(Color color, int current_row, int current_column){
        super(color,'D', 9, current_row, current_column);
    }

    @Override
    protected void calculatePossibleMovements(){
    }
}
