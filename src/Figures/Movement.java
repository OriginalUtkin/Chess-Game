package Figures;

/**
 *
 * @author xutkin00, xpolis03
 */

public class Movement {
    private int x;
    private int y;

    public Movement(int x_position, int y_position){
        /**
         * Constructor of Movement class
         *
         * @param x_position dst row value
         * @param y_position dst column value
         */
        this.x = x_position;
        this.y = y_position;
    }

    public int get_x(){
        return this.x;
    }

    public int get_y(){
        return this.y;
    }
}
