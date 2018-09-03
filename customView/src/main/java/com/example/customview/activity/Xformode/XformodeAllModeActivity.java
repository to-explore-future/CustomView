package com.example.customview.activity.Xformode;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.customview.R;
import com.example.customview.customview.XformodePackage.CustomView_Xfermode;

public class XformodeAllModeActivity extends Activity {

    private RecyclerView recyclerView;
    private String[] modeNames;
    private PorterDuffXfermode[] modes;
    private String[] introduces;
    private MyRecylerViewAdapter.MyRecyclerViewHolder myHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xformode_all_mode);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        MyRecylerViewAdapter adapter = new MyRecylerViewAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                showPopupWindow(view, data);
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        initNameSet();
    }

    private void initNameSet() {
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
                "Screen计算方式：[Sa + Da - Sa * Da, Sc + Dc - Sc * Dc]；Chinese：滤色，就是，本来已经有一个投影仪 A 的影像投射在屏幕上了，这时你又打开了另一个投影仪 B，两个投影仪一起投到屏幕上。于是" +
                        "如果投影仪 B 发出白光，则画面当然是白的。" +
                        "如果投影仪 B 发出黑光（⋯⋯相当于没有开），画面当然还是 A 的投影，完全不变。" +
                        "其他颜色也可按照这种规律来模拟",
                "Add计算方式：Saturate(S + D)；Chinese：饱和相加",
                "OverLay计算方式：未给出；Chinese：叠加"};

        modes = new PorterDuffXfermode[]{
                new PorterDuffXfermode(PorterDuff.Mode.CLEAR), new PorterDuffXfermode(PorterDuff.Mode.SRC),
                new PorterDuffXfermode(PorterDuff.Mode.DST), new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),
                new PorterDuffXfermode(PorterDuff.Mode.DST_OVER), new PorterDuffXfermode(PorterDuff.Mode.SRC_IN),
                new PorterDuffXfermode(PorterDuff.Mode.DST_IN), new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),
                new PorterDuffXfermode(PorterDuff.Mode.DST_OUT), new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),
                new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP), new PorterDuffXfermode(PorterDuff.Mode.XOR),
                new PorterDuffXfermode(PorterDuff.Mode.DARKEN), new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
                new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY), new PorterDuffXfermode(PorterDuff.Mode.SCREEN),
                new PorterDuffXfermode(PorterDuff.Mode.ADD), new PorterDuffXfermode(PorterDuff.Mode.OVERLAY)
        };
    }


    private void showPopupWindow(View anchor, String data) {
        //得到anchor的左上角点在屏幕中的位置,数据存到location中
        int location[] = new int[2];
        anchor.getLocationOnScreen(location);

        View contentView = View.inflate(XformodeAllModeActivity.this, R.layout.popupwindow_item,
                null);
        TextView textView = (TextView) contentView.findViewById(R.id.tv_popupwindown_item);
        textView.setText(data);

        //展示那个三角
//        ImageView imageView = new ImageView(this);
//        imageView.setImageResource(R.mipmap.indicator);
//        imageView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//        int imageViewHeight = imageView.getMeasuredHeight();
//        Log.i("imageViewHeight", "imageViewHeight:" + imageViewHeight);
//        final PopupWindow popupWindowIndicator = new PopupWindow(imageView, ViewGroup.LayoutParams
//                .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        popupWindowIndicator.showAtLocation(anchor, Gravity.NO_GRAVITY, location[0], location[1] + anchor
//                .getHeight());
//        popupWindowIndicator.setFocusable(false);


        PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams
                .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWindowHeight = popupWindow.getContentView().getMeasuredHeight();
        int popupWindowWidth = popupWindow.getContentView().getMeasuredWidth();
        Log.i("Xformode", "popupWindowHeight:" + popupWindowHeight + "==popupWindowWidth" +
                ":" + popupWindowWidth);
        popupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, location[0], location[1] + anchor
                .getHeight() /*+ imageViewHeight*/);
        popupWindow.setFocusable(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    //define interface
    interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

    class MyRecylerViewAdapter extends RecyclerView.Adapter<MyRecylerViewAdapter.MyRecyclerViewHolder> implements View.OnClickListener {
        int i = 0;
        private OnRecyclerViewItemClickListener mOnItemClickListener = null;

        @Override
        public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(XformodeAllModeActivity.this).inflate(R.layout
                    .recycleview_item, null);
            view.setTag(introduces[i++]);
            MyRecyclerViewHolder holder = new MyRecyclerViewHolder(view);
            view.setOnClickListener(this);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyRecyclerViewHolder holder, int position) {
            myHolder = holder;
            myHolder.tv_name.setText(modeNames[position]);
            myHolder.linearLayout.addView(new CustomView_Xfermode(XformodeAllModeActivity
                    .this, modes[position]));

        }

        @Override
        public int getItemCount() {
            return modeNames.length;
        }

        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            this.mOnItemClickListener = listener;
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, v.getTag() + "");
            }
        }

        class MyRecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView tv_name;
            LinearLayout linearLayout;

            public MyRecyclerViewHolder(View itemView) {
                super(itemView);
                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                linearLayout = (LinearLayout) itemView.findViewById(R.id.ll_linearlayout);

            }
        }
    }
}
