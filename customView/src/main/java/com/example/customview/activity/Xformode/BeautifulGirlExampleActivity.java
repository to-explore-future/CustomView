package com.example.customview.activity.Xformode;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.customview.R;
import com.example.customview.customview.XformodePackage.PorterDuffXfermodeExample;

public class BeautifulGirlExampleActivity extends Activity {

    private String[] modeNames;
    private String[] introduces;
    private PorterDuff.Mode[] modes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beautiful_girl_example);
        initData();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id
                .recylerView_perterDuffXfermode_beautifulGirl);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(new MyBeautifulGrilRecylerViewAdapter());
    }

    private void initData() {
        modeNames = new String[]{"Clear", "Src", "Dst", "SrcOver", "DstOver", "SrcIn", "DstIn", "SrcOut",
                "DstOut", "SrcAtop", "DstAtop", "Xor", "Darken", "Lighten", "Multiply",
                "Screen", "Add", "OverLay"};

        introduces = new String[]{"Clear计算方式：[0, 0]；Chinese：清除",
                "Src计算方式：[Sa, Sc]；Chinese：只绘制源图",
                "Dst计算方式：[Da, Dc]；Chinese：只绘制目标图像",
                "SrcOver计算方式：[Sa + (first - Sa)*Da, Rc = Sc + (first - Sa)*Dc]；Chinese：在目标图像的顶部绘制源图像,画笔其余部分不受影响",
                "DstOver计算方式：[Sa + (first - Sa)*Da, Rc = Dc + (first - Da)*Sc]；Chinese：在源图像的上方绘制目标图像,画笔其余部分不受影响",
                "SrcIn计算方式：[Sa * Da, Sc * Da]；Chinese：只在源图像和目标图像相交的地方绘制源图像",
                "DstIn计算方式：[Sa * Da, Sa * Dc]；Chinese：只在源图像和目标图像相交的地方绘制目标图像",
                "SrcOut计算方式：[Sa * (first - Da), Sc * (first - Da)]；Chinese：只在源图像和目标图像不相交的地方绘制源图像",
                "DstOut计算方式：[Da * (first - Sa), Dc * (first - Sa)]；Chinese：只在源图像和目标图像不相交的地方绘制目标图像",
                "SrcAtop计算方式：[Da, Sc * Da + (first - Sa) * Dc]；Chinese：在源图像和目标图像相交的地方绘制源图像，在不相交的地方绘制目标图像",
                "DstAtop计算方式：[Sa, Sa * Dc + Sc * (first - Da)]；Chinese：在源图像和目标图像相交的地方绘制目标图像而在不相交的地方绘制源图像",
                "Xor计算方式：[Sa + Da - second * Sa * Da, Sc * (first - Da) + (first - Sa) * Dc]；Chinese：在源图像和目标图像重叠之外的任何地方绘制他们，而在不重叠的地方不绘制任何内容",
                "Darken计算方式：[Sa + Da - Sa*Da, Sc*(first - Da) + Dc*(first - Sa) + min(Sc, Dc)]；Chinese：变暗",
                "Lighten计算方式：[Sa + Da - Sa*Da, Sc*(first - Da) + Dc*(first - Sa) + max(Sc, Dc)]；Chinese：变亮",
                "Multiply计算方式：[Sa * Da, Sc * Dc]；Chinese：正片叠底",
                "Screen计算方式：[Sa + Da - Sa * Da, Sc + Dc - Sc * Dc]；Chinese：滤色",
                "Add计算方式：Saturate(S + D)；Chinese：饱和相加",
                "OverLay计算方式：未给出；Chinese：叠加"};

        modes = new PorterDuff.Mode[]{
                (PorterDuff.Mode.CLEAR), (PorterDuff.Mode.SRC),
                (PorterDuff.Mode.DST), (PorterDuff.Mode.SRC_OVER),
                (PorterDuff.Mode.DST_OVER), (PorterDuff.Mode.SRC_IN),
                (PorterDuff.Mode.DST_IN), (PorterDuff.Mode.SRC_OUT),
                (PorterDuff.Mode.DST_OUT), (PorterDuff.Mode.SRC_ATOP),
                (PorterDuff.Mode.DST_ATOP), (PorterDuff.Mode.XOR),
                (PorterDuff.Mode.DARKEN), (PorterDuff.Mode.LIGHTEN),
                (PorterDuff.Mode.MULTIPLY), (PorterDuff.Mode.SCREEN),
                (PorterDuff.Mode.ADD), (PorterDuff.Mode.OVERLAY)
        };
    }

    class MyBeautifulGrilRecylerViewAdapter extends RecyclerView.Adapter<MyBeautifulGirlRecyclerViewHolder> {


        @Override
        public MyBeautifulGirlRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(BeautifulGirlExampleActivity.this).inflate(R.layout
                    .beautiful_girl_example_item, null);

            return new MyBeautifulGirlRecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyBeautifulGirlRecyclerViewHolder holder, int position) {
            holder.modeName.setText(modeNames[position]);
            holder.modeName.setTextColor(Color.RED);
            holder.modeName.setTextSize(25);
            //这里涉及到holder.mLinearlayout的复用,当它滑出屏幕的时候,再次被使用的时候,又会再次添加一个view,
            //这样也就每次复用都会添加一个新的view,这样反复几次使用 它里面的view越来越多,
            if (holder.mLinearlayout.getChildCount() == 2) {
                holder.mLinearlayout.removeViewAt(1);
            }
            holder.mLinearlayout.addView(new PorterDuffXfermodeExample
                    (BeautifulGirlExampleActivity.this, modes[position]));
            Log.i("BeautifulGirlExample", "子view的数量:" + holder.mLinearlayout.getChildCount());
        }

        @Override
        public int getItemCount() {
            return modeNames.length;
        }


    }

    class MyBeautifulGirlRecyclerViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mLinearlayout;
        private TextView modeName;

        public MyBeautifulGirlRecyclerViewHolder(View itemView) {
            super(itemView);
            modeName = (TextView) itemView.findViewById(R.id.tv_mode_name);
            mLinearlayout = (LinearLayout) itemView.findViewById(R.id.ll_linearlayout);

        }
    }


}
