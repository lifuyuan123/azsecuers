package com.androidy.azsecuer.activity.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;

/**
 * Created by Administrator on 2016/8/3.
 */
public abstract class BaseActiviyt extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initView();
//        listenerView();
    }
/*
初始化控件 空间的监听
 */
   protected abstract void  initView();
   protected abstract void listenerView();
/*
界面的跳转
 */
    protected  void startActivity(Class<?> oneclass){
        Intent intent=new Intent(this,oneclass);
        startActivity(intent);
    }
    protected  void  startActivity(Class<?> oneclass,Bundle bundle){
        Intent intent=new Intent(this,oneclass);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
