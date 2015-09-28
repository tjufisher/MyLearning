package com.mylearning.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.mylearning.R;
import com.mylearning.utils.StringUtils;

/**
 * Created by user on 2015/8/17.
 */
public class BaseFragement extends Fragment {
    protected Context mContext;

    public BaseFragement(){
        mContext = getActivity();
    }

    protected void toast(String msg) {
        if (StringUtils.isNullOrEmpty(msg)) {
            return;
        }
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
    }

    /**
     * 启动activity带有动画切换
     *
     * @param intent
     * @param requestCode
     */
    protected void startActivityForResultAndAnima(Intent intent, int requestCode) {
        startActivityForResultAndAnima(intent, requestCode, null);
    }

    /**
     * 启动activity带有动画切换
     *
     * @param intent
     */
    protected void startActivityForAnima(Intent intent) {
        startActivityForAnima(intent, null);
    }

    /**
     * 启动activity带有动画切换
     *
     * @param intent
     * @param requestCode
     * @param parentActivity
     */
    protected void startActivityForResultAndAnima(Intent intent,
                                                  int requestCode, Activity parentActivity) {
        if (intent != null) {
            if (parentActivity != null) {
                parentActivity.startActivityForResult(intent, requestCode);
                parentActivity.overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            } else {
                startActivityForResult(intent, requestCode);
                getActivity().overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        }
    }

    /**
     * 启动activity带有动画切换
     *
     * @param intent
     * @param parentActivity
     */
    protected void startActivityForAnima(Intent intent, Activity parentActivity) {
        if (intent != null) {
            if (parentActivity != null) {
                parentActivity.startActivity(intent);
                parentActivity.overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            } else {
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        }
    }

}
