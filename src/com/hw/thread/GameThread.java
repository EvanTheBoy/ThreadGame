package com.hw.thread;

import com.hw.object.Plane;
import com.hw.parameter.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameThread implements Runnable, KeyListener {
    public Graphics g;
    private Plane plane;
    private boolean gameRest; //判断游戏是否暂停
    private ImageIcon backgroundImage; //游戏背景
    public static String fileAddress = "img/"; //图片的存储根目录
    public GameThread(Graphics g) {
        this.g = g;
        backgroundImage = new ImageIcon(fileAddress + "background.jpg");
        Vector location = new Vector(10, 600);
        Vector velocity = new Vector(0, 0);
        Vector accelerator = new Vector(0, 0);
        //线程刚启动时就画出飞机
        plane = new Plane(location, velocity, accelerator, "command_post.png");
    }

    @Override
    public void run() {
        plane.drawObject(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W -> plane.setVelocity(0, 5);
            case KeyEvent.VK_A -> plane.setVelocity(-5, 0);
            case KeyEvent.VK_S -> plane.setVelocity(0, -5);
            case KeyEvent.VK_D -> plane.setVelocity(5, 0);
        }
        //控制飞机移动
        plane.move();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W, KeyEvent.VK_A,
            KeyEvent.VK_D, KeyEvent.VK_S -> plane.setVelocity(0, 0);
        }
        plane.move();
    }
}
