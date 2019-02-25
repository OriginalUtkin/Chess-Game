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

    public Knight(Color color){
        super(color,'J', 3);
    }

    @Override
    public List<Movement> calculatePossibleMovements(){

        List<Movement> possibleMovements =  new ArrayList<>();

        if (this.currentRow + 2 < 8 && this.currentColumn + 1 < 8)
            possibleMovements.add(new Movement(this.currentRow + 2, this.currentColumn + 1, false, null));

        if (this.currentRow + 2 < 8 && this.currentColumn - 1 >= 0)
            possibleMovements.add(new Movement(this.currentRow + 2, this.currentColumn - 1, false, null));

        if (this.currentRow - 2 >= 0 && this.currentColumn + 1 < 8)
            possibleMovements.add(new Movement(this.currentRow - 2, this.currentColumn + 1, false, null));

        if (this.currentRow - 2 >= 0 && this.currentColumn - 1 >= 0)
            possibleMovements.add(new Movement(this.currentRow - 2, this.currentColumn - 1, false, null));

        if (this.currentRow + 1 < 8 && this.currentColumn - 2 >= 0)
            possibleMovements.add(new Movement(this.currentRow + 1, this.currentColumn - 2, false, null));

        if (this.currentRow - 1 >= 0 && this.currentColumn - 2 >= 0)
            possibleMovements.add(new Movement(this.currentRow - 1, this.currentColumn - 2, false, null));

        if (this.currentRow + 1 < 8 && this.currentColumn + 2 < 8)
            possibleMovements.add(new Movement(this.currentRow + 1, this.currentColumn + 2, false, null));

        if (this.currentRow - 1 >= 0 && this.currentColumn + 2 < 8)
            possibleMovements.add(new Movement(this.currentRow - 1, this.currentColumn + 2, false, null));

        return possibleMovements;
    }
}
