package com.androidy.azsecuer.activity.entity;

import java.io.File;

/**
 * Created by Administrator on 2016/8/24.
 */
public class Filllist {
    public File file;
    public int romType;
    public String fileType,iconName,openType;
    public boolean ischeck;

    public Filllist(File file, int romType, String fileType, String iconName, String openType) {
        this.file = file;
        this.romType = romType;
        this.fileType = fileType;
        this.iconName = iconName;
        this.openType = openType;
        this.ischeck = false;
    }

    @Override
    public String toString() {
        return "Filllist{" +
                "file=" + file +
                ", romType=" + romType +
                ", fileType='" + fileType + '\'' +
                ", iconName='" + iconName + '\'' +
                ", openType='" + openType + '\'' +
                ", ischeck=" + ischeck +
                '}';
    }
}
