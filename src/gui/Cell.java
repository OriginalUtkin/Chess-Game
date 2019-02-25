package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Cell extends JPanel {
    private int x, y, row, column;
    private Color color;
    public Cell square;
    BufferedImage myImage;
    public boolean is_pressed = false;

    Cell(int row, int column, int x, int y, Color color) {
        this.row = row;
        this.column = column;
        this.x = x;
        this.y = y;
        this.color = color;
        this.square = this;
        try {
            myImage = ImageIO.read(getClass().getResource("/img/BQ.gif"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(x-8, y-8, 75, 75);
        if (is_pressed){g.drawImage(myImage, x+10, y+10, this);}

    }

    @Override
    public String toString(){
        return ("Coordinates: [" + this.row + "," + this.column + "]");
    }
}