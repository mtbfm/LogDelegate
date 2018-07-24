[![](https://jitpack.io/v/tianzhijiexian/logger.svg)](https://jitpack.io/#tianzhijiexian/logger)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Logger-brightgreen.svg?mFormat=flat)](http://android-arsenal.com/details/1/1658) [![](https://img.shields.io/badge/AndroidWeekly-%23147-blue.svg)](http://androidweekly.net/issues/issue-147)

<img align="right" src='https://raw.githubusercontent.com/tianzhijiexian/logger/master/images/logger-logo.png' width='128' height='128'/>

### Logger
Simple, pretty and powerful logger for android

Logger provides :

- Thread information
- Class information
- Method information
- Pretty-print for json/xml content
- Pretty-print for new line "\n"
- Clean output
- print max long log 
- Jump to source(by link)
- Smart log tag
- different log format
- null message
- null tag
- support large string  

### Gradle
Add it in your root build.gradle at the end of repositories:  

```groovy
allprojects {
    repositories {
	//...
	maven { url "https://jitpack.io" }
    }
}
```  

Add the dependency  
> compile 'com.github.tianzhijiexian:logger:[Latest release](https://github.com/tianzhijiexian/logger/releases)(<-click)'

### Print  

```logcat
D/MainActivity: │ first
D/MainActivity: │ second
D/MainActivity: └ third ==> levTest(MainActivity.java:62) // 多行

D/MainActivity: └ just test ==> test(MainActivity.java:67) // 单行

D/MainActivity: └ User{name=jack, sex=f, $change=Object}  ==> objTest(MainActivity.java:72)

D/MainActivity: │ ArrayList size = 3 [
D/MainActivity: │ [0]:kale,
D/MainActivity: │ [1]:jack,
D/MainActivity: │ [2]:tony
D/MainActivity: │ 
D/MainActivity: └ ] ==> objTest(MainActivity.java:74)

D/MainActivity: │ String[3] {
D/MainActivity: │ [Android,	ios,	wp]
D/MainActivity: └ } ==> objTest(MainActivity.java:76)

D/MainActivity: │ double[4][5] {
D/MainActivity: │ [1.2,	1.6,	1.7,	30.0,	33.0]
D/MainActivity: │ [1.2,	1.6,	1.7,	30.0,	33.0]
D/MainActivity: │ [1.2,	1.6,	1.7,	30.0,	33.0]
D/MainActivity: │ [1.2,	1.6,	1.7,	30.0,	33.0]
D/MainActivity: │ 
D/MainActivity: └ } ==> objTest(MainActivity.java:83)

D/MainActivity: │ {
D/MainActivity: │     "widget": {
D/MainActivity: │         "debug": "on",
D/MainActivity: │         "window": {
D/MainActivity: │             "title": "Sample Konfabulator Widget",
D/MainActivity: │             "name": "main_window",
D/MainActivity: │             "width": 500,
D/MainActivity: │             "height": 500
D/MainActivity: │         }
D/MainActivity: │     }
D/MainActivity: └ } ==> jsonTest(MainActivity.java:87)
```

### Usage

**With Timber**

```java
public class DefaultLogTree extends Timber.DebugTree {

    private LogDelegate mDelegate;   public DefaultLogTree(LogSettings settings) {
        this(settings, new DefaultLogFormatter()); // or new PrettyFormatter()
  }

    DefaultLogTree(LogSettings settings, AbsLogFormatter formatter) {
        mDelegate = new LogDelegate(settings, formatter,
  (priority, tag, message, throwable) -> Log.println(priority, tag, message));
  }

    @Override
  protected boolean isLoggable(String tag, int priority) {
        return mDelegate.isLoggable(priority, tag);
  }

    @Override
  protected String createStackElementTag(@NonNull StackTraceElement element) {
        return mDelegate.getAutoTag(element);
  }

    @Override
  protected void log(int priority, String tag, @NonNull String message, Throwable t) {
        mDelegate.printLog(priority, tag, message, t);
  }

}
```

```java
Timber.plant(new DefaultLogTree(settings));
```

```java
Timber.d("debug"); 
Timber.e("error"); 
Timber.w("warning"); 
Timber.v("verbose"); 
Timber.i("information"); 
Timber.wtf("wtf!!!!");

Timber.tag("Custom Tag").w("logger with custom tag"); // tag

Timber.i(JsonXmlParser.json(json)); // json

Timber.i(JsonXmlParser.xml(xmlStr)); // xml

// object 
Timber.d(ObjParser.obj(new User("jack", "f"))); 
// list 
Timber.d(ObjParser.obj(Arrays.asList("kale", "jack", "tony"))); 
// array 
Timber.d(ObjParser.obj(new String[]{"Android", "ios", "wp"}));
```

**With Your LogUtil**


```java
public final class LogUtils {

    private static LogDelegate sDelegate;

    public static void init(LogSettings settings) {
        sDelegate = new LogDelegate(settings, new DefaultLogFormatter(),
                (priority, tag, message, throwable) -> Log.println(priority, tag, message));
    }

    public static boolean isLoggable(int priority, String tag) {
        return sDelegate.isLoggable(priority, tag);
    }

    public static void d(String tag, String message) {
        if (isLoggable(Log.DEBUG, tag)) {
            print(Log.DEBUG, tag, message);
        }
    }

    public static void d(String message) {
        String tag = sDelegate.getAutoTag(null);
        d(tag, message);
    }

    public static void e(String tag, String message) {
        if (isLoggable(Log.ERROR, tag)) {
            print(Log.ERROR, tag, message);
        }
    }

    public static void openLog(boolean b) {
        if (b) {
            sDelegate.getSettings().changeLogLev(Log.VERBOSE);
        } else {
            sDelegate.getSettings().closeLog();
        }
    }

    private static void print(int priority, String tag, String message) {
        sDelegate.printLog(priority, tag, message, null);
    }

}
```

### Settings

This should be called only once. Best place would be in application class, all of them
 are optional.

```java
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

	LogSettings settings = new LogSettings.Builder()
	        .showMethodLink(true)
	        .showThreadInfo(true)
	        .tagPrefix("kale")
	        .globalTag("GLOBAL-TAG")
	        .methodOffset(1)
	        .logPriority(BuildConfig.DEBUG ? Log.VERBOSE : Log.ASSERT)
	        .build();
}
```

#### You might also like
- [Logger](https://github.com/orhanobut/logger) Simple, pretty and powerful logger for android
- [Hawk](https://github.com/orhanobut/hawk) Simple,powerful,secure key-value storage
- [Wasp](https://github.com/orhanobut/wasp) All-in-one network solution
- [Bee](https://github.com/orhanobut/bee) QA/Debug tool
- [DialogPlus](https://github.com/orhanobut/dialogplus) Easy,simple dialog solution
- [SimpleListView](https://github.com/orhanobut/simplelistview) Simple basic listview implementation with linearlayout
- [Android-PLog](https://github.com/Muyangmin/Android-PLog) A Pure, Pretty and Powerful logging library for android.

### License
<pre>
Copyright 2015 Orhan Obut

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
</pre>
