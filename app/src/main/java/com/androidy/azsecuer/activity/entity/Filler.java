package com.androidy.azsecuer.activity.entity;

/**
 * Created by Administrator on 2016/8/24.
 */
public class Filler {
    public String name,fileType;
    public long size;
    public boolean loadingover;

    public Filler(String name, String fileType) {
        this.name = name;
        this.size = size;
        this.fileType=fileType;
        this.loadingover=false;

    }
    @Override
    public String toString() {
        return "Filler{" +
                "name='" + name + '\'' +
                ", fileType='" + fileType + '\'' +
                ", size=" + size +
                ", loadingover=" + loadingover +
                '}';
    }

}
