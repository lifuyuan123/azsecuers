package com.androidy.azsecuer.activity.adapter.basedataAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/20.
 */
public abstract class BaseDataAdapter<E> extends BaseAdapter {
    protected List<E> datas= new ArrayList<>();
    protected Context context;
    protected LayoutInflater layoutInflater;

    public BaseDataAdapter(Context context) {
        this.context = context;
        this.layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public BaseDataAdapter(){
        super();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public E getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 设置数据
     * @param datas 数据源
     */
    public void setDatas(List<E> datas){
        this.datas = datas;
    }

    /**
     * 删除
     * @param position 删除的索引
     */
    public void removeDataFromAdapter(int position){
        this.datas.remove(position);
    }
    /**
     * 删除
     * @param e 删除的对象
     */
    public void removeDataFromAdapter(E e){
        this.datas.remove(e);
    }

    /**
     * 添加
     * @param e 添加的对象
     */
    public void addDataToAdapter(E e){
        if(e != null)
            this.datas.add(e);
    }
    public void addDataToAdapter(List<E> e){
        if(e != null)
            this.datas.addAll(e);
    }

    /**
     * 清除所有数据
     */
    public void clear(){
        this.datas.clear();
    }
    /*
    获取数据
     */
    public List<E> getDataFromAdapter() {
        return datas;
    }
}
