/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorcerok;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 *
 * @author Styshak Sergey
 */
public class UserPanel extends JPanel {

    private final int B_WIDTH = 490;
    private final int B_HEIGHT = 490;
    private final JLabel speedLbl;
    JComboBox speedBox;
    private final Image snake;
    JButton start, records;
    
    String[] speed = {"Low", "Normal", "Max"};

    public UserPanel() {

        setLayout(null);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        ImageIcon iia = new ImageIcon(getClass().getResource("/resources/snake.png"));
        snake = iia.getImage();

        speedLbl = new JLabel("Choose speed :");
        speedLbl.setBounds(250, 10, 100, 30);

        speedBox = new JComboBox(speed);
        speedBox.setBounds(350, 10, 70, 30);
        
        start = new JButton("Start game");
        start.setBounds(350, 70, 135, 40);
        start.setBackground(Color.LIGHT_GRAY);

        records = new JButton("See table records");
        records.setBounds(200, 70, 135, 40);
        
        
        add(speedLbl);
        add(speedBox);
        add(start);
        add(records);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(snake, 0, 0, this);
    }
    
    
}
