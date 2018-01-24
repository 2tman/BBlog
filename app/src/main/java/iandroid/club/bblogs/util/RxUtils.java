package iandroid.club.bblogs.util;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.jakewharton.rxbinding.view.RxView;


import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * @Description: rxjava 单线程 单次操作封装
 * @Author: 加荣
 * @Time: 2017/11/3
 */
public class RxUtils {


    private static final String TAG = RxUtils.class.getSimpleName();

    /**
     * 创建被观察者
     *
     * @param callable
     * @param <T>
     * @return
     */
    public static <T> Observable<T> createObservable(final Callable<T> callable) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    T result = callable.call();
                    subscriber.onNext(result);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        });
    }

    /**
     * 防止快速点击
     *
     * @param view
     * @param onClickListener
     */
    public static void clicks(final View view, long times, final View.OnClickListener onClickListener) {
        RxView.clicks(view)
                .throttleFirst(times, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        onClickListener.onClick(view);
                    }
                });
    }

    /**
     * 防止快速点击
     *
     * @param view
     * @param onClickListener
     */
    public static void clicks(final View view, final View.OnClickListener onClickListener) {
        RxView.clicks(view)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        onClickListener.onClick(view);
                    }
                });
    }


    public static final String[] OFFICE_FILES = {"doc", "docx", "wps", "ppt", "pptx", "xls", "xlsx"};

    /**
     * 判断是否为office文件
     *
     * @param file
     * @return
     */
    public static boolean validateIsOffice(File file) {
        for (String extendsname : OFFICE_FILES) {
            if (file.getAbsolutePath().toLowerCase().endsWith("." + extendsname)) {
                return true;
            }
        }
        return false;
    }


    /**
     * rxjava递归查询文件
     *
     * @param f
     * @return
     */
    public static Observable<File> listFiles(final File f) {
        if (f.isDirectory()) {
            return Observable.from(f.listFiles()).flatMap(new Func1<File, Observable<File>>() {
                @Override
                public Observable<File> call(File file) {
                    /**如果是文件夹就递归**/
                    return listFiles(file);
                }
            });
        } else {
            /**filter操作符过滤视频文件,是word文件就通知观察者**/
            return Observable.just(f).filter(new Func1<File, Boolean>() {
                @Override
                public Boolean call(File file) {
                    return f.exists() && f.canRead() && validateIsOffice(file);
                }
            });
        }
    }



}
