package com.androidy.azsecuer.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.adapter.FilllistAdapter;
import com.androidy.azsecuer.activity.base.BaseActionBarActivity;
import com.androidy.azsecuer.activity.entity.Filllist;
import com.androidy.azsecuer.activity.manager.FileSearchManager;
import com.androidy.azsecuer.activity.util.LogUtil;
import com.androidy.azsecuer.activity.util.PublicUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FilllistActivity extends BaseActionBarActivity implements AdapterView.OnItemClickListener ,CompoundButton.OnCheckedChangeListener{
    private ImageView iv_set_left;
    private TextView tv_filllist_all, tv_filllist_size;
    private FileSearchManager fileSearchManager;
    private FilllistAdapter filllistAdapter;
    private String fileType;
    private ListView lv_clean;
    private Button bt_fill_list;
    private CheckBox cb_filllist;



    /**
     * 所有文件分类集合
     */
    private HashMap<String, ArrayList<Filllist>> filllists;
    /**
     * 所有文件大小集合
     */
    private HashMap<String, Long> fileSizes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filllist);
        initView();
        initDta();
        listenerView();

    }

    @Override
    protected void initView() {
        iv_set_left = (ImageView) findViewById(R.id.iv_set_left);
        tv_filllist_all = (TextView) findViewById(R.id.tv_filllist_all);
        tv_filllist_size = (TextView) findViewById(R.id.tv_filllist_size);
        lv_clean = (ListView) findViewById(R.id.lv_clean);
        cb_filllist = (CheckBox) findViewById(R.id.cb_filllist);
        bt_fill_list = (Button) findViewById(R.id.bt_fill_list);
        setActionBarBack("文件浏览");

    }

    public void initDta() {
        // 取得FileServcieActivity传入的文件类型
        fileType = getIntent().getStringExtra("fileType");
        // 取得文件列表数据信息
        fileSearchManager = FileSearchManager.getInstance(false);
        filllists = fileSearchManager.getFileInfos(); // 文件实体集合(Map)
        fileSizes = fileSearchManager.getFileSizes(); // 文件大小集合(Map)
        long size = fileSizes.get(fileType);
        long count = filllists.get(fileType).size();
        // 将文件列表数量和大小分别设置到对应的文件控件上
        tv_filllist_all.setText("" + count);
        tv_filllist_size.setText(PublicUtils.formatSize(size));
        LogUtil.p("filllists.get(fileType)", (filllists.get(fileType)).toString());
        // 将文件实体集合数据适配到文件列表控件上
        filllistAdapter = new FilllistAdapter(this, lv_clean);
        filllistAdapter.addDataToAdapter(filllists.get(fileType));
        lv_clean.setAdapter(filllistAdapter);
        filllistAdapter.notifyDataSetChanged();
    }

    @Override
    protected void listenerView() {
        iv_set_left.setOnClickListener(this);
        bt_fill_list.setOnClickListener(this);
        cb_filllist.setOnCheckedChangeListener(this);
        lv_clean.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        List<Filllist> data = filllists.get(fileType);
        switch (v.getId()) {
            case R.id.iv_set_left:
                finish();
                break;
            case R.id.bt_fill_list:
                for (Filllist filllist : data) {
                    if (filllist.ischeck) {
                        filllist.file.delete();
                        filllistAdapter.removeDataFromAdapter(filllist);
                    }
                }
                filllistAdapter.notifyDataSetChanged();
                break;
        }
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int count = filllistAdapter.getCount();
        for (int i = 0; i < count; i++) {
            filllistAdapter.getItem(i).ischeck = isChecked;
        }
        filllistAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Filllist filllist = filllistAdapter.getItem(position);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.fromFile(filllist.file);
        String type = filllist.openType;
        intent.setDataAndType(data, type);
        startActivity(intent);
    }
}
