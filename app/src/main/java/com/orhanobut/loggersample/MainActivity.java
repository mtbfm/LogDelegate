package com.orhanobut.loggersample;

import java.util.Arrays;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.logger.helper.LogSettings;
import com.orhanobut.logger.helper.parser.JsonXmlParser;
import com.orhanobut.logger.helper.parser.ObjParser;
import com.orhanobut.loggersample.model.Demo;
import com.orhanobut.loggersample.model.Dummy;
import com.orhanobut.loggersample.model.Foo;
import com.orhanobut.loggersample.tree.CrashlyticsTree;
import com.orhanobut.loggersample.tree.DefaultLogTree;
import com.orhanobut.loggersample.tree.SimpleLogTree;
import com.orhanobut.loggersample.tree.SystemLogTree;
import com.orhanobut.loggersample.tree.PrettyLogTree;
import com.orhanobut.loggersample.tree.XLogTree;

import timber.log.Timber;

public class MainActivity extends BaseActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
        System.loadLibrary("stlport_shared");
        System.loadLibrary("marsxlog");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_main);

        // lint check
        System.out.println("lint error");

        Log.d(TAG, "onCreate: lint error");

        Button crashBtn = findViewById(R.id.btn);
        crashBtn.setText(stringFromJNI());
        crashBtn.setOnClickListener(view -> {
            throw new RuntimeException("This is a crash!!!!!");
        });

        LogSettings settings = new LogSettings.Builder()
                .showMethodLink(true)
                .showThreadInfo(true)
                .tagPrefix("kale")
//                        .globalTag("GLOBAL-TAG")
                .methodOffset(1)
                .logPriority(BuildConfig.DEBUG ? Log.VERBOSE : Log.ASSERT)
                .build();

        Timber.plant(
                new SystemLogTree(),
                new DefaultLogTree(settings),
                new SimpleLogTree(settings),
                new PrettyLogTree(settings),
                new CrashlyticsTree(this),
                new XLogTree(getApplicationContext())
        );

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
                Timber.d("In Other Thread");
            }
        }.start();

        new User("kale", "male").show();
        Foo.print();
        new Demo().print();

        setRes(123); // 模拟崩溃
    }

    private void levTest() {

        Timber.d("levTest: dd");

        Timber.d("debug");
        Timber.e("error");
        Timber.w("warning");
        Timber.v("verbose");
        Timber.i("information");
        Timber.wtf("wtf!!!!");

        Timber.v(null);
        Timber.d("%s test", "kale"); // 多参数 可以解决release版本中字符拼接带来的性能消耗
        String test = "abc %s def %s gh";

        Timber.d(test); // 测试timber的lint，正常时应该会报错并给出提示

        Timber.w(Log.getStackTraceString(new Throwable()));
        Timber.e("first\nsecond\nthird");

        innerTest();

//        Timber.d("Hello %s %s!", "a");

//        Timber.d("abc %s def %s gh", "s"); // Note:incorrect

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

            Throwable throwable = new Throwable("ddd");

            Timber.e(new LocalThrowable(throwable)); // 本地throwable
            Timber.e(throwable); // 要上报的throwable

            // 把崩溃的异常和当前的上下文通过log系统分发
            Timber.e(e, "res id = " + resId);
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
