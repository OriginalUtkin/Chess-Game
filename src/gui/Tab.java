package gui;

import backend.Abstracts.ChessPiece;
import backend.Figures.King;
import backend.Figures.Knight;
import backend.Figures.Movement;
import backend.Figures.Queen;
import controller.Game;

import java.io.File;
import java.util.List;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
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
            game.setPiece(new Knight(backend.Enums.Color.BLACK), 2,3);
            game.setPiece(new Queen(backend.Enums.Color.BLACK), 1,3);

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




        /*Indent*/
        JPanel emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension(300,25));
        emptyPanel.setBackground(Color.DARK_GRAY);
        rightPanel.add(emptyPanel);

        panelBoard.add(chessBoard);
        panelBoard.add(rightPanel);
        chessBoard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Component selectedCell = chessBoard.findComponentAt(e.getX(), e.getY());

                if (selectedCell instanceof Cell){

                    if (game.isCellSelected() && !game.destinationSelected()){
                        /*
                         * If source cell has already been selected, need to choose destination cell.
                         *
                         * If destination cell coordinates in possibleMovements list:
                         * BACKEND Approach:
                         * 1) Change game board. Move piece from source cell to destination cell
                         * 2) Set selected variables in game controller to null
                         *
                         * GUI approach:
                         * 1) Redraw  cells that were changed
                         *
                         * Else (destination cell not involved in Possible movements chess piece):
                         *  1) Drop all variables in game controller which were set
                         *
                         * */
                        game.setDestinationCell(((Cell) selectedCell));
                        List<Movement> possibleMovements = game.getPossibleMovements();

                        if (possibleMovements.size() == 0 || !game.isPossibleDestination(((Cell) selectedCell).getRow(),((Cell) selectedCell).getColumn())){
                            game.dropSelected();
                            game.dropDestinationCell();
                            //TODO: <<KATYA>> Set possible movements cells color to common color
                            System.out.println("This movement isn't possible");
                        }else{
                            // Move piece to destination and redraw GUI
                            try {
                                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/gui/sound/sound.wav").getAbsoluteFile());
                                Clip clip = AudioSystem.getClip();
                                clip.open(audioInputStream);
                                clip.start();
                            } catch(Exception ex) {
                                System.out.println("Error with playing sound.");
                                ex.printStackTrace();
                            }
                            setCellsColor(possibleMovements, Color.black);
                            System.out.println("Possible movement");
                            game.movePiece();
                        }

                        return;
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
                         * 2) Apply chess rules to all calculated movements
                         */

                        ((Cell) selectedCell).setBorder(BorderFactory.createLineBorder(Color.red));
                        selectedCell.repaint();

                        ChessPiece selectedPiece = game.getBoardPiece(((Cell) selectedCell).getRow(), ((Cell) selectedCell).getColumn());
                        game.setSelected((Cell)selectedCell, selectedPiece);

                        //TODO <KATYA>: Change color for possible movements cells on GUI
                        List<Movement> possibleMovements = game.getPossibleMovements();
                        setCellsColor(possibleMovements, Color.green);

                        System.out.println("Selected cell is " + selectedCell.toString());
                        System.out.println(selectedPiece);

                        return;
                    }
                }
            }
        });


        /*Restart Button*/
        JButton restartGame = new JButton("Restart Game");
        restartGame.setBackground(new Color(204,204,0));
        restartGame.setFont(new Font("Verdana", Font.PLAIN, 14));
        restartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: test version. Doesn't work properly; add new method to controller
                System.out.println("Restart");
                game = new Game(true);
                chessBoard.removeAll();
                initializeBoardCells(chessBoard);
                chessBoard.revalidate();
                chessBoard.repaint();
            }
        });
        rightPanel.add(restartGame);

        /*TextField with Movements*/
        JTextField movements = new JTextField(28);
        movements.setBackground(new Color(32,32,32));
        movements.setPreferredSize(new Dimension(330,300));
        rightPanel.add(movements);

        /*Buttons*/
        RightPanelButton redo =  new RightPanelButton("Redo", rightPanel, "img/redo.png", this.tabName);
        new RightPanelButton("Undo", rightPanel, "img/undo.png", this.tabName);
        new RightPanelButton("Save", rightPanel, "img/save.png", this.tabName);

        Tab.countOfTabs += 1;
        return panelBoard;
    }

    private void setCellsColor(List<Movement> possibleMovements, Color color){
        for (int i = 0; i < possibleMovements.size(); i++){
            Movement destMovement = possibleMovements.get(i);
            System.out.println("Row " + destMovement.getRow() + " Column " + destMovement.getColumn());
            Cell pomCell = squares[destMovement.getRow()][destMovement.getColumn()];
            pomCell.setBorder(BorderFactory.createLineBorder(color));
        }
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
}
