package com.example.bsinpormation;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;  //import 클래스


public class MainActivity extends AppCompatActivity {

    View MyPage1, MyPage2, MyPage3,MyPage4,MyPage5;       // frameLayout에 적용되지 view
    EditText BusNum_Input;               // 버스 노선 검색을 위한 사용자 입력창
    TextView textview;
    Button renewal;
    Button Page_Button1,Page_Button2,Page_Button3;

    String Service_Key = "xz2T8UWgGRf26MT53WiDx%2F9Zw0Cgs8oH5zicdOayNo0mC3P9gAeUSdcFHRAfjALQYwxSCrmcL6MKn1uJgTUngQ%3D%3D";    //openApi 요청을 위한 servicekey
    String Result_Xml = "";                // 응답 결과 저장
    String Selected_Station_Name;
    String Selected_Bus_Number;
    String Selected_Line_ID;
    String[] Result = new String[10];
    int count = 0;
    NetworkTask networkTask;             // 비동기 처리

    boolean flag;

    ArrayList<BusLine_Info> busline_info_list = new ArrayList<BusLine_Info>();    // 파싱한 버스 노선 정보를 저장
    ArrayList<BusStation_Info> busstation_info_list = new ArrayList<BusStation_Info>();

    ListView listview;
    ListView listview2;
    List_BusLine_Adapter line_adapter;
    List_BusStation_Adapter busstation_adpter;

    DataBaseHelper dbHelper;

    ArrayList<BusStation_Info> MyBusStation_Info =new ArrayList<BusStation_Info>();     // 변수선언

    ProgressBar progressBar;
    InputMethodManager Input_Key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        dbHelper = new DataBaseHelper(getApplicationContext(),"BusInfo",null,1);

        listview = (ListView) findViewById(R.id.listview1);
        listview2 = (ListView) findViewById(R.id.listview2);
        MyPage1 = findViewById(R.id.page1);
        MyPage2 = findViewById(R.id.page2);
        MyPage3 = findViewById(R.id.page3);
        MyPage4 = findViewById(R.id.page4);

        BusNum_Input = (EditText) findViewById(R.id.editText);

        progressBar = (ProgressBar)findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.GONE);
        renewal = (Button)findViewById(R.id.button5);
        Page_Button1 = (Button)findViewById(R.id.button1);
        Page_Button2 = (Button)findViewById(R.id.button2);
        Page_Button3 = (Button)findViewById(R.id.button3);

        Page_Button1.setBackgroundColor(Color.WHITE);
        Page_Button2.setBackgroundColor(Color.GRAY);
        Page_Button3.setBackgroundColor(Color.GRAY);



        renewal.setVisibility(View.INVISIBLE);

        //버튼 리스너 등록
        findViewById(R.id.button1).setOnClickListener(mClickListener);
        findViewById(R.id.button2).setOnClickListener(mClickListener);
        findViewById(R.id.button3).setOnClickListener(mClickListener);
        findViewById(R.id.button4).setOnClickListener(mClickListener);
        findViewById(R.id.button5).setOnClickListener(mClickListener);

        Input_Key = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        View_BusInfo();
    }

    public Button.OnClickListener mClickListener = new Button.OnClickListener() {
        public void onClick(View v) {
            //최초 이벤트 발생시에 모든 page Invisible로 설정
            MyPage1.setVisibility(View.INVISIBLE);
            MyPage2.setVisibility(View.INVISIBLE);
            MyPage3.setVisibility(View.INVISIBLE);
            MyPage4.setVisibility(View.INVISIBLE);

            switch (v.getId()) {
                case R.id.button1:
                    Page_Button1.setBackgroundColor(Color.WHITE);
                    Page_Button2.setBackgroundColor(Color.GRAY);
                    Page_Button3.setBackgroundColor(Color.GRAY);
                    if(flag){MyPage1.setVisibility(View.VISIBLE);}
                    else{MyPage4.setVisibility(View.VISIBLE);}
                    break;
                case R.id.button2:
                    Page_Button1.setBackgroundColor(Color.GRAY);
                    Page_Button2.setBackgroundColor(Color.WHITE);
                    Page_Button3.setBackgroundColor(Color.GRAY);
                    MyPage2.setVisibility(View.VISIBLE);
                    break;
                case R.id.button3:
                    Page_Button1.setBackgroundColor(Color.GRAY);
                    Page_Button2.setBackgroundColor(Color.GRAY);
                    Page_Button3.setBackgroundColor(Color.WHITE);
                    MyPage3.setVisibility(View.VISIBLE);
                    break;
                case R.id.button4:
                    MyPage2.setVisibility(View.VISIBLE);
                    busline_info_list = null;
                    String Result_Url = "";
                    String Bus_Num = "";
                    String Bus_Id = BusNum_Input.getText().toString();   //사용자가 입력한 버스 번호를 저장
                    Input_Key.hideSoftInputFromWindow(BusNum_Input.getWindowToken(),0);


                    if (Bus_Id.equals("")) {                                 //입력 내용이 공백인지 체크
                        Toast.makeText(getApplicationContext(), "버스 번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    if(Bus_Id.matches(".*[ㄱ-하-ㅣ가-힣]+.*")){              //입력 내용 한글 포함 체크
                        Toast.makeText(getApplicationContext(), "한글을 제외한 버스번호(숫자)를 입력하세요", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Bus_Num = Get_BusId(Bus_Id);                        //공백이 아니라면 버스번호->id로 변환해주는 Get_BusId()메소드 호출
                        Result_Url = Create_Url("busInfoRoute", Bus_Num, Service_Key, 1);

                        networkTask = new NetworkTask(0,0,Result_Url,null);
                        networkTask.execute();
                    }

                    break;

                case R.id.button5:
                    View_BusInfo();
                    break;
                    }
            }
    };

    public void View_BusInfo()
    {
        flag = true;
        int index = 0;
        count = dbHelper.Get_Count();
        MyPage1.setVisibility(View.VISIBLE);
        if(count==0)
        {
            flag = false;
            MyPage4.setVisibility(View.VISIBLE);
            MyPage1.setVisibility(View.INVISIBLE);
            renewal.setVisibility(View.INVISIBLE);
        }
        else{
            MyPage4.setVisibility(View.INVISIBLE);
            MyPage1.setVisibility(View.VISIBLE);
        }
        MyBusStation_Info.clear();

        networkTask = new NetworkTask(1,1,"", null);
        networkTask.execute();

    } //View_BusInfo() 함수 원형

    AdapterView.OnItemClickListener ListView_Listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            Selected_Station_Name= busline_info_list.get(position).Get_BusStation_Name();
            Selected_Bus_Number = busline_info_list.get(position).Get_Bus_Number();
            Selected_Line_ID =busline_info_list.get(position).Get_Node_Id();

            AlertDialog dialog  = Get_Dialog(position);
            dialog.show();
        }
    };
    AdapterView.OnItemClickListener ListView_Listener2 = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
