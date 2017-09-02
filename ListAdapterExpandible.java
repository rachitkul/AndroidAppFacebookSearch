package com.example.searchonfb;

/**
 * Created by vimal on 4/26/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListAdapterExpandible extends BaseExpandableListAdapter {

    private Context context;
    private List<String> headerArray;
    private HashMap<String, ArrayList<String>> childArray;
    private LayoutInflater infalInflater;

    // Initialize constructor for array list
    public ListAdapterExpandible(Context context, ArrayList<String> headerArray,
                                 HashMap<String, ArrayList<String>> listChildData) {
        this.context = context;
        this.headerArray = headerArray;
        this.childArray = listChildData;
        infalInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.childArray.get(this.headerArray.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    // Inflate child view

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {

            convertView = infalInflater.inflate(R.layout.child, null);
        }

        ImageView imageViewChild = (ImageView) convertView
                .findViewById(R.id.pic1);

        //imageViewChild.setText(childText);
        Picasso.with(context).load(childText).into(imageViewChild);
        return convertView;
    }

    // return number of headers in list
    @Override
    public int getChildrenCount(int groupPosition) {
        return this.childArray.get(this.headerArray.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.headerArray.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.headerArray.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    // inflate header view

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {

            convertView = infalInflater.inflate(R.layout.parent,
                    null);
        }

        TextView textViewHeader = (TextView) convertView
                .findViewById(R.id.header);
        textViewHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
