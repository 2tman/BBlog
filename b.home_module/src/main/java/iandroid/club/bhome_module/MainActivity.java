package iandroid.club.bhome_module;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.iandroid.bbase_module.router.HomeRouteUtils;
import com.orhanobut.logger.Logger;

@Route(path = HomeRouteUtils.Home_Activity_Main)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        if(intent!=null){
            Logger.d("url:"+intent.getStringExtra("url"));
        }
    }
}
