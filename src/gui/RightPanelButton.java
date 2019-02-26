package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class RightPanelButton extends JButton {
    RightPanelButton(String buttonName, JPanel rightPanel, String pathname){
        JButton btn = new JButton(buttonName);
        btn.setBackground(new Color(204,204,0));
        btn.setFont(new Font("Verdana", Font.PLAIN, 14));
        try {
            Image img = ImageIO.read(getClass().getResource(pathname));
            btn.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        rightPanel.add(btn);
    }
}
