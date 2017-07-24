package com.example.bsinpormation;

import android.content.Context;
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

public class List_BusStation_Adapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<BusStation_Info> data;
    private int layout;

    public List_BusStation_Adapter(Context context, int layout, ArrayList<BusStation_Info> data)
    {
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
    }

    @Override
    public int getCount() {return data.size();}
    @Override
    public Object getItem(int position) { return data.get(position).Get_Bus_StationName();}
    @Override
    public long getItemId(int position) {return position;}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView=inflater.inflate(layout,parent,false);
        }

        BusStation_Info busstation_info = data.get(position);
        TextView station_textview1 = (TextView)convertView.findViewById(R.id.station_view1);
        TextView station_textview2 = (TextView)convertView.findViewById(R.id.station_view2);
        TextView station_textview3 = (TextView)convertView.findViewById(R.id.station_view3);

        switch(busstation_info.Bus_Num)
        {

            case 2:
                station_textview3.setVisibility(View.VISIBLE);
                station_textview1.setText(busstation_info.Get_Bus_LineNum()+" " + busstation_info.Get_Bus_StationName());
                station_textview2.setText("남은시간 : " +busstation_info.Get_Bus_WaitMin1() + " " +"남은 정류장 수:" + busstation_info.Get_Bus_Station1()+" " );
                if(busstation_info.Get_Bus_LowPlate1().equals("1")) {
                    station_textview2.setText(station_textview2.getText()+ "저상버스");
                }

                station_textview3.setText("남은시간 : " +busstation_info.Get_Bus_WaitMin2() + " " + "남은 정류장 수:" + busstation_info.Get_Bus_Station2()+" " );
                if(busstation_info.Get_Bus_LowPlate2().equals("1")) {
                    station_textview3.setText(station_textview3.getText()+ "저상버스");
                }
                break;
            case 1:
                station_textview1.setText(busstation_info.Get_Bus_LineNum()+" " + busstation_info.Get_Bus_StationName());
                station_textview2.setText("남은시간 : " +busstation_info.Get_Bus_WaitMin1() + " " +"남은 정류장 수:" + busstation_info.Get_Bus_Station1()+" " );
                if(busstation_info.Get_Bus_LowPlate1().equals("1")) {
                    station_textview2.setText(station_textview2.getText()+ "저상버스");
                }
                station_textview3.setVisibility(View.INVISIBLE);

                break;
            case 0:
                station_textview1.setText(busstation_info.Get_Bus_LineNum()+" " + busstation_info.Get_Bus_StationName());
                station_textview2.setText("현재 도착 예정인 버스가 없습니다.");
                station_textview3.setVisibility(View.INVISIBLE);

                break;
        }

        return convertView;
    }
}
