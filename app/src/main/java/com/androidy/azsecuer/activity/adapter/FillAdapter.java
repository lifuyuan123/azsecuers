package com.androidy.azsecuer.activity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.adapter.basedataAdapter.BaseDataAdapter;
import com.androidy.azsecuer.activity.entity.Filler;
import com.androidy.azsecuer.activity.util.PublicUtils;

/**
 * Created by Administrator on 2016/8/24.
 */
public class FillAdapter extends BaseDataAdapter<Filler> {
    public FillAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=this.layoutInflater.inflate(R.layout.list_fill,null);
            viewHolder=new ViewHolder();
            viewHolder.tv_list_fillleft=(TextView) convertView.findViewById(R.id.tv_list_fillleft);
            viewHolder.tv_list_fillright=(TextView)convertView.findViewById(R.id.tv_list_fillright);
            viewHolder.iv_list_fill=(ImageView)convertView.findViewById(R.id.iv_list_fill);
            viewHolder.pb_list_fill=(ProgressBar)convertView.findViewById(R.id.pb_list_fill);
            convertView.setTag(viewHolder);
        }else {
          viewHolder=(ViewHolder) convertView.getTag();
        }
        Filler filler=(Filler) getItem(position);
        viewHolder.tv_list_fillleft.setText(filler.name);
        if(filler.loadingover){
            viewHolder.pb_list_fill.setVisibility(View.INVISIBLE);
            viewHolder.iv_list_fill.setVisibility(View.VISIBLE);
            viewHolder.tv_list_fillright.setText(PublicUtils.formatSize(filler.size));
        }else {
            viewHolder.pb_list_fill.setVisibility(View.VISIBLE);
            viewHolder.iv_list_fill.setVisibility(View.INVISIBLE);
            viewHolder.tv_list_fillright.setText("计算中...");
        }
        return convertView;
    }
    class ViewHolder{
        TextView tv_list_fillleft,tv_list_fillright;
        ImageView iv_list_fill;
        ProgressBar pb_list_fill;
    }
}
