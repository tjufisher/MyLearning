package com.mylearning.example;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mylearning.R;
import com.mylearning.base.BaseActivity;
import com.mylearning.view.VerifyTextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by user on 2015/8/18.
 */
public class RoundImageViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_image_view, LAYOUT_TYPE_HEADER);
        setHeaderBar("圆形图片");
        mContext = this;
    }

}
