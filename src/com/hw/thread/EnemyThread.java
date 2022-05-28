package com.hw.thread;

import com.hw.object.FlyObject;
import com.hw.parameter.MyVector;

import java.util.Random;
import java.util.Vector;

public class EnemyThread implements Runnable {
    public Vector<FlyObject> enemies, demons, attackers;
    private Random rand;
    private int count;
    public EnemyThread() {
        count = 0;
        rand = new Random();
        enemies = new Vector<>();
        demons = new Vector<>();
        attackers = new Vector<>();
    }

    //生成气球僵尸与恶魔
    private void generateZombies() {
        if (count % 40 == 0) {
            int ly = rand.nextInt(512) + 100;
            int vx = -rand.nextInt(1) - 2;
            MyVector loc = new MyVector(1024, ly);
            MyVector vel = new MyVector(vx, 0);
            MyVector acc = new MyVector(0, 0);
            FlyObject zombie = new FlyObject(loc, vel, acc, "balloon_zombie.png", 3);
            //这里之所以要用到队列，是因为僵尸画出来后要一直停留在页面上，也就是不停地画
            //所以在这里就体现为，每创建出一个僵尸对象，就把它添加进队列中，然后从队列中取出来
            //不停地画
            enemies.add(zombie);
        }
        if (count >= 300 && count % 90 == 0) {
            int ly = rand.nextInt(502) + 103;
            int vx = -rand.nextInt(1) - 3;
            MyVector loc = new MyVector(1024, ly);
            MyVector vel = new MyVector(vx, 0);
            MyVector acc = new MyVector(0, 0);
            FlyObject demon = new FlyObject(loc, vel, acc, "bullet_monster.png", 6);
            demons.add(demon);
        }
    }

    //生成恶魔发射的子弹
    private void generateBullets() {
        for (FlyObject monster : demons) {
            //然后获取僵尸的位置
            int mx = monster.location.x;
            int my = monster.location.y;
            if (mx % 67 == 0) {
                MyVector loc = new MyVector(mx - 60, my);
                MyVector vel = new MyVector(-5, 0);
                MyVector acc = new MyVector(0, 0);
                //创建僵尸发射的子弹对象
                FlyObject attacker = new FlyObject(loc, vel, acc, "zombie_bullet.png", 2);
                attackers.add(attacker);
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            ++count;
            //生成僵尸与恶魔
            generateZombies();
            //生成恶魔发射的子弹
            generateBullets();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
