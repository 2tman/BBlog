package iandroid.club.bmine_module;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.iandroid.bbase_module.router.MineRouteUtils;
import com.orhanobut.logger.Logger;

@Route(path = MineRouteUtils.Mine_Activity_Main)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        if(intent!=null){
            Bundle bundle = intent.getBundleExtra("params");
            Logger.d(bundle.getString("name"));
        }
    }
}
