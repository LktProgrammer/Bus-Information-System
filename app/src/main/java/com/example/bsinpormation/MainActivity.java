package com.example.bsinpormation;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;  //import 클래스


public class MainActivity extends AppCompatActivity {
    View MyPage1,MyPage2,MyPage3;       // frameLayout에 적용되지 view
    EditText BusNum_Input;               // 버스 노선 검색을 위한 사용자 입력창
    String Service_Key="xz2T8UWgGRf26MT53WiDx%2F9Zw0Cgs8oH5zicdOayNo0mC3P9gAeUSdcFHRAfjALQYwxSCrmcL6MKn1uJgTUngQ%3D%3D";    //openApi 요청을 위한 servicekey
    String Result_Xml="";                // 응답 결과 저장
    NetworkTask networkTask;             // 비동기 처리
    ArrayList<BusLine_Info> busline_info_list=new ArrayList<BusLine_Info>();    // 파싱한 버스 노선 정보를 저장
    ArrayList<BusStation_Info> busstation_info_list=new ArrayList<BusStation_Info>();
    String Test_url= "http://data.busan.go.kr/openBus/service/busanBIMS2/stopArr?bstopid=172080301&serviceKey=xz2T8UWgGRf26MT53WiDx%2F9Zw0Cgs8oH5zicdOayNo0mC3P9gAeUSdcFHRAfjALQYwxSCrmcL6MKn1uJgTUngQ%3D%3D";                 //정류소 정보 파싱 테스트를 위한 임시 변수
    ListView listview;
    List_BusLine_Adapter line_adapter;
    List_BusStation_Adapter busstation_adpter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        listview = (ListView)findViewById(R.id.listview1);
        MyPage1 = findViewById(R.id.page1);
        MyPage2 = findViewById(R.id.page2);
        MyPage3 = findViewById(R.id.page3);

        BusNum_Input = (EditText)findViewById(R.id.editText);

        //버튼 리스너 등록
        findViewById(R.id.button1).setOnClickListener(mClickListener);
        findViewById(R.id.button2).setOnClickListener(mClickListener);
        findViewById(R.id.button3).setOnClickListener(mClickListener);
        findViewById(R.id.button4).setOnClickListener(mClickListener);
    }
    public Button.OnClickListener mClickListener = new Button.OnClickListener()
    {
        public void onClick(View v)
        {
            //최초 이벤트 발생시에 모든 page Invisible로 설정
            MyPage1.setVisibility(View.INVISIBLE);
            MyPage2.setVisibility(View.INVISIBLE);
            MyPage3.setVisibility(View.INVISIBLE);

            switch(v.getId())
            {
                case R.id.button1:
                    MyPage1.setVisibility(View.VISIBLE);
                    break;
                case R.id.button2:
                    MyPage2.setVisibility(View.VISIBLE);
                    break;
                case R.id.button3:
                    MyPage3.setVisibility(View.VISIBLE);
                    break;
                case R.id.button4:
                    MyPage2.setVisibility(View.VISIBLE);
                    busline_info_list = null;;
                    Result_Xml="";
                    String Bus_Id = BusNum_Input.getText().toString();   //사용자가 입력한 버스 번호를 저장
                    String Result_Url="";
                    String Bus_Num="";

                    if(Bus_Id.equals("")){                                 //입력 내용이 공백인지 체크
                        Toast.makeText(getApplicationContext(),"버스 번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Bus_Num= Get_BusId(Bus_Id);                        //공백이 아니라면 버스번호->id로 변환해주는 Get_BusId()메소드 호출
                        Result_Url= Create_Url("busInfoRoute",Bus_Num,Service_Key,1);

                        networkTask = new NetworkTask(Result_Url,null);
                        networkTask.execute();

                        while(Result_Xml.equals("")){}                  //AsyncTask 처리 결과를 대기합니다.

                        My_Parser my_parser = new My_Parser(new Parser_Line(Result_Xml));

                        try{
                            my_parser.Parsing_Xml();                      //데이터 파싱
                            busline_info_list=(ArrayList<BusLine_Info>)my_parser.Get_InfoList();    //파싱 결과인 ArrayList를 가져옴

                            //listview를 통해 ArrayList의 내용을 표시함
                            //line_adapter = new List_BusLine_Adapter(getApplicationContext(),R.layout.linsview_line,busline_info_list);
                            //listview.setAdapter(line_adapter);

                        }catch(Exception e){}
                    }
            }
        }
    };

    public class NetworkTask extends AsyncTask<String,String,String>
    {
        private String url;
        private ContentValues values;

        public NetworkTask(String url,ContentValues values)
        {
            this.url = url;
            this.values = values;
        }
        protected String doInBackground(String... params)
        {
            String result="";
            UrlConnection urlconnection = new UrlConnection(this.url,values);
            result = urlconnection.Request_UrlConnect();
            Result_Xml=result;
            return result;
        }
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
        }

    }    //비동기 처리를 위한 AsyncTask 구현 //비동기 처리를 위한 AsyncTask 구현 부분

    public String Get_BusId(String bus_id)
    {
        String bus_num="";
        if(bus_id.contains("-")) {          // '-' 포함되어 있다면 제거
            bus_num = bus_id.substring(0, bus_id.indexOf('-'));
            bus_num += bus_id.substring(bus_id.indexOf('-')+1);
        }
        else{
            bus_num = bus_id;
        }
        if(bus_num.length()==1 ||bus_num.length()==2 ){            //버스 번호가 1자리 일때와 아닐때 생성규칙을 구분하여 구현
            bus_num = "52000" + bus_num ;
            while(bus_num.length()!=10)
            {
                bus_num+="0";
            }
        }
        else{
            bus_num = "5200" + bus_num;
            while(bus_num.length()!=10)
            {
                bus_num += "0";
            }
        }
        return bus_num;
    } // GetBusId() 함수 원형 부분 //버스번호를 생성규칙에 맞게 BusId로 변환하는 함수

    public String Create_Url(String Request_URL,String Request_Param,String Service_Key,int Request_Case)
    {
        String Request_Url = "";
        switch(Request_Case){               //Requesst_Case에 따라 URL 구성이 달라지도록 구현
            case 1:                         //버스 노선 검색을 위한 Url 구성
                Request_Url = "http://data.busan.go.kr/openBus/service/busanBIMS2/"
                              +Request_URL+"?" + "lineid=" +Request_Param + "&serviceKey=" + Service_Key;
                break;
        }
        return Request_Url;
    }  // Create_Url() 함수 원형 부분 // 요청에 알맞는 Url 형성을 위한 메소드
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        try
        {
            if(networkTask.getStatus()==AsyncTask.Status.RUNNING)
            {
                networkTask.cancel(true);
            }
        }catch(Exception e){}
    }
} //onDestroy() //AsyncTask 안전하게 종료하는 부분 구현
