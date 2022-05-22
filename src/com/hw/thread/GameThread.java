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
    public Vector<FlyObject> enemies, attackers, demons, z_explosions, d_explosions, b_explosions;
    public GameThread(Graphics g, JFrame jf) {
        this.g = g;
        this.jf = jf;
        z_explosions = new Vector<>();
        MyVector location = new MyVector(70, 384);
        MyVector velocity = new MyVector(0, 0);
        MyVector accelerator = new MyVector(0, 0);
        //线程刚启动时就画出飞机
        plane = new Plane(location, velocity, accelerator, "command_post.png");
        listener = new Listener(plane);
    }

    //判断爆炸
    private void explode(FlyObject object) {
        FlyObject explosion;
        MyVector loc;
        if (object.imgName.equals(fileAddress + "balloon_zombie")) {
            //现在是获取僵尸位置
            int zx = object.location.x;
            int zy = object.location.y;
            loc = new MyVector(zx, zy);
            explosion = new FlyObject(loc, null, null, "zombie_explosion1.png", 5);
            z_explosions.add(explosion);
        }
        if (object.imgName.equals(fileAddress + "bullet_monster")) {
            //获取恶魔的位置
            int dx = object.location.x;
            int dy = object.location.y;
            loc = new MyVector(dx, dy);
            explosion = new FlyObject(loc, null, null, "demon_boom.png", 10);
            d_explosions.add(explosion);
        }
        if (object.imgName.equals(fileAddress + "zombie_bullet.png")) {
            //获取恶魔发射的子弹位置
            int zbx = object.location.x;
            int zby = object.location.y;
            loc = new MyVector(zbx, zby);
            explosion = new FlyObject(loc, null, null, "bullet_boom.png", 3);
            b_explosions.add(explosion);
        }
    }

    //绘制爆炸的效果
    private void drawZombieExplosion(Graphics g) {
        for (int i = 0; i < z_explosions.size(); ++i) {
            FlyObject explosion = z_explosions.get(i);
            explosion.drawObject(g);
            --explosion.hp;
            if (explosion.imgName.equals(fileAddress + "zombie_explosion1.png")) {
                ImageIcon imageIcon = new ImageIcon(fileAddress + "zombie_explosion" + (explosion.hp%11 + 1) + ".png");
                explosion.img = imageIcon.getImage();
            }
            if (explosion.hp == 0) {
                z_explosions.remove(i);
            }
        }
    }

    //判断我方子弹是否打中敌人
    private void judgeAttack() {
        //判断是否打中僵尸
        for (int i = 0; i < enemies.size(); ++i) {
            FlyObject zombie = enemies.get(i);
            //现在获取僵尸的位置
            int zx = zombie.location.x;
            int zy = zombie.location.y;
            for (int j = 0; j < plane.bullets.size(); ++j) {
                FlyObject myBullet = plane.bullets.get(j);
                //首先记录子弹的位置
                int bx = myBullet.location.x;
                int by = myBullet.location.y;
                //算出子弹与僵尸的位置差
                int distance = (int)Math.sqrt(Math.pow((zx - bx), 2) + Math.pow((zy - by), 2));
                if (distance <= 30) {
                    //僵尸的血量减少
                    zombie.hp -= myBullet.hp;
                    if (zombie.hp <= 0) {
                        //添加爆炸特效
                        explode(zombie);
                        zombie.img = null;
                        zombie.location = new MyVector(-1100, 0);
                    }
                }
            }
        }
        for (int i = 0; i < demons.size(); ++i) {
            FlyObject demon = demons.get(i);
            //获取恶魔的位置
            int dx = demon.location.x;
            int dy = demon.location.y;
            for (int j = 0; j < plane.bullets.size(); ++j) {
                FlyObject myBullet = plane.bullets.get(j);
                //记录子弹位置
                int bx = myBullet.location.x;
                int by = myBullet.location.y;
                //算出自担与恶魔的位置差
                int distance = (int) Math.sqrt(Math.pow((dx - bx), 2) + Math.pow((dy - by), 2));
                if (distance <= 40) {
                    demon.hp -= myBullet.hp;
                    if (demon.hp <= 0) {
                        explode(demon);
                        demon.img = null;
                        demon.location = new MyVector(-1100, 0);
                    }
                }
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
            //判断是否发生碰撞
            judgeAttack();
            //绘制爆炸效果
            drawZombieExplosion(bufG);
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
