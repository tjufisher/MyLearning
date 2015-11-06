package com.mylearning.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.IMyAidlInterface;
import com.mylearning.utils.LogUtils;

/**
 * Created by user on 2015/11/5.
 */
public class MyService extends Service {
    private final String TAG = getClass().getSimpleName();
    private MyBinder mBinder = new MyBinder();

    IMyAidlInterface.Stub binder = new IMyAidlInterface.Stub() {
        @Override
        public int plus(int a, int b) throws RemoteException {
            return a + b;
        }

        @Override
        public String toUpperCase(String str) throws RemoteException {
            return str.toUpperCase();
        }
    };

    @Override
    public void onCreate() {
        LogUtils.e(TAG, "Thread id - " + Thread.currentThread().getId()+"");
        LogUtils.d(TAG, "onCreate() executed");
//        try {
//            Thread.sleep(60000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.d(TAG, "onStartCommand() executed");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.d(TAG, "onBind() executed");
        return binder;
//        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.d(TAG, "onUnbind() executed");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        LogUtils.d(TAG, "onDestroy() executed");
        super.onDestroy();
    }

    public class MyBinder extends Binder {

        public void doSomethind(){
            LogUtils.e(TAG, "do something");
        }

    }

}
