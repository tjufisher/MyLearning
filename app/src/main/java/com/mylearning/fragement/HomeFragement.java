package com.mylearning.fragement;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mylearning.R;
import com.mylearning.common.Constanse;
import com.mylearning.entity.AdInfo;
import com.mylearning.entity.QueryBeanAndList;
import com.mylearning.entity.Result;
import com.mylearning.utils.HttpApi;
import com.mylearning.utils.LogUtils;
import com.mylearning.utils.StringUtils;
import com.mylearning.utils.VolleyUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class HomeFragement extends Fragment {


    @InjectView(R.id.vp_ad)
    ViewPager vpAd;
    @InjectView(R.id.et)
    EditText et;
    private Context mContext;
    private ArrayList<String> adList;
    private GetHomeAdTask getHomeAdTask;
    private int[] home_ad = {R.drawable.home_ad};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.e(getClass().getName(), "create view");
        mContext = getActivity();
        View view = inflater.inflate(R.layout.fragement_home, container, false);
        ButterKnife.inject(this, view);
        initDatas();
        return view;
    }

    public void initDatas() {
        adList = new ArrayList<String>();
        if (getHomeAdTask != null) {
            getHomeAdTask.cancel(true);
        }
        getHomeAdTask = new GetHomeAdTask();
        getHomeAdTask.execute();
    }

    public void registerLister() {


    }

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
//            initImgNum(result.size());
            vpAd.setAdapter(new HomeAdViewPager(result));
            if (result.size() > 1) {
                vpAd.setOnPageChangeListener(onPageChangeListener);
//                vpAd.startAutoScroll(PHOTO_CHANGE_TIME);
//                vpAd.setInterval(PHOTO_CHANGE_TIME);
//                vpAd.setScrollDurationFactor(2);
            }
        } else {
//            initImgNum(home_ad.length);
            vpAd.setAdapter(new HomeAdViewPager(null));
        }
    }

    public class HomeAdViewPager extends PagerAdapter {
        private ArrayList<String> adList;

        public HomeAdViewPager(ArrayList<String> adList) {
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
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            if (adList != null && adList.size() > 0) {
                if (!StringUtils.isNullOrEmpty(adList.get(position))) {
                    VolleyUtils volleyUtils = new VolleyUtils(mContext);
                    volleyUtils.doImageRequest(adList.get(position), imageView, R.drawable.home_ad);

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
//            changePosition(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public class GetHomeAdTask extends AsyncTask<Void, Void, QueryBeanAndList<AdInfo, Result>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected QueryBeanAndList<AdInfo, Result> doInBackground(Void... params) {
            Map<String, String> map = new HashMap<String, String>();
            map.put(Constanse.MESSAGE_NAME, "getHomeVpAd");
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

            List<AdInfo> adListTest = (List<AdInfo>) result.list;
            Result r = (Result) result.bean;

            LogUtils.e("json", adListTest.size() + "");
            LogUtils.e("json", adListTest.get(0).imgUrl);

            LogUtils.e("json", r.message);

            if (null != result && null != r) {
                if (r.result.equals("100")) {
                    List<AdInfo> adInfoList = (List<AdInfo>) result.list;
                    for (AdInfo adInfo : adInfoList) {
                        adList.add(adInfo.imgUrl);
                    }
                    setAdData(adList);

                } else {
                    t(r.message);
                }

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

}
