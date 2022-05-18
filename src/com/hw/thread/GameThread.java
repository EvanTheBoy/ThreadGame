package com.hw.thread;

import com.hw.listener.Listener;
import com.hw.object.FlyObject;
import com.hw.object.Plane;
import com.hw.parameter.MyVector;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
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
    public Vector<FlyObject> enemies, attackers, demons;
    public GameThread(Graphics g, JFrame jf) {
        this.g = g;
        this.jf = jf;
        MyVector location = new MyVector(70, 384);
        MyVector velocity = new MyVector(0, 0);
        MyVector accelerator = new MyVector(0, 0);
        //线程刚启动时就画出飞机
        plane = new Plane(location, velocity, accelerator, "command_post.png");
        listener = new Listener(plane);
    }

    //判断爆炸
    private void explode(FlyObject object) {
        if (object.imgName.equals(fileAddress + "balloon_zombie")) {
            //现在是获取僵尸位置
            int zx = object.location.x;
            int zy = object.location.y;
            
        }
    }

    //判断我方子弹是否打中僵尸
    private void judgeAttack() {
        for (int i = 0; i < enemies.size(); ++i) {
            FlyObject zombie = enemies.get(i);
            //现在获取僵尸的位置
            int zx = zombie.location.x;
            int zy = zombie.location.y;
            for (int j = 0; j < plane.bullets.size(); ++i) {
                FlyObject myBullet = plane.bullets.get(j);
                //首先记录子弹的位置
                int bx = myBullet.location.x;
                int by = myBullet.location.y;
                //算出子弹与僵尸的位置差
                int distance = (int)Math.sqrt(Math.pow((zx - bx), 2) + Math.pow((zy - by), 2));

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
            System.out.println("count = " + count);
            //把我方飞机画出来
            plane.drawObject(bufG);
            plane.move();
            //绘制气球僵尸
            for (FlyObject f : enemies) {
                f.drawObject(bufG);
                f.move();
            }
            //绘制恶魔
            for (FlyObject f : demons) {
                f.drawObject(bufG);
                f.move();
            }
            //恶魔发射子弹
            for (FlyObject b : attackers) {
                b.drawObject(bufG);
                b.move();
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
