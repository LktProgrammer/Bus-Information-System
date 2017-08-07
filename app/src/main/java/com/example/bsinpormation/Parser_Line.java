package com.example.bsinpormation;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by 이기택 on 2017-07-20.
 */

public class Parser_Line implements Parser_Inter {
    private String xml;
    private ArrayList<BusLine_Info> BusLine_Info_List;

    public Parser_Line(String xml)
    {
        this.xml = xml;
    }
    public void Parsing_Xml() throws XmlPullParserException     //xml의 노선정보를 파싱하는 메소드
    {
        String Tag;
        int EventType;
        BusLine_Info_List = new ArrayList<BusLine_Info>();
        BusLine_Info busline_info=new BusLine_Info();
        boolean boolean_Node_Id=false,
                 boolean_BusStation_Name=false,
                 boolean_Bus_Exist=false,
                 boolean_BusStation_Num=false,
                 boolean_Bus_Number = false;

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance().newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(new StringReader(xml));
        EventType = parser.getEventType();
        int index = 0;


        while(EventType != XmlPullParser.END_DOCUMENT)
        {
            index++;

            switch(EventType) {
                case XmlPullParser.START_DOCUMENT:
                    break;

                case XmlPullParser.END_DOCUMENT:
                    break;

                case XmlPullParser.START_TAG:
                    Tag = parser.getName();
                    if (Tag.equals("nodeId")) {
                        boolean_Node_Id = true;
                    } else if (Tag.equals("lat")) {
                        boolean_Bus_Exist = true;
                    } else if (Tag.equals("bstopnm")) {
                        boolean_BusStation_Name = true;
                    } else if (Tag.equals("arsNo") || Tag.equals("avgtm")) {
                        boolean_BusStation_Num = true;
                    } else if(Tag.equals("lineNo")){
                        boolean_Bus_Number=true;
                    }

                    break;

                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("item")) {
                        index++;
                        BusLine_Info_List.add(busline_info);
                        busline_info = null;
                        busline_info = new BusLine_Info();
                    }
                    break;

                case XmlPullParser.TEXT:
                    if (boolean_Node_Id) {
                        busline_info.Set_Node_Id(parser.getText());
                        boolean_Node_Id = false;
                    } else if (boolean_BusStation_Num) {
                        busline_info.Set_BusStation_Num(parser.getText());
                        boolean_BusStation_Num = false;
                    } else if (boolean_BusStation_Name) {
                        busline_info.Set_BusStation_Name(parser.getText());
                        boolean_BusStation_Name = false;
                    } else if (boolean_Bus_Exist) {
                        busline_info.Set_Bus_Exist(true);
                        boolean_Bus_Exist = false;
                    } else if(boolean_Bus_Number) {
                        busline_info.Set_Bus_Number(parser.getText());
                        boolean_Bus_Number=false;
                    }
                    break;
            }
            try{
                EventType=parser.next();
            }catch(IOException e){}
        }
    }

    public ArrayList<?> Get_InfoList()
    {
        return BusLine_Info_List;
    }

}
