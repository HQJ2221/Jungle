package controller;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Color;

public class MyPanel extends JPanel implements ActionListener{
    private Timer timer;
    private int x = 0;
    private int y = 0;
    public MyPanel() {
        timer = new Timer(5000, this);
        timer.start();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillOval(x, y, 50, 50);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        x += 10;
        y += 10;
        repaint();
    }
}
