package Figures;

import Abstracts.ChessPiece;
import Enums.Color;

/**
 *
 * @author xutkin00, xpolis03
 */

public class King extends ChessPiece {

    public King(Color color, int current_row, int current_column){
        super(color,'K', 10, current_row, current_column);
    }

    @Override
    public boolean move(int new_row, int new_column) {
        return false;
    }
}
