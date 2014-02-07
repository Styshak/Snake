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
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

public class AuthorisationFrame {

    private final static int WIDTH = 700;
    private final static int HEIGHT = 400;
    JFrame authFrame;
    JPanel panel;
    JLabel loginLabel, passwordLabel, registration;
    JTextField loginField;
    JPasswordField passwordField;
    JButton enterBtn;
    String login, password;
    RegistrationFrame r;
    static String adminLogin = "admin";
    static String adminPass = "admin";
    static Map<String, User> userMap = new HashMap<>();
    User user = new User(adminLogin, adminPass);
    static User currentUser;
    File file = new File(RegistrationFrame.resourcesPath);

    AuthorisationFrame() {

        if (file.exists()) {
            if (file.length() != 0) {
                RegistrationFrame.readFromFile();
            } else {
                userMap.put(user.getLogin(), user);
                RegistrationFrame.writeToFile();
            }
        } else {
            try {
                file.createNewFile();
                userMap.put(user.getLogin(), user);
                RegistrationFrame.writeToFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        authFrame = new JFrame("Sign in");
        panel = new JPanel();
        authFrame.setResizable(false);
        panel.setLayout(null);

        registration = new JLabel("Registration");
        registration.setBounds(430, 200, 130, 30);
        loginLabel = new JLabel("Login");
        loginLabel.setBounds(180, 120, 35, 30);
        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(155, 170, 65, 30);
        loginField = new JTextField();
        loginField.setBounds(220, 120, 280, 30);
        passwordField = new JPasswordField();
        passwordField.setBounds(220, 170, 280, 30);

        enterBtn = new JButton("Enter");
        enterBtn.setBounds(420, 240, 80, 30);
        enterBtn.setFocusable(false);
        enterBtn.setToolTipText("Press for sign in");

        panel.add(registration);
        panel.add(loginLabel);
        panel.add(passwordLabel);
        panel.add(loginField);
        panel.add(passwordField);
        panel.add(enterBtn);

        authFrame.add(panel);
        authFrame.setSize(WIDTH, HEIGHT);
        authFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        authFrame.setLocationRelativeTo(null);
        authFrame.setVisible(true);
    }

    @SuppressWarnings("deprecation")
    public void enterBtnAction() {

        login = loginField.getText().trim();
        password = passwordField.getText().trim();
        if (!login.isEmpty() && !login.trim().equals("")) {
            if (!password.isEmpty() && !password.trim().equals("")) {
                currentUser = getUser(login);
                if (currentUser != null) {
                    if (authentification(currentUser, password)) {
                        if (currentUser.getLogin().equals(adminLogin)) {
                            authFrame.dispose();
                            AdminFrame af = new AdminFrame(authFrame);
                            af.deleteFromTable();
                            af.changeTable();
                            af.clearRecordsTable();
                        } else {
                            authFrame.dispose();
                            UserFrame uf = new UserFrame(authFrame);
                            uf.startGame();
                            uf.seeTableRec();
                        }
                    } else {
                        JOptionPane.showMessageDialog(authFrame,
                                "Wrong password");
                    }
                } else {
                    JOptionPane.showMessageDialog(authFrame,
                            "This account does not exist - " + login);
                }
            } else {
                JOptionPane.showMessageDialog(authFrame, "Field 'Password' is empty");
            }
        } else {
            JOptionPane.showMessageDialog(authFrame, "Field 'Login' is empty");
        }
    }

    public User getUser(String name) {
        return userMap.get(name);
    }

    public boolean authentification(User user, String password) {
        if (user.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    private void onRegister() {
        registration.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                RegistrationFrame r = new RegistrationFrame(authFrame);
                r.pushRegisterByKey();
                r.pushRegisterByMouse();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registration.setForeground(Color.black);

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                registration.setForeground(Color.blue);

            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });
    }
    Action EnterButton = new AbstractAction() {

        @Override
        public void actionPerformed(ActionEvent e) {
            enterBtnAction();
        }
    };

    public void pushEnterButtonByMouse() {
        enterBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                enterBtnAction();
            }
        });
    }

    public void pushEnterButtonByKey() {

        InputMap im = loginField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke("ENTER"), "EnterButton");
        loginField.getActionMap().put("EnterButton", EnterButton);

    }

    public static void main(String[] args) {
        AuthorisationFrame af = new AuthorisationFrame();
        af.onRegister();
        af.pushEnterButtonByKey();
        af.pushEnterButtonByMouse();

    }
}
