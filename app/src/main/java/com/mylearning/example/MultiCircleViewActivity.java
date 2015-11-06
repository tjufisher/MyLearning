package com.mylearning.example;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.mylearning.R;
import com.mylearning.activity.MainActivity;
import com.mylearning.base.BaseActivity;
import com.mylearning.view.FontView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by user on 2015/8/18.
 */
public class MultiCircleViewActivity extends BaseActivity {

    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_circle_view, LAYOUT_TYPE_HEADER);
        ButterKnife.inject(this);
        setHeaderBar("复杂圆");
        mContext = this;

        testNotification();
    }

    public void testNotification(){
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        initNotify();
        showNotify();
    }

    public void test(){
        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        Intent bindIntent = new Intent(this, MainActivity.class);
        bindService(bindIntent, connection, BIND_AUTO_CREATE);

    }

    /** 显示通知栏 */
    public void showNotify(){
        mBuilder.setContentTitle("测试标题")
                .setContentText("测试内容")
//				.setNumber(number)//显示数量
                .setTicker("测试通知来啦");//通知首次出现在通知栏，带上升动画效果的
        mNotificationManager.notify(11, mBuilder.build());
//		mNotification.notify(getResources().getString(R.string.app_name), notiId, mBuilder.build());
    }


    public PendingIntent getDefalutIntent(int flags){
        PendingIntent pendingIntent= PendingIntent.getActivity(this, 1, new Intent(), flags);
        return pendingIntent;
    }


    /** 初始化通知栏 */
    private void initNotify(){
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("测试标题")
                .setContentText("测试内容")
                .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
//				.setNumber(number)//显示数量
                .setTicker("测试通知来啦")//通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
                .setPriority(Notification.PRIORITY_DEFAULT)//设置该通知优先级
//				.setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
                        //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(R.drawable.lml);
    }


}
