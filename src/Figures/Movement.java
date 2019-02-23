package Figures;

/**
 *
 * @author xutkin00, xpolis03
 */

public class Movement {
    final private int x;
    final private int y;
    private boolean additionalCheck;

    public Movement(int x_position, int y_position){
        /**
         * Constructor of Movement class
         *
         * @param x_position dst row value
         * @param y_position dst column value
         */
        this.x = x_position;
        this.y = y_position;
        this.additionalCheck = false;

    }

    public void setAdditionalCheck(){
        this.additionalCheck = true;
    }

    public boolean getAddtitionalCheck(){
        return this.additionalCheck;
    }


    public int get_x(){
        return this.x;
    }

    public int get_y(){
        return this.y;
    }
}
