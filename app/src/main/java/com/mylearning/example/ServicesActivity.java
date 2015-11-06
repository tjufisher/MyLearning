package com.mylearning.example;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;

import com.IMyAidlInterface;
import com.mylearning.R;
import com.mylearning.base.BaseActivity;
import com.mylearning.services.MyService;
import com.mylearning.utils.LogUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by user on 2015/8/18.
 */
public class ServicesActivity extends BaseActivity {

    @InjectView(R.id.btn_start)
    Button btnStart;
    @InjectView(R.id.btn_stop)
    Button btnStop;
    @InjectView(R.id.btn_bind)
    Button btnBind;
    @InjectView(R.id.btn_unbind)
    Button btnUnbind;

    private Intent intent = null;

    private IMyAidlInterface myAidlInterface;
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            MyService.MyBinder myBind = (MyService.MyBinder)service;
//            myBind.doSomethind();
            myAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            try {
                LogUtils.e("plus:", "" + myAidlInterface.plus(2, 5));
                LogUtils.e("upper case:", "" + myAidlInterface.toUpperCase("abcde"));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_view, LAYOUT_TYPE_HEADER);
        ButterKnife.inject(this);
        setHeaderBar("Service");
        mContext = this;
        LogUtils.e(TAG, "Thread id - " +Thread.currentThread().getId()+"");

    }

    @OnClick({R.id.btn_start, R.id.btn_stop, R.id.btn_bind, R.id.btn_unbind})
    public void click(View v){

        switch(v.getId()){
            case R.id.btn_start:
                intent = new Intent(mContext, MyService.class);
                startService(intent);
                break;
            case R.id.btn_stop:
                intent = new Intent(mContext, MyService.class);
                stopService(intent);
                break;
            case R.id.btn_bind:
                intent = new Intent(mContext, MyService.class);
                bindService(intent, connection, BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind:
                unbindService(connection);
                break;
            default:
                break;
        }
    }


}
