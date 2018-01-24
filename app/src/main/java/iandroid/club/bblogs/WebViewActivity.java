package iandroid.club.bblogs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import iandroid.club.bblogs.widget.AppCacheWebView;

/**
 * WebView 界面
 */
public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.mWebView)
    AppCacheWebView mWebView;

    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        mUrl = getIntent().getStringExtra("url");
        mWebView.getDXHWebView().loadUrl(mUrl);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.getDXHWebView().destroy();
    }
}
