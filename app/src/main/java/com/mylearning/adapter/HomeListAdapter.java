package com.mylearning.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.mylearning.R;
import com.mylearning.common.App;
import com.mylearning.entity.HomeListContent;
import com.mylearning.utils.LogUtils;
import com.mylearning.utils.StringUtils;
import com.mylearning.utils.VolleyUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by user on 2015/8/26.
 */
public class HomeListAdapter extends BaseAdapter {
    private Context mContext;
    private List<HomeListContent> dataList;
    private LayoutInflater inflater;
    private HomeListContent content;

    public HomeListAdapter(Context context) {
        this(context, null);
    }

    public HomeListAdapter(Context context, List<HomeListContent> list) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        dataList = new ArrayList<HomeListContent>();
        if( list != null){
            dataList = list;
        }
    }



    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_listview_home, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (dataList.get(position) != null) {
            content = dataList.get(position);
        }
        if (!StringUtils.isNullOrEmpty(content.imgUrl)) {
            VolleyUtils.createInstance(mContext).
                    doImageRequest(content.imgUrl, viewHolder.ivHomeListviewItem, R.drawable.loading);
        }
        if (!StringUtils.isNullOrEmpty(content.title)) {
            viewHolder.tvTitle.setText(content.title);
        }
        if (content.rate != null) {
            viewHolder.rb.setRating(content.rate / 2);
        }
        if (content.rateNum != null) {
            viewHolder.tvRateNum.setText("（" + content.rateNum + "）");
        }
        if (content.sellNum != null) {
            viewHolder.tvSellNum.setText("已售 " + content.sellNum + " 份");
        }

        viewHolder.tvSellPrice.setText("￥" + content.sellPrice);
//        距离计算
        BDLocation mLocation = App.getSelf().getmLocationUtils().getmLocation();
        LatLng latLngCurrent = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
        LatLng latLngObject = new LatLng(content.latitude, content.longitude);
        LogUtils.e("latitude",""+content.latitude);
        LogUtils.e("longitude",""+content.longitude);
        Double ditance = DistanceUtil.getDistance(latLngCurrent, latLngObject);
        DecimalFormat df = new DecimalFormat("#0.00");
        viewHolder.tvDistance.setText(df.format(ditance / 1000) + "KM");

        String strs = content.tag;
        String[] strArr = strs.split(" ");
//        StringUtils.setViewGone(viewHolder.tvTag1, viewHolder.tvTag2,viewHolder.tvTag3);

        if( !StringUtils.isNullOrEmpty(content.description)){
            viewHolder.tvDescription.setText(content.description);
        }

        return convertView;
    }


    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_listview_home.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @InjectView(R.id.iv_home_listview_item)
        ImageView ivHomeListviewItem;
        @InjectView(R.id.rl_home_listview_item)
        RelativeLayout rlHomeListviewItem;
        @InjectView(R.id.tv_title)
        TextView tvTitle;
        @InjectView(R.id.rb)
        RatingBar rb;
        @InjectView(R.id.tv_rate_num)
        TextView tvRateNum;
        @InjectView(R.id.tv_sell_num)
        TextView tvSellNum;
        @InjectView(R.id.tv_sell_price)
        TextView tvSellPrice;
        @InjectView(R.id.ll_rate)
        RelativeLayout llRate;
        @InjectView(R.id.iv_distance)
        ImageView ivDistance;
        @InjectView(R.id.tv_distance)
        TextView tvDistance;
        @InjectView(R.id.tv_tag1)
        TextView tvTag1;
        @InjectView(R.id.tv_tag2)
        TextView tvTag2;
        @InjectView(R.id.tv_tag3)
        TextView tvTag3;
        @InjectView(R.id.ll_tag)
        LinearLayout llTag;
        @InjectView(R.id.rl_tag)
        RelativeLayout rlTag;
        @InjectView(R.id.tv_description)
        TextView tvDescription;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    public void update(List<HomeListContent> list){
        dataList = list;
        this.notifyDataSetChanged();
    }
}
