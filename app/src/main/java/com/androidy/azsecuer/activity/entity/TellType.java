package com.androidy.azsecuer.activity.entity;

/**
 * Created by Administrator on 2016/8/12.
 */
public class TellType {
    private String name;
    private int idx;

    public TellType(String name, int idx) {
        this.name = name;
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    @Override
    public String toString() {
        return "TellType{" +
                "name='" + name + '\'' +
                ", idx=" + idx +
                '}';
    }
}
