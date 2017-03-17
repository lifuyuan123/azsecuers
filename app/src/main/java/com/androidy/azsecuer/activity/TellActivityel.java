package com.androidy.azsecuer.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.adapter.TellAdapter;
import com.androidy.azsecuer.activity.base.BaseActionBarActivity;
import com.androidy.azsecuer.activity.db.AssetsManager;
import com.androidy.azsecuer.activity.db.DbManager;
import com.androidy.azsecuer.activity.entity.TellType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TellActivityel extends BaseActionBarActivity implements AdapterView.OnItemClickListener {
  private ListView lv_list_tell;
//    private List<String> datas;
    private TellAdapter tellAdapter;
    private ImageView iv_set_left;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tell_activityel);
//        innitDatas();
        initDB();
        initView();
        listenerView();

    }
//    private void innitDatas(){
//        datas=new ArrayList<String>();
//        datas.add("测试数据1");
//        datas.add("测试数据2");
//        datas.add("测试数据3");
//        datas.add("测试数据4");
//        datas.add("测试数据5");
//        datas.add("测试数据6");
//        datas.add("测试数据7");
//        datas.add("测试数据8");
//        datas.add("测试数据9");
//        datas.add("测试数据10");
//        adapter=new TellAdapter(this,datas);
//
//    }
    public void initDB() {
        DbManager.createFile(this);
        if (DbManager.dbFileIsExists()) {
            try {
                AssetsManager.copyDbFile(this, "commonnum.db", DbManager.fileDB);
                Log.i("copyDBFile", "-----");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("nocopyDBFile", "-----");
        }
//        DbManager.readTellType();
//        DbManager.readTellNumber(3);
    }


    @Override
    protected void initView() {
        setActionBarBack("通讯大全");
        iv_set_left=(ImageView)findViewById(R.id.iv_set_left) ;

        lv_list_tell=(ListView)findViewById(R.id.lv_list_tell);
        tellAdapter=new TellAdapter(this);
        lv_list_tell.setAdapter(tellAdapter);
        tellAdapter.setDatas(DbManager.rendTellType());
        lv_list_tell.setOnItemClickListener(this);



    }

    @Override
    protected void listenerView() {
        iv_set_left.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_set_left:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//    Bundle bundle=new Bundle();
//        bundle.putString("type",(String)tellAdapter.getItem(position));
//        startActivity(TellListActivity.class,bundle);

        Intent intent=new Intent(this,TellListActivity.class);
        Bundle bundle=new Bundle();
        int idx=((TellType)tellAdapter.getItem(position)).getIdx();
        bundle.putInt("idx",idx);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
