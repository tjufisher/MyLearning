package com.mylearning.utils;

import com.google.jtm.Gson;
import com.google.jtm.reflect.TypeToken;
import com.mylearning.entity.AdInfo;
import com.mylearning.entity.QueryBeanAndList;
import com.mylearning.entity.Result;

import java.util.Map;

/**
 * Created by user on 2015/8/20.
 */
public class HttpApi {
    public static<T, O> QueryBeanAndList<T,O> getQuery(Map<String, String> map, Class<T> listClazz,
                                                       Class<O> beanClazz){
        String strResponse = WebUtils.doPost(map);
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<QueryBeanAndList<T,O>>() {}.getType();
        QueryBeanAndList<T,O> result = gson.fromJson(strResponse, type);
        return result;
    }
}
