package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RightPanelButton extends JButton {
    RightPanelButton(String buttonName, JPanel rightPanel, String backgroundImage, ActionListener buttonListener){
        JButton btn = new JButton(buttonName);
        btn.setBackground(new Color(204,204,0));
        btn.setFont(new Font("Verdana", Font.PLAIN, 14));

        try {
            Image img = ImageIO.read(getClass().getResource(backgroundImage));
            btn.setIcon(new ImageIcon(img));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        btn.addActionListener(buttonListener);

        rightPanel.add(btn);
    }
}
