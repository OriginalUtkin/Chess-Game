package backend.Figures;

import backend.Enums.Direction;

/**
 * Project: Chess game IJA project
 * File: Movement.java
 * Date: 27.04.2019
 * Authors: xutkin00 <xutkin00@stud.fit.vutbr.cz>
 *          xpolis03 <xpolis03@stud.fit.vutbr.cz>
 * Description: Class Movement represents a movement of the chess piece
 */

public class Movement {
    final private int destinationRow;
    final private int destinationColumn;
    final private boolean additionalFlag;
    final private Direction direction;

    /**
     * Constructor of Movement class
     *
     * @param x_position dst row value
     * @param y_position dst column value
     * @param additionalFlag value of additional flag
     */
    public Movement(int x_position, int y_position, boolean additionalFlag, Direction direction){

        this.destinationRow = x_position;
        this.destinationColumn = y_position;

        this.additionalFlag = additionalFlag;
        this.direction = direction;
    }

    /**
     * Getter for the direction object field
     * @return direction field value
     */
    public Direction getDirection(){
        return this.direction;
    }

    /**
     * Getter for the additionalCheck object field
     * @return direction field value
     */
    public boolean getAdditionalCheck(){
        return this.additionalFlag;
    }

    public int getRow(){
        return this.destinationRow;
    }
    public int getColumn(){
        return this.destinationColumn;
    }
}
