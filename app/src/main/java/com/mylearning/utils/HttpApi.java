package com.mylearning.utils;

import com.google.jtm.Gson;
import com.google.jtm.JsonArray;
import com.google.jtm.JsonElement;
import com.google.jtm.JsonObject;
import com.google.jtm.JsonParser;
import com.google.jtm.reflect.TypeToken;
import com.mylearning.entity.AdInfo;
import com.mylearning.entity.QueryBeanAndList;
import com.mylearning.entity.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2015/8/20.
 */
public class HttpApi {
    public static<T, O> QueryBeanAndList<T,O> getQuery(Map<String, String> map, Class<T> listClazz,
                                                       Class<O> beanClazz){
        String strResponse = WebUtils.doPost(map);
        Gson gson = new Gson();

        QueryBeanAndList<T,O> result = new  QueryBeanAndList<T,O>();
        //手动解析list
        List<T> list = new ArrayList<T>();
        JsonObject jsonObject = new JsonParser().parse(strResponse).getAsJsonObject();
        JsonArray array = jsonObject.get("list").getAsJsonArray();
        for(final JsonElement elem : array){
            list.add(new Gson().fromJson(elem, listClazz));
        }
        result.list = list;

        //手动解析bean
        O object = null;
        try {
            object = beanClazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        JsonObject jsonObjectBean = jsonObject.get("bean").getAsJsonObject();
        object = new Gson().fromJson(jsonObjectBean, beanClazz);

        result.bean = object;

//        java.lang.reflect.Type type = new TypeToken<QueryBeanAndList<T,O>>() {}.getType();
//        result = gson.fromJson(strResponse, type);
        return result;
    }
}
