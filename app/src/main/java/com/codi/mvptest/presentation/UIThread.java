package com.codi.mvptest.presentation;

import android.os.Handler;
import android.os.Looper;

import com.codi.mvptest.domain.executor.PostExecutionThread;

/**
 * Created by Codi on 2015/2/10.
 */
public class UIThread implements PostExecutionThread {

    private static class LazyHolder {
        private static final UIThread INSTANCE = new UIThread();
    }

    public static UIThread getInstance() {
        return LazyHolder.INSTANCE;
    }

    private final Handler mHandler;

    private UIThread() {
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void post(Runnable runnable) {
        this.mHandler.post(runnable);
    }
}
