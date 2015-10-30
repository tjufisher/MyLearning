package com.mylearning.entity;

/**
 * Created by user on 2015/8/18.
 */
public class ClassLink {
    private String title;
    private String className;
    private static final String PACKAGE_NAME = "com.mylearning.example";

    public ClassLink( ){

    }

    public ClassLink(String activityName ){
        title = activityName + " Example";
        className = PACKAGE_NAME + "." + activityName;
    }
    public ClassLink( String t, String c){
        title = t;
        className = c;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public String getTitle() {
        return title;
    }




}
