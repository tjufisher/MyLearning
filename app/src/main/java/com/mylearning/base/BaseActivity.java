package com.mylearning.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mylearning.R;

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
public class BaseActivity extends Activity {


    private final int LAYOUT_TYPE_NORMAL = 0X00000001;
    private final int LAYOUT_TYPE_HEADER = 0x00000002;

    private Context mContext;

    private RelativeLayout header_bar;
    private Button btn_back, btn_right2, btn_right1;
    private TextView tv_header;
    private LinearLayout ll_header_right;

    public BaseActivity() {
        mContext = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initView(int layoutResId, int type){
        switch (type){
            case LAYOUT_TYPE_NORMAL:
                super.setContentView(layoutResId);
                break;
            case LAYOUT_TYPE_HEADER:
                LayoutInflater layoutInflater = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View view = layoutInflater.inflate(layoutResId, null);
//                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                        RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);

                header_bar = (RelativeLayout)layoutInflater.inflate(R.layout.header_bar, null);
                btn_back = (Button)header_bar.findViewById(R.id.btn_back);
                btn_right2 = (Button)header_bar.findViewById(R.id.btn_right2);
                btn_right1 = (Button)header_bar.findViewById(R.id.btn_right1);
                tv_header = (TextView)header_bar.findViewById(R.id.tv_header);
                ll_header_right = (LinearLayout)header_bar.findViewById(R.id.ll_header_right);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                params.addRule(RelativeLayout.BELOW, R.id.header_bar);
                header_bar.addView(view, params);

                setContentView(header_bar);

                break;
            default:
                break;
        }
    }

    @Override
    public void setContentView(int layoutResID) {

        initView(layoutResID, LAYOUT_TYPE_NORMAL);
    }

    public void setContentView(int layoutResID, int type){
        initView(layoutResID, type);
    }
}
