package com.example.customview.activity.text;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.customview.R;

public class TextTestActivity extends Activity implements View.OnClickListener {

    private Button typeFace;
    private Button drawText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_test);

        typeFace = (Button) findViewById(R.id.activity_text_test_typeface);
        drawText = (Button) findViewById(R.id.activity_text_draw_text);

        typeFace.setOnClickListener(this);
        drawText.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_text_draw_text:

                startActivity(new Intent(this,DrawTextActivity.class));
                break;

            case R.id.activity_text_test_typeface:
                startActivity(new Intent(this,TypeFaceActivity.class));
                break;
        }
    }
}
