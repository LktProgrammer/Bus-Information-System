package com.example.bsinpormation;

/**
 * Created by 이기택 on 2017-07-20.
 */

public class BusStation_Info {

    private String Bus_LineNum;     //버스번호
    private String Bus_WaitMin1;    //버스 남은 시간
    private String Bus_WaitMin2;
    private String Bus_LowPlate1;  //버스 종류
    private String Bus_LowPlate2;
    public int Bus_Num;                  //대기 버스 개수
    private String Bus_Station1;         //남은 정류장 수
    private String Bus_Station2;
    private String Bus_StationName;     //정류소 이름

    public BusStation_Info()
    {
        this.Bus_Num=0;
        this.Bus_LineNum = "";
    }

    public void Set_Bus_LineNum(String Bus_LineNum){this.Bus_LineNum=Bus_LineNum;}
    public void Set_Bus_WaitMin1(String Bus_WaitMin1){this.Bus_WaitMin1=Bus_WaitMin1;}
    public void Set_Bus_WaitMin2(String Bus_WaitMin2){this.Bus_WaitMin2=Bus_WaitMin2;}
    public void Set_Bus_LowPlate1(String Bus_LowPlate1){this.Bus_LowPlate1=Bus_LowPlate1;}
    public void Set_Bus_LowPlate2(String Bus_LowPlate2){this.Bus_LowPlate2=Bus_LowPlate2;}
    public void Set_Bus_Station1(String Bus_Station1){this.Bus_Station1=Bus_Station1;}
    public void Set_Bus_Station2(String Bus_Station2){this.Bus_Station2=Bus_Station2;}
    public void Set_Bus_StationName(String Bus_StationName){this.Bus_StationName=Bus_StationName;}

    public String Get_Bus_LineNum(){return Bus_LineNum;}
    public String Get_Bus_WaitMin1(){return Bus_WaitMin1;}
    public String Get_Bus_WaitMin2(){return Bus_WaitMin2;}
    public String Get_Bus_LowPlate1(){return Bus_LowPlate1;}
    public String Get_Bus_LowPlate2(){return Bus_LowPlate2;}
    public String Get_Bus_Station1(){return Bus_Station1;}
    public String Get_Bus_Station2(){return Bus_Station2;}
    public String Get_Bus_StationName(){return Bus_StationName;}



}
