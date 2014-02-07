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

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class AdminFrame extends JFrame {

    private final JFrame frame;
    private final JPanel panel;
    private final JButton delete, change, clearRecords;
    private final JTable usersTable;
    private static int WIDTH = 600;
    private static int HEIGHT = 300;
    private final List<User> list;
    private final MyTableModel model;

    public AdminFrame(JFrame frame) {
        super("View users");
        setResizable(false);
        this.frame = frame;
        frame.setVisible(false);

        panel = new JPanel();

        panel.setLayout(null);

        delete = new JButton("Delete");
        delete.setBounds(200, 230, 100, 30);
        change = new JButton("Change");
        change.setBounds(50, 230, 100, 30);
        clearRecords = new JButton("Clear table of records");
        clearRecords.setBounds(350, 230, 200, 30);

        list = new ArrayList<>(AuthorisationFrame.userMap.values());
        if (list.size() > 10) {
            WIDTH = 510;
        }

        model = new MyTableModel();

        usersTable = new JTable(model);
        JTableHeader header = usersTable.getTableHeader();
        header.setForeground(Color.black);
        header.setFont(new Font("Times New Roman", Font.BOLD, 20));
        JScrollPane sp = new JScrollPane(usersTable);

        sp.setBounds(0, 0, 600, 191);

        panel.add(delete);
        panel.add(change);
        panel.add(clearRecords);
        panel.add(sp);

        add(panel);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void dispose() {
        frame.setVisible(true);
        super.dispose();
    }

    public void deleteFromTable() {

        delete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int row = usersTable.getSelectedRow();
                if (list.get(row).getLogin().equals(AuthorisationFrame.adminLogin)) {
                    JOptionPane.showMessageDialog(rootPane, "You can not delete the admin account!");
                } else {
                    if (getSelectedUser() != null) {
                        AuthorisationFrame.userMap.remove(getSelectedUser().getLogin());
                        list.remove(getSelectedUser());
                        RegistrationFrame.writeToFile();
                        model.removeRow(row);
                    }
                }
            }
        });
    }

    public void changeTable() {
        change.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int column = usersTable.getSelectedColumn();
                if (column == 0) {
                    JOptionPane.showMessageDialog(rootPane, "Id field can not be changed!");
                }
                if (column > 0) {
                    String name = JOptionPane.showInputDialog(rootPane, "On what change?");
                    if (name != null) {
                        if (!name.isEmpty()) {
                            switch (column) {
                                case 1:
                                    for (User u : list) {
                                        if (u.getLogin().equals(name)) {
                                            JOptionPane.showMessageDialog(rootPane, "This username is already taken!");
                                            return;
                                        }
                                    }
                                    if (getSelectedUser().getLogin().equals(AuthorisationFrame.adminLogin)) {
                                        AuthorisationFrame.adminLogin = name;
                                    }
                                    AuthorisationFrame.userMap.remove(getSelectedUser().getLogin());
                                    AuthorisationFrame.userMap.put(name, new User(name, getSelectedUser().getPassword()));
                                    getSelectedUser().setLogin(name);
                                    break;
                                case 2:
                                    getSelectedUser().setPassword(name);
                                    break;
                            }
                            model.fireTableDataChanged();
                            RegistrationFrame.writeToFile();
                            JOptionPane.showMessageDialog(rootPane, "Account Details were changed!");
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "Field is empty");
                        }
                    }
                }
            }
        });
    }

    public void clearRecordsTable() {

        clearRecords.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int q = JOptionPane.showConfirmDialog(rootPane,
                        "Are you sure", "", 0, 1);
                if (q == 0) {
                    InitRecordsTableFrame.recordsList.clear();
               RegistrationFrame.writeToFile();
                } else {

                }
            }
        });

    }

    public User getSelectedUser() {
        int index = usersTable.getSelectedRow();
        if (isSelectedUser(index)) {
            String name = (String) usersTable.getValueAt(index, 1);
            return getUser(name);
        } else {
            return null;
        }
    }

    public boolean isSelectedUser(int index) {
        if (index < 0) {
            return false;
        } else {
            return true;
        }
    }

    public User getUser(String name) {
        for (User u : list) {
            if (name.equals(u.getLogin())) {
                return u;
            }
        }
        return null;
    }

    public class MyTableModel extends DefaultTableModel {

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public String getColumnName(int columnIndex) {

            switch (columnIndex) {
                case 0:
                    return "#";
                case 1:
                    return "Login";
                case 2:
                    return "Password";
            }
            return "";
        }

        @Override
        public int getRowCount() {
            return list.size();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            User user = list.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return ++rowIndex;
                case 1:
                    return user.getLogin();
                case 2:
                    return user.getPassword();

            }
            return "";
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex) {
        }
    }
}

