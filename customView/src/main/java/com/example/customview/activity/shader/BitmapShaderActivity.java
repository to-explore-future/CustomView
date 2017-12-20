package com.example.customview.activity.shader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.customview.R;
import com.example.customview.customview.ShaderView.BitmapShaderView;

public class BitmapShaderActivity extends Activity {

    private BitmapShaderActivity bitmapShaderActivity;

    private TextView mTileState;
    private BitmapShaderView bitmapShaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bitmapShaderActivity = new BitmapShaderActivity();
        setContentView(R.layout.activity_bitmap_shader);

        mTileState = (TextView) findViewById(R.id.tv_bitmap_shader_tile_state);
        bitmapShaderView = (BitmapShaderView) findViewById(R.id.cus_bitmapShaderView);
        TextView mTileState = (TextView) findViewById(R.id.tv_bitmap_shader_tile_state);
        bitmapShaderView.setTextView(mTileState);

        bitmapShaderView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;

                    //只有点击 立马抬起 这里才会有反应
                    case MotionEvent.ACTION_UP:
                        //控件就得停止循环
                        if (bitmapShaderView.loopState == true) {
                            bitmapShaderView.stopLoop();
                        } else {
                            bitmapShaderView.toLoop();
                        }
                        break;
                }

                return true;
            }
        });


    }
}