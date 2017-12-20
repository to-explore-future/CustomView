package com.example.customview.activity.Xformode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.customview.R;

public class XformodeActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xformode);

        Button mShowKnowledgePoint = (Button) findViewById(R.id.btn_show_knowledge_point);
        Button mGirlDemo = (Button) findViewById(R.id.btn_girl_demo);
        Button mXformodeColorFilter = (Button) findViewById(R.id.btn_xformode_colorFilter);

        mShowKnowledgePoint.setOnClickListener(this);
        mGirlDemo.setOnClickListener(this);
        mXformodeColorFilter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_show_knowledge_point:
                startActivity(new Intent(this,XformodeAllModeActivity.class));
                break;
            case R.id.btn_girl_demo:
                startActivity(new Intent(this, BeautifulGirlExampleActivity.class));
                break;
            case R.id.btn_xformode_colorFilter:
                 startActivity(new Intent(this, DreamEffectActivity.class));
                 break;
        }
    }
}
