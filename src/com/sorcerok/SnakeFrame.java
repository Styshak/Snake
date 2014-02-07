/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorcerok;

import javax.swing.JFrame;

/**
 *
 * @author Styshak Sergey
 */
public class SnakeFrame extends JFrame {

    JFrame frame;
    private int speed;
    
    public SnakeFrame(JFrame frame, int speed) {

        this.frame = frame;
        frame.setVisible(false);
        this.speed = speed;
        
        add(new SnakePanel(speed));

        setTitle("Snake");
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void dispose() {
        frame.setVisible(true);
        super.dispose();
    }
}
