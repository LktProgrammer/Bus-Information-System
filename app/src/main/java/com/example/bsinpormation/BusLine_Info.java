package com.example.bsinpormation;

/**
 * Created by 이기택 on 2017-07-20.
 */

public class BusLine_Info {
    private String Node_Id;             //노선ID
    private String BusStation_Name;    //정류소이름
    private boolean Bus_Exist;
    private String BusStation_Num;
    private String Bus_Number;
    private String Bus_Rpoint;
    ///////////////////////////////


    public BusLine_Info()
    {
        Bus_Exist=false;
    }

    public void Set_Node_Id(String Node_Id){this.Node_Id = Node_Id;}
    public void Set_BusStation_Name(String BusStation_Name){this.BusStation_Name = BusStation_Name;}
    public void Set_Bus_Exist(boolean Bus_Exist){this.Bus_Exist = Bus_Exist;}
    public void Set_BusStation_Num(String BusStation_Num){this.BusStation_Num = BusStation_Num;}
    public void Set_Bus_Number(String Bus_Number){this.Bus_Number = Bus_Number;}
    public void Set_Bus_Rpoint(String Bus_Rpoint){this.Bus_Rpoint = Bus_Rpoint;}


    public String Get_Node_Id(){return this.Node_Id;}
    public String Get_BusStation_Name(){return this.BusStation_Name;}
    public boolean Get_Bus_Exist(){return this.Bus_Exist;}
    public String Get_BusStation_Num(){return this.BusStation_Num;}
    public String Get_Bus_Number(){return this.Bus_Number;}
    public String Get_Bus_Rpoint(){return this.Bus_Rpoint;}
}
