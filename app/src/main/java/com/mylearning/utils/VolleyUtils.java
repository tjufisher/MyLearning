package com.mylearning.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.mylearning.R;

/**
 * Created by user on 2015/8/20.
 */
public class VolleyUtils {
    private Context mContext;
    private String TAG = getClass().getSimpleName();
    private static RequestQueue mQueue;
    private static VolleyUtils volleyUtils;
    private ImageRequest imageRequest = null;
    private ImageLoader imageLoader;
    private ImageLoader.ImageListener imageListener;

    private VolleyUtils(Context context){
        mContext = context;
        mQueue = Volley.newRequestQueue(mContext);
        imageLoader = new ImageLoader(mQueue, new BitmapCache());

    }

    public static VolleyUtils createInstance(Context context) {
        if (volleyUtils == null) {
            volleyUtils = new VolleyUtils(context);
        }
        return volleyUtils;
    }

    public void doImageRequest(String url, final ImageView imageView, final int default_pic) {
        imageListener = ImageLoader.getImageListener(imageView, R.drawable.default_loading, default_pic);
        imageLoader.get(url, imageListener);


//        imageRequest = new ImageRequest(
//                url,
//                new Response.Listener<Bitmap>() {
//                    @Override
//                    public void onResponse(Bitmap response) {
//                        imageView.setImageBitmap(response);
//                    }
//                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                imageView.setImageResource(default_pic);
//            }
//        });
//        mQueue.add(imageRequest);
    }

    public class BitmapCache implements ImageLoader.ImageCache {
        private LruCache<String, Bitmap> cache;

        public BitmapCache() {
            cache = new LruCache<String, Bitmap>(8 * 1024 * 1024) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getRowBytes() * bitmap.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return cache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            cache.put(url, bitmap);
        }
    }

}
