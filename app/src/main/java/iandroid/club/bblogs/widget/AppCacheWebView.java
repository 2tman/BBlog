package iandroid.club.bblogs.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


import java.io.File;
import java.lang.ref.WeakReference;

import iandroid.club.bblogs.AppContext;
import iandroid.club.bblogs.R;
import ren.yale.android.cachewebviewlib.CacheInterceptor;
import ren.yale.android.cachewebviewlib.CacheWebView;
import ren.yale.android.cachewebviewlib.WebViewCache;
import ren.yale.android.cachewebviewlib.utils.NetworkUtils;

/**
 * Created by gabriel on 2017/12/26.
 * 功能描述 自带缓存的通用WebView
 */

public class AppCacheWebView extends RelativeLayout {
    private CacheWebView mDXHWebView = null;
    private WeakReference<Context> mContext;
    private boolean showProgressBar = true;

    public AppCacheWebView(Context context) {
        super(context);
        init(context);
    }

    public AppCacheWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AppCacheWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    public CacheWebView getDXHWebView() {
        return mDXHWebView;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void setShowProgressBar(boolean showProgressBar) {
        this.showProgressBar = showProgressBar;
    }

    private void addWebView(Context context) {
        mDXHWebView = new CacheWebView(context);
        addProgressBar();
        RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        this.addView(mDXHWebView, rl);
    }

    private void addProgressBar(){
        final ProgressBar mProgressBar = new ProgressBar(mContext.get(), null,
                android.R.attr.progressBarStyleHorizontal);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, AppContext.dip2px(mContext.get(), 2));
        mProgressBar.setLayoutParams(layoutParams);
        Drawable drawable = getResources().getDrawable(
                R.drawable.webview_progressbar);
        mProgressBar.setProgressDrawable(drawable);
        if(showProgressBar) {
            mDXHWebView.addView(mProgressBar);
        }
        mDXHWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(showProgressBar) {
                    if (newProgress == 100) {
                        mProgressBar.setVisibility(GONE);
                    } else {
                        if (mProgressBar.getVisibility() == GONE) {
                            mProgressBar.setVisibility(VISIBLE);
                        }
                        mProgressBar.setProgress(newProgress);

                    }
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }

    private void initSettings() {

        //设置缓存拦截器，可以针对每一个url是否拦截缓存
        mDXHWebView.setCacheInterceptor(new CacheInterceptor() {
            @Override
            public boolean canCache(String url) {
                return true;
            }
        });

        //强制缓存
        mDXHWebView.setCacheStrategy(WebViewCache.CacheStrategy.FORCE);

        WebSettings webSettings = mDXHWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= 16) {
            webSettings.setAllowFileAccessFromFileURLs(true);
        }
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        //自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);

        if (NetworkUtils.isConnected(mContext.get())) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            webSettings.setCacheMode(
                    WebSettings.LOAD_CACHE_ELSE_NETWORK);


        }
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);
        webSettings.setAppCacheEnabled(true);


        //File fDatabase = new File(mContext.getCacheDir().getAbsolutePath(),"webview_db");
        //webSettings.setDatabasePath(fDatabase.getAbsolutePath());
        File fAppCache = new File(mContext.get().getCacheDir().getAbsolutePath(), "webview_cache");
        webSettings.setAppCachePath(fAppCache.getAbsolutePath());

        webSettings.setBuiltInZoomControls(true);// api-3
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        if (Build.VERSION.SDK_INT >= 11) {
            webSettings.setDisplayZoomControls(false);// api-11
        }

        //http://wiki.jikexueyuan.com/project/chrome-devtools/remote-debugging-on-android.html
        if (Build.VERSION.SDK_INT >= 19) {//for chrome debug
            WebView.setWebContentsDebuggingEnabled(true);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
    }

    private void init(final Context context) {

        mContext = new WeakReference<Context>(context);
        addWebView(context);

        initSettings();

    }

    public void destroy(){
        if (Build.VERSION.SDK_INT >= 19) {//for chrome debug
            WebView.setWebContentsDebuggingEnabled(false);
        }
        mDXHWebView.loadUrl("");
        mDXHWebView.destroy();
    }
}
