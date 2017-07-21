package com.example.bsinpormation;

import org.xmlpull.v1.XmlPullParserException;

import java.util.ArrayList;

/**
 * Created by 이기택 on 2017-07-20.
 */

public class My_Parser {
    Parser_Inter parser_inter;

    public My_Parser(Parser_Inter parser_inter)
    {
        this.parser_inter = parser_inter;
    }

    public void Parsing_Xml() throws XmlPullParserException
    {
        parser_inter.Parsing_Xml();
    }
    public ArrayList<?> Get_InfoList()
    {
            return parser_inter.Get_InfoList();
    }
}
