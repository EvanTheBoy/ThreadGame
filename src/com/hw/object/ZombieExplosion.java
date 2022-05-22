package com.hw.object;

import javax.swing.*;
import java.awt.*;

public class ZombieExplosion {
    private ImageIcon[] image;
    private String fileAddress = "explosion_image/";
    private int index = 0;
    public int x, y;
    public ZombieExplosion(int x, int y) {
        image = new ImageIcon[11];
        this.x = x;
        this.y = y;
    }

    private void loadPictures() {
        for (int i = 0; i < image.length; ++i) {
            image[i] = new ImageIcon(fileAddress + "zombie_explosion" + i + ".png");
        }
    }

    public void drawExplosion(Graphics g) {
        loadPictures();
        System.out.println("开始绘制爆炸效果");
        if (index <= 11) {
            g.drawImage(image[index].getImage(), x, y, null);
            ++index;
            System.out.println("index = " + index);
        }
    }
}
