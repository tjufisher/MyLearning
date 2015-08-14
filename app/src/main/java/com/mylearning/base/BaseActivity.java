package com.mylearning.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mylearning.R;
import com.mylearning.utils.StringUtils;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @return [返回类型说明]
 * @exception/throws [违例类型] [违例说明]
 * @see [类、类#方法、类#成员]
 */
public class BaseActivity extends Activity implements View.OnClickListener{
    protected String TAG = this.getClass().getSimpleName();
    protected final int LAYOUT_TYPE_NORMAL = 0X00000001;
    protected final int LAYOUT_TYPE_HEADER = 0x00000002;

    protected Context mContext;

    private RelativeLayout header_bar;
    private Button btn_back, btn_right1, btn_right2;
    private ImageView img_right1, img_right2;
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
                RelativeLayout v = new RelativeLayout(this);
                LayoutInflater layoutInflater = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                RelativeLayout.LayoutParams params;

                header_bar = (RelativeLayout)layoutInflater.inflate(R.layout.header_bar, null);
                btn_back = (Button)header_bar.findViewById(R.id.btn_back);
                btn_right1 = (Button)header_bar.findViewById(R.id.btn_right1);
                btn_right2 = (Button)header_bar.findViewById(R.id.btn_right2);
                img_right1 = (ImageView)header_bar.findViewById(R.id.img_right1);
                img_right2 = (ImageView)header_bar.findViewById(R.id.img_right2);
                tv_header = (TextView)header_bar.findViewById(R.id.tv_header);
                ll_header_right = (LinearLayout)header_bar.findViewById(R.id.ll_header_right);
                params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                v.addView(header_bar, params);

                View view = layoutInflater.inflate(layoutResId, null);
                params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
                params.addRule(RelativeLayout.BELOW, R.id.header_bar);
                v.addView(view, params);

                setContentView(v);
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

    public void setHeaderBar(String title){
        setHeaderBar(title, "");
    }

    public void setHeaderBar(String title, String right1){
        tv_header.setText(title);
        if(!StringUtils.isNullOrEmpty(right1)){
            StringUtils.setViewVisible(btn_right1);
            btn_right1.setText(right1);
        }

    }

    public void setHeaderBar(String title, int right1){
        tv_header.setText(title);
        if(right1 != 0){
            StringUtils.setViewVisible(img_right1);
            img_right1.setBackgroundResource(right1);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                exit();
                break;
            case R.id.btn_right1:
            case R.id.img_right1:
                handleHeaderEvent1();
                break;
            case R.id.btn_right2:
            case R.id.img_right2:
                handleHeaderEvent2();
                break;
            default:
                break;
        }
    }

    public void exit() {
        onKeyDown(KeyEvent.KEYCODE_BACK, new KeyEvent(KeyEvent.KEYCODE_BACK, KeyEvent.ACTION_DOWN));
    }

    public void handleHeaderEvent1(){}
    public void handleHeaderEvent2(){}
}
