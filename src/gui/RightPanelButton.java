package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Project: Chess game IJA project
 * File: RightPanelButton.java
 * Date: 27.04.2019
 * Authors: xutkin00 <xutkin00@stud.fit.vutbr.cz>
 *          xpolis03 <xpolis03@stud.fit.vutbr.cz>
 * Description: Class that represents button on the gui part
 */

class RightPanelButton extends JButton {
    /**
     * Base constructor for the button on the right panel of tab.
     * @param buttonName name of button
     * @param rightPanel object that represents the place, where button is placed
     * @param backgroundImage image that used for represent button
     * @param buttonListener button listener which will be used after interaction with button. This listener is created
     *                       as anonymous class in the tab
     */
    RightPanelButton(String buttonName, JPanel rightPanel, String backgroundImage, ActionListener buttonListener){
        JButton btn = new JButton(buttonName);
        if (buttonName.equals("Set pause")){
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
