package com.mylearning.fragement;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.mylearning.R;
import com.mylearning.base.BaseFragement;
import com.mylearning.common.App;
import com.mylearning.entity.HomeListContent;
import com.mylearning.utils.BMapUtils;
import com.mylearning.utils.LogUtils;
import com.mylearning.utils.StringUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class LocationFragement extends BaseFragement {
    @InjectView(R.id.bmapView)
    MapView bmapView;

    private Context mContext;
    private BaiduMap baiduMap;
    public Vibrator mVibrator;//震动

    private List<HomeListContent> homeContentList;
    private InfoWindow mInfoWindow;


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
        init();
        initData();
        registerListerner();
        return view;
    }

    public void init() {
        baiduMap = bmapView.getMap();
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        baiduMap.setMyLocationEnabled(true);
        baiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(16.0f));

        BDLocation mLocation = App.getSelf().getmLocationUtils().getmLocation();
        if (mLocation != null && mLocation.getLatitude() != 4.9E-324
                && mLocation.getLongitude() != 4.9E-324) {
            LatLng ll = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(mLocation.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(mLocation.getLatitude())
                    .longitude(mLocation.getLongitude()).build();
            baiduMap.setMyLocationData(locData);

            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);//设置当前位置
            baiduMap.animateMapStatus(u);
        }else{
            Toast.makeText(mContext,"定位失败！",Toast.LENGTH_SHORT);
        }
    }

    public void initData() {
        getDatas();
        addPoints();
    }

    public void registerListerner() {
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                Button button = new Button(mContext);
                button.setBackgroundResource(R.drawable.popup);
                InfoWindow.OnInfoWindowClickListener listener = null;
                int index = -1;
                if (StringUtils.canParseInt(marker.getTitle())) {
                    index = Integer.parseInt(marker.getTitle());
                }
                if (index != -1) {
                    button.setText(homeContentList.get(index).title);
                }

                listener = new InfoWindow.OnInfoWindowClickListener() {
                    public void onInfoWindowClick() {
                        LatLng ll = marker.getPosition();
                        LatLng llNew = new LatLng(ll.latitude + 0.005,
                                ll.longitude + 0.005);
                        marker.setPosition(llNew);
                        baiduMap.hideInfoWindow();
                    }
                };
                LatLng ll = marker.getPosition();
                mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(button), ll, -47, listener);
                baiduMap.showInfoWindow(mInfoWindow);
                return true;
            }
        });
    }

    public void getDatas() {
        if (getArguments() != null && getArguments().getSerializable("data") != null)
            homeContentList = (List<HomeListContent>) getArguments().getSerializable("data");
    }

    public void addPoints() {
        double lat = 0, lng = 0;
        if (homeContentList != null && homeContentList.size() > 0) {
            for (int i = 0; i < homeContentList.size(); i++) {
                lat += homeContentList.get(i).latitude;
                lng += homeContentList.get(i).longitude;
                LatLng ll = new LatLng(homeContentList.get(i).latitude, homeContentList.get(i).longitude);
                drawPoint(ll, i);
            }
            lat = lat / homeContentList.size();
            lng = lng / homeContentList.size();
            LatLng latLngCenter = new LatLng(lat, lng);
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLngCenter);//设置中心位置
            baiduMap.animateMapStatus(u);
        }
    }

    public void drawPoint(LatLng latLng, int index) {
        View viewPoint = LayoutInflater.from(mContext).inflate(R.layout.map_marker, null);
        TextView tv_num = (TextView) viewPoint.findViewById(R.id.tv_num);
        tv_num.setText(String.valueOf(index));
        Bitmap viewBitmap = BMapUtils.getBitmapFromView(viewPoint);

        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromBitmap(viewBitmap);

        OverlayOptions options = new MarkerOptions().position(latLng).icon(bitmap);

        ((Marker) (baiduMap.addOverlay(options))).setTitle(index + "");//设置marker title，用以区分不同的marker
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
//        baiduMap.setMyLocationEnabled(false);
//
//        bmapView.onDestroy();
//        bmapView = null;
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
