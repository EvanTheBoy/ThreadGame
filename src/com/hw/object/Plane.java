package com.hw.object;

import com.hw.parameter.Vector;

import java.util.ArrayList;


public class Plane extends FlyObject {
    public ArrayList<FlyObject> bullets;
    public Plane(Vector location, Vector velocity, Vector accelerator, String imgName) {
        super(location, velocity, accelerator, imgName);
        bullets = new ArrayList<>();
    }

    public void setVelocity(int x, int y) {
        this.velocity.x = x;
        this.velocity.y = y;
    }

    //重写我机的移动方法
    public void move() {
        velocity.add(accelerator);
        location.add(velocity);
        if (location.x < 40) {
            location.x = 40;
        } else if (location.x > 964) {
            location.x = 964;
        }
        if (location.y < 40) {
            location.y = 40;
        } else if (location.y > 708) {
            location.y = 708;
        }
    }

    //发送子弹
    public void fire() {
        //获取我机当前的位置
        int originX = this.location.x;
        int originY = this.location.y;
        Vector loc = new Vector(originX, originY);
        Vector vel = new Vector(7, 0);
        Vector acc = new Vector(0, 0);
        FlyObject bullet = new FlyObject(loc, vel, acc);
        bullets.add(bullet);
    }
}
