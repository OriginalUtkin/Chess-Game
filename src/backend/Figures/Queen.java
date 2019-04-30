package backend.Figures;

import backend.Abstracts.ChessPiece;
import backend.Enums.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: Chess game IJA project
 * File: Queen.java
 * Date: 27.04.2019
 * Authors: xutkin00 <xutkin00@stud.fit.vutbr.cz>
 *          xpolis03 <xpolis03@stud.fit.vutbr.cz>
 * Description: Class queen represents a king chess piece and define all possible movements for this piece according
 * to the game rules.
 */

public class Queen extends ChessPiece {

    public Queen(Color color){
        super(color,'D', 9);
    }

    @Override
    public List<Movement> calculatePossibleMovements(){

        List<Movement> possibleMovements =  new ArrayList<>();

        possibleMovements.addAll(super.getDiagonalMovements(7));
        possibleMovements.addAll(super.getVerticalMovements(7));
        possibleMovements.addAll(super.getHorizontalMovements(7));

        return possibleMovements;
    }
}
