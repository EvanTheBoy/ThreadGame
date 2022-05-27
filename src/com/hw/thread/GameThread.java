package com.hw.thread;

import com.hw.listener.Listener;
import com.hw.object.FlyObject;
import com.hw.object.Plane;
import com.hw.object.DrawExplosion;
import com.hw.parameter.MyVector;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class GameThread implements Runnable {
    public Graphics g;
    private int backgroundX = 0;
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

    //添加爆炸
    private void explode(FlyObject object, Graphics g) {
        DrawExplosion ze;
        if (object.imgName.equals(fileAddress + "balloon_zombie.png")) {
            //现在是获取僵尸位置
            System.out.println("获取到僵尸的位置了!");
            int zx = object.location.x;
            int zy = object.location.y;
            ze = new DrawExplosion(zx, zy);
            for (; ;) {
                if (ze.index < 11) {
                    ze.drawZombieExplosion(g);
                } else {
                    break;
                }
            }
        }
        if (object.imgName.equals(fileAddress + "bullet_monster.png")) {
            //获取恶魔的位置
            int dx = object.location.x;
            int dy = object.location.y;
            ze = new DrawExplosion(dx, dy);
            for (; ;) {
                if (ze.index < 14) {
                    ze.drawDemonExplosion(g);
                } else {
                    break;
                }
            }
        }
        if (object.imgName.equals(fileAddress + "zombie_bullet.png")) {
            //获取恶魔发射的子弹位置
            int zbx = object.location.x;
            int zby = object.location.y;
            ze = new DrawExplosion(zbx, zby);
            for (; ;) {
                if (ze.index < 6) {
                    ze.drawBulletExplosion(g);
                } else {
                    break;
                }
            }
        }
    }

    //判断我方子弹是否打中敌人
    private void judgeAttack(Graphics g) {
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
                        explode(zombie, g);
                        zombie.img = null;
                        zombie.location = new MyVector(-1100, 0);
                    }
                }
            }
        }
        //判断是否打中恶魔
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
                //算出子弹与恶魔的位置差
                int distance = (int) Math.sqrt(Math.pow((dx - bx), 2) + Math.pow((dy - by), 2));
                if (distance <= 40) {
                    demon.hp -= myBullet.hp;
                    if (demon.hp <= 0) {
                        explode(demon, g);
                        demon.img = null;
                        demon.location = new MyVector(-1100, 0);
                    }
                }
            }
        }
        //判断是否打中子弹
        for (int i = 0; i < attackers.size(); ++i) {
            FlyObject bullet = attackers.get(i);
            //获取子弹的位置
            int zbx = bullet.location.x;
            int zby = bullet.location.y;
            for (int j = 0; j < plane.bullets.size(); ++j) {
                FlyObject myBullet = plane.bullets.get(j);
                //记录子弹位置
                int bx = myBullet.location.x;
                int by = myBullet.location.y;
                //算出子弹与恶魔的位置差
                int distance = (int) Math.sqrt(Math.pow((zbx - bx), 2) + Math.pow((zby - by), 2));
                if (distance <= 20) {
                    bullet.hp -= myBullet.hp;
                    if (bullet.hp <= 0) {
                        explode(bullet, g);
                        bullet.img = null;
                        bullet.location = new MyVector(-1100, 0);
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
            bufG.drawImage(backgroundImage.getImage(), backgroundX, 0, null);
            bufG.drawImage(backgroundImage.getImage(), backgroundX - backgroundImage.getIconWidth(), 0, null);
            if (backgroundX >= backgroundImage.getIconWidth()) {
                backgroundX = 0;
            }
            ++backgroundX;
            //计时器自增，用来后续随机生成僵尸等
            //把我方飞机画出来
            plane.drawObject(bufG);
            plane.move();
            //绘制气球僵尸
            for (int i = 0; i < enemies.size(); ++i) {
                FlyObject f = enemies.get(i);
                f.drawObject(bufG);
                f.move();
            }
            //绘制恶魔
            for (int i = 0; i < demons.size(); ++i) {
                FlyObject d = demons.get(i);
                d.drawObject(bufG);
                d.move();
            }
            //恶魔发射子弹
            for (int i = 0; i < attackers.size(); ++i) {
                FlyObject a = attackers.get(i);
                a.drawObject(bufG);
                a.move();
            }
            //生成我机攻击子弹
            for (int i = 0; i < plane.bullets.size(); ++i) {
                FlyObject b = plane.bullets.get(i);
                b.drawObject(bufG);
                b.move();
            }
            //判断是否发生碰撞
            judgeAttack(bufG);
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
