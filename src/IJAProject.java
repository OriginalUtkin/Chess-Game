import controller.NotationParser;
import controller.Turn;
import gui.Tab;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import java.lang.String;
import java.util.List;

public class IJAProject {
    private static boolean flag = false;
    private static Tab loadedTab;
    static int count = 0;
    private static int period = 6000;
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
        Tab tabs = new Tab(tabPane, frame, "Game1", false); // implicitly one game


        /*Menu panel*/
        JMenuBar menuBar = new JMenuBar();
        JMenu menuGame = new JMenu("Game");
        menuGame.setFont(font);
        JMenuItem newGame = new JMenuItem("New");
        newGame.setFont(font);
        menuGame.add(newGame);

        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Tab(tabPane,frame, "Game" + (Tab.getNumOfTabs()+1), false);
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
                    loadedTab = new Tab(tabPane,frame, "(l) Game" + (Tab.getNumOfTabs()+1), true);
<<<<<<< HEAD
                    loadedTab.setReplayMode(flag, period, loader);

                    for(int counter = 0; counter < turns.size(); counter++) {
                        loadedTab.loadTurn(turns.get(counter), counter, loader.getLine(counter));
                        loadedTab.setTurnNotation(loader.getLine(counter), Color.yellow);
=======
                    loadedTab.setReplayMode(flag);

                    ActionListener listener = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (count == turns.size()-1){
                                ((Timer)e.getSource()).stop();
                            }
                            loadedTab.loadTurn(turns.get(count), count, loader.getLine(count));
                            loadedTab.setTurnNotation(loader.getLine(count), Color.yellow);
                            loadedTab.setEvent(e);
                            count++;
                        }
                    };

                    if (flag){ /*automatic*/
                        Timer timer = new Timer(period,listener);
                        timer.start();
                    }else{
                        for(int counter = 0; counter < turns.size(); counter++) {
                            loadedTab.loadTurn(turns.get(counter), counter, loader.getLine(counter));
                            loadedTab.setTurnNotation(loader.getLine(counter), Color.yellow);
                        }
>>>>>>> 4e632fd37bec87c2f893bcccf0961c2801e2423e
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


        /*Automatic or manual re-play*/
        JMenu menuSettings = new JMenu("Settings");
        menuSettings.setFont(font);


        JMenu automaticBox = new JMenu("Automatic re-play");
<<<<<<< HEAD
=======
        JCheckBox manualBox = new JCheckBox("Manual re-play");
>>>>>>> 4e632fd37bec87c2f893bcccf0961c2801e2423e

        JCheckBox fast = new JCheckBox("Fast (2s)");
        JCheckBox slower = new JCheckBox("Slower (4s)");
        JCheckBox slow = new JCheckBox("Slow (6s)");
        fast.setFont(font);
        fast.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (fast.isSelected()){
<<<<<<< HEAD
=======
                    manualBox.setSelected(false);
>>>>>>> 4e632fd37bec87c2f893bcccf0961c2801e2423e
                    slower.setSelected(false);
                    slow.setSelected(false);
                    period = 2000;
                }
                flag = true; /*manual*/
            }
        });
        automaticBox.add(fast);

        slower.setFont(font);
        slower.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (slower.isSelected()){
                    fast.setSelected(false);
<<<<<<< HEAD
=======
                    manualBox.setSelected(false);
>>>>>>> 4e632fd37bec87c2f893bcccf0961c2801e2423e
                    slow.setSelected(false);
                    period = 4000;
                }
                flag = true; /*automatic*/
            }
        });
        automaticBox.add(slower);


        slow.setFont(font);
        slow.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (slow.isSelected()){
<<<<<<< HEAD
=======
                    manualBox.setSelected(false);
>>>>>>> 4e632fd37bec87c2f893bcccf0961c2801e2423e
                    fast.setSelected(false);
                    slower.setSelected(false);
                    period = 6000;
                }
                flag = true; /*automatic*/
            }
        });
        automaticBox.add(slow);



<<<<<<< HEAD
        automaticBox.setFont(font);
        menuSettings.add(automaticBox);
=======
        manualBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (manualBox.isSelected()){
                    slower.setSelected(false);
                    fast.setSelected(false);
                    slow.setSelected(false);
                }
                flag = false; /*manual*/
            }
        });

        automaticBox.setFont(font);
        menuSettings.add(automaticBox);
        manualBox.setFont(font);
        menuSettings.add(manualBox);
>>>>>>> 4e632fd37bec87c2f893bcccf0961c2801e2423e


        JMenu menuView = new JMenu("View");
        menuView.setFont(font);

        frame.setJMenuBar(menuBar);
        menuBar.add(menuGame);
        menuBar.add(menuSettings);
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
