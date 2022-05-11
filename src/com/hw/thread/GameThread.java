package com.hw.thread;

import com.hw.object.Plane;
import com.hw.parameter.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class GameThread implements Runnable, KeyListener {
    public Graphics g;
    private Plane plane;
    private JFrame jf;
    private boolean gameRest; //判断游戏是否暂停
    private ImageIcon backgroundImage; //游戏背景
    public static String fileAddress = "img/"; //图片的存储根目录
    public GameThread(Graphics g, JFrame jf) {
        this.g = g;
        this.jf = jf;
        Vector location = new Vector(70, 384);
        Vector velocity = new Vector(0, 0);
        Vector accelerator = new Vector(0, 0);
        //线程刚启动时就画出飞机
        plane = new Plane(location, velocity, accelerator, "command_post.png");
    }

    @Override
    public void run() {
        BufferedImage bufferedImage = new BufferedImage(1024, 768, BufferedImage.TYPE_INT_RGB);
        Graphics bufG = bufferedImage.getGraphics();
        System.out.println("成功获取缓冲区画笔对象");
        jf.addKeyListener(this);
        while (true) {
            //获取缓冲区画笔对象
            backgroundImage = new ImageIcon(fileAddress + "background.jpg");
            bufG.drawImage(backgroundImage.getImage(), 0, 0, null);
            plane.drawObject(bufG);
            //最后记得把这个也要画出来
            g.drawImage(bufferedImage, 0, 0, null);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("即将获取按键值");
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_S -> {
                plane.setVelocity(0, 5);
                plane.move();
            }
            case KeyEvent.VK_A -> {
                plane.setVelocity(-5, 0);
                plane.move();
            }
            case KeyEvent.VK_W -> {
                plane.setVelocity(0, -5);
                plane.move();
            }
            case KeyEvent.VK_D -> {
                plane.setVelocity(5, 0);
                plane.move();
            }
        }
        //控制飞机移动
        System.out.println("成功移动");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_S -> {
                plane.setVelocity(0, 0);
                plane.move();
            }
        }
        System.out.println("停止");
    }
}
