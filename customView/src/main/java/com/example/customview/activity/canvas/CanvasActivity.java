package com.example.customview.activity.canvas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.customview.R;

public class CanvasActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_mesh);

        Button mBigBosomsGril = (Button) findViewById(R.id.btn_big_bosoms_girl);
        Button mBitmapMesh = (Button) findViewById(R.id.btn_bitmap_mesh_activity);
        Button mCanvasSize = (Button) findViewById(R.id.btn_canvas_size);
        Button mCanvasClip = (Button) findViewById(R.id.btn_canvas_clip);
        Button mCanvasClip2 = (Button) findViewById(R.id.btn_canvas_clip_2);
        Button mSnow = (Button) findViewById(R.id.btn_snow);
        Button mMySnow = (Button) findViewById(R.id.btn_my_snow);
        Button mCCW = (Button) findViewById(R.id.btn_ccw);
        Button mBrokenLine = (Button) findViewById(R.id.btn_draw_broken_line);
        Button mRegionView = (Button) findViewById(R.id.btn_regionView);
        Button mCanvasSave1 = (Button) findViewById(R.id.btn_canvas_save_restore);
        Button mCanvasSave2 = (Button) findViewById(R.id.btn_canvas_save_restore_2);
        Button mCanvasSave3 = (Button) findViewById(R.id.btn_canvas_save_restore_3);

        mBigBosomsGril.setOnClickListener(this);
        mBitmapMesh.setOnClickListener(this);
        mCanvasSize.setOnClickListener(this);
        mCanvasClip.setOnClickListener(this);
        mCanvasClip2.setOnClickListener(this);
        mSnow.setOnClickListener(this);
        mMySnow.setOnClickListener(this);
        mCCW.setOnClickListener(this);
        mBrokenLine.setOnClickListener(this);
        mRegionView.setOnClickListener(this);
        mCanvasSave1.setOnClickListener(this);
        mCanvasSave2.setOnClickListener(this);
        mCanvasSave3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_big_bosoms_girl:
                startActivity(new Intent(this, BigBosomsGirlActivity.class));
                break;
            case R.id.btn_bitmap_mesh_activity:
                startActivity(new Intent(this, BitmapMeshActivity.class));
                break;
            case R.id.btn_canvas_size:
                startActivity(new Intent(this, CanvasSizeActivity.class));
                break;
            case R.id.btn_canvas_clip:
                startActivity(new Intent(this, CanvasClipActivity.class));
                break;
            case R.id.btn_canvas_clip_2:
                startActivity(new Intent(this, CanvasClipActivity2.class));
                break;
            case R.id.btn_snow:
                startActivity(new Intent(this, SnowActivity.class));
                break;
            case R.id.btn_my_snow:
                startActivity(new Intent(this, MySnowActivity.class));
                break;
            case R.id.btn_ccw:
                 startActivity(new Intent(this, CCWActivity.class));
                 break;
            case R.id.btn_draw_broken_line:
                 startActivity(new Intent(this, BrokenLineActivity.class));
                 break;
            case R.id.btn_regionView:
                 startActivity(new Intent(this, RegionViewActivity.class));
                 break;
            case R.id.btn_canvas_save_restore:
                 startActivity(new Intent(this, CanvasSomeActivity.class));
                 break;
            case R.id.btn_canvas_save_restore_2:
                 startActivity(new Intent(this, CanvasSomeActivity2.class));
                 break;
            case R.id.btn_canvas_save_restore_3:
                 startActivity(new Intent(this, CanvasSomeActivity3.class));
                 break;


        }
    }
}
