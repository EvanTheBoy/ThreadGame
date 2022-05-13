package com.hw.frame;

import com.hw.thread.GameThread;
import com.hw.thread.ZombieBulletThread;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

        //优化:使用线程池来管理
        ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 10, 10,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10));
        pool.execute(new GameThread(g, jf));
        pool.execute(new ZombieBulletThread());
    }

    public static void main(String[] args) {
        new GameUI().showFrame();
    }
}
