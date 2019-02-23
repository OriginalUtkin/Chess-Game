import Abstracts.ChessPiece;
import Board.Board;
import Enums.Direction;
import Figures.Movement;
import Board.Cell;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Game {

    Board gameBoard;

//    List<Board> gameStatements;

    public Game(){
        this.gameBoard = new Board();
//        this.gameStatements = new ArrayList<Board>();

    }

    public void gameState(){

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

    public void initializeGUI(JPanel panel){
        JButton[][] c1squares = new JButton[8][8];
        Insets Margin = new Insets(0,0,0,0);
        ImageIcon icon = new ImageIcon(new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB));
        System.out.println(c1squares.length);
        for (int i = 0; i < c1squares.length; i++) {
            for (int j = 0; j < c1squares[i].length; j++) {
                System.out.println(c1squares[i].length);
                JButton b = new JButton();
                b.setMargin(Margin);
                b.setIcon(icon);
                if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
                    b.setBackground(Color.WHITE);
                } else {
                    b.setBackground(Color.BLACK);
                }
                c1squares[j][i] = b;
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                panel.add(c1squares[j][i]);
            }
        }
    }

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

    public int isPossible(Movement movement, final Color pieceColor){
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

    public boolean isPossibleSpecial(Movement movement, final Color pieceColor){
        return false;
    }
}

