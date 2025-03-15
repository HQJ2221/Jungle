package view;

import controller.Server;
import controller.User;
import view.UI.ImagePanel;
import view.UI.RoundButton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SettingFrame extends JFrame {
    private int width;
    private int height;
    private JFrame frame;
    private Server server = new Server();

    public Server getServer() {
        return server;
    }

    private User user1;
    private User user2;
    private ArrayList<User> topPlayer = new ArrayList<>();
    private JPanel settingPanel;
    private JButton loginButton, bgButton, rankButton, backButton;
    private MainGameFrame mainGameFrame;
    private ChessGameFrame chessGameFrame;


    // TODO: 1.用户登录 2.排行榜 3.背景切换 4.棋子切换
    public SettingFrame(int width, int height, JFrame frame) {
        this.width = width;
        this.height = height;
        this.frame = frame;

//        if (frame instanceof MainGameFrame) {
//            this.mainGameFrame = (MainGameFrame) frame;
//            this.server = mainGameFrame.getServer();
//            this.user1 = mainGameFrame.getUser1();
//            this.user2 = mainGameFrame.getUser2();
//        } else if (frame instanceof ChessGameFrame) {
//            this.chessGameFrame = (ChessGameFrame) frame;
//            this.server = chessGameFrame.getServer();
//            this.user1 = chessGameFrame.getUser1();
//            this.user2 = chessGameFrame.getUser2();
//        } else {
//            System.out.println("Error: mainFrame is not a MainGameFrame or ChessGameFrame");
//        }

        settingPanel = new ImagePanel(getClass().getResource("/settingJungle.jpg"));
        settingPanel.setSize(width, height);
        settingPanel.setLayout(null);

        initFrame();
        addComponent();
        add(settingPanel);
    }

    public void initFrame() {
        this.setTitle("游戏设置");
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private void addComponent() { //添加按钮
        addLabel();
        addLoginButton();
        addRankButton();
        addBackgroundButton();
        addTurnbackButton();
    }

    private void addLabel() {
        JLabel title = new JLabel("设置");
        title.setSize(300, 100);
        title.setLocation(width / 2 - 70, height / 2 - 300);
        title.setFont(new Font("华文行楷", Font.BOLD, 60));
        title.setForeground(new Color(0, 0, 0));
        settingPanel.add(title);
    }

    private void addLoginButton() {
        loginButton = new RoundButton("用户登录");
        loginButton.setFont(new Font("华文行楷", Font.BOLD, 24));
        loginButton.setSize(120, 40);
        loginButton.setLocation(width / 2 - 65, height / 2 - 150);
        settingPanel.add(loginButton);

        loginButton.addActionListener(e -> {
            //创建登录对话框
            JDialog loginDialog = new JDialog(this, "用户登录", true);
            loginDialog.setSize(400, 200);
            loginDialog.setLocationRelativeTo(this);

            //添加用户名和密码输入框
            JPanel inputPanel = new JPanel(new GridLayout(2, 2, 20, 10));
            inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            JLabel name = new JLabel("用户名:");
            name.setHorizontalAlignment(SwingConstants.CENTER);
            name.setFont(new Font("楷体", Font.BOLD, 24));
            inputPanel.add(name);
            JTextField usernameField = new JTextField();
            usernameField.setPreferredSize(new Dimension(100, 20));
            inputPanel.add(usernameField);
            JLabel password = new JLabel("密码:");
            password.setHorizontalAlignment(SwingConstants.CENTER);
            password.setFont(new Font("楷体", Font.BOLD, 24));
            inputPanel.add(password);
            JPasswordField passwordField = new JPasswordField();
            passwordField.setPreferredSize(new Dimension(100, 20));
            inputPanel.add(passwordField);

            JButton loginButton = new JButton("登录");
            JButton registerButton = new JButton("注册");
            JButton cancelButton = new JButton("取消");
            //进行登录操作
            loginButton.addActionListener(e1 -> {
                System.out.println("Login button clicked");
                if (!server.verifyUser(usernameField.getText(), passwordField.getText())) {
                    JOptionPane.showMessageDialog(loginDialog, "用户名或密码错误", "注意", JOptionPane.ERROR_MESSAGE);
                } else if (server.getUsersList() != null && user1.getUsername().equals("")
                        && server.verifyUser(usernameField.getText(), passwordField.getText())) {
                    JOptionPane.showMessageDialog(loginDialog, "登录成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    user1 = server.getUserByName(usernameField.getText());
                    if (mainGameFrame != null) {
                        mainGameFrame.setUser1(user1);
                    }
                    if (chessGameFrame != null) {
                        chessGameFrame.setUser1(user1);
                    }
                    System.out.println(user1.getUsername() + "(user1)登录成功");
                    loginDialog.dispose();
                } else if (server.getUsersList() != null && user2.getUsername().equals("")
                        && !user1.getUsername().equals(usernameField.getText())
                        && server.verifyUser(usernameField.getText(), passwordField.getText())) {
                    JOptionPane.showMessageDialog(loginDialog, "登录成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    user2 = server.getUserByName(usernameField.getText());
                    if (mainGameFrame != null) {
                        mainGameFrame.setUser1(user2);
                    }
                    if (chessGameFrame != null) {
                        chessGameFrame.setUser1(user2);
                    }
                    System.out.println(user2.getUsername() + "(user2)登录成功");
                    loginDialog.dispose();
                }
            });
            //进行注册操作
            registerButton.addActionListener(e1 -> {
                System.out.println("Register button clicked");
                if (!server.getUsersList().contains(server.getUserByName(usernameField.getText()))) {
                    try {
                        server.register(usernameField.getText(), passwordField.getText());
                        JOptionPane.showMessageDialog(loginDialog, "注册成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(loginDialog, "用户名已存在", "注意", JOptionPane.ERROR_MESSAGE);
                }
            });
            //取消登录
            cancelButton.addActionListener(e1 -> loginDialog.dispose());
            //添加按钮
            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(loginButton);
            buttonPanel.add(registerButton);
            buttonPanel.add(cancelButton);
            //添加到组件
            loginDialog.add(inputPanel, BorderLayout.CENTER);
            loginDialog.add(buttonPanel, BorderLayout.SOUTH);
            loginDialog.setVisible(true);
        });
    }

    private void addRankButton() {
        rankButton = new RoundButton("排行榜");
        rankButton.setFont(new Font("华文行楷", Font.BOLD, 24));
        rankButton.setSize(120, 40);
        rankButton.setLocation(width / 2 - 65, height / 2 - 70);
        settingPanel.add(rankButton);

        //设置排行榜,列出全部用户排名
        rankButton.addActionListener(e -> {
            topPlayer.clear();
            System.out.println("Rank Button Clicked");
            server.rankUsers(server.getUsersList());
            for (int i = 0; i < server.getRankList().size(); i++) {
                topPlayer.add(server.getRankList().get(i));
            }
            createRankingList(topPlayer);
        });
    }

    private void createRankingList(ArrayList<User> topPlayer) {
        JDialog rankDialog = new JDialog(this, "排行榜", true);
        rankDialog.setSize(400, 300);
        rankDialog.setLocationRelativeTo(null);
        rankDialog.setResizable(false);
        rankDialog.setLayout(new BorderLayout());

        Object[] col = new Object[]{"排名", "用户名", "分数"};
        Object[][] rowData = new Object[topPlayer.size()][3];
        for (int i = 0; i < topPlayer.size(); i++) {
            rowData[i][0] = i + 1;
            rowData[i][1] = topPlayer.get(i).getUsername();
            rowData[i][2] = topPlayer.get(i).getScore();
        }

        JTable table = new JTable(rowData, col);
        table.setEnabled(false);
        table.setRowHeight(30);
        table.setFont(new Font("楷体", Font.BOLD, 20));
        table.getTableHeader().setFont(new Font("隶书", Font.PLAIN, 26));
        JScrollPane scrollPane = new JScrollPane(table);

        rankDialog.add(scrollPane, BorderLayout.CENTER);
        rankDialog.setVisible(true);
    }

    public void addBackgroundButton() {
        bgButton = new RoundButton("背景设定");
        bgButton.setFont(new Font("华文行楷", Font.BOLD, 24));
        bgButton.setSize(120, 40);
        bgButton.setLocation(width / 2 - 65, height / 2 + 10);
        settingPanel.add(bgButton);

        if (frame instanceof ChessGameFrame) {
            bgButton.addActionListener(e -> {
                System.out.println("Background Button Clicked");
                JDialog bgDialog = new JDialog(this, "背景设定", true);
                bgDialog.setSize(360, 180);
                bgDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                bgDialog.setLocationRelativeTo(null);
                bgDialog.setResizable(false);
                bgDialog.setLayout(new BorderLayout());

                JPanel bgPanel = new JPanel(new GridLayout(2, 2));
                bgPanel.setSize(360, 180);
                bgPanel.setLocation(0, 0);
                bgPanel.setLayout(new GridLayout(2, 2, 5, 5));

                JButton bg1 = new JButton("background 1");
                JButton bg2 = new JButton("background 2");
                JButton bg3 = new JButton("background 3");
                JButton bg4 = new JButton("background 4");
                bg1.setFont(new Font("Microsoft Tai Le", Font.BOLD, 20));
                bg2.setFont(new Font("Microsoft Tai Le", Font.BOLD, 20));
                bg3.setFont(new Font("Microsoft Tai Le", Font.BOLD, 20));
                bg4.setFont(new Font("Microsoft Tai Le", Font.BOLD, 20));
                bg1.setSelected(true);

                bg1.addActionListener(e1 -> {
                    System.out.println("background 1 selected");
                    chessGameFrame.setBackgroundImage(1);
                });
                bg2.addActionListener(e1 -> {
                    System.out.println("background 2 selected");
                    chessGameFrame.setBackgroundImage(2);
                });
                bg3.addActionListener(e1 -> {
                    System.out.println("background 3 selected");
                    chessGameFrame.setBackgroundImage(3);
                });
                bg4.addActionListener(e1 -> {
                    System.out.println("background 4 selected");
                    chessGameFrame.setBackgroundImage(4);
                });

                bgPanel.add(bg1);
                bgPanel.add(bg2);
                bgPanel.add(bg3);
                bgPanel.add(bg4);

                bgDialog.add(bgPanel, BorderLayout.CENTER);
                bgDialog.setVisible(true);
            });
        }
    }

    public void addTurnbackButton() {
        backButton = new RoundButton("返回");
        backButton.setFont(new Font("华文行楷", Font.BOLD, 24));
        backButton.setSize(120, 40);
        backButton.setLocation(width / 2 - 65, height / 2 + 90);
        settingPanel.add(backButton);

        backButton.addActionListener(e -> {
            System.out.println("Turn back Button Clicked");
            this.dispose();
        });
    }
}
