package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Project: Chess game IJA project
 * File: Cell.java
 * Date: 27.04.2019
 * Authors: xutkin00 <xutkin00@stud.fit.vutbr.cz>
 *          xpolis03 <xpolis03@stud.fit.vutbr.cz>
 * Description: Class that represents a graphical representation for the board cell.
 */

public class Cell extends JPanel {

    // Cells coordinates on graphical game board
    private int x;
    private int y;

    // Cells coordinated on backend
    private int row;
    private int column;

    private Color color;
    private BufferedImage pieceImage;

    private String abbreviation ;

    /**
     * Basic constructor for the cell
     * @param row integer value that represents row where cell is placed
     * @param column integer value that represents column where cell is place
     * @param x raw row coordinates which could be used by controller for getting cell from backend
     * @param y raw column coordinates which could be used by controller for getting cell from backend
     * @param color color of the board cell
     * @param abbreviation abbreviation of the chess piece which is staying on the board cell
     */
    Cell(int row, int column, int x, int y, Color color, String abbreviation) {
        this.row = row;
        this.column = column;
        this.x = x;
        this.y = y;
        this.color = color;
        this.abbreviation = abbreviation;
        this.setPieceImage();
    }

    /**
     * Setter for the chess piece abbreviation on the board cell.
     * @param abbreviation abbreviation of the chess piece
     */
    public void setAbbreviation(String abbreviation){
        this.abbreviation = abbreviation;
        this.setPieceImage();
        this.repaint();
    }

    /**
     * Setter for the image of piece which is staying on the cell. Abbreviation should be set.
     */
    private void setPieceImage() {
        try {
            pieceImage = ImageIO.read(getClass().getResource("/gui/img/"+this.abbreviation+".png"));
        }catch (Exception ex) {/* cell is empty */}
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(x-8, y-8, 75, 75);

        if (!this.abbreviation.equals("")){
            g.drawImage(pieceImage, x+10, y+10, this);
        }
    }

    /**
     * Getter for the raw row coordinate of the cell
     * @return int value that represents a row coordinate in the backend
     */
    public int getRow(){
        return this.row;
    }

    /**
     * Getter for the raw column coordinate of the cell
     * @return int value that represents a column coordinate in the backend
     */
    public int getColumn(){
        return this.column;
    }

    @Override
    public String toString(){
        return ("Coordinates: [" + this.row + "," + this.column + "]" + " " + this.abbreviation);
    }
}