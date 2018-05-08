package com.wangjf.library.collection;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangjf on 2018/3/28.
 */

public class CollectionUtils {

    public static final String TAG = "CollectionUtils";

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    //
    public static <T> T find(Collection<T> collection, T t) {
        return find(collection, t, 0);
    }
    public static <S, T> T find(Collection<T> collection, S s, Comparator<S, T> compFunc) {
        return find(collection, s, 0, compFunc);
    }
    public static <T> T find(Collection<T> collection, T t, int startOffset) {
        return find(collection, t, startOffset, null);
    }
    public static <S, T> T find(Collection<T> collection, S s, int startOffset, Comparator<S, T> compFunc) {
        if (collection == null) {
            return null;
        }

        Iterator<T> iterator = collection.iterator();
        for (int i = 0; i < collection.size(); i++) {
            T elem = iterator.next();

            if (i >= startOffset) {

                if(compFunc == null && s.equals(elem)){
                    return elem;
                }

                if (compFunc != null && compFunc.compareTo(s, elem) == 0){
                    return elem;
                }
            }
        }

        return null;
    }

    public static <K, V> Map.Entry<K, V> find(Map<K, V> collection, V v) {
        return find(collection, v, 0);
    }
    public static <S, K, V> Map.Entry<K, V> find(Map<K, V> collection, S s, Comparator<S, K> compFunc) {
        return find(collection, s, 0, compFunc);
    }
    public static <K, V> Map.Entry<K, V> find(Map<K, V> collection, V v, int startOffset) {
        return find(collection, v, startOffset, null);
    }
    /**
     *
     * @param map
     * @param s
     * @param startOffset
     * @param compFunc `a` is `s`, `b` is element in container
     * @param <S>
     * @param <K>
     * @param <V>
     * @return
     */
    public static <S, K, V> Map.Entry<K, V> find(Map<K, V> map, S s, int startOffset, Comparator<S, K> compFunc) {
        if (map == null) {
            return null;
        }

        Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
        for (int i = 0; i < map.size(); i++) {
            Map.Entry<K, V> kv = iterator.next();
            K key = kv.getKey();
            V value = kv.getValue();

            if (i >= startOffset) {

                if (compFunc == null && s.equals(key)){
                    return kv;
                }

                if (compFunc != null && compFunc.compareTo(s, key) == 0){
                    return  kv;
                }
            }
        }

        return null;
    }

    /**
     * Only support `ArrayList / HashSet / LinkedList / ArrayDeque`
     * @param t
     * @param comparable return 0 if you want keep the element
     * @param <T>
     * @param <E>
     * @return
     */
    public static <T extends Collection, E> T filter(T t, Comparable<E> comparable) {
        T filtered;
        if (t instanceof ArrayList) {
            filtered = (T) new ArrayList<>();
        } else if (t instanceof HashSet) {
            filtered = (T) new HashSet<>();
        } else if (t instanceof LinkedList) {
            filtered = (T) new LinkedList<>();
        } else if (t instanceof ArrayDeque) {
            filtered = (T) new ArrayDeque<>();
        } else {
            return null;
        }

        Iterator<E> iterator = t.iterator();
        while (iterator.hasNext()) {
            E e = iterator.next();
            if (comparable.compareTo(e) == 0) {
                filtered.add(e);
            }
        }

        return filtered;
    }

    /**
     * Fast create and initialize
     */
    public static <T> List<T> newArrayList(T... ts) {
        List<T> list = new ArrayList<>();
        if (ts != null) {
            for (T t : ts) {
                list.add(t);
            }
        }
        return list;
    }

    public static <K, V> Map<K, V> newHashMap(Object... objs) {
        Map<K, V> m = new HashMap<>();
        if (objs == null || objs.length % 2 != 0) {
            return m;
        }

        for (int i = 0; i < objs.length; i += 2) {
            int j = i + 1;

            K k = (K) objs[i];
            V v = (V) objs[j];

            m.put(k, v);
        }

        return m;
    }
}
