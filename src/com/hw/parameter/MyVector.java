package com.hw.parameter;

/**
 * 此处定义一个向量类，也为矢量，诸如速度、加速度等都为矢量
 * 因此各种物体的速度、加速度等属性全部都应该是这种数据类型的
 */
public class MyVector {
    public int x;
    public int y;
    public MyVector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //向量的加法
    public void add(MyVector vec) {
        this.x += vec.x;
        this.y += vec.y;
    }
}
