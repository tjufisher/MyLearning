package com.mylearning.example;

import android.os.Bundle;

import com.mylearning.R;
import com.mylearning.base.BaseActivity;
import com.mylearning.view.FontView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by user on 2015/8/18.
 */
public class FontViewActivity extends BaseActivity {


    @InjectView(R.id.fv)
    FontView fv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font_view, LAYOUT_TYPE_HEADER);
        ButterKnife.inject(this);
        setHeaderBar("字符");
        mContext = this;
    }


}
