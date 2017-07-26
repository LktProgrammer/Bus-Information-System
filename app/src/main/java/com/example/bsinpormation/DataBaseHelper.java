package com.example.bsinpormation;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 이기택 on 2017-07-26.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(Context context, String name,SQLiteDatabase.CursorFactory factory,int version)
    {
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {           //DB 생성 최초 한번만 실행 // 테이블 생성
        db.execSQL("CREATE TABLE BUSINFO (_id INTEGER PRIMARY KEY AUTOINCREMENT, BusStation TEXT,BusNumber TEXT,LineID TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void insert(String Station_Name,String Bus_Number,String Line_ID)        //INSERT문
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO BUSINFO VALUES(null,'" +Station_Name+ "','" + Bus_Number + "','" + Line_ID + "');");
        db.close();
    }

    public String View_Table()                                                      //Select문
    {
        SQLiteDatabase db = getReadableDatabase();
        String result="";

        Cursor cursor = db.rawQuery("SELECT * FROM BUSINFO",null);
        while(cursor.moveToNext())
        {
            result += cursor.getString(0)+cursor.getString(1)+cursor.getString(2)+cursor.getString(3)+"\n";
        }
        return result;
    }
}
