package com.hw.frame;

import javax.swing.*;
import java.awt.*;

public class GameUI extends JFrame {
    public Graphics g;
    public void showFrame() {
        this.setSize(1200, 1200);
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {

    }
}
