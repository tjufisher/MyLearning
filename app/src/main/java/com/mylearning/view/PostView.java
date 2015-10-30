package com.mylearning.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylearning.R;
import com.mylearning.entity.PostInfo;

import java.util.List;


/**
 * Created by user on 2015/10/28.
 */
public class PostView extends LinearLayout {
    private LayoutInflater layoutInflater;
    private RoundImageView riv_header;
    private TextView tv_nickname, tv_zan, tv_position, tv_post_time, tv_content;
    private FloorView fv;

    public PostView(Context context) {
        this(context, null);
    }

    public PostView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PostView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        layoutInflater = LayoutInflater.from(context);
        initView();
    }

    public void initView(){
        View view = layoutInflater.inflate(R.layout.postview, this);
        riv_header = (RoundImageView) view.findViewById(R.id.riv_header);
        tv_nickname = (TextView) view.findViewById(R.id.tv_nickname);
        tv_zan = (TextView) view.findViewById(R.id.tv_zan);
        tv_position = (TextView) view.findViewById(R.id.tv_position);
        tv_post_time = (TextView) view.findViewById(R.id.tv_post_time);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        fv = (FloorView) view.findViewById(R.id.fv);
    }

    public void setData(List<PostInfo> list){

        if( list == null || list.size() == 0){
            return;
        }
        PostInfo postInfo = list.get(0);
        setPostInfo(postInfo);
        if( list.size() == 1){//只有楼主
            fv.setVisibility(View.GONE);
            return;
        }else{
            fv.setVisibility(View.VISIBLE);
//            list.remove(0);
            fv.setData(list);
        }
    }

    public void setPostInfo(PostInfo postInfo){
        riv_header.setBackgroundResource(R.drawable.lml);
        tv_nickname.setText(postInfo.nickname);
        tv_zan.setText(postInfo.zan);
        tv_position.setText(postInfo.position);
        tv_post_time.setText(postInfo.postTime);
        tv_content.setText(postInfo.content);
    }


}
