package com.mylearning.fragement;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mylearning.R;
import com.mylearning.activity.MainActivity;
import com.mylearning.adapter.HomeListAdapter;
import com.mylearning.common.Constants;
import com.mylearning.entity.AdInfo;
import com.mylearning.entity.HomeListContent;
import com.mylearning.entity.QueryBeanAndList;
import com.mylearning.entity.Result;
import com.mylearning.utils.HttpApi;
import com.mylearning.utils.LogUtils;
import com.mylearning.utils.StringUtils;
import com.mylearning.utils.VolleyUtils;
import com.mylearning.view.JustifyEditView;
import com.mylearning.view.WrapContentViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class HomeFragement extends Fragment {
    //    @InjectView(R.id.et)
//    EditText et;

    @InjectView(R.id.ll_imgswitch)
    LinearLayout llImgswitch;
    @InjectView(R.id.et)
    JustifyEditView et;
    @InjectView(R.id.vp_ad)
    WrapContentViewPager vpAd;
    @InjectView(R.id.btn)
    Button btn;


    private Context mContext;
    private ArrayList<String> adList;
    private GetHomeAdTask getHomeAdTask;
    private int[] home_ad = {R.drawable.home_ad};
    private int screenWidth;
    private ImageView currentImg;// 当前选中的小圆点
    private Boolean vpRecycle = false;


    private GetHomeListTask getHomeListTask;
    private ListView lv;
    private String[] mStrings = {"a", "b", "c", "d", "e", "f", "g", "a", "b", "c",
            "d", "e", "f", "g", "a", "b", "c", "d", "e", "f", "g"};
    private ArrayAdapter<String> mAdapter;
    private LinkedList<String> mListItems;
    private HomeListAdapter homeListAdapter;

    private List<HomeListContent> homeContentList;
    private ShowInMap mCallback;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.e(getClass().getName(), "create view");
        View view = inflater.inflate(R.layout.fragement_home, null);
        ButterKnife.inject(this, view);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;

        lv = new ListView(mContext);
        lv.addHeaderView(view);
        initDatas();
        registerLister();

        return lv;
    }


    public void initDatas() {
        homeContentList = new ArrayList<HomeListContent>();
        adList = new ArrayList<String>();
        //获取首页ViewPager广告信息
        if (getHomeAdTask != null) {
            getHomeAdTask.cancel(true);
        }
        getHomeAdTask = new GetHomeAdTask();
        getHomeAdTask.execute();
        //获取首页listview信息
        if (getHomeListTask != null) {
            getHomeListTask.cancel(true);
        }
        getHomeListTask = new GetHomeListTask();
        getHomeListTask.execute();
    }

    public void registerLister() {
//        mListItems = new LinkedList<String>();
//        mListItems.addAll(Arrays.asList(mStrings));
//
//        MyListAdapter myListAdapter = new MyListAdapter(mListItems);
//        lv.setAdapter(myListAdapter);
        homeListAdapter = new HomeListAdapter(mContext);
        lv.setAdapter(homeListAdapter);

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    int adCount = adList.size();
                    int currentAd = vpAd.getCurrentItem();
                    int nextAd = (currentAd + 1) % adCount;
                    vpAd.setCurrentItem(nextAd);
                    changePosition(nextAd);
                    this.sendEmptyMessageDelayed(1, 2000);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 设置广告viewpager信息
     *
     * @param result
     */
    public void setAdData(ArrayList<String> result) {
        if (result == null) {
            result = new ArrayList<String>();
        }
        if (null != result && result.size() > 0) {
            initImgNum(result.size());
            vpAd.setAdapter(new HomeAdViewPagerAdapter(result));
            if (result.size() > 1) {
                vpAd.setOnPageChangeListener(onPageChangeListener);
//                vpAd.startAutoScroll(PHOTO_CHANGE_TIME);
//                vpAd.setInterval(PHOTO_CHANGE_TIME);
//                vpAd.setScrollDurationFactor(2);
            }
        } else {
            initImgNum(home_ad.length);
            vpAd.setAdapter(new HomeAdViewPagerAdapter(null));
        }
    }

    public void initImgNum(int num) {
        llImgswitch.removeAllViews();
        if (num == 1) {
            return;
        } else {
            for (int i = 0; i < num; i++) {
                ImageView img = new ImageView(mContext);
                img.setImageResource(R.drawable.ad_switcher_btn);
                if (screenWidth <= 480) {
                    img.setPadding(10, 0, 0, 0);

                } else {
                    img.setPadding(25, 0, 0, 0);
                }
                llImgswitch.addView(img);
            }
            changePosition(0);
        }
    }

    public void changePosition(int position) {
        if (currentImg != null)
            currentImg.setImageResource(R.drawable.ad_switcher_btn);

        currentImg = (ImageView) llImgswitch.getChildAt(position);
        if (currentImg == null)
            return;
        currentImg.setImageResource(R.drawable.ad_switcher_btn_selected);
    }


    public class HomeAdViewPagerAdapter extends PagerAdapter {
        private ArrayList<String> adList;

        public HomeAdViewPagerAdapter(ArrayList<String> adList) {
            this.adList = adList;
        }

        @Override
        public int getCount() {
            if (adList != null && adList.size() > 0) {
                return adList.size();
            } else {
                return home_ad.length;
            }
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @SuppressLint("NewApi")
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(mContext);
//            NetworkImageView imageView = new NetworkImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            if (adList != null && adList.size() > 0) {
                if (!StringUtils.isNullOrEmpty(adList.get(position))) {
                    VolleyUtils.createInstance(mContext).
                            doImageRequest(adList.get(position), imageView, R.drawable.home_ad);

                }
            } else {
                int resId = 0;

                Bitmap mbigBitmap = BitmapFactory.decodeResource(
                        mContext.getResources(), R.drawable.home_ad);

                if (Build.VERSION.SDK_INT >= 17) {
                    imageView.setBackground(new BitmapDrawable(mContext
                            .getResources(), mbigBitmap));
                } else {
                    imageView.setBackgroundDrawable(new BitmapDrawable(
                            mbigBitmap));
                }
            }
            imageView.setTag(position);
//            imageView.setOnClickListener(listener);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            // 此方法保持空，不要删除，默写版本viewpager会调用此父类方法，抛出异常
            try {
                ((ViewPager) container).removeView((View) object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            changePosition(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    public class MyListAdapter extends BaseAdapter {
        List<String> strList;

        public MyListAdapter(List list) {
            strList = list;
        }

        @Override
        public int getCount() {
            return strList.size();
        }

        @Override
        public Object getItem(int position) {
            return strList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_view, null);
                viewHolder.tv = (TextView) convertView.findViewById(R.id.tv);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv.setText(strList.get(position));

            return convertView;
        }

        public class ViewHolder {
            TextView tv;
        }
    }

    public class GetHomeAdTask extends AsyncTask<Void, Void, QueryBeanAndList<AdInfo, Result>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected QueryBeanAndList<AdInfo, Result> doInBackground(Void... params) {
            Map<String, String> map = new HashMap<String, String>();
            map.put(Constants.MESSAGE_NAME, "getHomeVpAd");
            map.put("category", "home_vp_ad");
            try {
                return HttpApi.getQuery(map, AdInfo.class, Result.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(QueryBeanAndList<AdInfo, Result> result) {
            super.onPostExecute(result);

            if (null != result && null != result.bean) {
                Result r = (Result) result.bean;
                if (r.result.equals("100")) {
                    if (result.list != null && result.list.size() > 0) {
                        List<AdInfo> adInfoList = result.list;
                        for (AdInfo adInfo : adInfoList) {
                            adList.add(adInfo.imgUrl);
                        }
//                    setAdData(adList);
                    }
                } else {
                    t(r.message);
                }

            } else {
                t("接口异常");
            }
            setAdData(adList);

            if (adList.size() > 1) {
                vpRecycle = true;//广告自动循环开始
                handler.sendEmptyMessageDelayed(1, 2000);
            }

        }
    }

    public class GetHomeListTask extends AsyncTask<Void, Void, QueryBeanAndList<HomeListContent, Result>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected QueryBeanAndList<HomeListContent, Result> doInBackground(Void... params) {
            Map<String, String> map = new HashMap<String, String>();
            map.put(Constants.MESSAGE_NAME, "getHomeList");
            map.put("rows", "5");
            map.put("times", "0");
            try {
                return HttpApi.getQuery(map, HomeListContent.class, Result.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(QueryBeanAndList<HomeListContent, Result> result) {
            super.onPostExecute(result);
            if (null != result && null != result.bean) {
                Result r = (Result) result.bean;
                if (r.result.equals("100")) {
                    if (result.list != null && result.list.size() > 0) {
                        List<HomeListContent> contentList = result.list;
//                        for (HomeListContent content : contentList) {
//                            homeContentList.addAll(contentList);
//                        }
                        homeContentList.addAll(contentList);

                    }
                } else {
                    t(r.message);
                }
                homeListAdapter.update(homeContentList);
            } else {
                t("接口异常");
            }
        }
    }

    public void t(String str) {
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (vpRecycle) {
            handler.sendEmptyMessageDelayed(1, 2000);
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeMessages(1);
    }

    public interface ShowInMap{
        public <T> void showPoints(List<T> list);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mCallback = (ShowInMap)activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + " must implement ShowInMap interface");
        }

    }

    @OnClick(R.id.btn)
    public void mapShow() {
        if( homeContentList != null && homeContentList.size() > 0){
//            ((MainActivity)getActivity()).changeFragement(1);
            mCallback.showPoints(homeContentList);

        }
    }

}
