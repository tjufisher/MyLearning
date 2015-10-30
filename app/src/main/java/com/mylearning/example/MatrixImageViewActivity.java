package com.mylearning.example;

import android.os.Bundle;

import com.mylearning.R;
import com.mylearning.base.BaseActivity;
import com.mylearning.view.MatrixImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by user on 2015/8/18.
 */
public class MatrixImageViewActivity extends BaseActivity {


    @InjectView(R.id.miv)
    MatrixImageView miv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metrix_image_view, LAYOUT_TYPE_HEADER);
        ButterKnife.inject(this);
        setHeaderBar("变换imageview");
        mContext = this;
    }


}
