package com.mylearning.fragement;

import android.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylearning.R;
import com.mylearning.utils.LogUtils;
import com.mylearning.utils.Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class LocationFragement extends Fragment {
    private Context mContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.e(getClass().getName(), "create view");
        View view = inflater.inflate(R.layout.fragement_location, container,false);
        Location location = Utils.getLocation(mContext);
        Log.e("latitude", location.getLatitude()+"");
        Log.e("longitude", location.getLongitude()+"");

        readData();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void readData(){

        String extSdCardPath = Utils.getExtSdcardPath();

//        File file = new File(Environment.getExternalStorageDirectory(), "3.mp4");
        File file = new File(extSdCardPath, "DevLogo.fil");
        try {
            BufferedReader bufferedReader = new BufferedReader( new FileReader(file));
            String str;
            StringBuilder sb = new StringBuilder();
            while( (str = bufferedReader.readLine()) != null){
//                sb.append(str);
                LogUtils.e("read Stream", str.toString());
            }
//            LogUtils.e("read Stream", sb.toString());

            String info = "fisher's file, donnot touch it!!!";
            File outputFile2 = new File( extSdCardPath, "fisher2.txt");
            if( !outputFile2.exists()){
                outputFile2.createNewFile();
            }

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile2));
            bufferedWriter.write(info);
            bufferedWriter.flush();
            bufferedWriter.close();




//            FileInputStream fileInputStream = new FileInputStream(file);
//            byte[] bytes = new byte[fileInputStream.available()];
//            fileInputStream.read(bytes);
//            String re = new String(bytes);
//            LogUtils.e("read Stream", re);


            File outputFile = new File( extSdCardPath, "fisher.txt");
            if( !outputFile.exists()){
                outputFile.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

            fileOutputStream.write(info.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doFuture() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "done";
            }
        });

        String futureResult = null;
        executorService.execute(futureTask);
        try {
            futureResult = futureTask.get(2000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
