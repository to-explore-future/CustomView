package com.example.customview.activity.twoWay2StartActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.example.customview.R;

public class Activity02 extends Activity {

    private int resultCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_02);

        Intent mIntent = new Intent();
        mIntent.putExtra("change01", "1000");
        mIntent.putExtra("change02", "2000");
        // 设置结果，并进行传送
        this.setResult(resultCode, mIntent);

        SystemClock.sleep(2000);
//        this.finish();
    }
}