;
            Selected_Station_Name= MyBusStation_Info.get(position).Get_Bus_StationName();
            Selected_Bus_Number = MyBusStation_Info.get(position).Get_Bus_LineNum();
            AlertDialog dialog  = Get_Dialog2(position);
            dialog.show();
        }
    };  //Listview의 OnItemClickListener
    public class NetworkTask extends AsyncTask<String, ProgressBar, String> {
        private String url;
        private ContentValues values;
        private int process_type;
        private int num;

        public NetworkTask(int index,int process_type,String url, ContentValues values) {
            this.url = url;
            this.values = values;
            this.process_type = process_type;
            this.num = index;
        }

        protected void onPreExecute()
        {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }
        protected void onProgressUpdate(ProgressBar... params)
        {
            super.onPreExecute();
        }
        protected String doInBackground(String... params) {
            String result = "";
            if(process_type==1)
            {
                for(int i=0;i<dbHelper.Get_Count();i++)
                {
                    Result[i]= "";
                    String Request_Url = Create_Url("stopArr", dbHelper.Get_id(i), Service_Key, 2);
                    UrlConnection urlconnection = new UrlConnection(Request_Url, values);
                    result = urlconnection.Request_UrlConnect();
                    Result[i] = result;
                }
            }
            else
            {
                UrlConnection urlconnection = new UrlConnection(this.url, values);
                result = urlconnection.Request_UrlConnect();
                Result_Xml = result;
            }
            return result;
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (process_type == 0) {
                My_Parser my_parser = new My_Parser(new Parser_Line(Result_Xml));

                try {
                    my_parser.Parsing_Xml();                      //데이터 파싱
                    busline_info_list = (ArrayList<BusLine_Info>) my_parser.Get_InfoList();    //파싱 결과인 ArrayList를 가져옴
                    if (busline_info_list.size() == 0) {
                        Toast.makeText(getApplicationContext(), "검색 결과가 없습니다. 번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                    } else {
                        line_adapter = new List_BusLine_Adapter(getApplicationContext(), R.layout.linsview_line, busline_info_list);
                        listview.setAdapter(line_adapter);
                        listview.setOnItemClickListener(ListView_Listener);
                    }

                } catch (Exception e) {
                }
            }
            else if(process_type==1) {
                int index = 0;
                while(index<count && flag)
                {
                    Log.d("kkk","cc");
                    My_Parser my_parser = new My_Parser(new Parser_BusStation(Result[index]));

                    try{
                        my_parser.Parsing_Xml();
                        busstation_info_list = (ArrayList<BusStation_Info>)my_parser.Get_InfoList();

                        for(int j=0;j<busstation_info_list.size();j++)
                        {
                            if(busstation_info_list.get(j).Get_Bus_LineNum().equals(dbHelper.Get_Bus_Number(index)))
                            {
                                MyBusStation_Info.add(busstation_info_list.get(j));
                            }
                        }
                    }catch(Exception e){};
                    index++;
                }
                busstation_adpter = new List_BusStation_Adapter(getApplicationContext(),R.layout.listview_station,MyBusStation_Info);
                listview2.setAdapter(busstation_adpter);
                listview2.setOnItemClickListener(ListView_Listener2);
                renewal.setVisibility(View.VISIBLE);
            }
            progressBar.setVisibility(View.GONE);
        }

    }    //class NetworkTask 원형 // 비동기 처리를 위한 AsyncTask 구현 //비동기 처리를 위한 AsyncTask 구현 부분

    public String Get_BusId(String bus_id) {
        String bus_num = "";
        if (bus_id.contains("-")) {          // '-' 포함되어 있다면 제거
            bus_num = bus_id.substring(0, bus_id.indexOf('-'));
            bus_num += bus_id.substring(bus_id.indexOf('-') + 1);
        } else {
            bus_num = bus_id;
        }
        if (bus_num.length() == 1 || bus_num.length() == 2) {            //버스 번호가 1자리 일때와 아닐때 생성규칙을 구분하여 구현
            bus_num = "52000" + bus_num;
            while (bus_num.length() != 10) {
                bus_num += "0";
            }
        } else {
            bus_num = "5200" + bus_num;
            while (bus_num.length() != 10) {
                bus_num += "0";
            }
        }
        return bus_num;
    } // GetBusId() 함수 원형 부분 //버스번호를 생성규칙에 맞게 BusId로 변환하는 함수

    public String Create_Url(String Request_URL, String Request_Param, String Service_Key, int Request_Case) {
        String Request_Url = "";

        switch (Request_Case) {               //Requesst_Case에 따라 URL 구성이 달라지도록 구현
            case 1:                         //버스 노선 검색을 위한 Url 구성
                Request_Url = "http://data.busan.go.kr/openBus/service/busanBIMS2/"
                        + Request_URL + "?" + "lineid=" + Request_Param + "&serviceKey=" + Service_Key;
                break;
            case 2:
                Request_Url = "http://data.busan.go.kr/openBus/service/busanBIMS2/"
                        + Request_URL + "?" + "bstopid=" + Request_Param + "&serviceKey=" + Service_Key;
                break;
        }
        return Request_Url;
    }  // Create_Url() 함수 원형 부분 // 요청에 알맞는 Url 형성을 위한 메소드

    public AlertDialog Get_Dialog(int position)
    {
        AlertDialog dialog  = new AlertDialog.Builder(this)
                .setTitle("관심등록하기")
                .setMessage("나의 관심 정류소로 등록하시겠습니까?\n\n정류소명 : "+busline_info_list.get(position).Get_BusStation_Name() +"\n"
                        +"버스번호:" +busline_info_list.get(position).Get_Bus_Number() +"\n"
                        +"노선ID:" + busline_info_list.get(position).Get_Node_Id())
                .setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)                      //사용자가 예를 클릭 할 경우 db에 insert
                            {
                                dbHelper.insert(Selected_Station_Name,Selected_Bus_Number,Selected_Line_ID);
                                View_BusInfo();
                                MyPage2.setVisibility(View.INVISIBLE);
                                MyPage1.setVisibility(View.VISIBLE);
                                Page_Button1.setBackgroundColor(Color.WHITE);
                                Page_Button2.setBackgroundColor(Color.GRAY);
                                Page_Button3.setBackgroundColor(Color.GRAY);
                            }
                        })
                .setNeutralButton("아니오",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){}                             //아니오를 클릭한 경우 아무 처리하지 않음
                }).create();
        return dialog;
    }   //AlertDialog 원형 1
    public AlertDialog Get_Dialog2(int position)
    {
        AlertDialog dialog  = new AlertDialog.Builder(this)
                .setTitle("관심삭제하기")
                .setMessage("해당 정류소 및 버스를 관심 삭제 하시겠습니까?\n\n정류소명 : "+Selected_Station_Name +"\n"
                        +"버스번호:" +Selected_Bus_Number +"\n")
                .setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)                      //사용자가 예를 클릭 할 경우 db에 insert
                            {
                                dbHelper.Delete(Selected_Station_Name,Selected_Bus_Number);
                                MyPage2.setVisibility(View.INVISIBLE);
                                MyPage1.setVisibility(View.VISIBLE);

                                View_BusInfo();
                            }
                        })
                .setNeutralButton("아니오",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){}                             //아니오를 클릭한 경우 아무 처리하지 않음
                }).create();
        return dialog;
    }     //AlertDialog 원형 2

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (networkTask.getStatus() == AsyncTask.Status.RUNNING) {
                networkTask.cancel(true);
            }
        } catch (Exception e) {
        }
    }
} //onDestroy() //AsyncTask 안전하게 종료하는 부분 구현

