package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Project: Chess game IJA project
 * File: JCPanel.java
 * Date: 27.04.2019
 * Authors: xutkin00 <xutkin00@stud.fit.vutbr.cz>
 *          xpolis03 <xpolis03@stud.fit.vutbr.cz>
 * Description: Background for the game coordinates of the board on gui part
 */
public class JCPanel extends JPanel {
    private BufferedImage image;

    public JCPanel() {

        setLayout(new FlowLayout(FlowLayout.RIGHT));
        setBackground(Color.DARK_GRAY);
        try {
            image = ImageIO.read(getClass().getResource("img/board.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return image == null ? super.getPreferredSize() : new Dimension (image.getWidth(), image.getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            int x = (getWidth() - image.getWidth()) / 2;
            int y = (getHeight()- image.getHeight()) / 2;
            g.drawImage(image, x, y, this);
        }
    }

}
