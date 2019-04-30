package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Project: Chess game IJA project
 * File: JMovePanel.java
 * Date: 27.04.2019
 * Authors: xutkin00 <xutkin00@stud.fit.vutbr.cz>
 *          xpolis03 <xpolis03@stud.fit.vutbr.cz>
 * Description: Class that represents a turn on the gui part.
 */
public class JMovePanel extends JPanel {
    private String text;

    public JMovePanel(){
        new FlowLayout(FlowLayout.CENTER, 0, 0);
        setBackground(new Color(32,32,32));
    }

    public void setText(String text){
        this.text = text;
    }
    public String getText(){return this.text;}
}
