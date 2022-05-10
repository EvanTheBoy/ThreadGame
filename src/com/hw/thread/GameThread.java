package com.hw.thread;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameThread implements Runnable, KeyListener {
    public Graphics g;
    private boolean gameRest; //判断游戏是否暂停
    public static String fileAddress = "img/"; //图片的存储根目录
    @Override
    public void run() {

    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
