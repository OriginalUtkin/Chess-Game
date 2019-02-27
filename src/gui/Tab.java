package gui;

import backend.Abstracts.ChessPiece;
import controller.Game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Tab extends JPanel {
    public static int countOfTabs = 0;

    private String tabName;
    private static Cell[][] squares;
    private static Game game;



    public Tab(JTabbedPane tabbedPane, JFrame frame, String tab_name) {
        if (Tab.countOfTabs <= 5){
            this.tabName = tab_name;
            this.squares =  new Cell[8][8];
            this.game = new Game(true);
            JComponent panel = makeBoardPanel();
            tabbedPane.addTab(tab_name, panel);
            tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        }else{
            JOptionPane.showMessageDialog(frame, "Too many tabs");
        }
    }

    public static int getNumOfTabs() {
        return countOfTabs;
    }

    protected JComponent makeBoardPanel() {
        /*Main panel*/
        JPanel panelBoard = new JPanel(new FlowLayout());
        panelBoard.setPreferredSize(new Dimension(950,620));
        panelBoard.setBackground(Color.DARK_GRAY);
        panelBoard.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,5,true));

        /*Right side*/
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(330,600));
        rightPanel.setBackground(Color.DARK_GRAY);

        /*Left side - chess board*/
        JPanel chessBoard = new JPanel(new GridLayout(0, 8));
        chessBoard.setPreferredSize(new Dimension(600,600));

        /*Initialize chessBoard for this Tab*/
        this.initializeBoardCells(chessBoard);

        /*Logo image*/
        ImageIcon logoIcon = new ImageIcon(this.getClass().getResource("img/logo.png"));
        JLabel label = new JLabel(logoIcon);
        rightPanel.add(label);


        /*Restart Button*/
        JButton restartGame = new JButton("Restart Game");
        restartGame.setBackground(new Color(204,204,0));
        restartGame.setFont(new Font("Verdana", Font.PLAIN, 14));
//        restartGame.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("Restart");
//                Tab.restartGAME(chessBoard);
//            }
//        });
        rightPanel.add(restartGame);

        /*TextField with Movements*/
        JTextField movements = new JTextField(28);
        movements.setBackground(new Color(32,32,32));
        movements.setPreferredSize(new Dimension(330,300));
        rightPanel.add(movements);

        /*Indent*/
        JPanel emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension(300,25));
        emptyPanel.setBackground(Color.DARK_GRAY);
        rightPanel.add(emptyPanel);

        /*Buttons*/
        RightPanelButton redo =  new RightPanelButton("Redo", rightPanel, "img/redo.png", this.tabName);
        new RightPanelButton("Undo", rightPanel, "img/undo.png", this.tabName);
        new RightPanelButton("Save", rightPanel, "img/save.png", this.tabName);


        panelBoard.add(chessBoard);
        panelBoard.add(rightPanel);

        chessBoard.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                Component tt = chessBoard.findComponentAt(e.getX(),e.getY());

                if (tt instanceof Cell){
                    ((Cell) tt).setBorder(BorderFactory.createLineBorder(Color.green));
                    System.out.println(tt.toString());
                    ((Cell) tt).is_pressed = true;
                    tt.repaint();
                    ChessPiece cellPiece = game.getBoardPiece(((Cell) tt).getRow(), ((Cell) tt).getColumn());
                    System.out.println(cellPiece);

//                    Tab.boards.get(((Cell) tt).tabNum)[0][0].setBorder(BorderFactory.createLineBorder(Color.yellow));
//                    Tab.boards.get(((Cell) tt).tabNum)[0][0].repaint();
                    //send selected cell coordinates to backend
                    // get possible movements list if there is piece
                    // else do nothing
                    // add current cell to some list of selected cell. (if no possible movements just select cell)
                    // try to move piece
                    //if dst cell isn't represented in possible movements array or all possible movements array is empty -> remove cell from selected, change selected cell border color to common border color  and do nothing
                    // else change statement of backend and redraw src cell (remove piece) and dst cell (draw piece)
                }
            }
        });

        Tab.countOfTabs += 1;
        return panelBoard;
    }


    private void initializeBoardCells(JPanel panel){

        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < this.squares[i].length; j++) {

                Color cellBackgroundColor;

                if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
                    cellBackgroundColor = new Color(205,170,125);

                }else {
                    cellBackgroundColor = new Color(85,60,42);
                }

                ChessPiece currentPiece = this.game.getBoardPiece(i,j);

                String abbreviation = (currentPiece != null)?currentPiece.getAbbreviation() : "";

                Cell drawing = new Cell(i, j,75/this.squares.length, 75/this.squares.length,
                        cellBackgroundColor, abbreviation);

                this.squares[i][j] = drawing;
                drawing.setBorder(BorderFactory.createLineBorder(Color.black));
                panel.add(drawing);
            }
        }
    }

//    public void restartGAME(JPanel panel){
//        panel.removeAll();
//        this.initializeGUI(panel);
//        panel.revalidate();
//        panel.repaint();
//    }
}
