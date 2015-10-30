package com.mylearning.example;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.mylearning.R;
import com.mylearning.base.BaseActivity;
import com.mylearning.entity.PostInfo;
import com.mylearning.utils.LogUtils;
import com.mylearning.view.PostView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by user on 2015/8/18.
 */
public class PostViewActivity extends BaseActivity {


    @InjectView(R.id.lv)
    ListView lv;
    private int num;
    private List<PostInfo> postInfoList;
    List<List<PostInfo>> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list_view, LAYOUT_TYPE_HEADER);
        ButterKnife.inject(this);
        setHeaderBar("盖楼");
        mContext = this;
        initData();
        lv.setDividerHeight(5);
        lv.setCacheColorHint(getResources().getColor(R.color.gray68));
        lv.setSelector(getResources().getDrawable(R.drawable.btn_gray_bg));
        lv.setAdapter(new MyAdapter());

    }

    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_post_view, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.pv.setData(datas.get(position));

            return convertView;
        }


        /**
         * This class contains all butterknife-injected Views & Layouts from layout file 'activity_post_view.xml'
         * for easy to all layout elements.
         *
         * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
         */
        public class ViewHolder {
            @InjectView(R.id.pv)
            PostView pv;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }

    public void initData() {
        datas = new ArrayList<List<PostInfo>>();

        for (int i = 0; i < 15; i++) {
            postInfoList = new ArrayList<PostInfo>();
            num = (int) (Math.random() * 10) + 1;//产生1-10随机数
            LogUtils.e("Random num", num + "");
            for (int j = 1; j <= num; j++) {
                PostInfo postInfo = new PostInfo();
                postInfo.header = "";
                postInfo.nickname = "匿名" + j;
                postInfo.zan = j + "楼";
                postInfo.position = "北京网友";
                postInfo.postTime = "刚刚";
                postInfo.content = "我是" + j + "楼！！！！";
                postInfoList.add(postInfo);
            }
            datas.add(postInfoList);
        }

    }


}
