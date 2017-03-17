package com.androidy.azsecuer.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.adapter.FillAdapter;
import com.androidy.azsecuer.activity.base.BaseActionBarActivity;
import com.androidy.azsecuer.activity.entity.Filler;
import com.androidy.azsecuer.activity.manager.FileSearchManager;
import com.androidy.azsecuer.activity.manager.FileSearchTypeEvent;
import com.androidy.azsecuer.activity.util.PublicUtils;

import java.util.HashMap;

public class FillManageActivity extends BaseActionBarActivity implements AdapterView.OnItemClickListener{
    private ImageView iv_set_left;
    private ListView lv_soft_manager;
    private ProgressBar soft_list_progress;
    private FillAdapter fillAdapter;
    private TextView tv_fill_totle_size,tv_list_fillright;
    private Button bt_fill;
    private long totalsize;
    private Thread thread;
    private HashMap<String, Long> fileSizes;
    private FileSearchManager fileSearchManager;
    private Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                int what = msg.what;
                switch (what) {
                    // onSearchStart 当开始搜索时更新UI
                    case 0:
                        bt_fill.setClickable(false);
                        bt_fill.setText("搜索中");
                        break;
                    // onSearching 每搜索到一个文件更新UI
                    case 1:
                        // String fileType = (String) msg.obj;
                        // long size = fileSizes.get(fileType);
                        tv_fill_totle_size.setText(PublicUtils.formatSize(totalsize));
                        break;
                    // onSearchEnd 搜索结束更新UI
                    case 2:
                        int searchLocationRom = msg.arg1;
                        switch (searchLocationRom) {
                            // 内置空间搜索结束后UI更新 (click为true,可再进行深度搜索)
                            case 0:
                                bt_fill.setClickable(true);
                                bt_fill.setText("深度搜索");
                                break;
                            // 外置空间搜索结束后UI更新(click为false,搜索完毕)
                            case 1:
                                bt_fill.setClickable(false);
                                bt_fill.setText("搜索完毕");
                                break;
                        }
                        int count = fillAdapter.getCount();
                        for (int i = 0; i < count; i++) {
                            Filler info = fillAdapter.getItem(i);
                            info.size = fileSizes.get(info.fileType);
                            info.loadingover = true;
                        }
                        fillAdapter.notifyDataSetChanged();
                        break;
                }
            };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_manage);
        initView();
        initData();
        listenerView();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 离开当前页面时，中止搜索
        fileSearchManager.setStopSearch(true);
        // 离开当前页面时，中止线程
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
        System.gc();
    };


    @Override
    protected void initView() {
        setActionBarBack("文件管理");
        iv_set_left=(ImageView)findViewById(R.id.iv_set_left);
        lv_soft_manager=(ListView)findViewById(R.id.lv_soft_manager);
        soft_list_progress=(ProgressBar)findViewById(R.id.soft_list_progress) ;
        tv_fill_totle_size=(TextView)findViewById(R.id.tv_fill_totle_size);
        bt_fill=(Button)findViewById(R.id.bt_fill);
        tv_list_fillright=(TextView)findViewById(R.id.tv_list_fillright);

    }
    protected void initData(){
        fillAdapter =new FillAdapter(this);
        fillAdapter.addDataToAdapter(new Filler("文本文件", FileSearchTypeEvent.TYPE_TXT));
        fillAdapter.addDataToAdapter(new Filler("图像文件", FileSearchTypeEvent.TYPE_IMAGE));
        fillAdapter.addDataToAdapter(new Filler("APK文件", FileSearchTypeEvent.TYPE_APK));
        fillAdapter.addDataToAdapter(new Filler("视频文件", FileSearchTypeEvent.TYPE_VIDEO));
        fillAdapter.addDataToAdapter(new Filler("音频文件", FileSearchTypeEvent.TYPE_AUDIO));
        fillAdapter.addDataToAdapter(new Filler("压缩文件", FileSearchTypeEvent.TYPE_ZIP));
        fillAdapter.addDataToAdapter(new Filler("其它文件", FileSearchTypeEvent.TYPE_OTHER));
        lv_soft_manager.setAdapter(fillAdapter);
        totalsize=0;
        fileSearchManager =FileSearchManager.getInstance(true);
        fileSizes=fileSearchManager.getFileSizes();
        asynLoadInRomFile();
    }
    // 异步搜索内置文件
    public void asynLoadInRomFile(){

       thread= new Thread(new Runnable() {
            @Override
            public void run() {
                fileSearchManager.setOnFileSearchListener(searchListener);
                fileSearchManager.startSearchFromInRom(FillManageActivity.this);
            }
       });
        thread.start();
    }
    // 异步搜索外置文件
    private void asynLoadOutRomFile() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                fileSearchManager.setOnFileSearchListener(searchListener);
                fileSearchManager.startSearchFromOutRom(FillManageActivity.this);
            }
        });
        thread.start();
    }
    /** 搜索监听,回调接口 */
    private FileSearchManager.OnFileSearchListener searchListener=new FileSearchManager.OnFileSearchListener() {
        @Override
        public void onSearchStart(int searchLocationRom) {
        handler.sendEmptyMessage(0);
        }

        @Override
        public void onSearching(String fileType, long totalSize) {
                FillManageActivity.this.totalsize=totalSize;
            Message message=handler.obtainMessage();
            message.what=1;
            message.obj=fileType;
            handler.sendMessage(message);
        }

        @Override
        public void onSearchEnd(boolean isExceptionEnd, int searchLocationRom) {
          Message message=handler.obtainMessage();
            message.what=2;
            message.arg1=searchLocationRom;
            handler.sendMessage(message);
        }
    };
    @Override
    protected void listenerView() {
        iv_set_left.setOnClickListener(this);
        bt_fill.setOnClickListener(this);
        lv_soft_manager.setOnItemClickListener(this);
        bt_fill.setClickable(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_set_left:
                finish();
                break;
            case R.id.bt_fill:
                // 按下"深度搜索"后,click失效
                bt_fill.setClickable(false);
                // 按下"深度搜索"后,更新Adapter上UI
                int count = fillAdapter.getCount();
                for (int i = 0; i < count; i++) {
                    fillAdapter.getItem(i).loadingover= false;
                }
                fillAdapter.notifyDataSetChanged();
                // 执行"深度搜索" - 异步搜索外置文件
                asynLoadOutRomFile();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Filler filler=(Filler) fillAdapter.getItem(position);
        String fileType=filler.fileType;
        Intent intent = new Intent(this, FilllistActivity.class);
        intent.putExtra("fileType", fileType);
        startActivity(intent);
    }
}
