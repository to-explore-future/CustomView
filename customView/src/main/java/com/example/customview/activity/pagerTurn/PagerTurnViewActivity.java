package com.example.customview.activity.pagerTurn;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.customview.R;
import com.example.customview.customview.PagerTurn.PagerTurnView;

import java.util.ArrayList;

public class PagerTurnViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_turn_view);

        PagerTurnView pagerTurnView = (PagerTurnView) findViewById(R.id.pager_turn_view);

        ArrayList<Bitmap> bitmaps = new ArrayList<>();

        Bitmap bt1 = BitmapFactory.decodeResource(getResources(), R.mipmap.pager1);
        Bitmap bt2 = BitmapFactory.decodeResource(getResources(), R.mipmap.pager2);
        Bitmap bt3 = BitmapFactory.decodeResource(getResources(), R.mipmap.pager3);
        Bitmap bt4 = BitmapFactory.decodeResource(getResources(), R.mipmap.pager4);

        bitmaps.add(bt1);
        bitmaps.add(bt2);
        bitmaps.add(bt3);
        bitmaps.add(bt4);

        pagerTurnView.setmBitmaps(bitmaps);
    }


}
