package com.androidy.azsecuer.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.entity.TellNumber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/12.
 */
public class TellNumberAdapter extends BaseAdapter {
    protected List<TellNumber> tellNumbers = new ArrayList<>();
    protected Context context;
    protected LayoutInflater layoutInflater;

    public TellNumberAdapter(Context context) {
        super();
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return tellNumbers.size();
    }

    @Override
    public Object getItem(int position) {
        return tellNumbers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = this.layoutInflater.inflate(R.layout.list_number_stye, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_list_name);
            viewHolder.tv_number = (TextView) convertView.findViewById(R.id.tv_list_number);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TellNumber tellNumber = (TellNumber) this.tellNumbers.get(position);
        viewHolder.tv_name.setText(tellNumber.getName());
        viewHolder.tv_number.setText(tellNumber.getNumber());
        return convertView;
    }

    class ViewHolder {
        TextView tv_name, tv_number;
    }

    /**
     * 设置数据
     * tellTypes 数据源
     */
    public void setDatas(List<TellNumber> tellNumbers) {
        this.tellNumbers = tellNumbers;
    }

    /**
     * 删除
     * position 删除的索引
     */
    public void removeDataFromAdpter(int position) {
        this.tellNumbers.remove(position);
    }

    /**
     * 删除
     * tellTypess删除的对象
     */
    public void removeDataFromAdpter(TellNumber tellType) {
        this.tellNumbers.remove(tellType);
    }

    /**
     * 添加
     * tellType 添加的对象
     */
    public void addDataToAdapter(TellNumber tellType) {
        if (tellType != null)
            this.tellNumbers.add(tellType);
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        this.tellNumbers.clear();
    }
}
