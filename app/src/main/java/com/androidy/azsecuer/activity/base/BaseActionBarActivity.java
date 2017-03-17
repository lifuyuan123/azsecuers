package com.androidy.azsecuer.activity.base;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidy.azsecuer.R;

/**
 * Created by Administrator on 2016/8/4.
 */
public abstract class BaseActionBarActivity extends BaseActiviyt {
    ImageView iv_set_left, iv_set_right;
    TextView tv_home_text1;

    /*
    绑定actionbar的控件
     */
    protected void findActionbarView() throws NoFindActionBarException {
        iv_set_left = (ImageView) findViewById(R.id.iv_set_left);
        iv_set_right = (ImageView) findViewById(R.id.iv_set_right);
        tv_home_text1 = (TextView) findViewById(R.id.tv_home_text1);
    }

    /*
    产生没有找到actionbar的异常
     */
    class NoFindActionBarException extends Exception {
        public NoFindActionBarException() {
            super("是否有action？这里没有找到");
        }
    }

    protected void setActionBar(int leftactionID, int rightactionID, String hometext) {
        try {
            findActionbarView();
        } catch (NoFindActionBarException e) {
            e.printStackTrace();
        }
        if (leftactionID == -1) {
            iv_set_left.setVisibility(View.INVISIBLE);
        } else {
            iv_set_left.setImageResource(leftactionID);
        }
        if (rightactionID == -1) {
            iv_set_right.setVisibility(View.INVISIBLE);
        } else {
            iv_set_right.setImageResource(rightactionID);
        }
        if (hometext == null) {
            tv_home_text1.setVisibility(View.INVISIBLE);
        } else {
            tv_home_text1.setText(hometext);
        }
    }

    /*
   返回界面
    */
    protected void setActionBarBack(String actiongBarTitl) {
        setActionBar(R.drawable.btn_homeasup_default, -1, actiongBarTitl);
    }

    /*
    主界面
     */
    protected void setActionBarHome(String actiongBarTitl) {
        setActionBar(R.drawable.ic_launcher, R.drawable.ic_child_configs, actiongBarTitl);
    }

}
