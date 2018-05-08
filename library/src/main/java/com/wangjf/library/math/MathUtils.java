package com.wangjf.library.math;

/**
 * Created by wangjf on 2018/3/28.
 */

public class MathUtils {

    public static final String TAG = "MathUtils";

    public static <T extends Comparable<T>> T min(T... values) {
        T minValue = values[0];
        for (T i : values) {
            if (i.compareTo(minValue) < 0) {
                minValue = i;
            }
        }

        return minValue;
    }

    public static <T extends Comparable<T>> T max(T... values) {
        T maxValue = values[0];
        for (T i : values) {
            if (i.compareTo(maxValue) > 0) {
                maxValue = i;
            }
        }

        return maxValue;
    }

    /**
     * Make return value in [min, max]
     * @param min
     * @param t
     * @param max
     * @param <T>
     * @return
     */
    public static <T extends Comparable<T>> T between(T min, T t, T max) {
        if (t.compareTo(min) < 0) {
            return min;
        }
        if (t.compareTo(max) > 0) {
            return max;
        }

        return t;
    }
}
