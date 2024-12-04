package com.example.bbcreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RssAdapter extends ArrayAdapter<RssItem> {
    public RssAdapter(Context context, List<RssItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        RssItem item = getItem(position);
        TextView title = convertView.findViewById(android.R.id.text1);
        title.setText(item.getTitle());

        return convertView;
    }
}