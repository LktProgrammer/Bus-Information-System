package com.example.bsinpormation;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}


    public void insert(String Station_Name,String Bus_Number,String Line_ID)        //INSERT문
    {
        SQLiteDatabase db = getWritableDatabase();                      //쓰기 가능한 db 객체 얻어오기
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
            result += cursor.getString(0)+cursor.getString(1)+cursor.getString(2)+cursor.getString(3)+"\n" +String.valueOf(cursor.getCount());
        }
        return result;
    }

    public void Delete(String Station_Name,String Bus_Number)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM BUSINFO WHERE BusStation='" + Station_Name +"'AND BusNumber='"+Bus_Number +"';");

    }

    public int Get_Count()          //테이블의 저장된 데이터 개수 반환
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM BUSINFO",null);
        return cursor.getCount();
    }

    public String Get_id(int count)
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM BUSINFO",null);  //Select문으로 테이블 조회
        cursor.moveToPosition(count);                                  //파라미터로 받은 값으로 해당 열로 Cursor 이동
        return cursor.getString(3);
    }
    public String Get_Bus_Number(int count)
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM BUSINFO",null);
        cursor.moveToPosition(count);
        return cursor.getString(2);
    }
}
