package com.hw.object;

import com.hw.parameter.Vector;

public class Plane extends FlyObject {
    public Plane(Vector location, Vector velocity, Vector accelerator) {
        super(location, velocity, accelerator);
    }

    public Plane(Vector location, Vector velocity, Vector accelerator, String imgName) {
        super(location, velocity, accelerator, imgName);
    }

    public Plane(Vector location, Vector velocity, Vector accelerator, String imgName, int HP) {
        super(location, velocity, accelerator, imgName, HP);
    }

    public void setVelocity(int x, int y) {
        this.velocity.x = x;
        this.velocity.y = y;
    }
}
