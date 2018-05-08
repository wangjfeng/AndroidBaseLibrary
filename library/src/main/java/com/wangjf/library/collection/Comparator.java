package com.wangjf.library.collection;

/**
 * Created by wangjf on 2018/3/28.
 */

public interface Comparator<U, V> {
    int compareTo(U a, V b);
}
