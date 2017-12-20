package com.example.customview.activity.twoWay2StartActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.customview.R;

public class Two2StartActivity extends Activity implements View.OnClickListener {

    private TextView mText01;
    private TextView mText02;
    private Button button01;
    private Button button02;
    private Intent mIntent;
    private int requestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two2_start);
        mText01 = (TextView) findViewById(R.id.mText01);
        mText02 = (TextView) findViewById(R.id.mText02);
        button01 = (Button) findViewById(R.id.mButton01);
        button02 = (Button) findViewById(R.id.mButton02);
        button01.setOnClickListener(this);
        button02.setOnClickListener(this);
        mText01.setText("01");
        mText02.setText("02");

        mIntent = new Intent();
        mIntent.setClass(this, Activity02.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mButton01:
                // 请求码的值随便设置，但必须>=0
                requestCode = 0;
                startActivityForResult(mIntent, requestCode);
                break;
            case R.id.mButton02:
                requestCode = 2;
                startActivityForResult(mIntent, requestCode);
                break;
            default:
                break;
        }
    }

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String change01 = data.getStringExtra("change01");
        String change02 = data.getStringExtra("change02");
        // 根据上面发送过去的请求吗来区别
        switch (requestCode) {
            case 0:
                mText01.setText(change01);
                break;
            case 2:
                mText02.setText(change02);
                break;
            default:
                break;
        }
    }
}
