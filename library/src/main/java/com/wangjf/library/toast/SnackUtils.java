package com.wangjf.library.toast;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by wangjf on 2018/3/21.
 */

public class SnackUtils {

    /**
     * snack short
     *
     * @param view
     * @param hintStr
     */
    public static void snackbarShort(View view, String hintStr) {
        snackbarShort(view, hintStr , null);
    }

    /**
     * snack short and click
     *
     * @param view
     * @param hintStr
     * @param clickListener
     */
    public static void snackbarShort(View view, String hintStr , View.OnClickListener clickListener) {
        Snackbar.make(view, hintStr, Snackbar.LENGTH_SHORT)
                .setAction("", clickListener).show();
    }

    /**
     * snack long
     *
     * @param view
     * @param hintStr
     * @param actionStr
     */
    public static void snackbarLong(View view, String hintStr, String actionStr) {
        snackbarLong(view, hintStr, actionStr , null);
    }

    /**
     * snack long and click
     *
     * @param view
     * @param hintStr
     * @param actionStr
     * @param clickListener
     */
    public static void snackbarLong(View view, String hintStr, String actionStr, View.OnClickListener clickListener) {
        Snackbar.make(view, hintStr, Snackbar.LENGTH_LONG)
                .setAction(actionStr, clickListener).show();
    }
}
