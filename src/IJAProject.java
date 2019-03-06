import backend.Abstracts.ChessPiece;
import controller.Turn;
import gui.Cell;
import gui.Tab;
import controller.Notation;
import sun.rmi.server.InactiveGroupException;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.String;

public class IJAProject {
    public static void main(String args[]) {

        /*Initialize main frame*/
        JFrame frame= new JFrame("IJA Project");
        frame.setSize(1100,850);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout(FlowLayout.LEFT));
        Font font = new Font("Verdana", Font.PLAIN, 15);


        /*Initialize JTabbedPane*/
        JTabbedPane tabPane = new JTabbedPane();
        tabPane.setFont( new Font( "Dialog", Font.BOLD|Font.ITALIC, 20 ) );
        Tab tabs = new Tab(tabPane, frame, "Game1"); // implicitly one game


        /*Menu panel*/
        JMenuBar menuBar = new JMenuBar();
        JMenu menuGame = new JMenu("Game");
        menuGame.setFont(font);
        JMenuItem newGame = new JMenuItem("New");
        newGame.setFont(font);
        menuGame.add(newGame);

        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Tab(tabPane,frame, "Game" + (Tab.getNumOfTabs()+1));
            }
        });

        JMenuItem loadGame = new JMenuItem("Load");
        menuGame.add(loadGame);
        loadGame.setFont(font);


        loadGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {

                System.out.println("[DEBUG][LOAD] Loading game");
                JFrame chooserFrame = new JFrame();

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Load game");

                if (fileChooser.showOpenDialog(chooserFrame) == JFileChooser.APPROVE_OPTION) {
                    File fileName = fileChooser.getSelectedFile();
                    Notation notationName = new Notation();
                    notationName.parseFile(fileName.toString());

                    // Now we cant create a tab and send parsed turn to the game
                    Tab loadedTab = new Tab(tabPane,frame, "(l) Game" + (Tab.getNumOfTabs()+1));

                    for (int i=0; i<notationName.returnTurnList().size(); i++){
                        Turn currentTurn = notationName.returnTurnList().get(i);
                        ChessPiece currentPiece = loadedTab.parseOneNotation(currentTurn);
                        int oldRow = currentTurn.getSourceRow();
                        int oldColumn = currentTurn.getSourceColumn();
                        int newRow = currentTurn.getDestinationRow();
                        int newColumn = currentTurn.getDestinationColumn();
                        loadedTab.setPieceToTheGame(currentPiece, oldRow, Integer.valueOf(oldColumn), newRow, newColumn);
                        String notation = Integer.toString(oldRow);
                        notation += notationName.convertCoordinateBack(oldColumn);
                        notation += newRow;
                        notation += notationName.convertCoordinateBack(newColumn);
                        loadedTab.setTurnNotation(notation, new Color(32,32,32));
                    }
                }
            }

        });

        JMenuItem exitGame = new JMenuItem("Exit");
        exitGame.setFont(font);
        menuGame.add(exitGame);

        exitGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JMenu menuView = new JMenu("View");
        menuView.setFont(font);

        frame.setJMenuBar(menuBar);
        menuBar.add(menuGame);
        menuBar.add(menuView);
        frame.add(tabPane);

        frame.setVisible(true);

        /**
         * Move string:
         * if returned piece not Pawn -> To string
         * Before calling setPiece get dst cell and call toString (dst)
         */
        System.out.println();
    }
}
