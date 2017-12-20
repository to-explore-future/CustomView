package com.example.customview.activity.eraser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.customview.R;

public class EraserActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eraser);

        Button mEraserPorterDuffXformode = (Button) findViewById(R.id
                .btn_eraser_porterDuffXformode);
        Button mEraserChangePixel = (Button) findViewById(R.id.btn_eraser_chang_pixel);

        mEraserPorterDuffXformode.setOnClickListener(this);
        mEraserChangePixel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_eraser_porterDuffXformode:
                startActivity(new Intent(this, EraserXfermodeActivity.class));
                break;
            case R.id.btn_eraser_chang_pixel:
                startActivity(new Intent(this, EraserSetPixelActivity.class));
                break;
        }
    }
}
