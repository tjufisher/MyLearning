package com.mylearning.utils;

import com.mylearning.annotation.MyAnnotation;
import com.mylearning.view.VerifyTextView;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2015/8/19.
 */
public class AnnotationUtils {
    public static AnnotationUtils anno = null;
    private AnnotationUtils(){
    }

    public static AnnotationUtils getInstance(){
        if( anno == null){
            anno = new AnnotationUtils();
        }
        return anno;
    }

    /**
     * 读取注解值
     *
     * @param annotationClasss 处理Annotation类名称
     * @param annotationField 处理Annotation类属性名称
     * @param className 处理Annotation的使用类名称
     * @return
     * @throws Exception
     */
    @SuppressWarnings("all")
    public Map<String, String> loadVlaue(Class annotationClasss,
                                         String annotationField, String className) throws Exception {

        System.out.println("处理Annotation类名称  === "+annotationClasss.getName());
        System.out.println("处理Annotation类属性名称  === "+annotationField);
        System.out.println("处理Annotation的调用类名称  === "+className);
        Map<String, String> map = new HashMap<String, String>();
        Method[] methods = Class.forName(className).getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotationClasss)) {
                Annotation p = method.getAnnotation(annotationClasss);
                Method m = p.getClass()
                        .getDeclaredMethod(annotationField, null);
//                String[] values = (String[]) m.invoke(p, null);
//                for (String key : values) {
//                    System.out.println("注解值 === " + key);
//                    map.put(key, key);
//                }
                String key = (String)m.invoke(p, null);
                System.out.println("注解值 === " + key);
                map.put(key, key);
            }
        }
        System.out.println("map数量  === " + map.size());
        return map;
    }

    public static void main(String[] args){
        try {
            AnnotationUtils.getInstance().loadVlaue(MyAnnotation.class, "author",
                    VerifyTextView.class.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
