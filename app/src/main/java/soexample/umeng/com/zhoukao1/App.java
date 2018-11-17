package soexample.umeng.com.zhoukao1;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import soexample.umeng.com.zhoukao1.SqliteUtils.SqliteUtils;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SqliteUtils.getSqliteUtils().init(this);
        Fresco.initialize(this);

    }

}
