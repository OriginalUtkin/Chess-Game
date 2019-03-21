package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JMovePanel extends JPanel {
    private String text;

    public JMovePanel(){
        new FlowLayout(FlowLayout.CENTER, 0, 0);
        setBackground(new Color(32,32,32));
    }

    public void setText(String text){
        this.text = text;
    }
    public void repaintPanel(){
        this.repaint();
    }
    public String getText(){return this.text;}
}
