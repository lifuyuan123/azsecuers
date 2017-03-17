package com.androidy.azsecuer.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.SettingPreferences.SettingPreferences;
import com.androidy.azsecuer.activity.base.BaseActionBarActivity;
import com.androidy.azsecuer.activity.base.BaseActiviyt;
import com.androidy.azsecuer.activity.notification.MainNotification;

public class SettingActivity extends BaseActionBarActivity implements CompoundButton.OnCheckedChangeListener{
    private ToggleButton tb_setting_on,tb_seting_off;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ImageView iv_set_left;
    private boolean starton=false;
    private boolean closeoff=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sharedPreferences=this.getSharedPreferences("data",this.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        setActionBarBack("设置");
        initView();
        listenerView();
    }

    @Override
    protected void initView() {
        iv_set_left=(ImageView)findViewById(R.id.iv_set_left) ;
        tb_setting_on = (ToggleButton) findViewById(R.id.toggleButton_on);
        tb_seting_off = (ToggleButton) findViewById(R.id.toggleButton_off);
        starton=sharedPreferences.getBoolean("key_starton",false);
        closeoff=sharedPreferences.getBoolean("key_closeoff",false);
        tb_setting_on.setChecked(starton);
        tb_seting_off.setChecked(closeoff);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.putBoolean("key_starton",tb_setting_on.isChecked());
        editor.putBoolean("key_closeoff",tb_seting_off.isChecked());
        editor.commit();
    }

    @Override
    protected void listenerView() {
        iv_set_left.setOnClickListener(this);
        tb_setting_on.setOnClickListener(this);
        tb_seting_off.setOnClickListener(this);
        tb_setting_on.setOnCheckedChangeListener(this);
        tb_seting_off.setOnCheckedChangeListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.iv_set_left:
                finish();
                break;
//            case R.id.toggleButton_on:
//               if(!starton){
//                   starton=true;
//                   tb_setting_on.setBackgroundResource(R.drawable.check_switch_on);
//               }else{
//                   starton=false;
//                   tb_setting_on.setBackgroundResource(R.drawable.check_switch_off);
//               }
//                break;
//            case R.id.toggleButton_off:
//                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.toggleButton_on:
                SettingPreferences.initSettingPre(this);
             SettingPreferences.saveCheckedAuto(isChecked);
                break;
            case R.id.toggleButton_off:
                SettingPreferences.initSettingPre(this);
                SettingPreferences.saveCheckedNoti(isChecked);
                if(isChecked){
                    MainNotification.openNotification(this);
                }else{
                    MainNotification.closeNotification(this);
                }
                break;
        }
    }
}
