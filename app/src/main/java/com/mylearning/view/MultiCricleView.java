package com.mylearning.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.mylearning.R;
import com.mylearning.utils.LogUtils;
import com.mylearning.utils.WindowUtils;


/**
 * Created by user on 2015/10/28.
 */
public class MultiCricleView extends View {
    private static final float CIRCLE_RADIUS = 3F / 32F;
    private static final float BIG_CIRCLE_RADIUS = 4F / 32F;
    private static final float CIRCLE_STROKE_WIDTH = 1F / 256F;
    private Context mContext;
    private int wWidth, wHeight;
    private Paint mPaint, textPaint;
    private int size = 0;
    private float circleRadius;
    private float bigCircleRadius;
    private float circleStrokeWidth;
    private float cx, cy;
    private float offY;



    public MultiCricleView(Context context) {
        this(context, null);
    }

    public MultiCricleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiCricleView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        mPaint.setDither(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint.setColor(getResources().getColor(R.color.black));
        textPaint.setTextSize(30);
        textPaint.setTextAlign(Paint.Align.CENTER);

        offY = (textPaint.ascent() + textPaint.descent()) / 2;

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        size = w;
        circleRadius = size * CIRCLE_RADIUS;
        bigCircleRadius = size * BIG_CIRCLE_RADIUS;
        circleStrokeWidth = size * CIRCLE_STROKE_WIDTH;

        cx = size / 2;
        cy = h / 2 + circleRadius;


        mPaint.setStrokeWidth(circleStrokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(getResources().getColor(R.color.white_01));

        canvas.drawCircle(cx, cy, circleRadius, mPaint);
        canvas.drawText("Hello", cx, cy - offY, textPaint);

        canvas.drawLine(cx, cy + circleRadius, cx, cy + 2 * circleRadius, mPaint);
        canvas.drawCircle(cx, cy + 3 * circleRadius, circleRadius, mPaint);
        canvas.drawText("F", cx, cy + 3 * circleRadius - offY, textPaint);


        drawLeftTop(canvas);
        drawRightTop(canvas);
        drawLeftBottom(canvas);
        drawRightBottom(canvas);
    }

    public void drawLeftTop(Canvas canvas){
        canvas.save();
        canvas.translate(cx, cy);
        canvas.rotate(-30);

        canvas.drawLine(0, -circleRadius, 0, -2 * circleRadius, mPaint);
        canvas.drawCircle(0, -3 * circleRadius, circleRadius, mPaint);
        canvas.drawText("A", 0, -3 * circleRadius - offY, textPaint);

        canvas.drawLine(0, -4 * circleRadius, 0, -5 * circleRadius, mPaint);
        canvas.drawCircle(0, -6 * circleRadius, circleRadius, mPaint);
        canvas.drawText("B", 0, -6 * circleRadius - offY, textPaint);
        canvas.restore();
    }

    public void drawRightTop(Canvas canvas){
        canvas.save();
        canvas.translate(cx, cy);
        canvas.rotate(30);

        canvas.drawLine(0, -circleRadius, 0, -2 * circleRadius, mPaint);
        canvas.drawCircle(0, -3 * circleRadius, circleRadius, mPaint);
        canvas.drawText("C", 0, -3 * circleRadius - offY, textPaint);

        canvas.drawLine(0, -4 * circleRadius, 0, -5 * circleRadius, mPaint);
        canvas.drawCircle(0, -6 * circleRadius, circleRadius, mPaint);
        canvas.drawText("D", 0, -6 * circleRadius - offY, textPaint);

        // 画弧形
        drawTopRightArc(canvas);

        canvas.restore();
    }

    public void drawTopRightArc(Canvas canvas){
        canvas.save();

        canvas.translate(0, -3 * circleRadius);
        RectF oval = new RectF(-bigCircleRadius, -bigCircleRadius, bigCircleRadius, bigCircleRadius);
        canvas.drawArc(oval, -60, -60, false, mPaint);

        drawArcText(canvas);

        canvas.restore();
    }

    public void drawArcText(Canvas canvas){
        Paint p = new Paint(textPaint);
        p.setTextSize(10);
        canvas.drawText("MidArc", 0, -(bigCircleRadius + 5), p);

        canvas.save();
        canvas.rotate(-30);
        canvas.drawText("LeftArc", 0, -(bigCircleRadius + 5), p);
        canvas.restore();

        canvas.save();
        canvas.rotate(30);
        canvas.drawText("RightArc", 0, -(bigCircleRadius + 5), p);
        canvas.restore();
    }

    public void drawLeftBottom(Canvas canvas){
        canvas.save();
        canvas.translate(cx, cy);
        canvas.rotate(-135);

        canvas.drawLine(0, -circleRadius, 0, -2 * circleRadius, mPaint);
        canvas.drawCircle(0, -3 * circleRadius, circleRadius, mPaint);
        canvas.drawText("G", 0, -3 * circleRadius - offY, textPaint);
        canvas.restore();
    }

    public void drawRightBottom(Canvas canvas){
        canvas.save();
        canvas.translate(cx, cy);
        canvas.rotate(135);

        canvas.drawLine(0, -circleRadius, 0, -2 * circleRadius, mPaint);
        canvas.drawCircle(0, -3 * circleRadius, circleRadius, mPaint);
        canvas.drawText("E", 0, -3 * circleRadius - offY, textPaint);
        canvas.restore();
    }
}
