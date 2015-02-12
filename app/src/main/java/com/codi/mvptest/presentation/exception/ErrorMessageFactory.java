package com.codi.mvptest.presentation.exception;

import android.content.Context;

import com.codi.mvptest.R;
import com.codi.mvptest.data.exception.NetworkConnectionException;
import com.codi.mvptest.data.exception.UserNotFoundException;

/**
 * Created by Codi on 2015/2/12.
 */
public class ErrorMessageFactory {

    private ErrorMessageFactory() {}

    public static String create(Context context, Exception exception) {
        String message = context.getString(R.string.exception_message_generic);

        if(exception instanceof NetworkConnectionException) {
            message = context.getString(R.string.exception_message_no_connection);
        } else if(exception instanceof UserNotFoundException) {
            message = context.getString(R.string.exception_message_user_not_found);
        }

        return message;
    }
}
