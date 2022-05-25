package com.hw.object;

import javax.swing.*;
import java.awt.*;

public class DrawExplosion {
    private final ImageIcon[] image1 = new ImageIcon[11];  //存放僵尸的爆炸效果图
    private final ImageIcon[] image2 = new ImageIcon[14]; //存放恶魔的爆炸效果图
    private final ImageIcon[] image3 = new ImageIcon[6]; //存放子弹的爆炸效果图
    public int index = 0;

    public int x, y;
    public DrawExplosion(int x, int y) {
        this.x = x;
        this.y = y;
        for (int i = 0; i < image1.length; ++i) {
            String zombieAddress = "balloon_explosion_image/";
            image1[i] = new ImageIcon(zombieAddress + "zombie_explosion" + i + ".png");
        }
        for (int i = 0; i < image2.length; ++i) {
            String demonAddress = "demon_explosion_image/";
            image2[i] = new ImageIcon(demonAddress + "demon_explosion" + i + ".png");
        }
        for (int i = 0; i < image3.length; ++i) {
            String bulletAddress = "bullet_explosion_image/";
            image3[i] = new ImageIcon(bulletAddress + "bullet_explosion" + i + ".png");
        }
    }

    public void drawZombieExplosion(Graphics g) {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        g.drawImage(image1[index].getImage(), x - 10, y, null);
        ++index;
        System.out.println("僵尸的index = " + index);
    }

    public void drawDemonExplosion(Graphics g) {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        g.drawImage(image2[index].getImage(), x - 10, y, null);
        ++index;
        System.out.println("恶魔的index = " + index);
    }

    public void drawBulletExplosion(Graphics g) {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        g.drawImage(image3[index].getImage(), x - 5, y, null);
        ++index;
        System.out.println("子弹的index = " + index);
    }
}
