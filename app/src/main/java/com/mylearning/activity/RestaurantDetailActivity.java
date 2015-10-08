package com.mylearning.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mylearning.R;
import com.mylearning.base.BaseActivity;
import com.mylearning.utils.FileUtils;
import com.mylearning.utils.IntentUtils;
import com.mylearning.utils.LogUtils;
import com.mylearning.view.MiddlePopWindow;
import com.mylearning.view.RoundImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class RestaurantDetailActivity extends BaseActivity {



    private Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_base_info, LAYOUT_TYPE_HEADER);
        mContext = this;
        ButterKnife.inject(this);
        setHeaderBar("欢迎页面", R.drawable.share);
    }



}
