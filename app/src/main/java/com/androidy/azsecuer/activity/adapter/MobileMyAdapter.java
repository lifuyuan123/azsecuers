package com.androidy.azsecuer.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidy.azsecuer.R;
import com.androidy.azsecuer.activity.entity.Mobilchild;
import com.androidy.azsecuer.activity.entity.Mobilgroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/16.
 */
public class MobileMyAdapter extends BaseExpandableListAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Mobilgroup> mobilgroups;
    private Map<String, List<Mobilchild>> mobilchilds;

    public MobileMyAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mobilgroups = new ArrayList<>();
        mobilchilds = new HashMap<>();
    }

    public void addtoAdapter(List<Mobilchild> mobilchild, Mobilgroup mobilgroup) {
        mobilgroups.add(mobilgroup);
        mobilchilds.put(mobilgroup.getTitle(), mobilchild);
    }

    @Override
    public int getGroupCount() {
        return mobilgroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mobilchilds.get(mobilgroups.get(groupPosition).getTitle()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mobilgroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return (mobilchilds.get(mobilgroups.get(groupPosition).getTitle())).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.mobile_group_, null);
        ((TextView) view.findViewById(R.id.tv_mobile_group)).setText(mobilgroups.get(groupPosition).getTitle());
        ((ImageView) view.findViewById(R.id.iv_mobile_group)).setImageDrawable(mobilgroups.get(groupPosition).getDrawable());
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.mobile_child, null);
        ((TextView) view.findViewById(R.id.tv_mobile_child_title)).setText(((mobilchilds.get(mobilgroups.get(groupPosition).getTitle())).get(childPosition)).getTitle());
        ((TextView) view.findViewById(R.id.tv_mobile_child_content)).setText(((mobilchilds.get(mobilgroups.get(groupPosition).getTitle())).get(childPosition)).getContent());

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
