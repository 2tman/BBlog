package iandroid.club.bbase_lib.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.badoualy.morphytoolbar.MorphyToolbar;
import com.gyf.barlibrary.ImmersionBar;

import iandroid.club.bbase_lib.R;

/**
 * Created by gabriel on 2018/3/16.
 * 功能描述
 */

public class BaseActivity extends AppCompatActivity {

    MorphyToolbar morphyToolbar;

    int primary2;
    int primaryDark2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        primary2 = getResources().getColor(R.color.material_white);
        primaryDark2 = getResources().getColor(R.color.material_black);

        ImmersionBar.with(this)
                .statusBarColor(R.color.material_white)
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                .flymeOSStatusBarFontColor(R.color.material_black)  //修改flyme OS状态栏字体颜色
                .fullScreen(true)      //有导航栏的情况下，activity全屏显示，也就是activity最下面被导航栏覆盖，不写默认非全屏
                .init();
    }

    protected void initToolbar(Toolbar toolbar, String title, String subTitle
            , int picRes) {
        // Attach to the given activity/toolbar
        morphyToolbar = MorphyToolbar.builder(this, toolbar)
                .withToolbarAsSupportActionBar()
                .withTitle(title)
                .withSubtitle(subTitle)
                .withPicture(picRes)
                .withHidePictureWhenCollapsed(false)
                .build();
        morphyToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (morphyToolbar.isCollapsed()) {
                    morphyToolbar.expand(primary2, primaryDark2, new MorphyToolbar.OnMorphyToolbarExpandedListener() {
                        @Override
                        public void onMorphyToolbarExpanded() {

                        }
                    });
                } else {
                    morphyToolbar.collapse();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (morphyToolbar!=null && !morphyToolbar.isCollapsed()) {
            morphyToolbar.collapse();
        } else
            super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
