import Abstracts.ChessPiece;
import Board.Board;
import Enums.Direction;
import Figures.Movement;
import Board.Cell;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Game {

    private JPanel board;
    Board gameBoard;

//    List<Board> gameStatements;

    public Game() {
        this.gameBoard = new Board();
//        this.gameStatements = new ArrayList<Board>();

    }

    public void gameState() {

    }

    public ChessPiece getBoardPiece(final int x, final int y) {
        /**
         * Return chess piece that is staying on cell with coordinates x and y.
         *
         * @param int x - row coordinate; array index 0 - 7
         * @param int y - column coordinate; array index 0 - 7
         *
         * @return ChessPiece object if chess piece is staying on selected cell; null otherwise
         */
        return this.gameBoard.gameBoard[x][y].getPiece();
    }


    public void initializeGUI(JPanel panel, Graphics g){
        Square[][] squares = new Square[8][8];
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {

                Square square = new Square();
                if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
                    square.setBackground(new Color(211,211,211));
                } else {
                    square.setBackground(new Color(70,130,180));
                }

                squares[i][j] = square;

                DrawSquare drawing = new DrawSquare(75/squares.length, 75/squares.length,
                        squares[i][j].getBackground());
                drawing.setBorder(BorderFactory.createLineBorder(Color.black));
                drawing.pressed(i,j, panel);
                panel.add(drawing);
            }
        }

    }

    public ChessPiece getBoardCell(int x, int y) {

        if (!this.gameBoard.gameBoard[x][y].isFree())
            return this.gameBoard.gameBoard[x][y].getPiece();

        else
            return null;
    }

    public void setPiece(ChessPiece piece, int x, int y) {
        // TODO: save previous gameboard for redo / undo operation
        this.gameBoard.gameBoard[x][y].setPiece(piece);
    }

    public int isPossible(Movement movement, final Color pieceColor) {
        /**
         * Check if movement of chess piece is possible.
         *
         * Chess piece can move to destination cell in case:
         * 1) Destination cell is free
         * 2) Destination cell isn't free but contains enemy player chess piece
         *
         * Check if additionalCheck flag is set. If so, that means that this movement should be
         * processed using other rules (pawn diagonal movement, pawn vertical movement):
         *
         * Diagonal:
         * 1) Destination cell shouldn't be free
         * 2) Same as 2 point above
         *
         * Vertical:
         * 1) Destination cell SHOULD be free
         *
         * @param movement - possible movement of chess piece
         * @param color - color of chess piece
         *
         * @return true if chess piece movement is possible, false otherwise
         */

        return 0;
    }

    public boolean isPossibleSpecial(Movement movement, final Color pieceColor) {
        return false;
    }

        private class Square {

            boolean isSelected;
            Color background;

            int column, row;

            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(final boolean isSelected) {
                this.isSelected = isSelected;
            }

            public Color getBackground() {
                return background;
            }

            public void setBackground(final Color background) {
                this.background = background;
            }

        }

        private class DrawSquare extends JPanel {
            private int x, y;
            private Color color;
            public DrawSquare square;
            BufferedImage myImage;
            public boolean is_pressed = false;

            DrawSquare(int x, int y, Color color) {
                this.x = x;
                this.y = y;
                this.color = color;
                this.square = this;
                try {
                    myImage = ImageIO.read(new File("IJA/src/img/BQ.gif"));
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

            public void pressed(int x, int y, JPanel panel) {
                   this.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            System.out.println("Coordinates: [" + (7-x) + "," + (7-y) + "]");
                            square.setBorder(BorderFactory.createLineBorder(Color.green));
                        }
                       @Override
                       public void mouseReleased(MouseEvent e) {
                           square.setBorder(BorderFactory.createLineBorder(Color.black));
                           is_pressed = true;
                           panel.validate();
                           panel.repaint();
                       }
                   });
            }
        }
    }


