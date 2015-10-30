package com.mylearning.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.mylearning.R;
import com.mylearning.utils.LogUtils;
import com.mylearning.utils.WindowUtils;


/**
 * Created by user on 2015/10/28.
 */
public class FontView extends View {
    private Context mContext;
    private Paint mPaint, linePaint;
    private int wWidth, wHeight;
    private String text = "ap爱哥ξτβбпшㄎㄊěǔぬも┰┠№＠↓";
    private Paint.FontMetrics mFontMetrics;



    public FontView(Context context) {
        this(context, null);
    }

    public FontView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();

    }

    public void init(){
        WindowManager wm = WindowUtils.getWindowManager(mContext);
        wWidth = wm.getDefaultDisplay().getWidth();
        wHeight = wm.getDefaultDisplay().getHeight();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(20);

        mFontMetrics = mPaint.getFontMetrics();

        LogUtils.d("Aige", "ascent：" + mFontMetrics.ascent);
        LogUtils.d("Aige", "top：" + mFontMetrics.top);
        LogUtils.d("Aige", "leading：" + mFontMetrics.leading);
        LogUtils.d("Aige", "descent：" + mFontMetrics.descent);
        LogUtils.d("Aige", "bottom：" + mFontMetrics.bottom);
        LogUtils.d("Aige", "fontSize：" + mPaint.getTextSize());

        DisplayMetrics dm = mContext.getApplicationContext().getResources().getDisplayMetrics();
        LogUtils.d("Aige", "density：" + dm.density);
        LogUtils.d("Aige", "densityDpi：" + dm.densityDpi);
        LogUtils.d("Aige", "widthPixels：" + dm.widthPixels);
        LogUtils.d("Aige", "widthPixels：" + dm.heightPixels);

        LogUtils.d("Aige", "wWidth：" + wWidth);
        LogUtils.d("Aige", "wHeight：" + wHeight);

        linePaint = new Paint();
        linePaint.setColor(Color.RED);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(1);




    }

    @Override
    protected void onDraw(Canvas canvas) {

        LogUtils.d("Aige", "canvas width：" + canvas.getWidth());
        LogUtils.d("Aige", "canvas height：" + canvas.getHeight());
        float x = (canvas.getWidth() - mPaint.measureText(text)) / 2;
        float y = (canvas.getHeight() -(-mPaint.getFontMetrics().top + mPaint.getFontMetrics().bottom)) /2;
        canvas.drawText(text,x , y, mPaint);

        canvas.drawLine(x, y, x + mPaint.measureText(text), y, linePaint);
    }
}
