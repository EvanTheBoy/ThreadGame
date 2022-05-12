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
        //获取按键值
        int key = e.getKeyCode();
        //根据对应按键值进行相应操作
        switch (key) {
            case KeyEvent.VK_S -> plane.setVelocity(0, 5);
            case KeyEvent.VK_A -> plane.setVelocity(-5, 0);
            case KeyEvent.VK_W -> plane.setVelocity(0, -5);
            case KeyEvent.VK_D -> plane.setVelocity(5, 0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //此处同上，只是操作更改
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_S -> plane.setVelocity(0, 0);
        }
    }
}
