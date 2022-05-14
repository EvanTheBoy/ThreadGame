package com.hw.thread;

import com.hw.listener.Listener;
import com.hw.object.FlyObject;
import com.hw.object.Plane;
import com.hw.parameter.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class GameThread implements Runnable {
    public Graphics g;
    private int count = 0;
    private Plane plane;
    private JFrame jf;
    private boolean gameRest; //判断游戏是否暂停
    private ImageIcon backgroundImage; //游戏背景
    public static String fileAddress = "img/"; //图片的存储根目录
    private Listener listener;

    private ArrayList<FlyObject> enemies, attackers;
    public GameThread(Graphics g, JFrame jf) {
        this.g = g;
        this.jf = jf;
        enemies = new ArrayList<>();
        attackers = new ArrayList<>();
        Vector location = new Vector(70, 384);
        Vector velocity = new Vector(0, 0);
        Vector accelerator = new Vector(0, 0);
        //线程刚启动时就画出飞机
        plane = new Plane(location, velocity, accelerator, "command_post.png");
        listener = new Listener(plane);
    }

    //生成气球僵尸
    private void generateZombies() {
        if (count % 40 == 0) {
            Random rand = new Random();
            int ly = rand.nextInt(512) + 100;
            int vx = -rand.nextInt(1) - 2;
            Vector loc = new Vector(1024, ly);
            Vector vel = new Vector(vx, 0);
            Vector acc = new Vector(0, 0);
            FlyObject zombie = new FlyObject(loc, vel, acc, "balloon_zombie.png", 5);
            //这里之所以要用到队列，是因为僵尸画出来后要一直停留在页面上，也就是不停地画
            //所以在这里就体现为，每创建出一个僵尸对象，就把它添加进队列中，然后从队列中取出来
            //不停地画
            enemies.add(zombie);
        }
    }



    @Override
    public void run() {
        //获取缓冲区画笔对象
        BufferedImage bufferedImage = new BufferedImage(1024, 768, BufferedImage.TYPE_INT_RGB);
        //获取缓冲区画笔
        Graphics bufG = bufferedImage.getGraphics();
        //为界面添加键盘监听器
        jf.addKeyListener(listener);
        while (true) {
            //获取游戏背景图
            backgroundImage = new ImageIcon(fileAddress + "background.jpg");
            bufG.drawImage(backgroundImage.getImage(), 0, 0, null);
            //计时器自增，用来后续随机生成僵尸等
            ++count;
            //把我方飞机画出来
            plane.drawObject(bufG);
            plane.move();
            //生成僵尸
            generateZombies();
            for (FlyObject f : enemies) {
                f.drawObject(bufG);
                f.move();
            }

            //生成我机攻击子弹
            for (FlyObject bullet : plane.bullets) {
                bullet.drawObject(bufG);
                bullet.move();
            }
            //最后记得要把这个也画出来
            g.drawImage(bufferedImage, 0, 0, null);
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
