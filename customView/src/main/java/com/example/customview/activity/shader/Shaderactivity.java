package com.example.customview.activity.shader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.customview.R;
import com.example.customview.activity.Xformode.DreamEffectActivity;

public class Shaderactivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shaderactivity);

        Button mBitmapShader = (Button) findViewById(R.id.btn_bitmap_shader);
        Button mBitmapShaderDemoBrick = (Button) findViewById(R.id.btn_bitmap_shader_demo_brick);
        Button mLinearShader = (Button) findViewById(R.id.btn_linear_shader);
        Button mBitmapShaderDemoGirl = (Button) findViewById(R.id.btn_bitmap_shader_demo_girl);
        Button mLinearShaderDemoGirl = (Button) findViewById(R.id.btn_linear_shader_demo_girl);
        Button mSweepGradient = (Button) findViewById(R.id.btn_sweepGridient);
        Button mRadialGradient = (Button) findViewById(R.id.btn_RadialGradient);
        Button mRadialGradientDemo = (Button) findViewById(R.id.btn_RadialGradient_demo);
        Button mMatrixImageView = (Button) findViewById(R.id.btn_Matrix_imageView);
        Button mMatrixImageViewMy = (Button) findViewById(R.id.btn_Matrix_imageView_my);
        Button mAnimListView = (Button) findViewById(R.id.btn_Matrix_anim_listView);
        Button mDrawPicture = (Button) findViewById(R.id.btn_draw_picture);

        mBitmapShader.setOnClickListener(this);
        mBitmapShaderDemoBrick.setOnClickListener(this);
        mLinearShader.setOnClickListener(this);
        mBitmapShaderDemoGirl.setOnClickListener(this);
        mLinearShaderDemoGirl.setOnClickListener(this);
        mSweepGradient.setOnClickListener(this);
        mRadialGradient.setOnClickListener(this);
        mRadialGradientDemo.setOnClickListener(this);
        mMatrixImageView.setOnClickListener(this);
        mMatrixImageViewMy.setOnClickListener(this);
        mAnimListView.setOnClickListener(this);
        mDrawPicture.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bitmap_shader:
                startActivity(new Intent(this, BitmapShaderActivity.class));
                break;
            case R.id.btn_bitmap_shader_demo_brick:
                startActivity(new Intent(this, BitmapShaderDemoActivity.class));
                break;
            case R.id.btn_linear_shader:
                startActivity(new Intent(this, LinearSharderActivity.class));
                break;
            case R.id.btn_bitmap_shader_demo_girl:
                startActivity(new Intent(this, LinearShaderDemoActivity.class));
                break;
            case R.id.btn_linear_shader_demo_girl:
                startActivity(new Intent(this, LinearShaderDemoGirlActivity.class));
                break;
            case R.id.btn_sweepGridient:
                 startActivity(new Intent(this, SweepGradientActivity.class));
                 break;
            case R.id.btn_RadialGradient:
                 startActivity(new Intent(this, RedialGradientActivity.class));
                 break;
            case R.id.btn_RadialGradient_demo:
                 startActivity(new Intent(this, DreamEffectActivity.class));
                 break;
            case R.id.btn_Matrix_imageView:
                 startActivity(new Intent(this, MatrixImageViewActivity.class));
                 break;
            case R.id.btn_Matrix_imageView_my:
                 startActivity(new Intent(this, MyMatrixImageViewActivity.class));
                 break;
            case R.id.btn_Matrix_anim_listView:
                 startActivity(new Intent(this, AnimListViewActivity.class));
                 break;
            case R.id.btn_draw_picture:
                 startActivity(new Intent(this, DrawAPictureActivity.class));
                 break;



        }
    }
}
