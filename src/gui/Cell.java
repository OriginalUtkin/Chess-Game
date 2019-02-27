package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Cell extends JPanel {

    // Cells coordinates on graphical game board
    private int x;
    private int y;

    // Cells coordinated on backend
    private int row;
    private int column;

    private Color color;
    private BufferedImage pieceImage;

    String abbreviation ;

    Cell(int row, int column, int x, int y, Color color, String abbreviation) {
        this.row = row;
        this.column = column;
        this.x = x;
        this.y = y;
        this.color = color;

        this.abbreviation = abbreviation;

        try {
            pieceImage = ImageIO.read(getClass().getResource("/gui/img/"+this.abbreviation+".png"));
        }catch (Exception ex) {/* cell is empty */}
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(x-8, y-8, 75, 75);

        if (this.abbreviation != ""){
            g.drawImage(pieceImage, x+10, y+10, this);
        }

    }

    public int getRow(){
        return this.row;
    }

    public int getColumn(){
        return this.column;
    }

    @Override
    public String toString(){
        return ("Coordinates: [" + this.row + "," + this.column + "]");
    }
}