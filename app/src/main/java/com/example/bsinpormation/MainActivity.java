package com.example.bsinpormation;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL; //import 클래스


public class MainActivity extends AppCompatActivity {
    View MyPage1,MyPage2,MyPage3;
    EditText BusNum_Input;
    String Service_Key="xz2T8UWgGRf26MT53WiDx%2F9Zw0Cgs8oH5zicdOayNo0mC3P9gAeUSdcFHRAfjALQYwxSCrmcL6MKn1uJgTUngQ%3D%3D";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

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
                    String Bus_Id = BusNum_Input.getText().toString();   //사용자가 입력한 버스 번호를 저장
                    String Result_Url="";
                    String Bus_Num="";
                    if(Bus_Id.equals("")){                                    //입력 내용이 공백인지 체크
                        Toast.makeText(getApplicationContext(),"버스 번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Bus_Num= Get_BusId(Bus_Id);                        //공백이 아니라면 버스번호->id로 변환해주는 Get_BusId()메소드 호출
                        Result_Url= Create_Url("busInfo",Bus_Num,Service_Key,1);
                        NetworkTask networkTask = new NetworkTask(Result_Url,null);
                        networkTask.execute();
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
            return result;
        }
        protected void onPostExecute(String s)
        {
            BusNum_Input.setText(s);
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
        if(bus_num.length()==1){            //버스 번호가 1자리 일때와 아닐때 생성규칙을 구분하여 구현
            bus_num = "52000" + bus_num + "0000";
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
    }       // Create_Url() 함수 원형 부분 // 요청에 알맞는 Url 형성을 위한 메소드
}
