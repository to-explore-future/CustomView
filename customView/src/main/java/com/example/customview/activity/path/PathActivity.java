package com.example.customview.activity.path;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.customview.R;

public class PathActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);

        Button mEarthRotate = findViewById(R.id.btn_path_earth_revolve_around_the_sun);
        Button mPathTest = findViewById(R.id.btn_path_knoledge_point_test);
        Button mPathDemo = findViewById(R.id.btn_path_test_demo);
        Button mPathEffectView = findViewById(R.id.btn_pathEffectView);
        Button mWaveView = findViewById(R.id.btn_wave_view);
        Button mPathLine = findViewById(R.id.btn_path_line);
        Button ECGView = findViewById(R.id.ecg_view);


        mPathEffectView.setOnClickListener(this);
        mEarthRotate.setOnClickListener(this);
        mPathTest.setOnClickListener(this);
        mPathDemo.setOnClickListener(this);
        mWaveView.setOnClickListener(this);
        mPathLine.setOnClickListener(this);
        ECGView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_path_knoledge_point_test:
                startActivity(new Intent(this, PathExampleActivity.class));
                break;

            case R.id.btn_path_test_demo:
                startActivity(new Intent(this, PathDemoActivity.class));
                break;

            case R.id.btn_path_earth_revolve_around_the_sun:
                startActivity(new Intent(this, RevolveAroundSunActivity.class));
                break;

            case R.id.btn_pathEffectView:
                startActivity(new Intent(this, PathEffectActivity.class));
                break;

            case R.id.btn_wave_view:
                startActivity(new Intent(this, WaveViewActivity.class));
                break;

            case R.id.btn_path_line:
                startActivity(new Intent(this, PathLineActivity.class));
                break;

            case R.id.ecg_view:
                startActivity(new Intent(this, ECGActivity.class));
                break;
        }
    }
}
