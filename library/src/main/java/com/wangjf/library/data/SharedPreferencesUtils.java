package com.wangjf.library.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.wangjf.library.log.DLog;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by wangjf on 2018/3/21.
 */

public class SharedPreferencesUtils {

    /**
     * 保存数据
     *
     * @param name
     * @param value
     */
    public static void saveData(Context context , String name, String key , String value) {
        SharedPreferences shared = null;
        shared = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        shared.edit().putString(key, value).commit();
    }

    /**
     * 读取数据
     *
     * @param context
     * @param name
     * @return
     */
    public static String readData(Context context , String name , String key) {
        SharedPreferences shared = null;
        shared = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return shared.getString(key, "");
    }

    /**
     * 将obj 存储到data目录
     *
     * @param obj
     * @param fileName
     * @param context
     */
    public static void saveObjToData(Context context , Object obj, String fileName) {
        ObjectOutputStream oos = null;
        FileOutputStream fis = null;
        try {
            fis = context.openFileOutput(
                    fileName, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fis);
            oos.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
            DLog.e("SharedPreferencesUtils" , e.getMessage());
        } finally {
            try {
                oos.close();
                fis.close();
            } catch (Exception e) {
                DLog.e("SharedPreferencesUtils" , e.getMessage());
            }
        }
    }

    /**
     * 取Object
     *
     * @param fileName
     * @param context
     * @return
     */
    public static Object getObjectFromData(Context context , String fileName) {
        Object obj = null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(
                    context.openFileInput(fileName));
            obj = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            DLog.e("SharedPreferencesUtils" , e.getMessage());
        }finally {
            try {
                ois.close();
            }catch (Exception e){
                DLog.e("SharedPreferencesUtils" , e.getMessage());
            }
        }
        return obj;
    }
}
