package com.example.bsinpormation;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by 이기택 on 2017-07-20.
 */

public class Parser_BusStation implements Parser_Inter {
    private String xml;
    private ArrayList<BusStation_Info> BusStation_Info_List;

    public Parser_BusStation(String xml)
    {
        this.xml = xml;
    }
    public void Parsing_Xml() throws XmlPullParserException
    {
        String Tag;
        int EventType;
        BusStation_Info_List = new ArrayList<BusStation_Info>();
        BusStation_Info busstation_info=new BusStation_Info();

        boolean boolean_Bus_LineNum=false,
                 boolean_Bus_StationName=false,
                 boolean_Bus_WaitMin1=false,
                 boolean_Bus_WaitMin2=false,
                 boolean_Bus_LowPlate1=false,
                 boolean_Bus_LowPlate2=false,
                 boolean_Bus_Station1=false,
                 boolean_Bus_Station2=false;

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance().newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(new StringReader(xml));
        EventType = parser.getEventType();

        while(EventType != XmlPullParser.END_DOCUMENT)
        {
            switch(EventType)
            {
                case XmlPullParser.END_DOCUMENT:
                    break;
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    Tag = parser.getName();

                    if(Tag=="lineno"){
                        boolean_Bus_LineNum=true;
                    } else if(Tag=="lowplate1"){
                        boolean_Bus_LowPlate1=true;
                    }else if(Tag=="lowplate2"){
                        boolean_Bus_LowPlate2=true;
                    }else if(Tag=="min1"){
                        boolean_Bus_WaitMin1=true;
                    }else if(Tag=="min2"){
                        boolean_Bus_WaitMin2=true;
                    }else if(Tag=="nodeNm"){
                        boolean_Bus_StationName=true;
                    }else if(Tag=="station1"){
                        boolean_Bus_Station1=true;
                    }else if(Tag=="station2") {
                        boolean_Bus_Station2 = true;
                    }

                    break;
                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("item")) {
                        BusStation_Info_List.add(busstation_info);
                        busstation_info = null;
                        busstation_info = new BusStation_Info();
                    }
                    break;
                case XmlPullParser.TEXT:
                    if(boolean_Bus_LineNum)
                    {
                        busstation_info.Set_Bus_LineNum(parser.getText());
                        boolean_Bus_LineNum=false;
                    }
                    else if(boolean_Bus_LowPlate1)
                    {
                        busstation_info.Set_Bus_LowPlate1(parser.getText());
                        busstation_info.Bus_Num++;
                        boolean_Bus_LowPlate1=false;
                    }
                    else if(boolean_Bus_LowPlate2)
                    {
                        busstation_info.Set_Bus_LowPlate2(parser.getText());
                        boolean_Bus_LowPlate2=false;
                    }
                    else if(boolean_Bus_WaitMin1)
                    {
                        busstation_info.Set_Bus_WaitMin1(parser.getText());
                        boolean_Bus_WaitMin1=false;
                    }
                    else if(boolean_Bus_WaitMin2)
                    {
                        busstation_info.Set_Bus_WaitMin2(parser.getText());
                        boolean_Bus_WaitMin2=false;
                    }
                    else if(boolean_Bus_StationName)
                    {
                        busstation_info.Set_Bus_StationName(parser.getText());
                        boolean_Bus_StationName=false;
                    }
                    else if(boolean_Bus_Station1)
                    {
                        busstation_info.Set_Bus_Station1(parser.getText());
                        boolean_Bus_Station1=false;
                    }
                    else if(boolean_Bus_Station2)
                    {
                        busstation_info.Set_Bus_Station1(parser.getText());
                        boolean_Bus_Station2=false;
                    }
            }
        }


    }
    public ArrayList<?> Get_InfoList()
    {
        return BusStation_Info_List;
    }
}
