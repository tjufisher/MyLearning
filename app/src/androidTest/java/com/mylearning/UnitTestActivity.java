package com.mylearning;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.mylearning.activity.MainActivity;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @return [返回类型说明]
 * @exception/throws [违例类型] [违例说明]
 * @see [类、类#方法、类#成员]
 */
public class UnitTestActivity extends ActivityInstrumentationTestCase2 <MainActivity>{
    private TextView tv ;
    private MainActivity mainActivity;

    public UnitTestActivity() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mainActivity = getActivity();
//        tv = (TextView) mainActivity.findViewById(R.id.tv);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testAdd(){
        int a = 1, b = 2;
        int c = a + b + 1;
//        assertEquals(c, getActivity().add(a, b));
    }

    public void testTextView(){
        assertEquals(tv.getText().toString(),"Hello world!");
    }


}
