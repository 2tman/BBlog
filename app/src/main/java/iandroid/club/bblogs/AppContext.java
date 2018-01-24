package iandroid.club.bblogs;

import android.app.Application;
import android.content.Context;

import com.zchu.rxcache.RxCache;
import com.zchu.rxcache.diskconverter.SerializableDiskConverter;

import java.io.File;

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

    private RxCache rxCache;

    public RxCache getRxCache() {
        return rxCache;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initRxCache();
    }

    /**
     * 初始化rxcache
     */
    private void initRxCache() {
        rxCache = new RxCache.Builder()
                .appVersion(1)
                .diskDir(new File(getCacheDir().getPath() + File.separator + "data-cache"))
                .diskConverter(new SerializableDiskConverter())//支持Serializable、Json(GsonDiskConverter)
                .memoryMax(20 * 1024 * 1024)
                .diskMax(1024 * 5 * 1024 * 1024)
                .build();
    }

    /**
     * dip转pix
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * pix转dip
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
