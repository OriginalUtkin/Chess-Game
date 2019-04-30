package backend.Figures;

import backend.Abstracts.ChessPiece;
import backend.Enums.Color;

import java.util.ArrayList;
import java.util.List;


/**
 * Project: Chess game IJA project
 * File: King.java
 * Date: 27.04.2019
 * Authors: xutkin00 <xutkin00@stud.fit.vutbr.cz>
 *          xpolis03 <xpolis03@stud.fit.vutbr.cz>
 * Description: Class King represents a king chess piece and define all possible movements for this piece according
 * to the game rules.
 */

public class King extends ChessPiece {

    public King(Color color){
        super(color,'K', 10);
    }

    /**
     * Calculate possible movements from current position
     *
     * @return List of all possible movements
     */
    @Override
    public List<Movement> calculatePossibleMovements(){

        List<Movement> possibleMovements = new ArrayList<>();

        possibleMovements.addAll(super.getVerticalMovements(1));
        possibleMovements.addAll(super.getHorizontalMovements(1));
        possibleMovements.addAll(super.getDiagonalMovements(1));

        return possibleMovements;
    }
}
