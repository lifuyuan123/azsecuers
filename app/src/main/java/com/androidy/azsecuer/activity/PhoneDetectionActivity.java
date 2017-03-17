package com.androidy.azsecuer.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.adapter.MobileMyAdapter;
import com.androidy.azsecuer.activity.base.BaseActionBarActivity;
import com.androidy.azsecuer.activity.entity.Mobilchild;
import com.androidy.azsecuer.activity.entity.Mobilgroup;
import com.androidy.azsecuer.activity.manager.MobileManager;
import com.androidy.azsecuer.activity.progressBarMove.ProgressbarMove;

import java.util.ArrayList;
import java.util.List;

public class PhoneDetectionActivity extends BaseActionBarActivity {
    private ImageView iv_set_left;
    private ProgressbarMove progressBar;
    private TextView tv_text_detection;
    private MobileMyAdapter mobileMyAdapter;
    private MobileManager mobileManager;
    private ExpandableListView expandableListView;
    private MyReceiver myReceiver = new MyReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_detection);
        initData();
        initView();
        listenerView();

    }

    @Override
    protected void initView() {
        setActionBarBack("手机检测");
        iv_set_left = (ImageView) findViewById(R.id.iv_set_left);
        progressBar = (ProgressbarMove) findViewById(R.id.progressBar);
        tv_text_detection = (TextView) findViewById(R.id.tv_text_detection);
        expandableListView = (ExpandableListView) findViewById(R.id.expandablelistview);
        expandableListView.setAdapter(mobileMyAdapter);

//        动态设定
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(myReceiver, intentFilter);


    }

    public void initData() {
        mobileMyAdapter = new MobileMyAdapter(this);
        mobileManager=new MobileManager(this);
        aysnLoad();
//        List<Mobilchild> mobilchild=new ArrayList<>();
//        mobilchild.add(new Mobilchild("手机品牌",Build.BRAND));
//        mobilchild.add(new Mobilchild("手机制造商",Build.MANUFACTURER));
//        mobilchild.add(new Mobilchild("手机型号",Build.MODEL));
//        mobileMyAdapter.addtoAdapter(mobilchild,new Mobilgroup("信息",getResources().getDrawable(R.drawable.setting_info_icon_camera)));
    }
    public void aysnLoad(){
        final ProgressDialog pd = ProgressDialog.show(this,null,"加载中..",false,true);
        new Thread() {
            @Override
            public void run() {
                final Mobilgroup phoneInfoGroup = new Mobilgroup("设备信息",getResources().getDrawable(R.drawable.setting_info_icon_version));
                final List<Mobilchild> phonnChild = mobileManager.getPhoneMessage();

                final Mobilgroup sytemInfoGroup =new Mobilgroup("系统信息",getResources().getDrawable(R.drawable.setting_info_icon_space));
                final List<Mobilchild> systemChild = mobileManager.getSystemMessage();

                final Mobilgroup netInfoGroup = new Mobilgroup("网络信息",getResources().getDrawable(R.drawable.setting_info_icon_root));
                final List<Mobilchild> netChild = mobileManager.getWIFIMessage();

                final Mobilgroup creamInfoGroup = new Mobilgroup("相机信息",getResources().getDrawable(R.drawable.setting_info_icon_camera));
                final List<Mobilchild> creamChild = mobileManager.getCameraMessage();

                final Mobilgroup ramInfoFroup = new Mobilgroup("存储信息",getResources().getDrawable(R.drawable.setting_info_icon_space));
                final List<Mobilchild> ramChild = mobileManager.getMemoryMessage(PhoneDetectionActivity.this);

                /**
                 * 回到ui
                 * 跟handler非常相似
                 */
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mobileMyAdapter.addtoAdapter(phonnChild,phoneInfoGroup);
                        mobileMyAdapter.addtoAdapter(systemChild,sytemInfoGroup);
                        mobileMyAdapter.addtoAdapter(netChild ,netInfoGroup);
                        mobileMyAdapter.addtoAdapter(creamChild,creamInfoGroup);
                        mobileMyAdapter.addtoAdapter(ramChild,ramInfoFroup);
                        mobileMyAdapter.notifyDataSetChanged();
                        pd.cancel();
                    }
                });
                super.run();
            }
        }.start();
    }

    @Override
    protected void listenerView() {
        iv_set_left.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_set_left:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(myReceiver);
        super.onDestroy();
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            final int battery = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            tv_text_detection.setText(battery + "%");
            progressBar.setprogressBarMove(battery);
        }
    }
}
