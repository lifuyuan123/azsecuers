package com.androidy.azsecuer.activity.util;

import java.io.File;

/**
 * Created by Administrator on 2016/8/18.
 */
public class FileUtil {
    public static long getFilesize(File file) {
        long size = 0;
        if (file != null&&file.exists()) { //判定当前对象是否有效
            if(file.isFile()){ //判断是否是文件
                return size+=file.length();
            }
            if (file.isDirectory()) {  //判断是否是文件夹
                File[] files = file.listFiles();//获取文件夹的所有目录文件
                if (files != null) {
                    for (File temp : files) {
                             size += getFilesize(temp);
                    }
                }
                return size;
            }
        }
        return size;
    }

    public static void deleteSize(File file) {  //删除文件
        if (file != null&& file.exists()) {
            if(file.isFile()){  //是一个文件
                file.delete();//删除文件
            }
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    for (File temp : files) {
                        deleteSize(temp);
                    }
                }
            }
        }
    }
}
