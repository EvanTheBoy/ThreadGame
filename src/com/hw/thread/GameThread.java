package com.hw.thread;

import com.hw.listener.Listener;
import com.hw.object.FlyObject;
import com.hw.object.Plane;
import com.hw.parameter.MyVector;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Vector;

public class GameThread implements Runnable {
    public Graphics g;
    private int count = 0;
    private Plane plane;
    private JFrame jf;
    private boolean gameRest; //判断游戏是否暂停
    private ImageIcon backgroundImage; //游戏背景
    public static String fileAddress = "img/"; //图片的存储根目录
    private Listener listener;
    private Random rand;

    public Vector<FlyObject> enemies, attackers;
    public GameThread(Graphics g, JFrame jf) {
        this.g = g;
        this.jf = jf;
        rand = new Random();
        enemies = new Vector<>();
        attackers = new Vector<>();
        MyVector location = new MyVector(70, 384);
        MyVector velocity = new MyVector(0, 0);
        MyVector accelerator = new MyVector(0, 0);
        //线程刚启动时就画出飞机
        plane = new Plane(location, velocity, accelerator, "command_post.png");
        listener = new Listener(plane);
    }

    //生成气球僵尸
    private void generateZombies() {
        if (count % 40 == 0) {
            int ly = rand.nextInt(512) + 100;
            int vx = -rand.nextInt(1) - 2;
            MyVector loc = new MyVector(1024, ly);
            MyVector vel = new MyVector(vx, 0);
            MyVector acc = new MyVector(0, 0);
            FlyObject zombie = new FlyObject(loc, vel, acc, "balloon_zombie.png", 5);
            //这里之所以要用到队列，是因为僵尸画出来后要一直停留在页面上，也就是不停地画
            //所以在这里就体现为，每创建出一个僵尸对象，就把它添加进队列中，然后从队列中取出来
            //不停地画
            enemies.add(zombie);
        }
    }

    //生成僵尸发射的子弹
    private void generateBullets() {
        if (count % 140 == 0) {
            for (FlyObject monster : enemies) {
                //然后获取僵尸的位置
                int mx = monster.location.x;
                int my = monster.location.y;
                MyVector loc = new MyVector(mx - 60, my);
                MyVector vel = new MyVector(-3, 0);
                MyVector acc = new MyVector(0, 0);
                //创建僵尸发射的子弹对象
                FlyObject attacker = new FlyObject(loc, vel, acc, "zombie_bullet.png");
                attackers.add(attacker);
            }
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
            //僵尸发射子弹
            for (FlyObject b : attackers) {
                b.drawObject(bufG);
                b.move();
            }
            generateBullets();
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
