package com.wangjf.library.log;

import android.util.Log;

/**
 * Created by wangjf on 2018/3/22.
 */

public class DLog {
    public static void e(String tag, Object value) {
        Log.e(tag, value + "");
    }
}
