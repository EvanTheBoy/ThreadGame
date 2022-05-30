package com.hw.thread;

import com.hw.listener.Listener;
import com.hw.object.FlyObject;
import com.hw.object.FreshNumbers;
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
    private int score;
    private final Plane plane;
    private JFrame jf;
    private boolean gameOver = false; //判断游戏是否结束
    public static String fileAddress = "img/"; //图片的存储根目录
    private Listener listener;
    public Vector<FlyObject> enemies, attackers, demons;
    public boolean[] flag;

    public GameThread(Graphics g, JFrame jf) {
        this.g = g;
        this.jf = jf;
        score = 0;
        flag = new boolean[1];
        MyVector location = new MyVector(70, 384);
        MyVector velocity = new MyVector(0, 0);
        MyVector accelerator = new MyVector(0, 0);
        //线程刚启动时就画出飞机
        plane = new Plane(location, velocity, accelerator, "command_post.png", 100);
        listener = new Listener(plane);
    }

    //添加爆炸
    private void explode(FlyObject object, Graphics g) {
        DrawExplosion ze;
        if (object.imgName.equals(fileAddress + "balloon_zombie.png")) {
            //现在是获取僵尸位置
            int zx = object.location.x;
            int zy = object.location.y;
            ze = new DrawExplosion(zx, zy);
            for (; ; ) {
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
            for (; ; ) {
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
            for (; ; ) {
                if (ze.index < 6) {
                    ze.drawBulletExplosion(g);
                } else {
                    break;
                }
            }
        }
    }

    //判断敌人是否打中我方
    private void judgeAttackMe(Graphics g) {
        //首先获取我机的位置
        int my_x = plane.location.x;
        int my_y = plane.location.y;
        //然后判断恶魔发射的子弹是否打中我
        for (int i = 0; i < attackers.size(); ++i) {
            FlyObject rebel = attackers.get(i);
            int rx = rebel.location.x;
            int ry = rebel.location.y;
            if (Math.abs(my_x - rx) <= 28) {
                int down = my_y - ry; //子弹打中飞机下翼
                int up = ry - my_y; //子弹打中飞机上翼
                if ((down >= 0 && down <= 60) || (up >= 0 && up <= 20)) {
                    //飞机的hp要减
                    plane.hp -= 1;
                    rebel.img = null;
                    rebel.location = new MyVector(-1100, 0);
                    //飞机的血量都小于0了,必然game over
                    if (plane.hp <= 0) {
                        System.out.println("飞机阵亡!");
                    }
                }
            }
        }

        //判断僵尸是否碰撞我机
        for (int i = 0; i < enemies.size(); ++i) {
            FlyObject rebel = enemies.get(i);
            int rx = rebel.location.x;
            int ry = rebel.location.y;
            if (Math.abs(my_x - rx) <= 25) {
                int down = my_y - ry;
                int up = ry - my_y;
                if ((down >= 0 && down <= 88) || (up >= 0 && up <= 48)) {
                    plane.hp -= 3;
                    rebel.img = null;
                    rebel.location = new MyVector(-1100, 0);
                    if (plane.hp <= 0) {
                        System.out.println("飞机阵亡!");
                    }
                }
            }
        }

        //判断恶魔是否与我机碰撞
        for (int i = 0; i < demons.size(); ++i) {
            FlyObject rebel = demons.get(i);
            int rx = rebel.location.x;
            int ry = rebel.location.y;
            int down = my_y - ry; //我机在下面
            int up = ry - my_y; //恶魔在下面
            if (((rx - my_x) <= 14 && down >= 0 && down <= 120) || (((rx - my_x) <= 45) && up >= 0 && up <= 60)) {
                plane.hp -= 6;
                rebel.img = null;
                rebel.location = new MyVector(-1100, 0);
                if (plane.hp <= 0) {
                    System.out.println("飞机没了!");
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
                if ((zx - bx) <= 20) {
                    int down = zy - by; //子弹击中僵尸下方
                    int up = by - zy; //子弹击中僵尸上方
                    if ((down >= 0 && down <= 35) || (up >= 0 && up <= 33)) {
                        //僵尸的血量减少
                        zombie.hp -= myBullet.hp;
                        if (zombie.hp <= 0) {
                            this.score += 7;
                            //添加爆炸特效
                            explode(zombie, g);
                            zombie.img = null;
                            enemies.remove(zombie);
                            break;
                        }
                        myBullet.img = null;
                        plane.bullets.remove(myBullet);
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
                if ((dx - bx) <= 25) {
                    int down = by - dy;
                    int up = dy - by;
                    if ((down >= 0 && down <= 50) || (up >= 0 && up <= 40)) {
                        demon.hp -= myBullet.hp;
                        if (demon.hp <= 0) {
                            this.score += 14;
                            explode(demon, g);
                            demon.img = null;
                            demons.remove(demon);
                            break;
                        }
                        myBullet.img = null;
                        plane.bullets.remove(myBullet);
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
                //算出子弹与恶魔发射的子弹的位置差
                int distance = (int) Math.sqrt(Math.pow((zbx - bx), 2) + Math.pow((zby - by), 2));
                if (distance <= 20) {
                    bullet.hp -= myBullet.hp;
                    if (bullet.hp <= 0) {
                        explode(bullet, g);
                        bullet.img = null;
                        attackers.remove(bullet);
                        break;
                    }
                    myBullet.img = null;
                    plane.bullets.remove(myBullet);
                }
            }
        }
    }

    //暂停画面中所有正在移动的物体
    private void stopAll(Graphics g) {
        for (int i = 0; i < enemies.size(); ++i) {
            FlyObject zombie = enemies.get(i);
            MyVector loc = zombie.location;
            zombie.velocity = new MyVector(0, 0);
            zombie = new FlyObject(loc, zombie.velocity, zombie.accelerator, "balloon_zombie.png");
            zombie.drawObject(g);
            zombie.move();
        }
        for (int i = 0; i < demons.size(); ++i) {
            FlyObject demon = demons.get(i);
            MyVector loc = demon.location;
            demon.velocity = new MyVector(0, 0);
            demon = new FlyObject(loc, demon.velocity, demon.accelerator, "bullet_monster.png");
            demon.drawObject(g);
            demon.move();
        }
        for (int i = 0; i < attackers.size(); ++i) {
            FlyObject bullet = attackers.get(i);
            MyVector loc = bullet.location;
            bullet.velocity = new MyVector(0, 0);
            bullet = new FlyObject(loc, bullet.velocity, bullet.accelerator, "zombie_bullet.png");
            bullet.drawObject(g);
            bullet.move();
        }
        for (int i = 0; i < plane.bullets.size(); ++i) {
            FlyObject myBullet = plane.bullets.get(i);
            MyVector loc = myBullet.location;
            myBullet.velocity = new MyVector(0, 0);
            myBullet = new FlyObject(loc, myBullet.velocity, myBullet.accelerator, "bullet.png");
            myBullet.drawObject(g);
            myBullet.move();
        }
    }

    //判断是否可以赢
    private void judgeWinning(Graphics g, int score) {
        if (score >= 500) {
            stopAll(g);
            flag[0] = true;
            ImageIcon winning = new ImageIcon("score_image/YouWIN.png");
            Image win = winning.getImage();
            g.drawImage(win, 200, 100, null);
        }
    }

    //判断是否游戏结束
    private void judgeGameOver(Graphics g, int hp) {
        if (hp <= 0) {
            stopAll(g);
            flag[0] = true;
            ImageIcon gameOver = new ImageIcon("score_image/GameOver.png");
            Image over = gameOver.getImage();
            g.drawImage(over, 193, 97, null);
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
            //游戏背景
            ImageIcon backgroundImage = new ImageIcon(fileAddress + "background.jpg");
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
            //判断敌人是否打中我机
            judgeAttackMe(bufG);

            //刷新分数和我机生命值
            FreshNumbers fs = new FreshNumbers(score, plane.hp);
            fs.refreshScore(bufG);
            fs.refreshLives(bufG);

            //最后要判断是赢了还是输了
            judgeWinning(bufG, fs.score);
            judgeGameOver(bufG, plane.hp);

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
