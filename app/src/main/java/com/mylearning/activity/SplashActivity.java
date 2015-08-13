package com.mylearning.activity;

import android.os.Bundle;

import com.mylearning.R;
import com.mylearning.base.BaseActivity;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @param [参数1] [参数1说明]
 * @param [参数2] [参数2说明]
 * @return [返回类型说明]
 * @exception/throws [违例类型] [违例说明]
 * @see [类、类#方法、类#成员]
 */
public class SplashActivity extends BaseActivity{
    private final int LAYOUT_TYPE_NORMAL = 0X00000001;
    private final int LAYOUT_TYPE_HEADER = 0x00000002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main, LAYOUT_TYPE_HEADER);
    }
}
