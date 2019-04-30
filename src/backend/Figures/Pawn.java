package backend.Figures;

import backend.Abstracts.ChessPiece;
import backend.Enums.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: Chess game IJA project
 * File: Pawn.java
 * Date: 27.04.2019
 * Authors: xutkin00 <xutkin00@stud.fit.vutbr.cz>
 *          xpolis03 <xpolis03@stud.fit.vutbr.cz>
 * Description: Class pawn represents a king chess piece and define all possible movements for this piece according
 * to the game rules.
 */

public class Pawn extends ChessPiece {

    public Pawn(Color color){
        super(color,'p', 1);
    }

    /**
     *  Pawn can only move forward one square
     *  Pawn can move forward two squares (if desired) on it's first move of the game
     *  When capturing other pieces, a pawn can only move forward diagonally one square
     */
    @Override
    public List<Movement> calculatePossibleMovements(){


        List<Movement> possibleMovements = new ArrayList<>();

        int maxPossibleMovements = 1;

        // Calculate possible extra movement from start pawn position
        if (super.startedPosition)
            maxPossibleMovements = 2;

        possibleMovements.addAll(super.getVerticalMovements(maxPossibleMovements));
        possibleMovements.addAll(super.getDiagonalMovements(1));

        return possibleMovements;
    }
}
