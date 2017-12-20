package com.example.customview.activity.pagerTurn;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.example.customview.R;
import com.example.customview.customview.PagerTurn.PageTurnViewAiGe;

import java.util.ArrayList;

public class PagerTurnViewAiGeActivity extends Activity {

    private int[] pageIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_turn_view_ai_ge);

        PageTurnViewAiGe pageTurnViewAiGe = (PageTurnViewAiGe) findViewById(R.id.btn_pager_turnView_aige);

        pageIds = new int[]{
                R.mipmap.pager1, R.mipmap.pager2,
                R.mipmap.pager3, R.mipmap.pager4
        };

        ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();

        for (int i = 0; i < 4; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), pageIds[i]);
            bitmaps.add(bitmap);
        }

        pageTurnViewAiGe.setBitmaps(bitmaps);
    }
}
