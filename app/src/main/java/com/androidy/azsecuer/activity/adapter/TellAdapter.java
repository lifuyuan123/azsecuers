package com.androidy.azsecuer.activity.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.entity.TellType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/9.
 */
public class TellAdapter extends BaseAdapter {
   protected Context context;
   protected List<TellType> tellTypes=new ArrayList<>();
    protected LayoutInflater layoutInflater;

  public TellAdapter(Context context){
      super();
      this.context=context;
      layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }
    //显示listview的项数
    @Override
    public int getCount() {
        return tellTypes.size();
    }

    @Override
    public Object getItem(int position) {
        return tellTypes.get(position);// 返回当前项的数据 Object
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            LayoutInflater inflater=((Activity)context).getLayoutInflater();
            convertView=inflater.inflate(R.layout.list_tell_stye,null);//每行布局样式
            holder = new ViewHolder();
            holder.textView=(TextView)convertView.findViewById(R.id.tv_tell_stly);//布局样式中传入textviw的id
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        TellType tellType=(TellType)this.tellTypes.get(position);
        holder.textView.setText(tellType.getName());
        return convertView;
    }
    class ViewHolder{
        TextView textView;
    }
    /**
     * 设置数据
     * tellTypes 数据源
     */
    public void setDatas(List<TellType> tellTypes){
        this.tellTypes = tellTypes;
    }

    /**
     * 删除
     *  position 删除的索引
     */
    public void removeDataFromAdpter(int position){
        this.tellTypes.remove(position);
    }
    /**
     * 删除
     *  tellTypess删除的对象
     */
    public void removeDataFromAdpter(TellType tellType){
        this.tellTypes.remove(tellType);
    }

    /**
     * 添加
     *tellType 添加的对象
     */
    public void addDataToAdapter(TellType tellType ){
        if(tellType != null)
            this.tellTypes.add(tellType);
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        this.tellTypes.clear();
    }
}
