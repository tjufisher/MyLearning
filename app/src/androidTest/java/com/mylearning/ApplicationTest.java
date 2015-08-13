package com.mylearning;

import android.app.Application;
import android.content.Intent;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testA(){
        Intent intent = new Intent();
        int num1 = 1;
        int num2 = 1;
        int num3 = 2;
        assertEquals(num1,num2);
        assertEquals(num1,num3);
    }
}