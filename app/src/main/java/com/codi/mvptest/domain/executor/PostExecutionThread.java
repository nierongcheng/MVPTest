package com.codi.mvptest.domain.executor;

/**
 * Created by Codi on 2015/2/10.
 */
public interface PostExecutionThread {

    void post(Runnable runnable);

}
