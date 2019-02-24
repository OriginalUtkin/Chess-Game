import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;


public class Tab extends JPanel {
    private JTabbedPane tabPane;
    private Game tabGame;

    Tab() {
        super(new GridLayout(1, 1));
        this.tabGame = new Game();
        this.tabPane = new JTabbedPane();
    }
    public void addNewTab(JFrame frame, String titleOfTab){

        JComponent panel1 = makeBoardPanel();
        this.tabPane.addTab(titleOfTab, panel1);
        /*
        * TODO add VK_1, VK_2 counter
        * */
        this.tabPane.setMnemonicAt(0, KeyEvent.VK_1);
        //Add the tabbed pane to this panel.

        frame.add(this.tabPane);
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

        chessBoard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Component tt = chessBoard.findComponentAt(e.getX(),e.getY());
                System.out.println(tt.toString());

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {


            }

            @Override
            public void mouseEntered(MouseEvent e) {


            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        }
    );


        panelBoard.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,5,true));

        JLabel gameInfo = new JLabel("Game Info");
        rightPanel.setBackground(Color.GRAY);


        /*TextField or sth*/
        JTextField textField = new JTextField(100);
        rightPanel.add(gameInfo);
        rightPanel.add(textField);

        int w = 75;
        int h = 75;

        BufferedImage src = new BufferedImage(w, h,
                BufferedImage.TYPE_BYTE_INDEXED);
        Graphics g = src.createGraphics();

        this.tabGame.initializeGUI(chessBoard, g);
        panelBoard.add(chessBoard);
        panelBoard.add(rightPanel);


        return panelBoard;
    }





}
