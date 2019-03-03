package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RightPanelButton extends JButton {
    RightPanelButton(String buttonName, JPanel rightPanel, String pathname, String tabName, ActionListener buttonListener){
        JButton btn = new JButton(buttonName);
        btn.setBackground(new Color(204,204,0));
        btn.setFont(new Font("Verdana", Font.PLAIN, 14));
        btn.addActionListener(buttonListener);

        rightPanel.add(btn);
    }
}
