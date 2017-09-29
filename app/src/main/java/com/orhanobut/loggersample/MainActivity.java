package com.orhanobut.loggersample;

import java.util.Arrays;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.orhanobut.logger.helper.LogSettings;
import com.orhanobut.logger.helper.parser.JsonXmlParser;
import com.orhanobut.logger.helper.parser.ObjParser;
import com.orhanobut.loggersample.model.Demo;
import com.orhanobut.loggersample.model.Dummy;
import com.orhanobut.loggersample.model.Foo;
import com.orhanobut.loggersample.tree.CrashlyticsTree;
import com.orhanobut.loggersample.tree.LoggerTree;
import com.orhanobut.loggersample.tree.MyDebugTree;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // lint check
        System.out.println("lint error");

        Log.d(TAG, "onCreate: lint error");

        LogSettings settings = new LogSettings.Builder()
                .showMethodLink(true)
                .showThreadInfo(true)
                .tagPrefix("kale")
//                        .globalTag("GLOBAL-TAG")
                .methodOffset(1)
                .logPriority(BuildConfig.DEBUG ? Log.VERBOSE : Log.ASSERT)
                .build();

        Timber.plant(new MyDebugTree(settings), new CrashlyticsTree(), new LoggerTree());

        levTest();

        largeDataTest();

        objTest();
        
        jsonTest();

        Timber.d(JsonXmlParser.xml("<name>\n"
                + "   <first>Bill</first>\n"
                + "   <last>Gates</last>\n"
                + "</name>"));

        Timber.d(ObjParser.obj(new User("kale", "male")));

        
        
        new Thread() {
            @Override
            public void run() {
                super.run();
                Timber.d("In Other Thread");
            }
        }.start();

        new User("kale", "male").show();
        Foo.print();
        new Demo().print();
    }

    private void levTest() {
        Timber.d("debug");
        Timber.e("error");
        Timber.w("warning");
        Timber.v("verbose");
        Timber.i("information");
        Timber.wtf("wtf!!!!");

        Timber.v(null);
        Timber.d("%s test", "kale"); // 多参数 可以解决release版本中字符拼接带来的性能消耗
        String test = "abc %s def %s gh";
        Timber.d(test);
        Timber.w(Log.getStackTraceString(new Throwable()));
        Timber.e("first\nsecond\nthird");

        innerTest();

//        Timber.d(test, "s"); // Note:incorrect

        Timber.tag("Custom Tag").w("logger with custom tag");
        Timber.tag("Custom Tag02").e("logger with custom tag02");

        try {
            Class.forName("kale");
        } catch (ClassNotFoundException e) {
            Timber.e(e, "something happened"); // exception
        }
    }

    private void innerTest() {
        Timber.d("just test");
    }

    private void largeDataTest() {
        for (int i = 0; i < 20; i++) {
            Timber.d("No." + i);
        }
    }

    private void objTest() {
        // object
        Timber.d(ObjParser.obj(new User("jack", "f")));
        // list
        Timber.d(ObjParser.obj(Arrays.asList("kale", "jack", "tony")));
        // array
        Timber.d(ObjParser.obj(new String[]{"Android", "ios", "wp"}));
        double[][] doubles = {
                {1.2, 1.6, 1.7, 30, 33},
                {1.2, 1.6, 1.7, 30, 33},
                {1.2, 1.6, 1.7, 30, 33},
                {1.2, 1.6, 1.7, 30, 33}
        };
        Timber.d(ObjParser.obj(doubles));
    }

    private void jsonTest() {
        Timber.d(JsonXmlParser.json(Dummy.SMALL_SON_WITH_NO_LINE_BREAK)); // json
        String jsn = "[" + Dummy.JSON_WITH_NO_LINE_BREAK + "," + Dummy.JSON_WITH_LINE_BREAK + "]";
        Timber.i(JsonXmlParser.json(jsn));
    }

    private static class User {

        private String name;

        private String sex;

        User(String name, String sex) {
            this.name = name;
            this.sex = sex;
        }

        void show() {
            Timber.d("name:%s sex:%s", name, sex);
        }
    }

    /**
     * 这里模拟后端给客户端传值的情况。
     *
     * 这里的id来自外部输入，如果外部输入的值有问题，那么就可能崩溃。
     * 但理论上是不会有数据异常的，为了不崩溃，这里加try-catch
     */
    private void setRes(@StringRes int resId) {
        TextView view = new TextView(this);

        try {
            view.setText(resId); // 如果出现了崩溃，那么就会调用崩溃处理机制
        } catch (Exception e) {
            // 防御了崩溃
            e.printStackTrace();

            // 把崩溃的异常和当前的上下文通过log系统分发
            Timber.e(e, "res id = " + resId);
        }
    }
}
