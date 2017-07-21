package com.example.bsinpormation;

import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.util.ArrayList;

/**
 * Created by 이기택 on 2017-07-20.
 */

public interface Parser_Inter {

    public void Parsing_Xml() throws XmlPullParserException;
    public ArrayList<?> Get_InfoList();
}
