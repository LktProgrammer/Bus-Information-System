package com.example.bsinpormation;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by 이기택 on 2017-08-09.
 */

public class View_Station extends AppCompatActivity {
    private String Result_Url= "http://data.busan.go.kr/openBus/service/busanBIMS2/stopArr?serviceKey=ZoZXyocp1pZ6ikv7VCNZlKvVFDCjUVWM%2BiwgZ2AHblNEJX6Qr%2FblSS43%2BzhhmM0%2Fapmwo0SAbYc4MkgYRqNrVA%3D%3D&bstopid=";
    private String Result_Xml;
    private ArrayList<BusStation_Info> busstation_info_list =new ArrayList<BusStation_Info>();
    private List_BusStation_Adapter adapter;
    private ListView listview;
    private ProgressBar progressBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stationactivity);

        listview=(ListView)findViewById(R.id.listview3);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        Intent intent = getIntent();
        String Arsno = intent.getStringExtra("Arsno");
        Result_Url=Result_Url+Arsno;
        NetworkTask2 networktask = new NetworkTask2(Result_Url,null);
        networktask.execute();
    }
    public class NetworkTask2 extends AsyncTask<String, ProgressBar, String> {
        String url;
        ContentValues values;

        public NetworkTask2(String url,ContentValues values) {
            this.values=values;
            this.url = url;
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
            UrlConnection urlconnection = new UrlConnection(this.url, values);
            result = urlconnection.Request_UrlConnect();
            Result_Xml = result;

            return result;
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            My_Parser my_parser = new My_Parser(new Parser_BusStation(Result_Xml));
            try {
                my_parser.Parsing_Xml();
                busstation_info_list = (ArrayList<BusStation_Info>) my_parser.Get_InfoList();
                adapter = new List_BusStation_Adapter(getApplicationContext(),R.layout.listview_station,busstation_info_list);
                listview.setAdapter(adapter);
            }catch(Exception e){}
            progressBar.setVisibility(View.GONE);

        }

    }

}

