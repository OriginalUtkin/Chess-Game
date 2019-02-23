import Abstracts.ChessPiece;
import Board.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Game{

    private JPanel board;
    Board gameBoard;

//    List<Board> gameStatements;

    public Game(){
        this.gameBoard = new Board();
//        this.gameStatements = new ArrayList<Board>();

    }

    public void gameState(){

    }

    public void initializeGUI(JPanel panel, Graphics g){
        JButton[][] c1squares = new JButton[8][8];
        Insets Margin = new Insets(0,0,0,0);
        ImageIcon icon = new ImageIcon(new BufferedImage(70, 70, BufferedImage.TYPE_INT_ARGB));
        System.out.println(c1squares.length);
        for (int i = 0; i < c1squares.length; i++) {
            for (int j = 0; j < c1squares[i].length; j++) {
                System.out.println(c1squares[i].length);
                JButton b = new JButton();
                b.setBorder(BorderFactory.createLineBorder(Color.black));
                b.addMouseListener(mouseListener);

                b.setMargin(Margin);
                b.setIcon(icon);
                if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
                    b.setBackground(new Color(211,211,211));
                } else {
                    b.setBackground(new Color(70,130,180));
                }
                c1squares[j][i] = b;
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                panel.add(c1squares[j][i]);
            }
        }

//        Square[][] c1squares = new Square[8][8];
//        for (int i = 0; i < c1squares.length; i++) {
//            for (int j = 0; j < c1squares[i].length; j++) {
//
//                Square square = new Square();
//
//                if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
//                    square.setBackground(Color.WHITE);
//                } else {
//                    square.setBackground(Color.BLACK);
//                }
//
//                c1squares[i][j] = square;
//            }
//        }
//        for (int i = 0; i < c1squares.length; i++) {
//            for (int j = 0; j < c1squares[i].length; j++) {
//                Square currentSquare = c1squares[i][j];
//                DrawSquare drawing = new DrawSquare(i*75/c1squares.length, i*75/c1squares.length,
//                        currentSquare.getBackground());
//
//
////                g.setColor(currentSquare.getBackground());
////                g.fillRect(i * 50 / c1squares.length, j * 50 / c1squares.length, 50 / c1squares.length,
////                        50 / c1squares.length);
//
//                panel.add(drawing);
//            }
//        }

    }

    MouseListener mouseListener = new MouseAdapter() {
        public void mousePressed(MouseEvent mouseEvent) {
            System.out.println("Left button pressed, i, j");
        }

        public void mouseReleased(MouseEvent mouseEvent) {
            System.out.println();
        }
    };


    public ChessPiece getBoardCell(int x, int y){

        if (!this.gameBoard.gameBoard[x][y].isFree())
            return this.gameBoard.gameBoard[x][y].getPiece();

        else
            return null;
    }

    public void setPiece(ChessPiece piece, int x, int y){
        // TODO: save previous gameboard for redo / undo operation
        this.gameBoard.gameBoard[x][y].setPiece(piece);
    }

    private class Square {

        boolean isSelected;
        Color background;

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
    private class DrawSquare extends JPanel{
        private int x, y;
        private Color color;
        DrawSquare(int x, int y,  Color color){
            this.x = x;
            this.y = y;
            this.color = color;
        }
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(color);
            g.fillRect(x, y, 75, 75);
        }
    }
}

