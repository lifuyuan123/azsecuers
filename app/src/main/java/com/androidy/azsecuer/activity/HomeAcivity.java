package com.androidy.azsecuer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.View.CustomProc;
import com.androidy.azsecuer.activity.base.BaseActionBarActivity;
import com.androidy.azsecuer.activity.base.BaseActiviyt;
import com.androidy.azsecuer.activity.entity.quick.Quicker;
import com.androidy.azsecuer.activity.manager.MemoryManager;
import com.androidy.azsecuer.activity.manager.QuickManager;
import com.androidy.azsecuer.activity.util.LogUtil;
import com.androidy.azsecuer.activity.util.PublicUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/8/3.
 */
public class HomeAcivity extends BaseActionBarActivity {
    private ImageView iv_home_ic_child_configs;
    private TextView tv_home_text_tell,tv_home_text_quick,tv_home_soft,
            tv_home_detection,tv_home_text_fill,tv_home_text_clean;
    private CustomProc customPrco;
    private Button button;
    private int data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit_home);
        setActionBarHome("手机管家");
        initView();
        listenerView();
    }

    @Override
    protected void initView() {
        iv_home_ic_child_configs=(ImageView)findViewById(R.id.iv_set_right);
        tv_home_text_tell=(TextView)findViewById(R.id.tv_home_text_tell);
        tv_home_text_quick=(TextView)findViewById(R.id.tv_home_text_quick);
        tv_home_soft=(TextView)findViewById(R.id.tv_home_soft);
        tv_home_detection=(TextView)findViewById(R.id.tv_home_detection);
        tv_home_text_fill=(TextView)findViewById(R.id.tv_home_text_fill);
        tv_home_text_clean=(TextView)findViewById(R.id.tv_home_text_clean);
        button=(Button)findViewById(R.id.button);
        customPrco=(CustomProc)findViewById(R.id.customPrco);
        long memAll=MemoryManager.getTotalRamMemory(this),
                memsize= MemoryManager.getTotalRamMemory(this) - MemoryManager.getAvailRamMemory(this);
         data=(int)(((float)memsize/(float)memAll)*360);
        customPrco.startAnimSetProgress3(data);
    }

    @Override
    protected void listenerView() {
        iv_home_ic_child_configs.setOnClickListener(this);
        tv_home_text_tell.setOnClickListener(this);
        tv_home_text_quick.setOnClickListener(this);
        tv_home_soft.setOnClickListener(this);
        tv_home_detection.setOnClickListener(this);
        tv_home_text_fill.setOnClickListener(this);
        tv_home_text_clean.setOnClickListener(this);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_set_right:
                startActivity(SettingActivity.class);
                break;
            case R.id.tv_home_text_tell:
                startActivity(TellActivityel.class);
                break;
            case R.id.tv_home_text_quick:
                startActivity(PhoneQuickActivity.class);
                break;
            case R.id.tv_home_soft:
                startActivity(SoftwareManageActivity.class);
                break;
            case R.id.tv_home_detection:
                startActivity(PhoneDetectionActivity.class);
                break;
            case R.id.tv_home_text_fill:
                startActivity(FillManageActivity.class);
                break;
            case R.id.tv_home_text_clean:
                startActivity(CleanActivity.class);
                break;
            case R.id.button:
                QuickManager quickManager= QuickManager.getInstance(HomeAcivity.this);
                List<Quicker> quickers=quickManager.getRuningApp(QuickManager.CLASSIFY_USER,true);
                for(Quicker quicker:quickers){
                    quickManager.kill(quicker.name);
                }
                LogUtil.p("data",data+"");
                customPrco.startAnimSetProgress3(data);
                break;
        }

    }
}
