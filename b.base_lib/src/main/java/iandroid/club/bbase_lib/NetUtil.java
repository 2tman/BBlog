package iandroid.club.bbase_lib;

import android.util.Log;

import java.io.IOException;

import iandroid.club.bbase_lib.listener.NetCallBack;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author: 耿加荣
 * @CreatedDate: 2019/6/13
 * @CreatedDesc:
 * @Version: 1.0
 */
public class NetUtil {

    public static String syncGet(String url) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        return response.body().string();
    }

    public static void asyncGet(String url, final NetCallBack netCallBack){
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(netCallBack!=null && response.isSuccessful()){
                    netCallBack.onSuccess(response.body().string());
                }
            }
        });
    }
}
