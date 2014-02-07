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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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

public class RegistrationFrame extends JFrame {

    static File file = new File("DataBase.txt");
    final static String resourcesPath = file.getAbsolutePath();
    private final static int WIDTH = 500;
    private final static int HEIGHT = 300;
    JFrame frame;
    JPanel panel;
    JLabel loginLabel, passwordLabel, confirmPasswordLabel;
    JTextField loginField;
    JPasswordField passwordField, confirmPasswordField;
    JButton registerBtn;
    private String login, password, confirmPassword;
    private Set<String> keySet = new HashSet<>();

    public RegistrationFrame(JFrame frame) {
        super("Registration");
        this.frame = frame;
        frame.setVisible(false);
        panel = new JPanel();
        setResizable(false);
        panel.setLayout(null);

        loginLabel = new JLabel("Login");
        loginLabel.setBounds(50, 50, 130, 30);
        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(50, 100, 60, 30);
        confirmPasswordLabel = new JLabel("Confirm pass");
        confirmPasswordLabel.setBounds(50, 150, 120, 30);

        loginField = new JTextField();
        loginField.setBounds(150, 50, 280, 30);
        passwordField = new JPasswordField();
        passwordField.setBounds(150, 100, 280, 30);
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(150, 150, 280, 30);
        registerBtn = new JButton("Register");
        registerBtn.setBounds(320, 200, 110, 30);
        registerBtn.setToolTipText("Press for registration");

        panel.add(confirmPasswordLabel);
        panel.add(loginLabel);
        panel.add(passwordLabel);
        panel.add(loginField);
        panel.add(passwordField);
        panel.add(confirmPasswordField);
        panel.add(registerBtn);

        add(panel);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void dispose() {
        frame.setVisible(true);
        super.dispose();
    }

    private boolean isExistOnMap(String login) {

        keySet = AuthorisationFrame.userMap.keySet();
        for (String elem : keySet) {
            if (elem.equals(login)) {
                return false;
            }
        }
        return true;
    }

    private void addOnMap(String login, User user) {

        AuthorisationFrame.userMap.put(login, user);
        writeToFile();
    }
    Action RegisterBtn = new AbstractAction() {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        public void actionPerformed(ActionEvent e) {
            registrationAction();
        }
    };

    public void pushRegisterByMouse() {
        registerBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                registrationAction();
            }
        });
    }

    public void pushRegisterByKey() {

        InputMap im = loginField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke("ENTER"), "RegisterBtn");
        loginField.getActionMap().put("RegisterBtn", RegisterBtn);

    }

    @SuppressWarnings("deprecation")
    public void registrationAction() {

        login = loginField.getText().trim();
        password = passwordField.getText().trim();
        confirmPassword = confirmPasswordField.getText().trim();

        if (!login.isEmpty() && !login.trim().equals("")) {
            if (!password.isEmpty() && !password.trim().equals("")) {
                if (!confirmPassword.isEmpty()
                        && !confirmPassword.trim().equals("")) {
                    if (isExistOnMap(login)) {
                        if (password.equals(confirmPassword)) {
                            User user = new User(login, password);
                            addOnMap(login, user);
                            loginField.setText("");
                            passwordField.setText("");
                            confirmPasswordField.setText("");
                            JOptionPane.showMessageDialog(rootPane, "Registration success!");
                        } else {
                            JOptionPane.showMessageDialog(frame,
                                    "Passwords do not match!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame,
                                "This user already exists!");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Field 'Confirm password' is empty");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Field 'Passwords' is empty");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Field 'Login' is empty");
        }
    }

    public static void writeToFile() {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(resourcesPath);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(AuthorisationFrame.userMap);
            oos.flush();
            oos.close();
            os.close();
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    @SuppressWarnings("unchecked")
    public static void readFromFile() {
        ObjectInputStream ois = null;
        try {
            FileInputStream is = new FileInputStream(resourcesPath);
            ois = new ObjectInputStream(is);
            AuthorisationFrame.userMap = (HashMap<String, User>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException ex) {
        } finally {
            try {
                ois.close();
            } catch (IOException ex) {
            }
        }
    }
}
