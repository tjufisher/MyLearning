package com.mylearning.fragement;

import android.app.Fragment;
import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.mylearning.R;
import com.mylearning.common.App;
import com.mylearning.utils.LocationUtils;
import com.mylearning.utils.LogUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class LocationFragement extends Fragment {
    @InjectView(R.id.bmapView)
    MapView bmapView;


    private Context mContext;
    private BaiduMap baiduMap;
    public LocationClient mLocationClient;
    public Vibrator mVibrator;//震动

    BitmapDescriptor mCurrentMarker;
    boolean isFirstLoc = true;// 是否首次定位

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.e(getClass().getName(), "create view");
        View view = inflater.inflate(R.layout.fragement_location, container, false);

        ButterKnife.inject(this, view);
        baiduMap = bmapView.getMap();
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        baiduMap.setMyLocationEnabled(true);

        baiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(16.0f));

        BDLocation mLocation = App.getSelf().getmLocationUtils().getmLocation();

        LatLng ll = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());

        MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(mLocation.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(mLocation.getLatitude())
                    .longitude(mLocation.getLongitude()).build();
        baiduMap.setMyLocationData(locData);

        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
        baiduMap.animateMapStatus(u);

        View view1 = LayoutInflater.from(mContext).inflate(R.layout.notify, null);
        Bitmap bitmap1 = getBitmapFromView(view1);

        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.lml);

        OverlayOptions options = new MarkerOptions().position(ll).icon(BitmapDescriptorFactory.fromBitmap(bitmap1));
        baiduMap.addOverlay(options);

        return view;
    }

    public Bitmap getBitmapFromView(View view) {
        view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache(true);
        return bitmap;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        bmapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onResume() {

        bmapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {

        bmapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
