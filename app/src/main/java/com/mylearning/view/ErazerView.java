package com.mylearning.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylearning.R;
import com.mylearning.activity.MainActivity;
import com.mylearning.entity.PostInfo;
import com.mylearning.utils.WindowUtils;

import java.util.List;


/**
 * Created by user on 2015/10/28.
 */
public class ErazerView extends View {
    private Context mContext;
    private Paint ePaint;
    private int wWidth, wHeight;
    private Path mPath;
    private Bitmap fgBitMap, bgBitmap;
    private Canvas mCanvas;
    private float preX, preY;


    public ErazerView(Context context) {
        this(context, null);
    }

    public ErazerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ErazerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();

    }

    public void init(){
        WindowManager wm = WindowUtils.getWindowManager(mContext);
        wWidth = wm.getDefaultDisplay().getWidth();
        wHeight = wm.getDefaultDisplay().getHeight();

        mPath = new Path();

        ePaint = new Paint();//擦除画笔
        ePaint.setAntiAlias(true);//抗锯齿
        ePaint.setDither(true);//抗抖动

        ePaint.setARGB(128, 255, 0, 0);//透明度0
        ePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));//混合模式为DST_IN
        ePaint.setStyle(Paint.Style.STROKE);//描边
        ePaint.setStrokeWidth(50);//描边宽度
        ePaint.setStrokeJoin(Paint.Join.ROUND);//设置路径结合处样式
        ePaint.setStrokeCap(Paint.Cap.ROUND);//笔触类型

        fgBitMap = Bitmap.createBitmap(wWidth, wHeight, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(fgBitMap);
        mCanvas.drawColor(0xFF808080);//绘制画布背景为中性灰

        bgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lml);//背景图
        bgBitmap = Bitmap.createScaledBitmap(bgBitmap, wWidth, wHeight, true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bgBitmap, 0, 0, null);
        canvas.drawBitmap(fgBitMap, 0, 0, null);
        mCanvas.drawPath(mPath, ePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mPath.moveTo(x, y);
                preX = x;
                preY = y;

                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - preX);
                float dy = Math.abs(y - preY);
                if( dx > 5 && dy > 5){
                    mPath.quadTo(preX, preY, (preX + x) / 2, (preY + y) / 2);
                    preX = x;
                    preY = y;
                }
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }
}
