package com.androidy.azsecuer.activity.util;

import android.util.Log;

/**
 * Created by Administrator on 2016/8/3.
 */
public class LogUtil {
  private static final boolean isDebug=true;
    private static final boolean isErro=true;
    public static void p(String tag,String msg){
   if(isDebug){
       Log.i(tag,msg);
   }
    }
    public static void p(String tag,String msg,Throwable tr){
        if(isErro){
            Log.i(tag,msg,tr);
        }
    }
}
