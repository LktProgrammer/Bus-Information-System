package com.example.bsinpormation;

/**
 * Created by 이기택 on 2017-08-06.
 */

public class BusStation_Location {
    private String lat;
    private String lng;
    private String StationName;
    private String StationId;

    public void Set_lat(String lat){this.lat=lat;}
    public void Set_lng(String lng){this.lng=lng;}
    public void Set_StationName(String StationName){this.StationName=StationName;}
    public void Set_StationId(String StationId){this.StationId=StationId;}

    public String Get_lat(){return lat;}
    public String Get_lng(){return lng;}
    public String Get_StationName(){return StationName;}
    public String Get_StationId(){return StationId;}
}
