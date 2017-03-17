package com.androidy.azsecuer.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.adapter.QuickAdapter;
import com.androidy.azsecuer.activity.base.BaseActionBarActivity;
import com.androidy.azsecuer.activity.entity.quick.Quicker;
import com.androidy.azsecuer.activity.manager.MemoryManager;
import com.androidy.azsecuer.activity.manager.QuickManager;
import com.androidy.azsecuer.activity.progressBarMove.ProgressbarMove;
import com.androidy.azsecuer.activity.util.PublicUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PhoneQuickActivity extends BaseActionBarActivity implements CompoundButton.OnCheckedChangeListener{
    private ImageView iv_set_left;
    private TextView tv_phone_quick_phonesname, tv_phone_quick_phonestyle,
            tv_phone_quick_g,tv_phone_quick_clean;
    private ProgressbarMove progressbarMove;
    private Button bt_phone_quick_list;
    private ListView lv_clean;
    private ProgressBar clean_progressbar;
    private CheckBox cb_phone_qucik_check;
    private View quick_bottom_button;
    private QuickAdapter quickAdapter;
    private boolean ischek=true;
    private List<Quicker> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_quick);
        initView();
        listenerView();
    }

    @Override
    protected void initView() {
        iv_set_left = (ImageView) findViewById(R.id.iv_set_left);
        tv_phone_quick_phonesname=(TextView)findViewById(R.id.tv_phone_quick_phonesname);
        tv_phone_quick_phonestyle=(TextView)findViewById(R.id.tv_phone_quick_phonestyle);
        tv_phone_quick_g=(TextView)findViewById(R.id.tv_phone_quick_g);
        tv_phone_quick_clean=(TextView)findViewById(R.id.tv_phone_quick_clean);
        bt_phone_quick_list=(Button)findViewById(R.id.bt_phone_quick_list) ;
        lv_clean=(ListView)findViewById(R.id.lv_clean);
        quickAdapter=new QuickAdapter(this);
        lv_clean.setAdapter(quickAdapter);
        clean_progressbar=(ProgressBar)findViewById(R.id.clean_progressbar) ;
        progressbarMove=(ProgressbarMove)findViewById(R.id.progressBar_phone_quick) ;
        cb_phone_qucik_check=(CheckBox)findViewById(R.id.cb_phone_qucik_check) ;
        quick_bottom_button=findViewById(R.id.quick_bottom_button);
        tv_phone_quick_phonesname.setText(Build.BRAND.toUpperCase());
        tv_phone_quick_phonestyle.setText(Build.MODEL.toUpperCase());
         long memAll=MemoryManager.getTotalRamMemory(this),
                memsize= MemoryManager.getTotalRamMemory(this) -MemoryManager.getAvailRamMemory(this);
        tv_phone_quick_g.setText("内存信息" +PublicUtils.formatSize(memsize) + "/" +PublicUtils.formatSize(memAll));
        progressbarMove.setprogressBarMove((int)( ((float)memsize/(float) memAll)*100));
        setActionBarBack("手机加速");
        ansyload();
    }

    @Override
    protected void listenerView() {
        iv_set_left.setOnClickListener(this);
        bt_phone_quick_list.setOnClickListener(this);
        tv_phone_quick_clean.setOnClickListener(this);
        cb_phone_qucik_check.setOnCheckedChangeListener(this);
    }
   public void ansyload(){
       quick_bottom_button.setVisibility(View.GONE);
       lv_clean.setVisibility(View.INVISIBLE);
       clean_progressbar.setVisibility(View.VISIBLE);
       new Thread(){
           @Override
           public void run() {
               super.run();
               QuickManager quickManager= QuickManager.getInstance(PhoneQuickActivity.this);
               List<Quicker> quickers=quickManager.getRuningApp(QuickManager.CLASSIFY_USER,true);
               Log.i("Quick",quickers.toString());
               quickAdapter.setDatas(quickers);
               runOnUiThread(new Runnable(){
                   @Override
                   public void run() {
                       quickAdapter.notifyDataSetChanged();
                       clean_progressbar.setVisibility(View.INVISIBLE);
                       lv_clean.setVisibility(View.VISIBLE);
                       quick_bottom_button.setVisibility(View.VISIBLE);
                   }
               });
           }
       }.start();
   }
    @Override
    public void onClick(View view) {
        QuickManager quickManager=QuickManager.getInstance(this);
        switch (view.getId()) {
            //返回按钮
            case R.id.iv_set_left:
                finish();
                break;
            //显示系统或者app进程按钮
            case R.id.bt_phone_quick_list:
            datas=quickManager.getRuningApp(QuickManager.CLASSIFY_SYS,false);
                if(ischek){
                    bt_phone_quick_list.setText("隐藏系统进程");
                    Log.i("Quick",datas.toString());
                    quickAdapter.getDataFromAdapter().addAll(datas);
                    ischek=false;
                }else {
                    bt_phone_quick_list.setText("显示系统进程");
                    quickAdapter.getDataFromAdapter().removeAll(datas);
                    ischek=true;
                }
                quickAdapter.notifyDataSetChanged();
            break;
            //一键清除按钮
            case R.id.tv_phone_quick_clean :
                 datas=quickAdapter.getDataFromAdapter();
                Iterator<Quicker> iterator=datas.iterator();
                while (iterator.hasNext()){
                    Quicker quicker=(Quicker) iterator.next();
                    if(quicker.ischeck){
                        quickManager.kill(quicker.name);
                        iterator.remove();
                        quickAdapter.removeDataFromAdapter(quicker);
                        quicker.ischeck=false;

                    }
                }
                   quickAdapter.setDatas(datas);
                     quickAdapter.notifyDataSetChanged();
                     cb_phone_qucik_check.setChecked(false);

            break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
         for(Quicker quicker:quickAdapter.getDataFromAdapter()){
           quicker.ischeck=isChecked;
         quickAdapter.notifyDataSetChanged();
     }
    }
}
