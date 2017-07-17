package com.example.bsinpormation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    View MyPage1,MyPage2,MyPage3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        MyPage1 = findViewById(R.id.page1);
        MyPage2 = findViewById(R.id.page2);
        MyPage3 = findViewById(R.id.page3);
        //버튼 클릭 시 마다 보여줄 Layout의 id를 찾습니다.

        findViewById(R.id.button1).setOnClickListener(mClickListener);
        findViewById(R.id.button2).setOnClickListener(mClickListener);
        findViewById(R.id.button3).setOnClickListener(mClickListener);
        //버튼 리스너 등록

    }
    public Button.OnClickListener mClickListener = new Button.OnClickListener()
    {
        public void onClick(View v)
        {
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
            }
        }
    };
}
