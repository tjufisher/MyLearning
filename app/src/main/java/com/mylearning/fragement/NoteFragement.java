package com.mylearning.fragement;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylearning.R;
import com.mylearning.utils.UtilsLog;


public class NoteFragement extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        UtilsLog.e(getClass().getName(), "create view");
        View view = inflater.inflate(R.layout.fragement_note, container,false);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
