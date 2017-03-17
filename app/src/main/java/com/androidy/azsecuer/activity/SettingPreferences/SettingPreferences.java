package com.androidy.azsecuer.activity.SettingPreferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/8/9.
 */
public class SettingPreferences {
    private static SharedPreferences sharedPreferences = null;
    // 会使用到的上下文对象
    private static Context context = null;
    // 存取读取时所使用的键
    private static final String key_starton = "key_starton",key_closeoff = "key_closeoff";
    // 将构造方法私有化 不提供产生这个类的对象
    private SettingPreferences(){

    }
    public static void initSettingPre(Context context){
        SettingPreferences.context = context.getApplicationContext();
        if(sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences("SettingPreferences",context.MODE_PRIVATE);
    }
    // 存入数据到sharedPreferences的操作 帮助性方法
    private static void saveSetting(String key,boolean value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }
    // 保存当前的开机启动信息到手机
    public static void saveCheckedAuto(boolean isCheckedAuto){
        saveSetting(key_starton,isCheckedAuto);
    }
    public static void saveCheckedNoti(boolean isCheckedNoti){
        saveSetting(key_closeoff,isCheckedNoti);
    }
    // 获取当前设置信息返回给调用者
    public static boolean getCheckedAuto(){
        return sharedPreferences.getBoolean(key_starton,false);
    }
    public static boolean getCheckedNoti(){
        return  sharedPreferences.getBoolean(key_closeoff,false);
    }
}
