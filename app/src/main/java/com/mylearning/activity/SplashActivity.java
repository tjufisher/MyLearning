package com.mylearning.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.mylearning.R;
import com.mylearning.base.BaseActivity;
import com.mylearning.common.App;
import com.mylearning.common.Constants;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @return [返回类型说明]
 * @exception/throws [违例类型] [违例说明]
 * @see [类、类#方法、类#成员]
 */
public class SplashActivity extends BaseActivity{
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Constants.IS_HOME = false;
        mContext = this;
        setContentView(R.layout.activity_welcome);

        App.getSelf().init();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent  = new Intent();
                intent.setClass(mContext, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}
