package com.androidy.azsecuer.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.View.PiecharView;
import com.androidy.azsecuer.activity.base.BaseActionBarActivity;
import com.androidy.azsecuer.activity.manager.MemoryManager;
import com.androidy.azsecuer.activity.manager.NewMemoryManager;
import com.androidy.azsecuer.activity.manager.SoftwareManager;
import com.androidy.azsecuer.activity.progressBarMove.ProgressbarMove;
import com.androidy.azsecuer.activity.util.PublicUtils;

public class SoftwareManageActivity extends BaseActionBarActivity {
     private ImageView iv_set_left;
     private TextView tv_soft_insize,tv_soft_outsize,tv_pb_one_buttom,tv_pb_one_buttom_
             ,tv_soft_all,tv_soft_system,tv_soft_user;
    private ProgressbarMove progressbar_soft,progressbar_soft_;
    private int usedInRomProp100, usedOutRomProp100,inRomProp360,outRomProp360;
    private PiecharView soft_pidcharview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_software_manage);
        initView();
        listenerView();
    }

    @Override
    protected void initView() {
        iv_set_left=(ImageView)findViewById(R.id.iv_set_left);
        tv_soft_insize=(TextView)findViewById(R.id.tv_soft_insize) ;
        tv_soft_outsize=(TextView)findViewById(R.id.tv_soft_outsize) ;
        tv_pb_one_buttom=(TextView)findViewById(R.id.tv_pb_one_buttom) ;
        tv_pb_one_buttom_=(TextView)findViewById(R.id.tv_pb_one_buttom_) ;
        tv_soft_all=(TextView)findViewById(R.id.tv_soft_all);
        tv_soft_system=(TextView)findViewById(R.id.tv_soft_syetem);
        tv_soft_user=(TextView)findViewById(R.id.tv_soft_user);
        progressbar_soft=(ProgressbarMove)findViewById(R.id.progressbar_soft);
        progressbar_soft_=(ProgressbarMove)findViewById(R.id.progressbar_soft_);
        soft_pidcharview=(PiecharView)findViewById(R.id.soft_pidcharview);
       long out=NewMemoryManager.getTotalExternalMemorySize(),in= NewMemoryManager.getTotalInternalMemorySize()
                ,freeout=NewMemoryManager.getAvailableExternalMemorySize(),freein=NewMemoryManager.getAvailableInternalMemorySize();
        long totalRom = in + out ;
        inRomProp360 = (int) ((float) out / totalRom * 360);
        outRomProp360 = 360 - inRomProp360;
        // 设置饼状图信息
        int data[][] = new int[][]{{this.getResources().getColor(R.color.bulu),inRomProp360,0},
                {this.getResources().getColor(R.color.colorPrimary),outRomProp360,0}};
        soft_pidcharview.setAngleWithAnim2(data);
        tv_soft_insize.setText(PublicUtils.formatSize(in));
        tv_soft_outsize.setText(PublicUtils.formatSize(out));
        tv_pb_one_buttom.setText(PublicUtils.formatSize(in-freein)+"/"+PublicUtils.formatSize(in));
        tv_pb_one_buttom_.setText(PublicUtils.formatSize(out-freeout)+"/"+PublicUtils.formatSize(out));
        progressbar_soft.setprogressBarMove((int)((((float)(in-freein))/(float)in)*100));
        progressbar_soft_.setprogressBarMove((int)((((float)(out-freeout))/(float)out)*100));
        setActionBarBack("软件管理");
    }

    @Override
    protected void listenerView() {
        iv_set_left.setOnClickListener(this);
        tv_soft_all.setOnClickListener(this);
        tv_soft_system.setOnClickListener(this);
        tv_soft_user.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(Intent.ACTION_DELETE);
        Bundle bundle=new Bundle();
      switch (view.getId()){
          case R.id.iv_set_left:
              finish();
              break;
          case R.id.tv_soft_all:
              bundle.putInt("type", SoftwareManager.CLASSIFY_ALL);
              startActivity(Soft_managerActivity.class,bundle);
              break;
          case R.id.tv_soft_syetem:
              bundle.putInt("type", SoftwareManager.CLASSIFY_SYS);
              startActivity(Soft_managerActivity.class,bundle);
              break;
          case R.id.tv_soft_user:
              bundle.putInt("type", SoftwareManager.CLASSIFY_USER);
              startActivity(Soft_managerActivity.class,bundle);
              break;

      }
    }
}
