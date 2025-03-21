package view.UI;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

// ImagePanel 类
public class ImagePanel extends JPanel {
    private Image background;

    public ImagePanel(URL imagePath) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        this.background = imageIcon.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }

    public void setBackgroundImage(URL imagePath) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        this.background = imageIcon.getImage();
    }
}