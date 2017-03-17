package com.androidy.azsecuer.activity.db;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/8/12.
 */
public class AssetsManager {
    public static void copyDbFile(Context context,String assetsFilePath, File toFile)throws IOException{
        InputStream inputStream=context.getResources().getAssets().open(assetsFilePath);
        byte[] buff=new byte[1024];
        int len=0;
        BufferedInputStream bufferedInputStream=new BufferedInputStream(inputStream);
        FileOutputStream fileOutputStream=new FileOutputStream(toFile);
        BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(fileOutputStream);
        while ((len=bufferedInputStream.read(buff))!=-1){
           bufferedOutputStream.write(buff,0,len);
        }
        bufferedOutputStream.flush();//刷新
        bufferedOutputStream.close();
        bufferedInputStream.close();
        inputStream.close();
        fileOutputStream.close();
    }
}
