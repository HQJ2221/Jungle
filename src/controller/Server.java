package controller;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class Server {
    private final URL USERS_FILE = getClass().getResource("/users.txt");
    private final ArrayList<User> usersList = new ArrayList<>();
    private ArrayList<User> rankList;
    private File file;


    public Server() {
        loadUsers();
        if (usersList.isEmpty()) {
            usersList.add(new User("旅行者", ""));
        }
    }

    public ArrayList<User> getUsersList() {
        return usersList;
    }

    public ArrayList<User> getRankList() {
        return rankList;
    }

    public User getUserByName(String name) {
        for (User user : usersList) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    public void register(String name, String password) throws IOException {
        User user = new User(name, password);
        if (equals(user)) {
            return;
        } else {
            usersList.add(user);
        }
        saveUsers();
    }

    public boolean verifyUser(String name, String password) {
        for (User user : usersList) {
            if (user.getUsername().equals(name) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private void saveUsers() {
        if (USERS_FILE == null) {
            file = new File("users.txt");
        } else {
            file = new File(USERS_FILE.getPath());
        }
        try {
            FileWriter fw = new FileWriter(file,true);
            for (User user : usersList) {

                fw.write(user.getUsername() + " " + user.getPassword() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUsers() {
        if (USERS_FILE != null) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(USERS_FILE.getPath()));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] info = line.split(" ");
                    usersList.add(new User(info[0], info[1]));
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void rankUsers(ArrayList<User> users) {
        rankList = new ArrayList<User>();
        for (User user : users) {
            rankList.add(user);
        }
        rankList.sort((o1, o2) -> o2.getScore() - o1.getScore()); // 降序
    }

    public boolean equals(User user) {
        for (User u : usersList) {
            if (u.getUsername().equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }
}
