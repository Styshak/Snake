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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class InitRecordsTableFrame extends JFrame {

    private JFrame frame;
    private JPanel panel;
    private JTable recordsTable;
    private final static int WIDTH = 610;
    private final static int HEIGHT = 230;
    private MyTableModel model;
    static List<User> recordsList = new ArrayList<>();
    static File file = new File("Records.txt");
    final static String resourcesPath = file.getAbsolutePath();

    InitRecordsTableFrame() {

        super("Table of records");
        setResizable(false);
        if (file.exists()) {
            if (file.length() != 0) {
                readFromFile();      
            } else {
            }
        } else {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        panel = new JPanel();

        panel.setLayout(null);
        model = new MyTableModel();

        recordsTable = new JTable(model);
        JTableHeader header = recordsTable.getTableHeader();
        header.setForeground(Color.black);
        header.setFont(new Font("Times New Roman", Font.BOLD, 20));
        JScrollPane sp = new JScrollPane(recordsTable);

        sp.setBounds(0, 0, 600, 191);
        panel.add(sp);

        add(panel);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

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
                    return "Login";
                case 1:
                    return "Score";
                case 2:
                    return "Speed";
            }
            return "";
        }

        @Override
        public int getRowCount() {
            return recordsList.size();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            User user = recordsList.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return user.getLogin();
                case 1:
                    return user.getScore();
                case 2:
                    return user.getSpeed();
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

    public static void writeToFile() {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(resourcesPath);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(recordsList);
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
            recordsList = (List<User>) ois.readObject();
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
