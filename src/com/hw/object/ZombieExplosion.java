package com.hw.object;

import javax.swing.*;
import java.awt.*;

public class ZombieExplosion {
    private ImageIcon[] image = new ImageIcon[11];
    private String fileAddress = "explosion_image/";
    private int index = 0;
    public int x, y;
    public ZombieExplosion(int x, int y) {
        this.x = x;
        this.y = y;
        for (int i = 0; i < image.length; ++i) {
            image[i] = new ImageIcon(fileAddress + "zombie_explosion" + i + ".png");
        }
    }

    public void drawExplosion(Graphics g) {
        System.out.println("开始绘制爆炸效果");
        for (int i = 0; i < image.length; ++i) {
            if (index < 11) {
                g.drawImage(image[index].getImage(), x - 10, y, null);
                ++index;
                System.out.println("index = " + index);
            }
        }
    }
}
