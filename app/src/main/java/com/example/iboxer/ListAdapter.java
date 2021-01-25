package com.example.iboxer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class ListAdapter extends ArrayAdapter<RowData> {
    private Context mContext;
    private int mResource;
    public ListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<RowData> objects) {
        super(context, resource, objects);
        this.mContext=context;
        this.mResource=resource;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource,parent,false);

        TextView name = (TextView) convertView.findViewById(R.id.list_name);
        TextView country = (TextView) convertView.findViewById(R.id.list_country);
        TextView points = (TextView) convertView.findViewById(R.id.list_points);
        TextView pos = (TextView) convertView.findViewById(R.id.list_number);

        name.setText(getItem(position).getNickname());
        country.setText(getItem(position).getCountry());
        points.setText(getItem(position).getStrPoints());
        pos.setText("" + (position+1) + ".");


        return convertView;
    }
}
