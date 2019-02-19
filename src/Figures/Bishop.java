package Figures;

import Abstracts.ChessPiece;
import Enums.Color;

/**
 *
 * @author xutkin00, xpolis03
 */

public class Bishop extends ChessPiece {

    public Bishop(Color color, int current_row, int current_column){
        super(color,'S', 3, current_row, current_column);
    }

    @Override
    public boolean move(int new_row, int new_column) {
        return false;
    }
}
