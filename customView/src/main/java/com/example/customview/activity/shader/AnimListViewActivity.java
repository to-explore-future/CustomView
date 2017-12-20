package com.example.customview.activity.shader;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.customview.R;
import com.example.customview.customview.ShaderView.AnimListView;

public class AnimListViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_list_veiw);
        AnimListView animListView = (AnimListView) findViewById(R.id.matrix_animListView);
        animListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 1000;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = null;
                if (convertView == null) {
                    view = LayoutInflater.from(AnimListViewActivity.this).inflate(R.layout
                            .item_anim_listview, null);
                } else {
                    view = convertView;
                }

                return view;
            }
        });
    }
}
