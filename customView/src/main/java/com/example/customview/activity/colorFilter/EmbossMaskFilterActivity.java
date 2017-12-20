package com.example.customview.activity.colorFilter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.customview.R;
import com.example.customview.customview.MaskFilterViewPackage.EmbossMaskFilterView;

public class EmbossMaskFilterActivity extends Activity {

    private TextView mLightState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_emboss_mask_filter);
        mLightState = (TextView) findViewById(R.id.tv_light_state);
        EmbossMaskFilterView mEmbossView = (EmbossMaskFilterView) findViewById(R.id.emf_embossMaskFilterView);
        mEmbossView.setLightState(new LightStateCallBack());
    }


    public class LightStateCallBack {

        public void changeState(String state) {
            mLightState.setText(state);
        }


    }
}
