package com.orhanobut.loggersample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.orhanobut.logger.helper.LogSettings;
import com.orhanobut.loggersample.model.Dummy;
import com.orhanobut.loggersample.model.Foo;
import com.orhanobut.loggersample.timber.TimberActivity;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (true) {
            startActivity(new Intent(this, TimberActivity.class));
            return;
        }
        
        LogSettings settings = new LogSettings.Builder()
                .showMethodLink(true)
                .showThreadInfo(true)
                .tagPrefix("kale")
//                        .globalTag("GLOBAL-TAG")
                .methodOffset(0)
                .logPriority(BuildConfig.DEBUG ? Log.VERBOSE : Log.ASSERT)
                .build();
        
        Logger.addLogAdapter(new MyLogAdapter(settings));
        
//        Logger.addLogAdapter(new AndroidLogAdapter());
        
        Logger.d("debug");
        Logger.e("error");
        Logger.w("warning");
        Logger.v("verbose");
        Logger.i("information");
        Logger.wtf("wtf!!!!");

        levTest();
        
        largeDataTest();
        
        jsonTest();

        String xml = "<name>\n"
                + "   <first>Bill</first>\n"
                + "   <last>Gates</last>\n"
                + "</name>";
        Logger.xml(xml);

        Logger.d(new User("kale", "male"));

        // TODO: 2017/9/28 close log,change log lev 


        new Thread() {
            @Override
            public void run() {
                super.run();
                Logger.d("In Other Thread");
            }
        }.start();

        new User("kale", "male").show();
        new Foo().print();
    }

    private void jsonTest() {
        Logger.json(Dummy.SMALL_SON_WITH_NO_LINE_BREAK); // json
        String j = "[" + Dummy.JSON_WITH_NO_LINE_BREAK + "," + Dummy.JSON_WITH_LINE_BREAK + "]";
        Logger.json(j);
    }
    
    private static class User {

        private String name;

        private String sex;

        User(String name, String sex) {
            this.name = name;
            this.sex = sex;
        }

        void show() {
            Logger.d("name:%s sex:%s", name, sex);
        }
    }

    private void levTest() {
        Logger.v(null);
        Logger.d("%s test", "kale"); // 多参数 可以解决release版本中字符拼接带来的性能消耗
        String test = "abc %s def %s gh";
        Logger.d(test);

        //Logger.d(test, "s"); // Note:incorrect
        Logger.t("Custom Tag");
        Logger.t("Custom Tag").w("logger with custom tag");
        try {
            Class.forName("kale");
        } catch (ClassNotFoundException e) {
            Logger.e(e, "something happened"); // exception
        }
        Logger.w(Log.getStackTraceString(new Throwable()));

        Logger.d("first\nsecond\nthird");
        test();
    }

    private void test() {
        Logger.d("just test");
    }

    private void largeDataTest() {
        for (int i = 0; i < 20; i++) {
            Logger.d("No." + i);
        }
    }
}
