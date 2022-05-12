package com.hw.listener;

import com.hw.object.Plane;

import java.awt.event.*;

public class Listener implements ActionListener, MouseMotionListener, MouseListener, KeyListener {
    public Plane plane;
    public Listener(Plane p) {
        this.plane = p;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("即将获取按键值");
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_S -> plane.setVelocity(0, 1);
            case KeyEvent.VK_A -> plane.setVelocity(-1, 0);
            case KeyEvent.VK_W -> plane.setVelocity(0, -1);
            case KeyEvent.VK_D -> plane.setVelocity(1, 0);
        }
        //控制飞机移动
        System.out.println("成功移动");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_S -> plane.setVelocity(0, 0);
        }
        System.out.println("停止");
    }
}
