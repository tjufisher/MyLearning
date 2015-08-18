package com.mylearning.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.mylearning.R;

import java.util.Random;

/**
 * TextView can generate random number and verify the number
 * setNumLength() modify length of random number(default length is 4)
 * getmCode() get the random number string
 * Created by fisher on 2015/8/17.
 */
public class VerifyTextView extends TextView implements View.OnClickListener{
    private Context mContext;
    private Paint mPaint;
    private Rect mBound;
    private String mCode = "1234";

    private int numLength = 4;

    private int bgColor;
    private int fontColor;
    private float fontSize;


    public VerifyTextView(Context context) {
        this(context, null);
    }

    public VerifyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);


        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.VerifyTextView);
        numLength = array.getInt(R.styleable.VerifyTextView_numLength, 4);
        bgColor = array.getColor(R.styleable.VerifyTextView_android_background, Color.parseColor("#fea64b"));
        fontColor = array.getColor(R.styleable.VerifyTextView_android_textColor, Color.parseColor("#333333"));
        fontSize = array.getDimension(R.styleable.VerifyTextView_android_textSize, 14);

        mCode = randomNum();

        mPaint = new Paint();
        mBound = new Rect();
        mPaint.setTextSize(fontSize);
        mPaint.getTextBounds(mCode, 0, mCode.length(), mBound);

        array.recycle();

        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(bgColor);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(fontColor);
        canvas.drawText(mCode, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height ;
        if (widthMode == MeasureSpec.EXACTLY)
        {
            width = widthSize;
        } else
        {
            mPaint.setTextSize(fontSize);
            mPaint.getTextBounds(mCode, 0, mCode.length(), mBound);
            float textWidth = mBound.width();
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY)
        {
            height = heightSize;
        } else
        {
            mPaint.setTextSize(fontSize);
            mPaint.getTextBounds(mCode, 0, mCode.length(), mBound);
            float textHeight = mBound.height();
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            height = desired;
        }

        setMeasuredDimension(width, height);
    }


    @Override
    public void onClick(View v) {
        mCode = randomNum();
        postInvalidate();
    }

    public String randomNum(){
        StringBuilder sb = new StringBuilder();
        Random random =  new Random();
        for( int i = 0; i < numLength; i++){
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public String getmCode(){
        return mCode;
    }

    public void setNumLength(int numLength) {
        this.numLength = numLength;
    }
}



