package com.androidy.azsecuer.activity.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.adapter.basedataAdapter.BaseDataAdapter;
import com.androidy.azsecuer.activity.entity.Softinfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/23.
 */
public class SoftAdapter extends BaseDataAdapter <Softinfo>{
    List<Softinfo> softinfos=new ArrayList<>();
    public SoftAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=this.layoutInflater.inflate(R.layout.soft_manager_list_style,null);
            viewHolder=new ViewHolder();
            viewHolder.tv_soft_packgename=(TextView)convertView.findViewById(R.id.tv_soft_packgename);
            viewHolder.tv_soft_name=(TextView)convertView.findViewById(R.id.tv_soft_name);
            viewHolder.tv_soft_type=(TextView)convertView.findViewById(R.id.tv_soft_type);
            viewHolder.im_soft_manager_list=(ImageView)convertView.findViewById(R.id.im_soft_manager_list);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        Softinfo softinfo=(Softinfo)this.getItem(position);
        viewHolder.tv_soft_packgename.setText(softinfo.name);
        viewHolder.tv_soft_name.setText(softinfo.lable);
        viewHolder.tv_soft_type.setText(softinfo.size);
        viewHolder.im_soft_manager_list.setImageDrawable(softinfo.drawable);
        return convertView;
    }
    class ViewHolder{
        private TextView tv_soft_packgename,tv_soft_name,tv_soft_type;
        private ImageView im_soft_manager_list;
    }
}
