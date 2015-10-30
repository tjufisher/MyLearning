package com.mylearning.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylearning.R;
import com.mylearning.entity.PostInfo;

import java.util.List;




/**
 * Created by user on 2015/10/28.
 */
public class FloorView extends LinearLayout {
    private Context mContext;
    private LayoutInflater layoutInflater;
    private TextView tv_nickname, tv_zan, tv_position, tv_post_time, tv_content;
    private LinearLayout ll_container;
    private Drawable drawable;
    private int length;

    public FloorView(Context context) {
        this(context, null);
    }

    public FloorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
        setOrientation(LinearLayout.VERTICAL);
        //获取背景Drawable的资源文件
        drawable = context.getResources().getDrawable(R.drawable.floor_view_shape);

    }

    public void setData(List<PostInfo> list) {

        if (list == null || list.size() < 2) {
            return;
        }
        length = list.size();
        if( length <= 6){
            showAllViews(list);
        }else{
            showAllViewsWithHidden(list);
        }

    }

    public void showAllViews(List<PostInfo> list){
        removeAllViews();
        for( int i = 1; i < length; i++){//楼主不增加
            View v = getView(list, i, length);
            addView(v);
        }
    }

    public void showAllViewsWithHidden(final List<PostInfo> list){
        removeAllViews();
        View v = getView(list, 1, length);
        addView(v);

        View v1 = getView(list, 2, length);
        addView(v1);

        TextView tv = new TextView(mContext);
        tv.setText("显示隐藏楼层");
        tv.setTextSize(30);
        tv.setTextColor(getResources().getColor(R.color.white));
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundColor(getResources().getColor(R.color.gray68));
        addView(tv);


        View v_end = getView(list, length - 1, length);
        addView(v_end);

        tv.setClickable(true);
        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showAllViews(list);
            }
        });

    }

    public View getView(List<PostInfo> list, int index, int length){
        PostInfo postInfo = list.get(index);
        View view = layoutInflater.inflate(R.layout.floorview, null);
        ((TextView) view.findViewById(R.id.tv_nickname)).setText(postInfo.nickname);
        ((TextView) view.findViewById(R.id.tv_zan)).setText(postInfo.zan);
        ((TextView) view.findViewById(R.id.tv_position)).setText(postInfo.position);
        ((TextView) view.findViewById(R.id.tv_post_time)).setText(postInfo.postTime);
        ((TextView) view.findViewById(R.id.tv_content)).setText(postInfo.content);
        ll_container = (LinearLayout) view.findViewById(R.id.ll_container);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        int marginIndex = length - index;
        int margin = marginIndex * 3;

        params.setMargins(margin, 0, margin, 0);
        view.setLayoutParams(params);

        return view;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        /*
        在FloorView绘制子控件前先绘制层叠的背景图片
         */
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View view = getChildAt(i);
            drawable.setBounds(view.getLeft(), view.getLeft(), view.getRight(), view.getBottom());
            drawable.draw(canvas);
        }
        super.dispatchDraw(canvas);
    }


}
