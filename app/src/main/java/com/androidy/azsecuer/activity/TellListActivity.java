package com.androidy.azsecuer.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.adapter.TellNumberAdapter;
import com.androidy.azsecuer.activity.base.BaseActionBarActivity;
import com.androidy.azsecuer.activity.db.DbManager;
import com.androidy.azsecuer.activity.entity.TellNumber;
import com.androidy.azsecuer.activity.util.LogUtil;

import java.util.List;

public class TellListActivity extends BaseActionBarActivity implements AdapterView.OnItemClickListener{
    private ListView lv_list_number;
    private Bundle bundle;
    private TellNumberAdapter tellNumberAdapter;
    private TextView tv_name,tv_number;
    private ImageView iv_set_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tell_list);
        initView();
        listenerView();
    }

    private void ininData() {
        bundle = this.getIntent().getExtras();

    }

    @Override
    protected void initView() {
        setActionBarBack("通讯大全");
        iv_set_left=(ImageView)findViewById(R.id.iv_set_left);

        int idx=this.getIntent().getExtras().getInt("idx");
        List<TellNumber> tellNumbers= DbManager.readTellNumber(idx);//拿到数据

        lv_list_number = (ListView) findViewById(R.id.lv_list_number);
        tellNumberAdapter=new TellNumberAdapter(this);
        lv_list_number.setAdapter(tellNumberAdapter);
        tellNumberAdapter.setDatas(tellNumbers);//设置到list中
    }

    @Override
    protected void listenerView() {
        iv_set_left.setOnClickListener(this);
        lv_list_number.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.iv_set_left:
              finish();
               LogUtil.p("~~~~~~~~~~","onClick");
               break;
       }
    }

    public void showDiaLog(int position) {
       final TellNumber tellNumber=(TellNumber)tellNumberAdapter.getItem(position);
        final String number = tellNumber.getNumber();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("拨号").setMessage("确定拨打"+tellNumber.getName()+"电话？\n" + "tell:" + number)
                .setCancelable(true)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //开启电话操作，开启权限
                        Intent intent = new Intent(Intent.ACTION_CALL);//开启一个打电话的意图
                        intent.setData(Uri.parse("tel:" + number));//格式是：“tel：//”，将打电话的number转化成uri设置给intent
                        if (ActivityCompat.checkSelfPermission(TellListActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.

                            return;
                        }
                        startActivity(intent);//开启打电话的操作
                    }
                });
builder.show();//需要show出来


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showDiaLog(position);//监听拨打电话
    }
}
