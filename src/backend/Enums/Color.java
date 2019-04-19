package backend.Enums;

/**
 * Project: Chess game IJA project
 * File: Color.java
 * Date: 27.04.2019
 * Authors: xutkin00 <xutkin00@stud.fit.vutbr.cz>
 *          xpolis03 <xpolis03@stud.fit.vutbr.cz>
 * Description: Enum class that represents a color of the chess piece
 */

public enum Color {
    WHITE, BLACK;

    @Override
    public String toString(){
        return this.name().equals("WHITE") ? "W":"B";
    }

    public static Color getOppositeColor(final Color color){
        return color == Color.BLACK ? Color.WHITE : Color.BLACK;
    }
}
