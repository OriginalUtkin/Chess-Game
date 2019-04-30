package backend.Figures;

import backend.Abstracts.ChessPiece;
import backend.Enums.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: Chess game IJA project
 * File: Knight.java
 * Date: 27.04.2019
 * Authors: xutkin00 <xutkin00@stud.fit.vutbr.cz>
 *          xpolis03 <xpolis03@stud.fit.vutbr.cz>
 * Description: Class Knight represents a knight chess piece and define all possible movements for this piece according
 * to the game rules
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
