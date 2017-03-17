package com.androidy.azsecuer.activity.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/8/16.
 */
public class Mobilgroup {
    private String title;
    private Drawable drawable;

    public Mobilgroup(String title, Drawable drawable) {
        this.title = title;
        this.drawable = drawable;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
