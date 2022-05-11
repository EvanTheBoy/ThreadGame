package com.hw.thread;

import com.hw.object.FlyObject;
import com.hw.object.Plane;
import com.hw.parameter.Vector;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameThread implements Runnable, KeyListener {
    public Graphics g;
    private Plane plane;
    private boolean gameRest; //判断游戏是否暂停
    public static String fileAddress = "img/"; //图片的存储根目录
    public GameThread(Graphics g) {
        this.g = g;
        Vector location = new Vector(10, 600);
        Vector velocity = new Vector(0, 0);
        Vector accelerator = new Vector(0, 0);
        //线程刚启动时就画出飞机
        plane = new Plane(location, velocity, accelerator, "command_post.png");
        plane.drawObject(g);
    }

    @Override
    public void run() {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W:

                plane.move();
                break;
            case KeyEvent.VK_A:

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
