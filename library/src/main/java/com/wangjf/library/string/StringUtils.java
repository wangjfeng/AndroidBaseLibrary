package com.wangjf.library.string;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangjf on 2018/3/21.
 */

public class StringUtils {

    /**
     * return empty char when value is empty
     *
     * @param value
     * @return
     */
    public static String empty2Char(String value) {
        if (TextUtils.isEmpty(value)) {
            return "";
        }
        return value;
    }

    /**
     * return true when value1 equals value2 that ignore case
     *
     * @param value1
     * @param value2
     * @return
     */
    public static boolean equalsIgnoreCase(String value1, String value2) {
        if (TextUtils.equals(value1, value2)) {
            return true;
        }
        if (null != value1) {
            return value1.equalsIgnoreCase(value2);
        }
        return value2.equalsIgnoreCase(value1);
    }

    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * return true if the CharSequence is phone number
     *
     * @param phone
     * @return
     */
    public static boolean isPhoneNumber(CharSequence phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        }
        String regex = "^[1]\\d{10}$";
        return isMatches(regex, phone);
    }

    /**
     * return true if the CharSequence is email
     *
     * @param email
     * @return
     */
    public static boolean isEmail(CharSequence email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        String regex = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        return isMatches(regex, email);
    }

    /**
     * return true if the CharSequence is Chinese
     *
     * @param chinese
     * @return
     */
    public static boolean isChinese(CharSequence chinese) {
        if (TextUtils.isEmpty(chinese)) {
            return false;
        }
        String regex = "^[\u4e00-\u9fa5],{0,}$";
        return isMatches(regex, chinese);
    }

    /**
     * return true if the CharSequence is ip
     *
     * @param ip
     * @return
     */
    public static boolean isIP(CharSequence ip) {
        if (TextUtils.isEmpty(ip)) {
            return false;
        }
        String regex = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";
        return isMatches(regex, ip);
    }

    /**
     * return true if the CharSequence is web url
     *
     * @param webUrl
     * @return
     */
    public static boolean isWebUrl(CharSequence webUrl) {
        if (TextUtils.isEmpty(webUrl)) {
            return false;
        }
        String regex = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
        return isMatches(regex, webUrl);
    }


    /**
     * return whether input matches the regex.
     *
     * @param regex
     * @param input
     * @return
     */
    public static boolean isMatches(String regex, CharSequence input) {
        return !TextUtils.isEmpty(input) && Pattern.matches(regex, input);
    }
}
