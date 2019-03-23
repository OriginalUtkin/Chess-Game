package gui;

import backend.Abstracts.ChessPiece;
import backend.Figures.*;
import controller.Game;
import controller.Turn;

import java.io.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 *
 * @author xutkin00, xpolis03
 */

public class Tab extends JPanel {
    private static int countOfTabs = 0;

    private String tabName;
    private Cell[][] squares;
    private Game game;
    private JMovePanel move;
    private boolean loaded;

    private ActionEvent event;

    private JPanel movements;


    /**
     *
     * @param tabbedPane
     * @param frame
     * @param tab_name
     * @param loaded
     */
    public Tab(JTabbedPane tabbedPane, JFrame frame, String tab_name, boolean loaded) {
        if (Tab.countOfTabs <= 5) {

            // initialise Tab variables
            this.tabName = tab_name;
            this.loaded = loaded;
            this.squares = new Cell[8][8];
            this.game = new Game(true, this.loaded);
            this.move = new JMovePanel();

            // initialise graphical part of tab
            JComponent panel = initializeTab();
            tabbedPane.addTab(tab_name, panel);
            tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

            countOfTabs += 1;

        } else {
            JOptionPane.showMessageDialog(frame, "Too many tabs");
        }
    }

    /**
     *
     * @return
     */
    public static int getNumOfTabs() {
        return countOfTabs;
    }


