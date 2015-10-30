package com.mylearning.fragement;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mylearning.R;
import com.mylearning.entity.ClassLink;
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
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class NoteFragement extends Fragment {
    @InjectView(R.id.lv_link)
    ListView lvLink;

    private Context mContext;
    private ArrayList<ClassLink> arr = new ArrayList<ClassLink>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.e(getClass().getName(), "create view");
        mContext = getActivity();
        View view = inflater.inflate(R.layout.fragement_note, container, false);
        ButterKnife.inject(this, view);
        initDatas();


        return view;
    }

    public void initDatas(){
        ClassLink cl = null;
        cl = new ClassLink("VerifyTextViewActivity");
        arr.add(cl);

        cl = new ClassLink("RoundImageViewActivity");
        arr.add(cl);

        cl = new ClassLink("PostViewActivity");
        arr.add(cl);

        cl = new ClassLink("ErazerViewActivity");
        arr.add(cl);

        cl = new ClassLink("FontViewActivity");
        arr.add(cl);

        cl = new ClassLink("MatrixImageViewActivity");
        arr.add(cl);


        MyAdapter myAdapter = new MyAdapter(mContext);
        lvLink.setAdapter(myAdapter);
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

    public class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater = null;

        MyAdapter(Context context){
            mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return arr.size();
        }

        @Override
        public Object getItem(int position) {
            return arr.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null){
                viewHolder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.item_list_view,null);
                viewHolder.tv = (TextView)convertView.findViewById(R.id.tv);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)convertView.getTag();
            }
            viewHolder.tv.setText(arr.get(position).getTitle());
            viewHolder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Class c = null;
                    try {
                        c = Class.forName(arr.get(position).getClassName());
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(mContext, c);
                    startActivity(intent);
                }
            });
            return convertView;
        }

        public class ViewHolder{
            TextView tv;
        }
    }

    public void readData() {

        String extSdCardPath = Utils.getExtSdcardPath();

//        File file = new File(Environment.getExternalStorageDirectory(), "3.mp4");
        File file = new File(extSdCardPath, "DevLogo.fil");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String str;
            StringBuilder sb = new StringBuilder();
            while ((str = bufferedReader.readLine()) != null) {
//                sb.append(str);
                LogUtils.e("read Stream", str.toString());
            }
//            LogUtils.e("read Stream", sb.toString());

            String info = "fisher's file, donnot touch it!!!";
            File outputFile2 = new File(extSdCardPath, "fisher2.txt");
            if (!outputFile2.exists()) {
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


            File outputFile = new File(extSdCardPath, "fisher.txt");
            if (!outputFile.exists()) {
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
}
