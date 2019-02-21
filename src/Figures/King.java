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

    public King(Color color, int current_row, int current_column){
        super(color,'K', 10, current_row, current_column);
        this.calculatePossibleMovements();
    }

    @Override
    protected void calculatePossibleMovements(){
        /**
         * Calculate possible movements from current position
         *
         * @return List of all possible movements
         */

        this.possibleMovements.clear();

        List<Movement> possibleMovements = new ArrayList<Movement>();

        this.possibleMovements.add(new Movement(super.current_row -1, super.current_column));
        this.possibleMovements.add(new Movement(super.current_row + 1, super.current_column));

        this.possibleMovements.add(new Movement(super.current_row , super.current_column + 1));
        this.possibleMovements.add(new Movement(super.current_row , super.current_column - 1));

        this.possibleMovements.add(new Movement(super.current_row + 1, super.current_column+1));
        this.possibleMovements.add(new Movement(super.current_row + 1, super.current_column - 1));

        this.possibleMovements.add(new Movement(super.current_row - 1, super.current_column + 1));
        this.possibleMovements.add(new Movement(super.current_row - 1, super.current_column -1));

    }

}