    /**
     *
     * @return
     */
    private JComponent initializeTab() {
        /*Main panel*/
        JPanel panelBoard = new JPanel(new FlowLayout());
        panelBoard.setPreferredSize(new Dimension(1150,680));
        panelBoard.setBackground(Color.DARK_GRAY);
        panelBoard.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,5,true));

        /*Right side*/
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(450,600));
        rightPanel.setBackground(Color.DARK_GRAY);

        /*Left side - coordinates board*/
        JCPanel coordinatesBoard = new JCPanel();
        coordinatesBoard.setPreferredSize(new Dimension(660,660));

        /*Left side - chess board*/
        JPanel chessBoard = new JPanel(new GridLayout(0, 8));
        chessBoard.setPreferredSize(new Dimension(600,600));

        coordinatesBoard.add(chessBoard);
        panelBoard.add(coordinatesBoard);

        /*Initialize chessBoard for this Tab*/
        initializeBoardCells(chessBoard);
        chessBoard.repaint();
        panelBoard.add(rightPanel);



        /*Restart Button*/
        JButton restartGame = new JButton("Restart Game");
        restartGame.setBackground(new Color(   222,184,135));
        restartGame.setFont(new Font("Verdana", Font.PLAIN, 14));
        restartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: test version. Doesn't work properly; add new method to controller
                System.out.println("Restart");
                game = new Game(true, loaded);
                chessBoard.removeAll();
                movements.removeAll();
                initializeBoardCells(chessBoard);
                movements.repaint();
                movements.revalidate();
                chessBoard.revalidate();
                chessBoard.repaint();
            }
        });
        rightPanel.add(restartGame);

        /* Set timeout button */
        new RightPanelButton("Set pause", rightPanel, null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JPanel panel = new JPanel();
                panel.add(new JLabel("Set interval between turns"));
                JFormattedTextField textField = new JFormattedTextField(5);
                textField.setColumns(5);
                panel.add(textField);

                int chosenOption = JOptionPane.showOptionDialog(null, panel, "Set time interval between turn",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

                if (chosenOption == JOptionPane.OK_OPTION){
                    int periodValue = ((Number)textField.getValue()).intValue();

                    /**
                     * TODO Check if period value is possible value
                     */

                    game.setPeriod(periodValue * 1000);
                }
            }
        });


        JPanel indentPanel = new JPanel();
        indentPanel.setPreferredSize(new Dimension(420,30));
        indentPanel.setBackground(Color.DARK_GRAY);
        rightPanel.add(indentPanel);


        /*Players panel*/
        JPanel players = new JPanel(new GridLayout());
        players.setPreferredSize(new Dimension(420, 50));
        players.setBackground(Color.WHITE);


        JPanel blackPlayer = new JPanel(new GridLayout());
        //blackPlayer.setPreferredSize(new Dimension(120,40));
        JLabel blackLabel = new JLabel("     Black player");
        blackLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        blackLabel.setForeground(Color.BLACK);

        players.add(blackLabel);
        players.add(blackLabel);


        JPanel whitePlayer = new JPanel(new GridLayout());
        whitePlayer.setBackground(new Color(32,32,32));
        //whitePlayer.setPreferredSize(new Dimension(120,40));
        JLabel whiteLabel = new JLabel("     White player");
        whiteLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        whiteLabel.setForeground(Color.WHITE);
        whitePlayer.add(whiteLabel);
        players.add(whitePlayer);

        rightPanel.add(players);



        /*TextField with Movements*/
        movements = new JPanel();
        movements.setBackground(new Color(32,32,32));

        movements.setLayout(new BoxLayout(movements, BoxLayout.Y_AXIS));
        movements.setAutoscrolls(true);

        JScrollPane scrollPane = new JScrollPane(movements);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 0, 420, 300);

        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(420, 300));
        contentPane.add(scrollPane);

        rightPanel.add(contentPane);

        movements.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
            Component selectedMovement = movements.getComponentAt(e.getX(), e.getY());

            if(selectedMovement instanceof JMovePanel){
                for(Component movement: movements.getComponents()){
                    if(!((JMovePanel)movement).equals(selectedMovement)){
                        ((JMovePanel) movement).setBorder(BorderFactory.createLineBorder(new Color(32,32,32)));
                    }
                }

                ((JMovePanel)selectedMovement).setBorder(BorderFactory.createLineBorder(Color.yellow));

                String notation =((JMovePanel) selectedMovement).getText();

                boolean isRedo = game.isRedo(notation);
                List<Turn> turns = game.getGameboardState(notation);

                if (!turns.isEmpty()){
                    // Draw turn depends on operation
                    for(final Turn turn: turns){
                        if (isRedo){

                            squares[turn.getSourceRow()][turn.getSourceColumn()].setAbbreviation("");

                            if (!turn.isTransform())
                                squares[turn.getDestinationRow()][turn.getDestinationColumn()].setAbbreviation(turn.getColor().toString() + turn.getAbbreviation());
                            else
                                squares[turn.getDestinationRow()][turn.getDestinationColumn()].setAbbreviation(turn.getColor().toString() + turn.getTransformTo());

                        }else{
                            if (turn.getBeaten().isEmpty())
                                squares[turn.getDestinationRow()][turn.getDestinationColumn()].setAbbreviation("");

                            else{
                                squares[turn.getDestinationRow()][turn.getDestinationColumn()]
                                        .setAbbreviation(backend.Enums.Color.getOppositeColor(turn.getColor()).toString() +
                                                turn.getBeaten());
                            }

                            squares[turn.getSourceRow()][turn.getSourceColumn()].setAbbreviation(
                                    turn.getColor().toString() + turn.getAbbreviation());
                        }
                    }
                }
            }
            }
        });


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
                            setCellsColor(possibleMovements, Color.black, 1);
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
                            setCellsColor(possibleMovements, Color.black,1);
                            System.out.println("Possible movement");
                            changeColors(whiteLabel, whitePlayer, blackLabel, players);

                            String turnNotation = game.movePiece();

                            // Pawn piece should be transformed to other piece
                            if (game.isTransformPawn()){

                                String[] values = {"D", "V", "S", "J"};

                                int selected = JOptionPane.showOptionDialog(
                                        null,
                                        "Transform pawn to:",
                                        "Transform pawn to other piece",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,
                                        values,
                                        null);

                                turnNotation = game.transformPawn(values[selected]);

                            }

                            // Player did own turn while old turn is shown
                            if (game.isDeleteGUINotations()){

                                int lastNotation = game.getSelectedTurnNumber() - 1;
                                List<Component> notationEntries = Arrays.asList(movements.getComponents());
                                for(Component entry: notationEntries){

                                    if(notationEntries.indexOf(entry) >= lastNotation){
                                        movements.remove(entry);
                                    }
                                }

                                game.setDeleteGUINotations(false);
                            }

                            setTurnNotation(turnNotation, Color.yellow);
                        }

                        return;
                    }

                    if (
                            !game.isCellSelected() &&
                            game.getBoardPiece(((Cell) selectedCell).getRow(), ((Cell) selectedCell).getColumn()) != null &&
                            game.getBoardPiece(((Cell) selectedCell).getRow(), ((Cell) selectedCell).getColumn()).getColor() == game.getCurrentTurn()
                    ){
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

                        move.setBorder(BorderFactory.createLineBorder(new Color(32,32,32)));

                        ((Cell) selectedCell).setBorder(BorderFactory.createLineBorder(Color.red));
                        selectedCell.repaint();

                        ChessPiece selectedPiece = game.getBoardPiece(((Cell) selectedCell).getRow(), ((Cell) selectedCell).getColumn());
                        game.setSelected((Cell)selectedCell, selectedPiece);

                        List<Movement> possibleMovements = game.getPossibleMovements();
                        setCellsColor(possibleMovements, Color.green, 2);

                        System.out.println("Selected cell is " + selectedCell.toString());
                        System.out.println(selectedPiece + " "  + selectedPiece.getColor());

                        return;
                    }
                }
            }
        });


        /*Buttons*/
        new RightPanelButton("", rightPanel, "img/previous_start.png", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int counter = 0;
                while (counter < game.returnsingleTurnNotation().size()){
                    Turn turnInfo = game.undo();

                    if (turnInfo != null){

                        if (turnInfo.getBeaten().isEmpty())
                            squares[turnInfo.getDestinationRow()][turnInfo.getDestinationColumn()].setAbbreviation("");

                        else{
                            squares[turnInfo.getDestinationRow()][turnInfo.getDestinationColumn()]
                                    .setAbbreviation(backend.Enums.Color.getOppositeColor(turnInfo.getColor()).toString() +
                                            turnInfo.getBeaten());
                        }

                        squares[turnInfo.getSourceRow()][turnInfo.getSourceColumn()].setAbbreviation(
                                turnInfo.getColor().toString() + turnInfo.getAbbreviation());
                    }
                    for(Component movement: movements.getComponents()){
                        ((JMovePanel)movement).setBorder(BorderFactory.createLineBorder(new Color(32,32,32)));
                    }

                    ((JMovePanel)movements.getComponent(0))
                            .setBorder(BorderFactory.createLineBorder(Color.yellow));

                    counter+=1;
                }
            }
        });

        new RightPanelButton("", rightPanel, "img/back.png", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Turn turnInfo = game.undo();

                if (turnInfo != null){

                    if (turnInfo.getBeaten().isEmpty())
                        squares[turnInfo.getDestinationRow()][turnInfo.getDestinationColumn()].setAbbreviation("");

                    else{
                        squares[turnInfo.getDestinationRow()][turnInfo.getDestinationColumn()]
                                .setAbbreviation(backend.Enums.Color.getOppositeColor(turnInfo.getColor()).toString() +
                                        turnInfo.getBeaten());
                    }

                    squares[turnInfo.getSourceRow()][turnInfo.getSourceColumn()].setAbbreviation(
                            turnInfo.getColor().toString() + turnInfo.getAbbreviation());

                    changeColors(whiteLabel, whitePlayer, blackLabel, players);

                    for(Component movement: movements.getComponents()){
                        ((JMovePanel)movement).setBorder(BorderFactory.createLineBorder(new Color(32,32,32)));
                    }

                    if (game.getSelectedTurnNumber() > 0)
                        ((JMovePanel)movements.getComponent(game.getSelectedTurnNumber() - 1))
                                .setBorder(BorderFactory.createLineBorder(Color.yellow));
                }
            }
        });

        new RightPanelButton("", rightPanel, "img/ahead.png", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Turn turnInfo = game.redo();
                System.out.println(turnInfo);

                if (turnInfo != null){

                    squares[turnInfo.getSourceRow()][turnInfo.getSourceColumn()].setAbbreviation("");

                    if (!turnInfo.isTransform())
                        squares[turnInfo.getDestinationRow()][turnInfo.getDestinationColumn()].setAbbreviation(turnInfo.getColor().toString() + turnInfo.getAbbreviation());
                    else
                        squares[turnInfo.getDestinationRow()][turnInfo.getDestinationColumn()].setAbbreviation(turnInfo.getColor().toString() + turnInfo.getTransformTo());

                    changeColors(whiteLabel, whitePlayer, blackLabel, players);

                    for(Component movement: movements.getComponents()){
                        ((JMovePanel)movement).setBorder(BorderFactory.createLineBorder(new Color(32,32,32)));
                    }

                    ((JMovePanel)movements.getComponent(game.getSelectedTurnNumber() - 1))
                            .setBorder(BorderFactory.createLineBorder(Color.yellow));
                }
            }
        });

        new RightPanelButton("", rightPanel, "img/play1.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Nothing");
                ActionListener listener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setEvent(e);

                        Turn turnInfo = game.undo();
                        if (turnInfo != null){

                            if (turnInfo.getBeaten().isEmpty())
                                squares[turnInfo.getDestinationRow()][turnInfo.getDestinationColumn()].setAbbreviation("");

                            else{
                                squares[turnInfo.getDestinationRow()][turnInfo.getDestinationColumn()]
                                        .setAbbreviation(backend.Enums.Color.getOppositeColor(turnInfo.getColor()).toString() +
                                                turnInfo.getBeaten());
                            }

                            squares[turnInfo.getSourceRow()][turnInfo.getSourceColumn()].setAbbreviation(
                                    turnInfo.getColor().toString() + turnInfo.getAbbreviation());

                            changeColors(whiteLabel, whitePlayer, blackLabel, players);

                            for(Component movement: movements.getComponents()){
                                ((JMovePanel)movement).setBorder(BorderFactory.createLineBorder(new Color(32,32,32)));
                            }

                            if (game.getSelectedTurnNumber() > 0)
                                ((JMovePanel)movements.getComponent(game.getSelectedTurnNumber() - 1))
                                        .setBorder(BorderFactory.createLineBorder(Color.yellow));
                        }
                    }
                };

                /*play movements*/
                Timer timer = new Timer(game.getPeriod(),listener);
                timer.start();

            }
        });


       new RightPanelButton("", rightPanel, "img/play.png", new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               System.out.println("Play");

               ActionListener listener = new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       setEvent(e);

                       Turn turnInfo = game.redo();
                       System.out.println(turnInfo);

                       if (turnInfo != null){
                           squares[turnInfo.getSourceRow()][turnInfo.getSourceColumn()].setAbbreviation("");

                           if (!turnInfo.isTransform())
                                squares[turnInfo.getDestinationRow()][turnInfo.getDestinationColumn()].setAbbreviation(turnInfo.getColor().toString() + turnInfo.getAbbreviation());
                           else
                               squares[turnInfo.getDestinationRow()][turnInfo.getDestinationColumn()].setAbbreviation(turnInfo.getColor().toString() + turnInfo.getTransformTo());

                           changeColors(whiteLabel, whitePlayer, blackLabel, players);

                           for(Component movement: movements.getComponents()){
                               ((JMovePanel)movement).setBorder(BorderFactory.createLineBorder(new Color(32,32,32)));
                           }

                           ((JMovePanel)movements.getComponent(game.getSelectedTurnNumber() - 1))
                                   .setBorder(BorderFactory.createLineBorder(Color.yellow));

                       }else{
                           ((Timer)e.getSource()).stop();
                       }
                   }
               };

               /*play movements*/
               Timer timer = new Timer(game.getPeriod(),listener);
               timer.start();
           }
       });

        new RightPanelButton("", rightPanel, "img/stop.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopTimer();
            }
        });


        /*Indent*/
        JPanel emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension(360,30));
        emptyPanel.setBackground(Color.DARK_GRAY);
        rightPanel.add(emptyPanel);


        new RightPanelButton("Save", rightPanel, "img/save1.png", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("[DEBUG][SAVE] Saving game");
                JFrame chooserFrame = new JFrame();

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save current game");

                if (fileChooser.showSaveDialog(chooserFrame) == JFileChooser.APPROVE_OPTION) {
                    File fileName = fileChooser.getSelectedFile();
                    System.out.println(fileName.toString());

                    try{
                        List<String> allTurns = game.saveGame();
                        FileWriter notationWriter = new FileWriter(fileName.toString());

                        for(final String singleNotation: allTurns){
                            notationWriter.write(singleNotation);
                        }

                        notationWriter.flush();
                        notationWriter.close();

                    }catch (IOException exception){
                        // TODO: Show error message for user and do nothing
                    }
                }
            }
        });

        return panelBoard;
    }

    private void changeColors(JLabel whiteLabel, JPanel whitePlayer, JLabel blackLabel, JPanel players){
        if (game.getCurrentTurn() == backend.Enums.Color.WHITE){
            whiteLabel.setForeground(Color.WHITE);
            whitePlayer.setBackground(new Color(32,32,32));
            blackLabel.setForeground(Color.BLACK);
            players.setBackground(Color.WHITE);
            players.repaint();
        }else {
            whiteLabel.setForeground(Color.BLACK);
            whitePlayer.setBackground(Color.WHITE);
            blackLabel.setForeground(Color.WHITE);
            players.setBackground(new Color(32,32,32));
            players.repaint();
        }
    }


    /**
     *
     * @param turn
     * @param counter
     * @param notation
     */
    public void loadTurn(final Turn turn, final int counter, final String notation){

        String additional_abb =  counter%2 == 0 ? "W" : "B";

        this.game.applyTurn(turn, notation, false);
        this.squares[turn.getSourceRow()][turn.getSourceColumn()].setAbbreviation("");
        this.squares[turn.getDestinationRow()][turn.getDestinationColumn()].setAbbreviation(additional_abb+turn.getAbbreviation());
    }


    /**
     *
     * @param str
     * @param color
     */
    public void setTurnNotation(String str, Color color){
        move = new JMovePanel();

        JLabel moveLabel = new JLabel(str);
        moveLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        moveLabel.setForeground(Color.WHITE);
        moveLabel.setText(moveLabel.getText() + "");
        move.setMaximumSize(new Dimension(300,40));
        move.setText(str);

        for(Component movement: movements.getComponents()){
            ((JMovePanel)movement).setBorder(BorderFactory.createLineBorder(new Color(32,32,32)));
        }
        move.setBorder(BorderFactory.createLineBorder(color));

        move.add(moveLabel);
        movements.add(move);
        movements.repaint();
    }


    /**
     *
     * @param possibleMovements
     * @param color
     * @param thickness
     */
    private void setCellsColor(List<Movement> possibleMovements, Color color, int thickness){

        for (Movement movement: possibleMovements) {
            Cell pomCell = squares[movement.getRow()][movement.getColumn()];
            pomCell.setBorder(BorderFactory.createLineBorder(color,thickness));
        }
    }


    /**
     *
     * @param panel
     */
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

                String abbreviation = (currentPiece != null)?currentPiece.getFullPieceString() : "";

                Cell drawing = new Cell(i, j,75/this.squares.length, 75/this.squares.length,
                        cellBackgroundColor, abbreviation);

                this.squares[i][j] = drawing;
                drawing.setBorder(BorderFactory.createLineBorder(Color.black));
                panel.add(drawing);
            }
        }
    }


    private void setEvent(ActionEvent e){
        this.event = e;
    }

    private void stopTimer(){
        ((Timer)this.event.getSource()).stop();
    }
}
