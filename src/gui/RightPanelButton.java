package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RightPanelButton extends JButton {
    RightPanelButton(String buttonName, JPanel rightPanel, String backgroundImage, ActionListener buttonListener){
        JButton btn = new JButton(buttonName);
        if (buttonName == "Set pause"){
            btn.setBackground(new Color(  222,184,135));
        }else{
            btn.setBackground(new Color(   216, 199, 187));
        }
        btn.setFont(new Font("Verdana", Font.PLAIN, 14));
        if (backgroundImage!=null){
            try {
                Image img = ImageIO.read(getClass().getResource(backgroundImage));
                if (img != null){
                    btn.setIcon(new ImageIcon(img));
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        btn.addActionListener(buttonListener);

        rightPanel.add(btn);
    }
}
