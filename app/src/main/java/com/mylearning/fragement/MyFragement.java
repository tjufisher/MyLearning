package com.mylearning.fragement;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylearning.R;
import com.mylearning.utils.LogUtils;

/**
 * Created by user on 2015/8/17.
 */
public class MyFragement extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.e(getClass().getName(), "create view");
        View view = inflater.inflate(R.layout.fragement_my, container,false);
        return view;
    }
}
