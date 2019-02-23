import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Tab extends JPanel {
    private JTabbedPane tabPane;

    Tab() {
        super(new GridLayout(1, 1));
        this.tabPane = new JTabbedPane();
    }
    public void addNewTab(JFrame frame, String titleOfTab){

        JComponent panel1 = makeTextPanel();
        this.tabPane.addTab(titleOfTab, panel1);
        /*
        * TODO add VK_1, VK_2 counter
        * */
        this.tabPane.setMnemonicAt(0, KeyEvent.VK_1);
        //Add the tabbed pane to this panel.
        frame.add(this.tabPane);
    }

    protected JComponent makeTextPanel() {
        JPanel panelBoard = new JPanel();
        panelBoard.setPreferredSize(new Dimension(950,700));
        panelBoard.setBackground(Color.GRAY);
        panelBoard.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,5,true));
        return panelBoard;
    }
}
