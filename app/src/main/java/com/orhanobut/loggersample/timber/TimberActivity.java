package com.orhanobut.loggersample.timber;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.orhanobut.logger.helper.LogSettings;
import com.orhanobut.loggersample.BuildConfig;

import timber.log.Timber;


public class TimberActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // lint check
        System.out.println("lint error");

        Log.d(TAG, "onCreate: lint error");

        // TODO: 2017/9/28 close log,change log lev 

        LogSettings settings = new LogSettings.Builder()
                .showMethodLink(true)
                .showThreadInfo(true)
                .tagPrefix("kale")
//                        .globalTag("GLOBAL-TAG")
                .methodOffset(1)
                .logPriority(BuildConfig.DEBUG ? Log.VERBOSE : Log.ASSERT)
                .build();

        Timber.plant(new TimberDebugTree(settings));
        

       /* if (!BuildConfig.DEBUG) {
            // for release
            Logger.plant(new CrashlyticsTree());
        }
*/
        levTest();
        largeDataTest();
        
        new User("kale", "male").show();
        new Foo01().print();
        

//        Timber.closeLog(); // close log

        Timber.e("can you see me~!");

//        Timber.openLog(Log.INFO);

        Timber.i("googogogoog~~~~~");

        new Thread() {
            @Override
            public void run() {
                super.run();
                Timber.d("In Other Thread");
            }
        }.start();
        
        CrashHandler.getInstance().init(); // 崩溃检测处理器

//        setRes(123);  // 模拟崩溃
    }

    private void levTest() {
        Timber.v(null);
        Timber.d("%s test", "kale"); // 多参数 可以解决release版本中字符拼接带来的性能消耗
        String test = "abc %s def %s gh";
        Timber.d(test);

        //Timber.d(test, "s"); // Note:incorrect
        Timber.tag("Custom Tag");
        Timber.tag("Custom Tag").w("logger with custom tag");
        Timber.w(Log.getStackTraceString(new Throwable()));
        try {
            Class.forName("kale");
        } catch (ClassNotFoundException e) {
            Timber.e(e, "something happened"); // exception
        }

        Timber.d("first\nsecond\nthird");
        test();
    }

    private void test() {
        Timber.d("just test");
    }

    private void largeDataTest() {
        for (int i = 0; i < 20; i++) {
            Timber.d("No." + i);
        }
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

    ///////////////////////////////////////////////////////////////////////////
    // crash
    ///////////////////////////////////////////////////////////////////////////

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
