package com.example.bsinpormation;

import android.content.ContentValues;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by 이기택 on 2017-07-18.
 */

public class UrlConnection extends Thread   //비동기 처리를 위한 URL Connection class
{
    String Request_url;
    public UrlConnection(String url, ContentValues _params)
    {
        this.Request_url = url;
    }
    public String  Request_UrlConnect()     //URL요청을 통한 xml 형식의 결과값을 반환하는 함수
    {
        String Result_xml="";
        try {
            URL Url = new URL(Request_url);
            HttpURLConnection conn = (HttpURLConnection) Url.openConnection();

            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setDefaultUseCaches(false);
            conn.connect();
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null)     //XML 내용 읽어옴
            {
                builder.append(line + "\n");
            }
            Result_xml = builder.toString();

        } catch (MalformedURLException | ProtocolException exception) {
            exception.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        }
        return Result_xml;
    }
}

