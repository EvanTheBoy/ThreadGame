package com.hw.object;

import javax.swing.*;
import java.awt.*;

public class FreshScore {
    public int x, y;
    public FreshScore(int x, int y) {
        this.x = x;
        this.y = y;
        String fileAddress = "score_image/";
        ImageIcon scoreIcon = new ImageIcon(fileAddress + "score.png");
        Image score = scoreIcon.getImage();
        ImageIcon num0 = new ImageIcon(fileAddress + "0.png");
        Image n0 = num0.getImage();
        ImageIcon num1 = new ImageIcon(fileAddress + "1.png");
        Image n1 = num1.getImage();
        ImageIcon num2 = new ImageIcon(fileAddress + "2.png");
        Image n2 = num2.getImage();
        ImageIcon num3 = new ImageIcon(fileAddress + "3.png");
        Image n3 = num3.getImage();
        ImageIcon num4 = new ImageIcon(fileAddress + "4.png");
        Image n4 = num4.getImage();
        ImageIcon num5 = new ImageIcon(fileAddress + "5.png");
        Image n5 = num5.getImage();
        ImageIcon num6 = new ImageIcon(fileAddress + "6.png");
        Image n6 = num6.getImage();
        ImageIcon num7 = new ImageIcon(fileAddress + "7.png");
        Image n7 = num7.getImage();
        ImageIcon num8 = new ImageIcon(fileAddress + "8.png");
        Image n8 = num8.getImage();
        ImageIcon num9 = new ImageIcon(fileAddress + "9.png");
        Image n9 = num9.getImage();
    }

    //刷新游戏中得到的分数
    public void refreshScore(Graphics g) {

    }

    //绘制game over,游戏结束
    public void gameOver(Graphics g) {

    }
}
