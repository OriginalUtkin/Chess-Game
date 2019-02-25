package backend.Enums;

/**
 *
 * @author xutkin00, xpolis03
 */

public enum Color {
    WHITE, BLACK;

    @Override
    public String toString(){
        return this.name().equals("WHITE") ? "W":"B";
    }
}
