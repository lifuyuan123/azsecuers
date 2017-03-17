package com.androidy.azsecuer.activity.entity.clean;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/8/17.
 */
public class Clean {
    private String name,Engname,apkname,filepath,stype,size;
    private boolean isCheck;
    private Drawable drawable;

    public Clean( String name,String engname,String apkname,String filepath,
                  String stype, boolean isCheck,Drawable drawable,String size) {
        this.stype = stype;
        this.filepath = filepath;
        this.apkname = apkname;
        Engname = engname;
        this.name = name;
        this.isCheck=isCheck;
        this.drawable=drawable;
        this.size=size;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        size = size;
    }

    public boolean getisCheck() {
        return isCheck;
    }


    public void setisCheck(boolean ischeck) {
        isCheck = ischeck;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getApkname() {
        return apkname;
    }

    public void setApkname(String apkname) {
        this.apkname = apkname;
    }

    public String getEngname() {
        return Engname;
    }

    public void setEngname(String engname) {
        Engname = engname;
    }
    @Override
    public String toString() {
        return "Clean{" +
                "name='" + name + '\'' +
                ", Engname='" + Engname + '\'' +
                ", apkname='" + apkname + '\'' +
                ", filepath='" + filepath + '\'' +
                ", stype='" + stype + '\'' +
                ", isCheck=" + isCheck +
                ", drawable=" + drawable +
                ", size=" + size +
                '}';
    }


}
