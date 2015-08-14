package com.mylearning.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mylearning.R;
import com.mylearning.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener{
    @InjectView(R.id.fl_container)
    FrameLayout flContainer;
    @InjectView(R.id.rg_navigation)
    RadioGroup rgNavigation;


    private Context mContext;
    private RadioButton[] radioButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main, LAYOUT_TYPE_HEADER);
        ButterKnife.inject(this);
        setHeaderBar("欢迎页面", R.drawable.share);
        mContext = this;
        initDatas();


    }

    public void initDatas(){
        radioButtons = new RadioButton[4];
        for( int i = 0; i < 4; i++){
            radioButtons[i] = (RadioButton)rgNavigation.findViewWithTag("radio_button" + i);
            radioButtons[i].setOnCheckedChangeListener(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
