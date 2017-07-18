package com.example.bsinpormation;

import android.content.ContentValues;

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

public class UrlConnection extends Thread
{
    String url;
    public UrlConnection(String url, ContentValues _params)
    {
        this.url = url;
    }
    public String  Request_UrlConnect()
    {
        String xml="";
        try {

            URL Url = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) Url.openConnection();

            conn.setRequestMethod("GET");    // GET 방식 통신
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
            xml = builder.toString();

        } catch (MalformedURLException | ProtocolException exception) {
            exception.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();

        }
        return xml;
    }
}

