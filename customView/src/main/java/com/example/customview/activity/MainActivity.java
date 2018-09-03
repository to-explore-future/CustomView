package com.example.customview.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.customview.R;
import com.example.customview.activity.QRcode.QRCodeActivity;
import com.example.customview.activity.Xformode.XformodeActivity;
import com.example.customview.activity.attrs_test.AttrbutesTestActivity;
import com.example.customview.activity.canvas.CanvasActivity;
import com.example.customview.activity.colorFilter.MaskFilterActivity;
import com.example.customview.activity.colorFilter.PorterDuffActivity;
import com.example.customview.activity.eraser.EraserActivity;
import com.example.customview.activity.interpolator.InterpolatorActivity;
import com.example.customview.activity.pagerTurn.PagerTurnViewActivity;
import com.example.customview.activity.pagerTurn.PagerTurnViewAiGeActivity;
import com.example.customview.activity.pagerTurn.PagerTurnViewBrokenLineActivity;
import com.example.customview.activity.path.PathActivity;
import com.example.customview.activity.shader.Shaderactivity;
import com.example.customview.activity.text.TextTestActivity;
import com.example.customview.activity.twoWay2StartActivity.Two2StartActivity;

public class MainActivity extends Activity implements View.OnClickListener {

    String TAG = "MainActivity";
    String TAG_CARD = "sdcard";
    private Button mAttrs;
    private Button mRotateTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //以后log 就是用局部变量


        Log.i(TAG, "onCreate: ");
        Log.i(TAG_CARD, "sd卡状态:" + Environment.getExternalStorageState());
        Log.i(TAG_CARD, "sd存储位置:" + Environment.getExternalStorageDirectory());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button porterDufferColorFilter = (Button) findViewById(R.id.btn_ColorFilter);
        Button mNewActivityPlugin = (Button) findViewById(R.id.btn_Main2);
        Button qrCode = (Button) findViewById(R.id.btn_QR_code);
        Button mText = (Button) findViewById(R.id.btn_text_test);
        Button mMaskFilter = (Button) findViewById(R.id.btn_MaskFilter_act);
        Button mXformode = (Button) findViewById(R.id.btn_XFormode);
        Button mEarser = (Button) findViewById(R.id.btn_eraser);
        Button mPath = (Button) findViewById(R.id.btn_path);
        Button mShader = (Button) findViewById(R.id.btn_shader);
        Button m2Way2StartActivity = (Button) findViewById(R.id.btn_twoWay_toStartActivity);
        Button mInterpolator = (Button) findViewById(R.id.btn_interpolator);
        Button mCanvas = (Button) findViewById(R.id.btn_canvas);
        Button mPagerTurnView = (Button) findViewById(R.id.btn_pager_turn_view);
        Button mPagerTurnViewAiGe = (Button) findViewById(R.id.btn_pager_turnView_aige);
        Button mTurnViewBrokenLine = (Button) findViewById(R.id.btn_draw_broken_line_aige);
        mAttrs = (Button) findViewById(R.id.btn_attr);
        mRotateTest = (Button) findViewById(R.id.imageview_rotate_test);

        porterDufferColorFilter.setOnClickListener(this);
        mNewActivityPlugin.setOnClickListener(this);
        qrCode.setOnClickListener(this);
        mText.setOnClickListener(this);
        mMaskFilter.setOnClickListener(this);
        mXformode.setOnClickListener(this);
        mEarser.setOnClickListener(this);
        mPath.setOnClickListener(this);
        mShader.setOnClickListener(this);
        m2Way2StartActivity.setOnClickListener(this);
        mInterpolator.setOnClickListener(this);
        mCanvas.setOnClickListener(this);
        mPagerTurnView.setOnClickListener(this);
        mTurnViewBrokenLine.setOnClickListener(this);
        mAttrs.setOnClickListener(this);
        mRotateTest.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ColorFilter:
                startActivity(new Intent(this, PorterDuffActivity.class));
                break;
            case R.id.btn_XFormode:
                startActivity(new Intent(this, XformodeActivity.class));
                break;
            case R.id.btn_eraser:
                startActivity(new Intent(this, EraserActivity.class));
                break;

            case R.id.btn_Main2:
                startActivity(new Intent(this, Main2Activity.class));
                break;

            case R.id.btn_QR_code:
                startActivity(new Intent(this, QRCodeActivity.class));
                break;

            case R.id.btn_text_test:
                startActivity(new Intent(this, TextTestActivity.class));
                break;
            case R.id.btn_MaskFilter_act:
                startActivity(new Intent(this, MaskFilterActivity.class));
                break;

            case R.id.btn_path:
                startActivity(new Intent(this, PathActivity.class));
                break;
            case R.id.btn_shader:
                startActivity(new Intent(this, Shaderactivity.class));
                break;
            case R.id.btn_twoWay_toStartActivity:
                startActivity(new Intent(this, Two2StartActivity.class));
                break;
            case R.id.btn_interpolator:
                startActivity(new Intent(this, InterpolatorActivity.class));
                break;
            case R.id.btn_canvas:
                startActivity(new Intent(this, CanvasActivity.class));
                break;
            case R.id.btn_pager_turn_view:
                startActivity(new Intent(this, PagerTurnViewActivity.class));
                break;
            case R.id.btn_pager_turnView_aige:
                 startActivity(new Intent(this, PagerTurnViewAiGeActivity.class));
                 break;
            case R.id.btn_draw_broken_line_aige:
                 startActivity(new Intent(this, PagerTurnViewBrokenLineActivity.class));
                 break;
            case R.id.btn_attr:
                 startActivity(new Intent(this, AttrbutesTestActivity.class));
                 break;
            case R.id.imageview_rotate_test:

                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.i(TAG, "onWindowFocusChanged: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }
}



