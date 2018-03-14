package com.iandroid.bbase_module.router;

import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by gabriel on 2018/3/7.
 * 功能描述
 */

public class FragmentUtils {

    /**
     * 主界面
     * @return
     */
    public static Fragment getHomeFragment() {
        Fragment fragment = (Fragment) ARouter.getInstance().build(HomeRouteUtils.Home_Fragment_Main).navigation();
        return fragment;
    }

    /**
     * code主界面
     * @return
     */
    public static Fragment getCodeFragment() {
        Fragment fragment = (Fragment) ARouter.getInstance().build(CodeRouteUtils.Code_Fragment_Main).navigation();
        return fragment;
    }

    /**
     * good app主界面
     * @return
     */
    public static Fragment getGoodAppFragment() {
        Fragment fragment = (Fragment) ARouter.getInstance().build(AppRouteUtils.APP_Fragment_Main).navigation();
        return fragment;
    }

    /**
     * mine主界面
     * @return
     */
    public static Fragment getMineFragment() {
        Fragment fragment = (Fragment) ARouter.getInstance().build(MineRouteUtils.Mine_Fragment_Main).navigation();
        return fragment;
    }
}
