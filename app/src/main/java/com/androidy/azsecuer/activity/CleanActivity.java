package com.androidy.azsecuer.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.adapter.CleanAdapter;
import com.androidy.azsecuer.activity.base.BaseActionBarActivity;
import com.androidy.azsecuer.activity.db.AssetsManager;
import com.androidy.azsecuer.activity.db.dbclean.DBmanager;
import com.androidy.azsecuer.activity.entity.clean.Clean;
import com.androidy.azsecuer.activity.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class CleanActivity extends BaseActionBarActivity {
    private ImageView iv_set_left;
    private ListView lv_clean;
    private CleanAdapter cleanAdapter;
    private ProgressBar clean_progressbar;
    private CheckBox checkBox;
    private List<Clean> cleans;
    private TextView tv_clean_text_buttom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean);
        initdb();
        initView();
        listenerView();
    }

    @Override
    protected void initView() {
        setActionBarBack("垃圾清理");
        iv_set_left = (ImageView) findViewById(R.id.iv_set_left);
        lv_clean = (ListView) findViewById(R.id.lv_clean);
        clean_progressbar = (ProgressBar) findViewById(R.id.clean_progressbar);
        tv_clean_text_buttom = (TextView) findViewById(R.id.tv_clean_text_buttom);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        cleanAdapter = new CleanAdapter(this);
        lv_clean.setAdapter(cleanAdapter);
        aysnLoad();

    }

    public void aysnLoad() {
        new Thread() {
            //            final ProgressDialog pd = ProgressDialog.show(CleanActivity.this,null,"加载中..",false,true);
            @Override
            public void run() {
                super.run();
                cleans = DBmanager.rendClean(CleanActivity.this);
                cleanAdapter.setDatas(cleans);
                try {
                    this.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                pd.cancel();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        clean_progressbar.setVisibility(View.INVISIBLE);
                        lv_clean.setVisibility(View.VISIBLE);
                        cleanAdapter.notifyDataSetChanged();
                    }
                });
            }
        }.start();

    }

    public void aysndelete() {
        clean_progressbar.setVisibility(View.VISIBLE);
        lv_clean.setVisibility(View.INVISIBLE);
        new Thread() {
            @Override
            public void run() {
                super.run();
                Iterator<Clean> iterator = cleans.iterator();
                while (iterator.hasNext()) {
                    Clean clean1 = (Clean) iterator.next();
                    if (clean1.getisCheck()) {//判断选择的
                        File file = new File(clean1.getFilepath());
                        FileUtil.deleteSize(file);//删除操作
                        iterator.remove();//在源数据中删除
                    }
                }
                try {
                    this.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cleanAdapter.setDatas(cleans);
                        cleanAdapter.notifyDataSetChanged();
                        clean_progressbar.setVisibility(View.INVISIBLE);
                        lv_clean.setVisibility(View.VISIBLE);
                    }
                });
            }
        }.start();

    }

    @Override
    protected void listenerView() {
        iv_set_left.setOnClickListener(this);
        tv_clean_text_buttom.setOnClickListener(this);
    }

    public void initdb() {
        DBmanager.createFile(this);
        if (DBmanager.dbFileIsExists()) {
            try {
                AssetsManager.copyDbFile(this, "clearpath.db", DBmanager.fileDB);
                Log.i("copyDBFile", "-----");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("nocopyDBFile", "-----");
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_set_left:
                finish();
                break;
            case R.id.tv_clean_text_buttom:
                aysndelete();
                break;
        }
    }

}
