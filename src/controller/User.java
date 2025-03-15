package controller;

import model.PlayerColor;

import java.io.Serializable;

public class User implements Serializable, Comparable<User> {
    private String username;
    private String password;
    private int score;
    private int rank;
    private PlayerColor color;
    private NoNetGameController gameController;

    public User() {
        this.username = "";
        this.password = "";
        this.score = 0;
    }// default constructor

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.score = 0;

    }// constructor

    public void setUsername(String username) {
        this.username = username;
    }// setUsername

    public void setPassword(String password) {
        this.password = password;
    }// setPassword

    public void setScore(int score) {
        this.score = score;
    }// setScore

    public void setRank(int rank) {
        this.rank = rank;
    }// setRank

    public String getUsername() {
        return username;
    }// getUsername

    public String getPassword() {
        return password;
    }// getPassword

    public int getScore() {
        return score;
    }// getScore

    public int getRank() {
        return rank;
    }// getRank
    public void setController(NoNetGameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public int compareTo(User o) {
        return o.score - this.score;
    }
}
