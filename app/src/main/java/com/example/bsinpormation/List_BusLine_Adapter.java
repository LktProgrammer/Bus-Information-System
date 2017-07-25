package com.example.bsinpormation;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 이기택 on 2017-07-24.
 */

public class List_BusLine_Adapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<BusLine_Info> data;
    private int layout;

    public List_BusLine_Adapter(Context context, int layout,ArrayList<BusLine_Info> data)
    {
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
    }

    @Override
    public int getCount() {return data.size();}
    @Override
    public Object getItem(int position) { return data.get(position).Get_BusStation_Name();}
    @Override
    public long getItemId(int position) {return position;}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            convertView = inflater.inflate(layout,parent,false);
        }
        BusLine_Info busLine_info = data.get(position);

        TextView station_name = (TextView)convertView.findViewById(R.id.bus_num_textview);
        station_name.setText("정류소명 : "+busLine_info.Get_BusStation_Name());

        TextView bus_line = (TextView)convertView.findViewById(R.id.line_id_textview);
        bus_line.setText("정류소ID: "+busLine_info.Get_Node_Id());

        ImageView image_view = (ImageView)convertView.findViewById(R.id.imageView2);

        if(busLine_info.Get_Bus_Exist()){
            image_view.setImageResource(R.drawable.exist);
        }
        else{
            image_view.setImageResource(R.drawable.nexist);
        }

        image_view.setVisibility(View.VISIBLE);


        return convertView;

    }
}
