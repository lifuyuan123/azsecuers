package com.androidy.azsecuer.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.adapter.basedataAdapter.BaseDataAdapter;
import com.androidy.azsecuer.activity.entity.quick.Quicker;
import com.androidy.azsecuer.activity.util.PublicUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/20.
 */
public class QuickAdapter extends BaseDataAdapter<Quicker> {
    private List<Quicker> quickers=new ArrayList<>();
    public QuickAdapter(Context context) {
        super(context);
    }

  public int getItemViewType(int position){
      Quicker quicker=(Quicker) getItem(position);
      if(quicker.isSystemApp){
          return 0;//系统的
      }
     return  1;//用户的
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type=getItemViewType(position);
        ViewHolder viewHolder;
        if(convertView==null){
            switch (type){
                case 0:
                    convertView=this.layoutInflater.inflate(R.layout.phone_quick_list_style_system,null);
                    break;
                case 1:
                    convertView=this.layoutInflater.inflate(R.layout.phone_quick_list_style,null);
                    break;
            }
            viewHolder=new ViewHolder();
            viewHolder.tv_phonequick_liststyle_top=(TextView) convertView.findViewById(R.id.tv_phonequick_liststyle_top);
            viewHolder.tv_phonequick_liststyle_buttom=(TextView)convertView.findViewById(R.id.tv_phonequick_liststyle_buttom);
            viewHolder.im_phone_quick_liststyle=(ImageView)convertView.findViewById(R.id.im_phone_quick_liststyle);
            viewHolder.phone_qiuck_liststyle_check=(CheckBox)convertView.findViewById(R.id.phone_qiuck_liststyle_check);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        Quicker quicker=(Quicker) this.getItem(position);
        viewHolder.tv_phonequick_liststyle_top.setText(quicker.name);
        viewHolder.tv_phonequick_liststyle_buttom.setText(PublicUtils.formatSize(quicker.nameSize));
        viewHolder.im_phone_quick_liststyle.setImageDrawable(quicker.drawable);
        viewHolder.phone_qiuck_liststyle_check.setChecked(quicker.ischeck);
        //check设置标签
        viewHolder.phone_qiuck_liststyle_check.setTag(quicker);
        viewHolder.phone_qiuck_liststyle_check.setOnCheckedChangeListener(onCheckedChangeListener);
        return convertView;
    }
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            ((Quicker)buttonView.getTag()).ischeck=isChecked;
        }
    };
    class ViewHolder{
        TextView tv_phonequick_liststyle_top,tv_phonequick_liststyle_buttom;
        ImageView im_phone_quick_liststyle;
        CheckBox phone_qiuck_liststyle_check;
    }
}
