import controller.NotationParser;
import controller.Turn;
import gui.Tab;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class IJAProject {
    private static Tab loadedTab;
    static int count = 0;
    private static int period = 2000;
    private static List<Tab> tabList = new ArrayList<>();
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
        tabList.add(tabs);
        tabs.setPeriod(period);


        /*Menu panel*/
        JMenuBar menuBar = new JMenuBar();
        JMenu menuGame = new JMenu("Game");
        menuGame.setFont(font);
        JMenuItem newGame = new JMenuItem("New");
        newGame.setFont(font);
        menuGame.add(newGame);

        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Tab newTab = new Tab(tabPane,frame, "Game" + (Tab.getNumOfTabs()+1), false);
                tabList.add(newTab);
                newTab.setPeriod(period);
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
                    tabList.add(loadedTab);
                    loadedTab.setPeriod(period);

                    for(int counter = 0; counter < turns.size(); counter++) {
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


        /*Automatic or manual re-play*/
        JMenu menuSettings = new JMenu("Settings");
        menuSettings.setFont(font);

        JMenuItem setInterval = new JMenuItem("Set speed");

        setInterval.setFont(font);
        menuSettings.add(setInterval);

        setInterval.addMouseListener(new MouseAdapter() {
            JFormattedTextField speedField;
            @Override
            public void mouseReleased(MouseEvent e) {
                JFrame frame = new JFrame("Interval");
                frame.setPreferredSize(new Dimension(300,150));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel speedPanel = new JPanel();
                speedPanel.setBackground(new Color(32,32,32));
                JLabel speedLabel = new JLabel("Set speed");
                speedLabel.setForeground(Color.WHITE);
                speedPanel.add(speedLabel);


                speedField = new JFormattedTextField("");
                speedField.setBackground(Color.WHITE);
                speedField.setValue(new Integer(0));
                speedField.setColumns(10);
                speedField.addPropertyChangeListener("value", new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        period = ((Number)speedField.getValue()).intValue();
                        System.out.println(period);
                        for (int i = 0; i<tabList.size(); i++){
                            tabList.get(i).setPeriod(period*1000);
                        }
                        frame.setVisible(false);
                    }
                });

                speedPanel.add(speedField);
                frame.add(speedPanel);
                frame.setLocation(350,150);
                frame.pack();
                frame.setVisible(true);
            }
        });


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
