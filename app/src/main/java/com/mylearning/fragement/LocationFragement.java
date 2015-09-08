package com.mylearning.fragement;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.mylearning.R;
import com.mylearning.entity.HomeListContent;
import com.mylearning.utils.LogUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class LocationFragement extends Fragment {
    @InjectView(R.id.bmapView)
    MapView bmapView;


    private Context mContext;
    private BaiduMap baiduMap;
    public LocationClient mLocationClient;
    public Vibrator mVibrator;//震动

    BitmapDescriptor mCurrentMarker;
    boolean isFirstLoc = true;// 是否首次定位
    private List<HomeListContent> homeContentList;

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

//        BDLocation mLocation = App.getSelf().getmLocationUtils().getmLocation();
//
//        LatLng ll = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
//
//        MyLocationData locData = new MyLocationData.Builder()
//                    .accuracy(mLocation.getRadius())
//                            // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .direction(100).latitude(mLocation.getLatitude())
//                    .longitude(mLocation.getLongitude()).build();
//        baiduMap.setMyLocationData(locData);
//
//        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
//        baiduMap.animateMapStatus(u);
//
//        getIntent();
//        addPoints();

        return view;
    }

    public void getIntent(){
        if( getActivity().getIntent() != null)
            homeContentList = (List<HomeListContent>)getActivity().getIntent()
                    .getSerializableExtra("data");
    }

    public void addPoints(){
        if( homeContentList != null && homeContentList.size() > 0){
            for( int i = 0; i < homeContentList.size(); i++){
                LatLng ll = new LatLng(homeContentList.get(i).latitude, homeContentList.get(i).longitude);
                drawPoint(ll, i);
            }
        }

    }

    public void drawPoint( LatLng latLng, int index){
        View viewPoint = LayoutInflater.from(mContext).inflate(R.layout.map_marker, null);
        TextView tv_num = (TextView)viewPoint.findViewById(R.id.tv_num);
        tv_num.setText(String.valueOf(index));
        Bitmap viewBitmap = getBitmapFromView(viewPoint);

        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromBitmap(viewBitmap);

        OverlayOptions options = new MarkerOptions().position(latLng).icon(bitmap);
        baiduMap.addOverlay(options);

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

    public class MyOverlay extends Overlay{



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
