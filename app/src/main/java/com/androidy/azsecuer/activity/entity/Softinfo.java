package com.androidy.azsecuer.activity.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/8/23.
 */
public class Softinfo {
    public String name,lable,size;
    public Drawable drawable;

    public Softinfo(String name, String lable, String size, Drawable drawable) {
        this.name = name;
        this.lable = lable;
        this.size = size;
        this.drawable = drawable;
    }

    @Override
    public String toString() {
        return "Softinfo{" +
                "name='" + name + '\'' +
                ", lable='" + lable + '\'' +
                ", size='" + size + '\'' +
                ", drawable=" + drawable +
                '}';
    }
}
