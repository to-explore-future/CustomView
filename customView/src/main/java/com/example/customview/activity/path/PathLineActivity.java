package com.example.customview.activity.path;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;

import com.example.customview.R;
import com.example.customview.customview.PathView.PathLineView;

public class PathLineActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_line);
        PathLineView pathLineView = (PathLineView) findViewById(R.id.pathLineView);

        Point point = new Point(100,100);
        Point point1 = new Point(800,100);
        Point point2 = new Point(800,800);
        Point point3 = new Point(100,800);
        Point point4 = new Point(100,1600);
//        Point point5 = new Point(800,1600);

        pathLineView.addPoint(point);
        pathLineView.addPoint(point1);
        pathLineView.addPoint(point2);
        pathLineView.addPoint(point3);
        pathLineView.addPoint(point4);
//        pathLineView.addPoint(point5);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
