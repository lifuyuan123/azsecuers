package com.androidy.azsecuer.activity.entity.quick;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/8/19.
 */
public class  Quicker {
    public String name,lable;
    public long nameSize;
    public  Drawable drawable;
    public boolean ischeck,isSystemApp;

    public Quicker(String name, long nameSize, String lable, Drawable drawable,boolean isSystemApp) {
        this.name = name;
        this.nameSize = nameSize;
        this.lable = lable;
        this.drawable = drawable;
        this.ischeck = false;
        this.isSystemApp = isSystemApp;

    }

    @Override
    public String toString() {
        return "Quicker{" +
                "name='" + name + '\'' +
                ", lable='" + lable + '\'' +
                ", nameSize=" + nameSize +
                ", drawable=" + drawable +
                ", ischeck=" + ischeck +
                ", isSystemApp=" + isSystemApp +
                '}';
    }
}
