import controller.NotationParser;
import controller.Turn;
import gui.Tab;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import java.lang.String;
import java.util.List;

/**
 * Project: Chess game IJA project
 * File: IJAProject.java
 * Date: 27.04.2019
 * Authors: xutkin00 <xutkin00@stud.fit.vutbr.cz>
 *          xpolis03 <xpolis03@stud.fit.vutbr.cz>
 * Description: Main class of the project
 */
public class IJAProject {
    public static void main(String args[]) {

        /*Initialize main frame*/
        JFrame frame= new JFrame("IJA Project");
        frame.setSize(1185,785);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout(FlowLayout.LEFT));
        frame.setLocationRelativeTo(null);
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
                    NotationParser loader = new NotationParser();
                    List<Turn> turns = loader.fileReader(fileName.toString());
                    Tab loadedTab = new Tab(tabPane,frame, "(l) Game" + (Tab.getNumOfTabs()+1));

                    for(int counter = 0; counter < turns.size(); counter++){
                        loadedTab.loadTurn(turns.get(counter), counter, loader.getLine(counter));
                        loadedTab.setTurnNotation(loader.getLine(counter), Color.yellow);
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

        frame.setJMenuBar(menuBar);
        menuBar.add(menuGame);
        frame.add(tabPane);

        frame.setVisible(true);
    }
}
