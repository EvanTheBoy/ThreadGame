package com.hw.thread;

import com.hw.object.FlyObject;
import com.hw.parameter.Vector;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ZombieBulletThread implements Runnable {
    public int count;
    public Graphics g;
    public ArrayList<FlyObject> enemies, attackers;
    public ZombieBulletThread(Graphics g) {
        count = 0;
        this.g = g;
        enemies = new ArrayList<>();
    }

    //生成僵尸发射的子弹
    private void generateBullets() {
        for (FlyObject monster : enemies) {
            if (count % 140 == 0) {
                //首先获取僵尸的位置
                int mx = monster.location.x;
                int my = monster.location.y;
                Vector loc = new Vector(mx - 60, my);
                Vector vel = new Vector(-3, 0);
                Vector acc = new Vector(0, 0);
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
        while (true) {
            ++count;
            generateBullets();
            //僵尸发射子弹
            for (FlyObject b : attackers) {
                b.drawObject(bufG);
                b.move();
            }
            g.drawImage(bufferedImage, 0, 0, null);
        }
    }
}
