package com.mylearning.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.baidu.mapapi.SDKInitializer;
import com.mylearning.R;
import com.mylearning.base.BaseActivity;
import com.mylearning.fragement.HomeFragement;
import com.mylearning.fragement.LocationFragement;
import com.mylearning.fragement.MyFragement;
import com.mylearning.fragement.NoteFragement;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener{
    @InjectView(R.id.fl_container)
    FrameLayout flContainer;
    @InjectView(R.id.rg_navigation)
    RadioGroup rgNavigation;


    private Context mContext;
    private RadioButton[] radioButtons;
    private Fragment[] tabs ;
    private int lastTabIndex = 0;
    private final String TAG_CURRENT_FRAGMENT = "MainActivity.current_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());


        setContentView(R.layout.activity_main, LAYOUT_TYPE_HEADER);
        ButterKnife.inject(this);
        setHeaderBar("欢迎页面", R.drawable.share);
        mContext = this;
        initDatas();

        lastTabIndex = 0;
        radioButtons[0].setSelected(true);
        getFragmentManager().beginTransaction().add(flContainer.getId(), tabs[0], TAG_CURRENT_FRAGMENT + 0)
                .commitAllowingStateLoss();

    }

    public void initDatas(){
        tabs = new Fragment[4];
        tabs[0] = new HomeFragement();
        tabs[1] = new LocationFragement();
        tabs[2] = new NoteFragement();
        tabs[3] = new MyFragement();
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
        if (isChecked) {
            FragmentTransaction ft =  getFragmentManager().beginTransaction();
            for (int i = 0; i < radioButtons.length; i++) {
                if (buttonView == radioButtons[i]) {
                   if(i == lastTabIndex){//当前页

                   }else{
                       ft.hide(tabs[lastTabIndex]);
                       lastTabIndex = i;
                       radioButtons[i].setSelected(true);
                       Fragment f = getFragmentManager().findFragmentByTag(TAG_CURRENT_FRAGMENT + i);
                       if( null == f){
                           ft.add(flContainer.getId(), tabs[i], TAG_CURRENT_FRAGMENT + i);
                       }else{
                           ft.show(tabs[i]);
                       }
                       ft.commitAllowingStateLoss();
                   }
                } else {
                    if (radioButtons[i] != null){
                        radioButtons[i].setSelected(false);
                    }
                }
            }
        }
    }
}
