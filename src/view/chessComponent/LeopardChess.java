package view.chessComponent;

import model.PlayerColor;
import view.ChessComponent;
import view.UI.ImagePanel;

import java.awt.*;

public class LeopardChess extends ChessComponent {
    public LeopardChess(PlayerColor owner, int size) {
        super(owner, size);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImagePanel imagePanel;
        if (getOwner().getColor() == PlayerColor.BLUE.getColor()) {
            imagePanel = new ImagePanel(getClass().getResource("/blueleopard.jpg"));
            imagePanel.setSize(getWidth(), getHeight());
            imagePanel.setOpaque(false);
            imagePanel.setLocation(0, 0);
            imagePanel.setVisible(true);
            add(imagePanel);
        } else if (getOwner().getColor() == PlayerColor.RED.getColor()) {
            imagePanel = new ImagePanel(getClass().getResource("/redleopard.jpg"));
            imagePanel.setSize(getWidth(), getHeight());
            imagePanel.setOpaque(false);
            imagePanel.setVisible(true);
            add(imagePanel);
        }
    }
}
