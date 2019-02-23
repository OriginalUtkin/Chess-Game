package Figures;

/**
 *
 * @author xutkin00, xpolis03
 */

public class Movement {
    final private int x;
    final private int y;
    private boolean additionalFlag;

    public Movement(int x_position, int y_position, boolean additionalFlag){
        /**
         * Constructor of Movement class
         *
         * @param x_position dst row value
         * @param y_position dst column value
         */
        this.x = x_position;
        this.y = y_position;
        this.additionalFlag = additionalFlag;
    }

    public void setAdditionalCheck(){
        this.additionalFlag = true;
    }

    public boolean getAddtitionalCheck(){
        return this.additionalFlag;
    }


    public int get_x(){
        return this.x;
    }

    public int get_y(){
        return this.y;
    }
}
