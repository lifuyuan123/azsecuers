package com.androidy.azsecuer.activity.progressBarMove;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/8/15.
 */
public class ProgressbarMove extends ProgressBar {
    public ProgressbarMove(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setprogressBarMove(final int progress) {//progress 广播当前的值
        final Timer timer = new Timer();
        final TimerTask timerTask = new TimerTask() {
            int move = getProgress() > progress ? 1 : 2;//三元运算符 判断当前值和获取值得大小
            @Override
            public void run() {
                switch (move) {
                    case 1:
                        setProgress(getProgress() - 1);
                        break;
                    case 2:
                        setProgress(getProgress() + 1);
                        break;
                }
                if(getProgress()==progress){
                   timer.cancel();   //如果值一样就停止
                }
            }
        };
        timer.schedule(timerTask, 30, 30);// 操作对象，间隔时间30毫秒，重复30次
    }
}
