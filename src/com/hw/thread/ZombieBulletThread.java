package com.hw.thread;

import com.hw.object.FlyObject;
import com.hw.parameter.MyVector;

import java.util.Random;
import java.util.Vector;

public class ZombieBulletThread implements Runnable {
    public int count;
    public Vector<FlyObject> enemies, attackers;
    public ZombieBulletThread() {
        count = 0;
        attackers = new Vector<>();
        enemies = new Vector<>();
    }

    @Override
    public void run() {
        while (true) {
            ++count;
            //生成僵尸子弹,子弹绘制过程在主线程中完成,这里只负责创建子弹对象

            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
