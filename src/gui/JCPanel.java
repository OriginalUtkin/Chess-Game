package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

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
