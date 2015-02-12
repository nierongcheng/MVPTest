package com.codi.mvptest.domain.exception;

/**
 * Created by Codi on 2015/2/10.
 */
public interface ErrorBundle {

    Exception getException();

    String getErrorMessage();

}
