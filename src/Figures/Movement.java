package Figures;

public class Movement {
    private int x;
    private int y;

    public Movement(int x_position, int y_position){
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
