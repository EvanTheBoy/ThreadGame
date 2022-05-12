package com.hw.object;

import com.hw.parameter.Vector;

public class Plane extends FlyObject {
    public Plane(Vector location, Vector velocity, Vector accelerator, String imgName) {
        super(location, velocity, accelerator, imgName);
    }

    public void setVelocity(int x, int y) {
        this.velocity.x = x;
        this.velocity.y = y;
    }

    public void fire() {
        
    }
}
