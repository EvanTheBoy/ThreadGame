package com.hw.object;

import com.hw.parameter.MyVector;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * 飞行物体，包括飞机、子弹、僵尸等，这里有不同的构造方法是因为像子弹这类物体
 * 是没有hp这种属性的，所以需要根据各种物体的不同属性创建不同的构造方法
 */
public class FlyObject {
    public MyVector location; //初始位置
    public MyVector velocity; //速度
    public MyVector accelerator; //加速度
    public static String imgAddress = "img/"; //存储图片的目录
    private HashMap<String, MyVector> hm = new HashMap<>();

    //移动物体的基本参数
    public String imgName; //图片
    public Image img; //图片对象
    public int hp; //血量

    public void initialize() {
        hm.put(imgAddress + "command_post.png", new MyVector(50, 50));
        hm.put(imgAddress + "balloon_zombie.png", new MyVector(50, 50));
        hm.put(imgAddress + "bullet.png", new MyVector(17, 10));
        hm.put(imgAddress + "background.png", new MyVector(0, 0));
        hm.put(imgAddress + "explosion.png", new MyVector(50, 50));
        hm.put(imgAddress + "zombie_bullet.png", new MyVector(0, 0));
    }

    //这里是只有位置、速度和加速度的物体
    public FlyObject(MyVector location, MyVector velocity, MyVector accelerator) {
        this.location = location;
        this.velocity = velocity;
        this.accelerator = accelerator;
        initialize();
    }

    //有图片的移动物体
    public FlyObject(MyVector location, MyVector velocity, MyVector accelerator, String imgName) {
        this.location = location;
        this.velocity = velocity;
        this.accelerator = accelerator;
        this.imgName = imgAddress + imgName;
        ImageIcon imageIcon = new ImageIcon(this.imgName);
        this.img = imageIcon.getImage();
        initialize();
    }

    //有图片和血量的移动物体， 这里包括气球僵尸等
    public FlyObject(MyVector location, MyVector velocity, MyVector accelerator, String imgName, int HP) {
        this.location = location;
        this.velocity = velocity;
        this.accelerator = accelerator;
        this.imgName = imgAddress + imgName;
        ImageIcon imageIcon = new ImageIcon(this.imgName);
        this.img = imageIcon.getImage();
        this.hp = HP;
        initialize();
    }

    //所有物体都遵循这个移动方法
    public void move() {
        velocity.add(accelerator);
        location.add(velocity);
    }

    //绘制图形
    public void drawObject(Graphics g) {
        if (imgName != null) {
            int deviatedX = hm.get(imgName).x;
            int deviatedY = hm.get(imgName).y;
            g.drawImage(img, location.x - deviatedX, location.y - deviatedY, null);
        } else {
            g.fillOval(location.x, location.y, 10, 10);
        }
    }
}
