package com.example.customview.activity.text;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.customview.R;

public class TypeFaceActivity extends AppCompatActivity {

    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_face);

        tv1 = (TextView) findViewById(R.id.activity_type_face_tv1);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "typeface1.ttf");
        tv1.setTypeface(typeface);
    }
}
