package Enums;

public enum Color {
    WHITE, BLACK;

    @Override
    public String toString(){
        return this.name().equals("WHITE") ? "W":"B";
    }
}
