package com.mylearning.fragement;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mylearning.R;
import com.mylearning.utils.UtilsLog;
import com.mylearning.view.VerifyTextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class HomeFragement extends Fragment {
    private Context mContext;
    @InjectView(R.id.et)
    EditText et;
    @InjectView(R.id.vtv)
    VerifyTextView vtv;
    @InjectView(R.id.btn)
    Button btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        UtilsLog.e(getClass().getName(), "create view");
        View view = inflater.inflate(R.layout.fragement_home, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
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
