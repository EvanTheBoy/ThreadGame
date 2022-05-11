package com.hw.frame;

import com.hw.listener.Listener;
import com.hw.thread.GameThread;

import javax.swing.*;
import java.awt.*;

public class GameUI extends JFrame {
    public Graphics g;
    public void showFrame() {
        this.setTitle("战斗吧!");
        this.setSize(1024, 768);
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.g = this.getGraphics();

//        Listener listener = new Listener();
//        this.addMouseListener(listener);
//        this.addMouseMotionListener(listener);

        //直接启动游戏线程
        GameThread gt = new GameThread(g);
        Thread t = new Thread(gt);
        t.start();
    }

    public static void main(String[] args) {
        new GameUI().showFrame();
    }
}
