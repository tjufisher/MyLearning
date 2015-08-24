package com.mylearning.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.mylearning.R;

/**
 * Created by user on 2015/8/20.
 */
public class VolleyUtils {
    private Context mContext;
    private static RequestQueue mQueue;
    private static VolleyUtils volleyUtils;

    private VolleyUtils(Context context){
        mContext = context;
        mQueue = Volley.newRequestQueue(mContext);
    }

    public static VolleyUtils createInstance( Context context){
        if ( volleyUtils == null){
            volleyUtils = new VolleyUtils(context);
        }
        mQueue = Volley.newRequestQueue(context);
        return volleyUtils;
    }

    public void doImageRequest(String url, final ImageView imageView, final int default_pic){
        ImageRequest imageRequest = new ImageRequest(
                url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(response);
                    }
                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                imageView.setImageResource(default_pic);
            }
        });
        mQueue.add(imageRequest);
    }

}
