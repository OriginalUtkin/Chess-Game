import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

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
        JPanel panelBoard = new JPanel(new GridLayout(0, 8));
        panelBoard.setPreferredSize(new Dimension(500,500));
        panelBoard.setBackground(Color.GRAY);
        panelBoard.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,5,true));
        this.tabGame.initializeGUI(panelBoard);
        return panelBoard;
    }
}
