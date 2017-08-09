package com.example.bsinpormation;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by 이기택 on 2017-08-09.
 */

public class Parser_StationID implements Parser_Inter{
    private String xml;
    private ArrayList<String> StationID= new ArrayList<String>();

    public Parser_StationID(String xml) {this.xml = xml;}

    @Override
    public void Parsing_Xml() throws XmlPullParserException {
        String Tag;
        int EventType;

        boolean bstopId=false;

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance().newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(new StringReader(xml));
        EventType = parser.getEventType();

        while(EventType != XmlPullParser.END_DOCUMENT) {

           switch (EventType)
            {
                case XmlPullParser.START_DOCUMENT:
                    break;

                case XmlPullParser.END_DOCUMENT:
                    break;

                case XmlPullParser.START_TAG:
                    Tag = parser.getName();
                    if(Tag.equals("bstopId"))
                    {
                        bstopId = true;
                    }
                    break;
                case XmlPullParser.TEXT:
                    if(bstopId)
                    {
                        StationID.add(parser.getText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                {
                    break;
                }
            }
            try{
                EventType=parser.next();
            }catch(IOException e){}
        }
    }

    @Override
    public ArrayList<?> Get_InfoList() {
        return StationID;
    }
}
