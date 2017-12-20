package com.example.customview.activity.colorFilter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.customview.R;
import com.example.customview.customview.colorFilterViewPackage.CustomView1;

public class PorterDuffActivity extends Activity implements View.OnClickListener {

    private int radius;
    private CustomView1 drawCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_porter_duff);

        drawCircle = (CustomView1) findViewById(R.id.dc_draw_circle);
        radius = drawCircle.getRadius();

        Button addRadius = (Button) findViewById(R.id.btn_radius_additon);
        Button subtracRadius = (Button) findViewById(R.id.btn_radius_subtraction);

        addRadius.setOnClickListener(this);
        subtracRadius.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_radius_additon:
                drawCircle.setRadius(radius += 20);
                break;
            case R.id.btn_radius_subtraction:
                drawCircle.setRadius(radius -= 20);
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        drawCircle = null;
    }
}
