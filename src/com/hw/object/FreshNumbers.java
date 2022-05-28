package com.hw.object;

import java.awt.*;

public class FreshNumbers {
    public int score, hp;
    public FreshNumbers(int score, int hp) {
        this.score = score;
        this.hp = hp;
    }

    //刷新游戏中得到的分数
    public void refreshScore(Graphics g) {
        g.setColor(Color.CYAN);
        g.setFont(new Font("仿宋", Font.BOLD, 40));
        g.drawString("得分:" + score, 8, 700);
    }

    public void refreshLives(Graphics g) {
        g.setColor(Color.CYAN);
        g.setFont(new Font("仿宋", Font.BOLD, 40));
        g.drawString("生命值:" + hp, 8, 750);
    }

    //绘制game over,游戏结束
    public void gameOver(Graphics g) {

    }
}
