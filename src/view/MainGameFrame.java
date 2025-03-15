package view;

import controller.NoNetGameController;
import controller.Server;
import controller.User;
import model.Chessboard;
import view.UI.ImagePanel;
import view.UI.RoundButton;

import javax.swing.*;
import java.awt.*;

public class MainGameFrame extends JFrame {
    private int width;
    private int height;
    private NoNetGameController controller;
    private User user1;
    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser1() {
        return user1;
    }

    private User user2;
    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public User getUser2() {
        return user2;
    }
    private Server server = new Server();

    public Server getServer() {
        return server;
    }

    private JPanel mainPanel = new JPanel();
    private ChessGameFrame chessFrame;
    private SettingFrame settingFrame;
    private JButton singleGameButton,multiGameButton;
    private JButton settingButton;
    private JButton exitButton;

    public MainGameFrame(int width, int height, User user1, User user2) {
        this.height = height;
        this.width = width;
        this.user1 = user1;
        this.user2 = user2;

        mainPanel = new ImagePanel(getClass().getResource("/jungle1.jpg"));
        mainPanel.setSize(width, height);
        mainPanel.setLayout(null);
        initFrame();
        addComponent();
        addActionListener();
        add(mainPanel);

    }

    private void initFrame() {
        this.setTitle("斗兽棋主界面");
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void addComponent() {
        addTitle("斗兽棋");
        addSingleGameButton("单人游戏");
        addMultiGameButton("双人游戏");
        addSettingButton();
        addExitButton();
    }

    private void addTitle(String text) {
        JLabel title = new JLabel(text);
        title.setFont(new Font("华文行楷", Font.BOLD, 100));
        title.setSize(360, 100);
        title.setForeground(new Color(234, 176, 50));
        title.setLocation(width / 2 - 180, height / 2 - 200);
        title.setVisible(true);
        mainPanel.add(title);
    }

    private void addSingleGameButton(String text) {
        singleGameButton = new RoundButton(text);
        singleGameButton.setFont(new Font("华文行楷", Font.BOLD, 24));
        singleGameButton.setSize(new Dimension(160, 40));
        singleGameButton.setLocation(width / 2 - 100, height / 2 - 60);
        mainPanel.add(singleGameButton);
    }

    private void addMultiGameButton(String text) {
        multiGameButton = new RoundButton(text);
        multiGameButton.setFont(new Font("华文行楷", Font.BOLD, 24));
        multiGameButton.setSize(new Dimension(160, 40));
        multiGameButton.setLocation(width / 2 - 100, height / 2 + 20);
        mainPanel.add(multiGameButton);
    }

    private void addSettingButton() {
        settingButton = new RoundButton("设置");
        settingButton.setFont(new Font("华文行楷", Font.BOLD, 24));
        settingButton.setSize(new Dimension(160, 40));
        settingButton.setLocation(width / 2 - 100, height / 2 + 100);
        mainPanel.add(settingButton);
    }

    private void addExitButton() {
        exitButton = new RoundButton("退出");
        exitButton.setFont(new Font("华文行楷", Font.BOLD, 24));
        exitButton.setSize(new Dimension(160, 40));
        exitButton.setLocation(width / 2 - 100, height / 2 + 180);
        mainPanel.add(exitButton);
    }


    public void addActionListener() {
        singleGameButton.addActionListener(e -> {
            this.setVisible(false);
            chessFrame = new ChessGameFrame(width, height, user1, user2);
            controller = new NoNetGameController(chessFrame.getChessboardComponent(), new Chessboard(), chessFrame,1);

            chessFrame.setVisible(true);
        });
        multiGameButton.addActionListener(e -> {
            this.setVisible(false);
            chessFrame = new ChessGameFrame(width, height, user1, user2);
            controller = new NoNetGameController(chessFrame.getChessboardComponent(), new Chessboard(), chessFrame,2);
            chessFrame.setVisible(true);
        });
        settingButton.addActionListener(e -> {
            settingFrame = new SettingFrame(450, 600, this);
            settingFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            settingFrame.setVisible(true);
        });
        exitButton.addActionListener(e -> {
            this.setVisible(false);
            System.exit(0);
        });
    }

}
