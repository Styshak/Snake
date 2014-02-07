/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorcerok;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 *
 * @author Styshak Sergey
 */
public class UserFrame extends JFrame {

    private final JFrame authFrame;
    UserPanel ub = new UserPanel();
    int low, normal, max;

    public UserFrame(JFrame frame) {
        add(ub);

        setTitle("Main menu");
        authFrame = frame;
        authFrame.setVisible(false);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        
        
    }

    @Override
    public void dispose() {
        authFrame.setVisible(true);
        super.dispose();
    }

    public void startGame() {

        ub.start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                new SnakeFrame(UserFrame.this, setSpeed());
            }
        });
    }
    
    public void seeTableRec(){
        
        ub.records.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                new InitRecordsTableFrame();
            }
        });
    }

    private int setSpeed() {

        int index = ub.speedBox.getSelectedIndex();

        int speed = 0;

        switch (index) {
            case 0:
                speed = 180;
                break;
            case 1:
                speed = 140;
                break;
            case 2:
                speed = 100;
                break;
        }
        return speed;
    }

}
