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
public class VerifyTextViewActivity extends BaseActivity {
    @InjectView(R.id.et)
    EditText et;
    @InjectView(R.id.vtv)
    VerifyTextView vtv;
    @InjectView(R.id.btn)
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_text_view, LAYOUT_TYPE_HEADER);
        ButterKnife.inject(this);
        setHeaderBar("验证码");
        mContext = this;
    }

    @OnClick(R.id.btn)
    public void verify(){
        if(vtv.getmCode().trim().equals(et.getText().toString().trim())){
            Toast.makeText(mContext, "right", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(mContext, "wrong", Toast.LENGTH_SHORT).show();
        }
    }
}
