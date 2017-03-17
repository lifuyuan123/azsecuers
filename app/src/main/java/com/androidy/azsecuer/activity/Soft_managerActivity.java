package com.androidy.azsecuer.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.adapter.SoftAdapter;
import com.androidy.azsecuer.activity.base.BaseActionBarActivity;
import com.androidy.azsecuer.activity.entity.Softinfo;
import com.androidy.azsecuer.activity.manager.SoftwareManager;
import com.androidy.azsecuer.activity.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class Soft_managerActivity extends BaseActionBarActivity implements AdapterView.OnItemClickListener {
    private ListView lv_soft_manager;
    private ImageView iv_set_left;
    private SoftAdapter softAdapter;
    private List<Softinfo> softinfos = new ArrayList<>();
    private ProgressBar soft_list_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_manager);
        initView();
        innitData();
        listenerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        softAdapter.setDatas(softinfos);
        softAdapter.notifyDataSetChanged();

    }

    @Override
    protected void initView() {
        lv_soft_manager = (ListView) findViewById(R.id.lv_soft_manager);
        soft_list_progress = (ProgressBar) findViewById(R.id.soft_list_progress);
        iv_set_left = (ImageView) findViewById(R.id.iv_set_left);
        setActionBarBack("软件列表");
        softAdapter = new SoftAdapter(this);
        lv_soft_manager.setAdapter(softAdapter);
    }

    protected void innitData() {

        asnyLoad();

    }

    protected void asnyLoad() {
        final int classify = this.getIntent().getExtras().getInt("type");
        lv_soft_manager.setVisibility(View.INVISIBLE);
        soft_list_progress.setVisibility(View.VISIBLE);
        new Thread() {
            @Override
            public void run() {
                super.run();
                SoftwareManager softwareManager = SoftwareManager.getInstance(Soft_managerActivity.this);
                softinfos = softwareManager.getSoftware(classify);
                LogUtil.p("softinfos", softinfos.toString());
                softAdapter.setDatas(softinfos);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lv_soft_manager.setVisibility(View.VISIBLE);
                        soft_list_progress.setVisibility(View.INVISIBLE);
                        softAdapter.notifyDataSetChanged();
                    }
                });
            }
        }.start();
    }

    @Override
    protected void listenerView() {
        lv_soft_manager.setOnItemClickListener(this);
        iv_set_left.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id.iv_set_left :
             finish();
         break;
     }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      final   Softinfo softinfo = (Softinfo) softAdapter.getItem(position);
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("删除").setMessage("确定删除"+softinfo.name)
                .setCancelable(true)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_DELETE);
                        LogUtil.p("Softinfo", softinfo.name);
                        intent.setData(Uri.parse("package:" + softinfo.lable));
                        startActivity(intent);
                        softinfos.remove(softinfo);
                    }
                });

        builder.show();
    }
}
