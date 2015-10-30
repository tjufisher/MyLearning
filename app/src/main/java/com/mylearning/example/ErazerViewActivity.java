package com.mylearning.example;

import android.os.Bundle;

import com.mylearning.R;
import com.mylearning.base.BaseActivity;
import com.mylearning.view.ErazerView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by user on 2015/8/18.
 */
public class ErazerViewActivity extends BaseActivity {


    @InjectView(R.id.ev)
    ErazerView ev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erazer_view, LAYOUT_TYPE_HEADER);
        ButterKnife.inject(this);
        setHeaderBar("擦除");
        mContext = this;
    }


}
