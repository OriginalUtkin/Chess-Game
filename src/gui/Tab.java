package gui;

import backend.Abstracts.ChessPiece;
import backend.Figures.King;
import backend.Figures.Movement;
import backend.Figures.Queen;
import controller.Game;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Tab extends JPanel {
    public static int countOfTabs = 0;

    private String tabName;
    private Cell[][] squares;
    private Game game;

    public Tab(JTabbedPane tabbedPane, JFrame frame, String tab_name) {
        if (Tab.countOfTabs <= 5){

            // initialise Tab variables
            this.tabName = tab_name;
            this.squares =  new Cell[8][8];
            this.game = new Game(false);
            game.setPiece(new King(backend.Enums.Color.BLACK), 2,3);
            game.setPiece(new Queen(backend.Enums.Color.BLACK), 2,2 );

            // initialise graphical part of tab
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
                Component selectedCell = chessBoard.findComponentAt(e.getX(),e.getY());

                if (selectedCell instanceof Cell){

                    if (game.isCellSelected() && !game.destinationSelected()){
                        /*
                         * If source cell has already been selected, need to choose destination cell.
                         *
                         * If destination cell in possibleMovements list:
                         * BACKEND Approach:
                         * 1) Change game board. Remove cell from source cell to destination cell
                         * 2) Set selected variables in game controller to null
                         *
                         * GUI approach:
                         * 1) Redraw  cells that were changed
                         *
                         * Else (destination cell not involved in Possible movements chess piece):
                         *
                         * GUI
                         * */
                        System.out.println("Destination cell is " + selectedCell.toString());
                        game.setDestinationCell(((Cell) selectedCell));
                        List<Movement> possibleMovements = game.getPossibleMovements();

                        if (possibleMovements.size() == 0 || !game.isPossibleDestination(((Cell) selectedCell).getRow(),((Cell) selectedCell).getColumn())){
                            game.dropSelected();
                            game.setDestinationCell(null);
                        }else{
                            // Move piece to destination and redraw GUI

                        }
                    }

                    if (!game.isCellSelected() && game.getBoardPiece(((Cell) selectedCell).getRow(), ((Cell) selectedCell).getColumn()) != null){
                        /*
                         * Just a one cell is selected on the game board. That means that we wanna calculate all
                         * possible movements for chess piece in that cell and show them on the game board table.
                         *
                         * GUI interaction:
                         * 1) Change border color to other color
                         * 2) Change color of cells in possibleMovements list
                         *
                         * Backend interaction:
                         * 1) Get all possible movements
                         * 2) Apply chess rules to all this rules
                         */

                        ((Cell) selectedCell).setBorder(BorderFactory.createLineBorder(Color.green));
                        selectedCell.repaint();

                        ChessPiece selectedPiece = game.getBoardPiece(((Cell) selectedCell).getRow(), ((Cell) selectedCell).getColumn());
                        game.setSelected((Cell)selectedCell, selectedPiece);

                        //TODO <KATYA>: Change color for possible movements cells on GUI
                        List<Movement> possibleMovements = game.getPossibleMovements();

                        System.out.println("Selected cell is " + selectedCell.toString());
                        System.out.println(selectedPiece);
                    }


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
