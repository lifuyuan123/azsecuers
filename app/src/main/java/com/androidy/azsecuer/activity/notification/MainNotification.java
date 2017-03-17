package com.androidy.azsecuer.activity.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.HomeAcivity;

/**
 * Created by Administrator on 2016/8/8.
 */
public class MainNotification {
    private static NotificationManager notificationManager=null;
    private static Notification notification;
    private  static final int NOTIID=1;

    public static void openNotification(Context context){
        if(notificationManager==null){
            notificationManager=(NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        }
        Notification notification=null;
        RemoteViews remoteViews=new RemoteViews(context.getPackageName(), R.layout.notification);
        Intent intent=new Intent(context, HomeAcivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);//做更新
        notification=new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContent(remoteViews)//去设置notification的自定义布局
                .setTicker("有新消息")
                .setContentIntent(pendingIntent)
                .build();
        notification.flags=Notification.FLAG_NO_CLEAR;
        notificationManager.notify(NOTIID,notification);//发送通知栏消息
    }
    public  static void closeNotification(Context context){
        if(notificationManager==null){
            notificationManager=(NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        }
        notificationManager.cancelAll();//调用cancelAll（）通知消失，
    }

}
