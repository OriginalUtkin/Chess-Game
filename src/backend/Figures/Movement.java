package backend.Figures;

import backend.Enums.Direction;

/**
 *
 * @author xutkin00, xpolis03
 */

public class Movement {
    final private int destinationRow;
    final private int destinationColumn;
    final private boolean additionalFlag;
    final private Direction direction;

    public Movement(int x_position, int y_position, boolean additionalFlag, Direction direction){
        /**
         * Constructor of Movement class
         *
         * @param x_position dst row value
         * @param y_position dst column value
         * @param additionalFlag value of additional flag
         */
        this.destinationRow = x_position;
        this.destinationColumn = y_position;

        this.additionalFlag = additionalFlag;
        this.direction = direction;
    }

    public Direction getDirection(){
        return this.direction;
    }

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
