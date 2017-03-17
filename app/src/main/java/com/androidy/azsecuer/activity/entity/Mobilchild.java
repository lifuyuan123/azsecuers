package com.androidy.azsecuer.activity.entity;

/**
 * Created by Administrator on 2016/8/16.
 */
public class Mobilchild {
    private String title,content;

    public Mobilchild(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
