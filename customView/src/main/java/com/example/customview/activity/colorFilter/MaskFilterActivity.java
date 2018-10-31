package com.example.customview.activity.colorFilter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.customview.R;

public class MaskFilterActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mask_filter);

        Button mMaskFilter = findViewById(R.id.btn_maskfilter);
        Button mPictureShadow = findViewById(R.id.btn_picture_shadow);
        Button mChangeChocolateShadow = findViewById(R.id.btn_change_chocolate_shadow);

        mMaskFilter.setOnClickListener(this);
        mPictureShadow.setOnClickListener(this);
        mChangeChocolateShadow.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_maskfilter:
                startActivity(new Intent(this, MaskFilterViewActivity.class));
                break;
            case R.id.btn_picture_shadow:
                startActivity(new Intent(this, BlurMaskFilterActivity.class));
                break;
            case R.id.btn_change_chocolate_shadow:
                startActivity(new Intent(this, EmbossMaskFilterActivity.class));
                break;
        }
    }

    /**
     *
     * @param view
     */
    public void drawViewShadow(View view) {
        startActivity(new Intent(this,ViewShadowActivity.class));
    }
}
