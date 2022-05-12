package com.hw.thread;

import com.hw.listener.Listener;
import com.hw.object.Plane;
import com.hw.parameter.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameThread implements Runnable {
    public Graphics g;
    private Plane plane;
    private JFrame jf;
    private boolean gameRest; //判断游戏是否暂停
    private ImageIcon backgroundImage; //游戏背景
    public static String fileAddress = "img/"; //图片的存储根目录
    private Listener listener;
    public GameThread(Graphics g, JFrame jf) {
        this.g = g;
        this.jf = jf;
        Vector location = new Vector(70, 384);
        Vector velocity = new Vector(0, 0);
        Vector accelerator = new Vector(0, 0);
        //线程刚启动时就画出飞机
        plane = new Plane(location, velocity, accelerator, "command_post.png");
        listener = new Listener(plane);
    }

    @Override
    public void run() {
        BufferedImage bufferedImage = new BufferedImage(1024, 768, BufferedImage.TYPE_INT_RGB);
        Graphics bufG = bufferedImage.getGraphics();
        System.out.println("成功获取缓冲区画笔对象");
        jf.addKeyListener(listener);
        while (true) {
            //获取缓冲区画笔对象
            backgroundImage = new ImageIcon(fileAddress + "background.jpg");
            bufG.drawImage(backgroundImage.getImage(), 0, 0, null);
            plane.drawObject(bufG);
            plane.move();
            //最后记得把这个也要画出来
            g.drawImage(bufferedImage, 0, 0, null);
        }
    }
}
