package view;

import controller.NoNetGameController;
import controller.Server;
import controller.User;
import model.Chessboard;
import model.PlayerColor;
import view.UI.ImagePanel;
import view.UI.RoundButton;

import javax.swing.*;
import java.awt.*;
import java.net.URL;


/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGHT;
    private final int ONE_CHESS_SIZE;
    private int turn = 1;
    private Chessboard chessboard = new Chessboard();
    private ChessboardComponent chessboardComponent;
    private JLabel statusLabel, turnLabel;
    private ImagePanel gamePanel;
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

    private String[] bgPaths = {"/jungle1.jpg", "/jungle2.jpg", "/jungle3.jpg", "/jungle4.jpg"};

    public ChessGameFrame(int width, int height, User user1, User user2) {
        super("2023 CS109 Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;
        this.ONE_CHESS_SIZE = (HEIGHT * 4 / 5) / 9;
        this.user1 = user1;
        this.user2 = user2;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭

        addBackgroundImage();
        gamePanel.setSize(WIDTH, HEIGHT);

        addComponents();

    }

    private void addBackgroundImage() {
        URL defaultPath = getClass().getResource(bgPaths[0]);
        gamePanel = new ImagePanel(defaultPath);
        setContentPane(gamePanel);
        gamePanel.setLayout(null);
    }

    public void setBackgroundImage(int index) {
        URL path = getClass().getResource(bgPaths[index - 1]);
        gamePanel.setBackgroundImage(path);
        setContentPane(gamePanel);
        gamePanel.setLayout(null);
    }

    public void addComponents() {
        //添加棋盘
        addChessboard();
        //添加文字
        addLabel();
        addTurn();
        addUsersLabel();
        addImage();
        //添加按钮
        addRestartButton();
        addGoBackButton();
        addUpLoadButton();
        addDownLoadButton();
        addSettingButton();
        addExitButton();
    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) { //这个在存档读档应该有用
        this.chessboardComponent = chessboardComponent;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getTurn() {
        return turn;
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE, this);//这里我改过了，把棋盘组件的构造方法改了
        chessboardComponent.setLocation(HEIGHT / 5, HEIGHT / 10);
        add(chessboardComponent);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addTurn() { //显示当前回合数的标签
        turnLabel = new JLabel();
        turnLabel.setText("Turn " + turn + " :");
        turnLabel.setLocation(HEIGHT - 90, HEIGHT / 10);
        turnLabel.setSize(200, 60);
        turnLabel.setFont(new Font("华文新魏", Font.ITALIC, 36));
        add(turnLabel);
    }

    public void setTurnLabel(PlayerColor currentPlayer) {
        if (currentPlayer.equals(PlayerColor.BLUE)) {
            turn++;
            turnLabel.setText("Turn " + turn + " :");
        }
    }

    public JLabel getTurnLabel() {
        return turnLabel;
    }

    private void addLabel() { //显示当前是谁的回合的标签
        statusLabel = new JLabel("Blue's Time");
        statusLabel.setLocation(HEIGHT + 60, HEIGHT / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setForeground(Color.BLUE);
        statusLabel.setFont(new Font("华文新魏", Font.ITALIC, 36));
        add(statusLabel);
    }

    public void setStatusLabel(PlayerColor currentPlayer) {
        if (currentPlayer.equals(PlayerColor.BLUE)) {
            statusLabel.setText("Blue's Time");
            statusLabel.setForeground(Color.BLUE);
        } else if (currentPlayer.equals(PlayerColor.RED)) {
            statusLabel.setText("Red's Time");
            statusLabel.setForeground(Color.RED);
        }
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public void addUsersLabel() {
        JLabel user1Label = new JLabel(user1.getUsername());
        user1Label.setLocation(100, HEIGHT - 100);
        user1Label.setSize(200, 60);
        user1Label.setForeground(Color.BLUE);
        user1Label.setFont(new Font("华文新魏", Font.ITALIC, 36));
        add(user1Label);
        JLabel user2Label = new JLabel(user2.getUsername());
        user2Label.setLocation(100, 0);
        user2Label.setSize(200, 60);
        user2Label.setForeground(Color.RED);
        user2Label.setFont(new Font("华文新魏", Font.ITALIC, 36));
        add(user2Label);
    }

    /**
     * 在游戏面板中添加按钮
     */

    public void addRestartButton() {
        JButton button = new RoundButton("Restart");
        button.setLocation(HEIGHT, HEIGHT / 10 + 100);
        button.setSize(120, 40);
        button.setFont(new Font("Microsoft Tai Le", Font.BOLD, 22));
        add(button);
        button.addActionListener((e) -> {
            System.out.println("Click restart");
            chessboardComponent.getGameController().restart();
        });
        add(button);
    }

    private void addGoBackButton() { //悔棋的按钮
        JButton button = new RoundButton("Undo");
        button.setSize(120, 40);
        button.setBackground(UIManager.getColor("Button.background"));
        button.setLocation(HEIGHT, HEIGHT / 10 + 160);
        button.setFont(new Font("Microsoft Tai Le", Font.BOLD, 22));
        button.addActionListener((e) -> {
            System.out.println("Click undo");
            chessboardComponent.getGameController().undo();
        });
        add(button);
    }

    private void addUpLoadButton() { //存档的按钮
        JButton button = new RoundButton("Save");
        button.setLocation(HEIGHT, HEIGHT / 10 + 220);
        button.setSize(120, 40);
        button.setFont(new Font("Microsoft Tai Le", Font.BOLD, 22));
        add(button);

        button.addActionListener((e) -> {
            System.out.println("Click upload");
            chessboardComponent.getGameController().save();
        });
    }

    private void addDownLoadButton() { //读档的按钮
        JButton button = new RoundButton("Load");
        button.setLocation(HEIGHT, HEIGHT / 10 + 280);
        button.setSize(120, 40);
        button.setFont(new Font("Microsoft Tai Le", Font.BOLD, 22));
        add(button);

        button.addActionListener((e) -> {
            System.out.println("Click download");
            chessboardComponent.getGameController().load();
        });
    }

    private void addSettingButton() {
        JButton button = new RoundButton("Setting");
        button.setLocation(WIDTH - 255, HEIGHT - 76);
        button.setSize(120, 40);
        button.setFont(new Font("Microsoft Tai Le", Font.BOLD, 22));
        add(button);

        button.addActionListener((e) -> {
            System.out.println("Click setting");
            SettingFrame settingFrame = new SettingFrame(450, 600, this);
            settingFrame.setVisible(true);
        });
    }

    private void addExitButton() {
        JButton button = new RoundButton("Exit");
        button.setLocation(WIDTH - 135, HEIGHT - 76);
        button.setSize(120, 40);
        button.setFont(new Font("Microsoft Tai Le", Font.BOLD, 22));
        add(button);

        button.addActionListener((e) -> {
            System.out.println("Click exit");
            this.dispose();
            MainGameFrame mainGameFrame = new MainGameFrame(WIDTH, HEIGHT, user1, user2);
            mainGameFrame.setVisible(true);
        });
    }

    private void addImage() {//not finished
        JComponent image = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(new ImageIcon("/bluelion.jpg").getImage(), 0, 0, null);
            }
        };
        image.setLocation(0, 400);
        image.setSize(100, 100);
        image.setVisible(true);
        add(image);
    }
}
