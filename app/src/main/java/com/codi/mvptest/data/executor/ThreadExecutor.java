package com.codi.mvptest.data.executor;

/**
 * Created by Codi on 2015/2/10.
 */
public interface ThreadExecutor {

    /**
     * @param runnable The class that implements {@link Runnable} interface.
     */
    void execute(final Runnable runnable);

}
