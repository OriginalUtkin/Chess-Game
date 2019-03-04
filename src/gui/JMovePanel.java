package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JMovePanel extends JPanel {
    public JMovePanel(){
        new FlowLayout(FlowLayout.CENTER, 0, 0);
        setPreferredSize(new Dimension(60,32));
        setBackground(new Color(32,32,32));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.red));
                System.out.println("Clicked!");
            }
        });
    }
}
