package com.mylearning.common;

import android.app.Application;
import android.content.Context;
import android.location.Location;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.mylearning.utils.LocationUtils;
import com.mylearning.utils.LogUtils;

/**
 * Created by fisher on 15-9-7.
 */
public class App extends Application{
    private static App app;

    public LocationUtils getmLocationUtils() {
        return mLocationUtils;
    }

    public void setmLocationUtils(LocationUtils mLocationUtils) {
        this.mLocationUtils = mLocationUtils;
    }

    private LocationUtils mLocationUtils;

    private User user;


    @Override
    public void onCreate() {
        super.onCreate();
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        app = (App)getApplicationContext();
        mLocationUtils = LocationUtils.getSelf(app);
    }

    public static App getSelf(){
        return app;
    }

    public void init(){
        mLocationUtils.startLocation();
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
