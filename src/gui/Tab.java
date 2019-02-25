package gui;

import controller.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class Tab extends JPanel {
    private JTabbedPane tabPane;
    public static int countOfTabs = 0;
    public Cell[][] squares;
    public int tabNUmber;

    static public List<Cell[][]> boards = new ArrayList<>();
    static public List<Game> boardGames = new ArrayList<>();

    public Tab() {
        super(new GridLayout(1, 1));
        this.tabPane = new JTabbedPane();
    }

    public static int getNumOfTabs() {
        return countOfTabs;
    }

    public void addNewTab(JFrame frame, String titleOfTab){
        if (countOfTabs <= 6){
            JComponent panel1 = makeBoardPanel();
            this.tabPane.addTab(titleOfTab, panel1);
            /*
             * TODO add VK_1, VK_2 counter
             * */
            this.tabPane.setMnemonicAt(0, KeyEvent.VK_1);
            //Add the tabbed pane to this panel.

            frame.add(this.tabPane);
        }else{
            JOptionPane.showMessageDialog(frame, "Too many tabs");
        }
    }

    protected JComponent makeBoardPanel() {
        /*Main panel*/
        JPanel panelBoard = new JPanel(new FlowLayout());
        /*Right side*/
        JPanel rightPanel = new JPanel();
        /*Left side - chess board*/
        JPanel chessBoard = new JPanel(new GridLayout(0, 8));
        panelBoard.setPreferredSize(new Dimension(950,620));
        chessBoard.setPreferredSize(new Dimension(600,600));
        rightPanel.setPreferredSize(new Dimension(320,600));

        panelBoard.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,5,true));

        JLabel gameInfo = new JLabel("Game Info");
        rightPanel.setBackground(Color.GRAY);


        /*TextField or sth*/
        JTextField textField = new JTextField(100);
        rightPanel.add(gameInfo);
        rightPanel.add(textField);

        int w = 75;
        int h = 75;

        BufferedImage src = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_INDEXED);
        Graphics g = src.createGraphics();

        this.initializeGUI(chessBoard, g);
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
                    Tab.boards.get(((Cell) tt).tabNum)[0][0].setBorder(BorderFactory.createLineBorder(Color.yellow));
                    Tab.boards.get(((Cell) tt).tabNum)[0][0].repaint();
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


    public void initializeGUI(JPanel panel, Graphics g){
        Cell[][] squares = new Cell[8][8];

        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < squares[i].length; j++) {

                Color cellBackgroundColor;

                if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
                    cellBackgroundColor = new Color(211,211,211);

                }else {

                    cellBackgroundColor = new Color(70,130,180);
                }

                Cell drawing = new Cell(i, j,75/squares.length, 75/squares.length,
                        cellBackgroundColor, Tab.getNumOfTabs());

                squares[i][j] = drawing;
                drawing.setBorder(BorderFactory.createLineBorder(Color.black));
                panel.add(drawing);
            }
        }

        Tab.boards.add(squares);
    }
}
