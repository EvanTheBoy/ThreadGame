package com.hw.frame;

import com.hw.listener.Listener;
import com.hw.thread.GameThread;

import javax.swing.*;
import java.awt.*;

public class GameUI {
    public Graphics g;
    private JFrame jf;
    public void showFrame() {
        jf = new JFrame();
        jf.setTitle("战斗吧!");
        jf.setSize(1024, 768);
        jf.setLayout(new FlowLayout());
        jf.setDefaultCloseOperation(3);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
        this.g = jf.getGraphics();

//        Listener listener = new Listener();
//        jf.addMouseListener(listener);
//        jf.addMouseMotionListener(listener);
//        jf.addKeyListener(listener);

        //直接启动游戏线程
        GameThread gt = new GameThread(g, jf);
        Thread t = new Thread(gt);
        t.start();
    }

    public static void main(String[] args) {
        new GameUI().showFrame();
    }
}
