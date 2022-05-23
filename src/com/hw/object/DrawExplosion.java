package com.hw.object;

import javax.swing.*;
import java.awt.*;

public class DrawExplosion {
    private ImageIcon[] image1 = new ImageIcon[11];  //存放僵尸的爆炸效果图
    private ImageIcon[] image2 = new ImageIcon[14]; //存放恶魔的爆炸效果图
    private ImageIcon[] image3 = new ImageIcon[11]; //存放子弹的爆炸效果图
    private String zombieAddress = "balloon_explosion_image/";
    private String demonAddress = "demon_explosion_image/";

    public int x, y;
    public DrawExplosion(int x, int y) {
        this.x = x;
        this.y = y;
        for (int i = 0; i < image1.length; ++i) {
            image1[i] = new ImageIcon(zombieAddress + "zombie_explosion" + i + ".png");
        }
        for (int i = 0; i < image2.length; ++i) {
            image2[i] = new ImageIcon(demonAddress + "demon_explosion" + i + ".png");
        }
    }

    public void drawZombieExplosion(Graphics g) {
        int index = 0;
        System.out.println("开始绘制爆炸效果");
        for (int i = 0; i < image1.length; ++i) {
            if (index < 11) {
                g.drawImage(image1[index].getImage(), x - 10, y, null);
                ++index;
                System.out.println("index = " + index);
            }
        }
    }

    public void drawDemonExplosion(Graphics g) {
        int index = 0;
        for (int i = 0; i < image2.length; ++i) {
            if (index < 14) {
                g.drawImage(image2[index].getImage(), x - 10, y, null);
                ++index;
            }
        }
    }
}
