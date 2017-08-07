package com.example.bsinpormation;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by 이기택 on 2017-08-06.
 */

public class Parser_Location implements Parser_Inter {

    private ArrayList<BusStation_Location> busstation_location;
    private String xml;


    public Parser_Location(String xml){this.xml =xml;}

    @Override
    public void Parsing_Xml() throws XmlPullParserException
    {
        String Tag;
        int EventType;
        busstation_location= new ArrayList<>();
        BusStation_Location buslocation = new BusStation_Location();

        boolean lat =false,
                lng = false,
                StationName=false,
                StationId =false;

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance().newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(new StringReader(xml));
        EventType = parser.getEventType();

        while(EventType != XmlPullParser.END_DOCUMENT)
        {
            switch(EventType)
            {
                case XmlPullParser.START_DOCUMENT:
                    break;

                case XmlPullParser.END_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    Tag = parser.getName();
                    if(Tag.equals("gpslati")){
                        lat = true;
                    }else if(Tag.equals("gpslong")){
                        lng = true;
                    }else if(Tag.equals("nodeid")){
                        StationId = true;
                    }else if(Tag.equals("nodenm")){
                        StationName = true;
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("item")) {
                        busstation_location.add(buslocation);
                        buslocation = null;
                        buslocation = new BusStation_Location();
                    }
                    break;
                case XmlPullParser.TEXT:
                    if(lat){
                        buslocation.Set_lat(parser.getText());
                        lat = false;
                    }else if(lng){
                        buslocation.Set_lng(parser.getText());
                        lng = false;
                    }else if(StationName){
                        buslocation.Set_StationName(parser.getText());
                        StationName = false;
                    }else if(StationId){
                        buslocation.Set_StationId(parser.getText());
                        StationId = false;
                    }
                    break;
            }
            try{
                EventType=parser.next();
            }catch(Exception e){}
        }
    }

    @Override
    public ArrayList<?> Get_InfoList() {
        return busstation_location;
    }
}
