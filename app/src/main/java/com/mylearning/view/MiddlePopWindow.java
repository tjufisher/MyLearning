package com.mylearning.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mylearning.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by user on 2015/9/28.
 */
public class MiddlePopWindow extends PopupWindow {


    @InjectView(R.id.tv_choose)
    TextView tvChoose;
    @InjectView(R.id.btn_take_video)
    Button btnTakeVideo;
    @InjectView(R.id.btn_take_photo)
    Button btnTakePhoto;
    @InjectView(R.id.btn_pick_photo)
    Button btnPickPhoto;
    @InjectView(R.id.btn_cancel)
    Button btnCancel;
    @InjectView(R.id.pop_layout)
    LinearLayout popLayout;
    private View mMenuView;

    public MiddlePopWindow(Context context, View.OnClickListener itemsOnClick,
                           String... flag) {
        super(context);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = layoutInflater.inflate(R.layout.pop_window_photo, null);
        ButterKnife.inject(this, mMenuView);

        switch (flag.length) {
            case 4:
                btnTakeVideo.setText(flag[3]);
                btnTakeVideo.setVisibility(View.VISIBLE);
                btnTakeVideo.setTextColor(Color.RED);
                btnTakePhoto.setTextColor(Color.RED);
                btnPickPhoto.setTextColor(Color.RED);
            case 3:
                btnCancel.setText(flag[2]);
            case 2:
                btnPickPhoto.setText(flag[1]);
            case 1:
                btnTakePhoto.setText(flag[0]);
                break;
        }

        btnTakeVideo.setOnClickListener(itemsOnClick);
        btnTakePhoto.setOnClickListener(itemsOnClick);
        btnPickPhoto.setOnClickListener(itemsOnClick);
        btnCancel.setOnClickListener(itemsOnClick);

        setContentView(mMenuView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setTouchable(true);
        setAnimationStyle(R.style.AnimBottom);
        ColorDrawable cd = new ColorDrawable(0xb0000000);
        setBackgroundDrawable(cd);

        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int)event.getY();
                if( event.getAction() == MotionEvent.ACTION_UP){
                    if( y < height){
                        dismiss();
                    }
                }
                return true;
            }
        });



    }
}
