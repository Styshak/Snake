/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorcerok;

/**
 *
 * @author Styshak Sergey
 */
import java.io.Serializable;

public class User implements Serializable {

    private String login, passwords, speed;
    private String password;
    private int score;

    public User(String login, int score, String speed) {
        this.login = login;
        this.score = score;
        this.speed = speed;
    }

    public User(String login, String password) {

        this.login = login;
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "User [login=" + login + ", password=" + password + "]";
    }
}
