package iandroid.club.bblogs;

import android.app.Application;

/**
 * @Description:
 * @Author: 2tman
 * @Time: 2018/1/24
 */
public class AppContext extends Application {

    private static AppContext instance;

    public static AppContext getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
