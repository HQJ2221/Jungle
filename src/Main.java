import view.MainGameFrame;
import music.MusicThread;

import javax.swing.*;
import controller.User;

import java.net.URL;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            User user1 = new User();
            User user2 = new User();
            MainGameFrame mainFrame = new MainGameFrame(1100, 800, user1, user2);
            mainFrame.setVisible(true);
        });

        URL music = Main.class.getResource("/青石巷.wav");

        MusicThread musicThread = new MusicThread(music);

        Thread thread = new Thread(musicThread);
        thread.start();
    }
}
