package com.iandroid.bbase_module.application;


import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bilibili.magicasakura.utils.ThemeUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import iandroid.club.bbase_lib.BuildConfig;
import iandroid.club.bbase_lib.application.BaseAppContext;

/**
 * Created by gabriel on 2018/3/7.
 * 功能描述 基类模块App
 */

public class BaseModuleAppContext extends BaseAppContext implements ThemeUtils.switchColor {

    @Override
    public void onCreate() {
        super.onCreate();
        //init
        ThemeUtils.setSwitchColor(this);

        if (BuildConfig.DEBUG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);

        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Override
    public int replaceColorById(Context context, int colorId) {
        return context.getResources().getColor(colorId);
    }

    @Override
    public int replaceColor(Context context, int color) {
        return color;
    }
}
