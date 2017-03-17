package com.androidy.azsecuer.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.entity.clean.Clean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/17.
 */
public class CleanAdapter extends BaseAdapter {
    private Context context;
    private List<Clean> cleans=new ArrayList<>();
    private LayoutInflater layoutInflater;
    public CleanAdapter(Context context) {
        super();
        this.context=context;
        this.layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return cleans.size();
    }

    @Override
    public Object getItem(int position) {
        return cleans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=layoutInflater.inflate(R.layout.list_clean_style,null);
        ((TextView)view.findViewById(R.id.tv_clean_list_top)).setText(cleans.get(position).getName());
        ((TextView)view.findViewById(R.id.tv_clean_list_bottom)).setText(cleans.get(position).getSize());
        ((CheckBox)view.findViewById(R.id.checkBox)).setChecked(cleans.get(position).getisCheck());
        ((ImageView)view.findViewById(R.id.iv_clean_list_img)).setImageDrawable(cleans.get(position).getDrawable());
        //设置上标签tag
        ((CheckBox)view.findViewById(R.id.checkBox)).setTag(cleans.get(position));
        ((CheckBox)view.findViewById(R.id.checkBox)).setOnCheckedChangeListener(onCheckedChangeListener);
        return view;
    }
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            ((Clean)buttonView.getTag()).setisCheck(isChecked);
        }
    };
    public void setDatas( List<Clean> cleans) {
        this.cleans = cleans;
    }
    /**
     * 删除
     *  position 删除的索引
     */
    public void removeDataFromAdpter(int position){
        this.cleans.remove(position);
    }
    /**
     * 删除
     *  tellTypess删除的对象
     */

    /**
     * 添加
     *tellType 添加的对象
     */
    public void addDataToAdapter(Clean clean ){
        if(clean != null)
            this.cleans.add(clean);
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        this.cleans.clear();
    }
}
