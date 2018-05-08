package com.wangjf.library.date;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangjf on 2018/3/21.
 */

public class DateUtils {
    private DateUtils() {

    }

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    public static final String DEFAULT_MILLISECOND_FORMAT = "yyyyMMddHHmmssSSS";

    /**
     * is same day
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (null == date1 || null == date2) {
            return false;
        }
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        if (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                && calendar1.get(Calendar.DATE) == calendar2.get(Calendar.DATE)) {
            return true;
        }
        return false;
    }

    public static boolean isToady(String dateString) {
        Date date = dateString2Date(dateString);
        return isToady(date);
    }

    public static boolean isToady(Date date) {
        if (null == date) {
            return false;
        }
        Date today = new Date();
        return isSameDay(date, today);
    }

    /**
     * Return true when the year is leap year.
     *
     * @param year
     */
    public static boolean isLeapYear(int year) {
        if (year % 400 == 0) {
            return true;
        }
        if (year % 4 == 0 && year % 100 != 0) {
            return true;
        }
        return false;
    }

    /**
     * format time(HH:mm:ss)
     * the method is used for video play format time(long)
     *
     * @param mss
     * @return
     */
    public static String formatDuring(long mss) {
        StringBuffer stringBuffer = new StringBuffer();
        //long days = mss / (1000 * 60 * 60 * 24);
        //long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long hours = mss / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumIntegerDigits(2);
        stringBuffer.append(nf.format(hours))
                .append(":")
                .append(nf.format(minutes))
                .append(":")
                .append(nf.format(seconds));
        return stringBuffer.toString();
    }

    /**
     * return format(default: "yyyy-MM-dd HH:mm:ss") string by appoint milliSeconds
     *
     * @param milliSeconds
     * @param format
     * @return
     */
    public static String buildDateString(long milliSeconds, String format) {
        Date date = milliSeconds2Date(milliSeconds);
        return buildDateString(date, format);
    }

    /**
     * format date
     *
     * @param dateString
     * @param format
     * @return
     */
    public static String buildDateString(String dateString, String format) {
        Date date = dateString2Date(dateString);
        return buildDateString(date, format);
    }

    /**
     * format date
     *
     * @param date
     * @param format
     * @return
     */
    public static String buildDateString(Date date, String format) {
        if (null == date) {
            return null;
        }
        if (TextUtils.isEmpty(format)) {
            format = DEFAULT_FORMAT;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * return format(default: "yyyy-MM-dd HH:mm:ss") string by current date
     *
     * @param format
     * @return
     */
    public static String buildCurrentDateString(String format) {
        return buildDateString(System.currentTimeMillis(), format);
    }

    /**
     * return Milliseconds by date
     *
     * @param date
     * @return
     */
    public static long date2Milliseconds(Date date) {
        if (null == date) {
            return 0;
        }
        return date.getTime();
    }

    /**
     * return date by milliSeconds
     *
     * @param milliSeconds
     * @return
     */
    public static Date milliSeconds2Date(long milliSeconds) {
        Date date = new Date(milliSeconds);
        return date;
    }

    /**
     * milliSeconds to string by format
     *
     * @param milliSeconds
     * @param format
     * @return
     */
    public static String milliSeconds2DateString(long milliSeconds, String format) {
        Date date = milliSeconds2Date(milliSeconds);
        return buildDateString(date, format);
    }

    /**
     * return date by string
     *
     * @param dateString
     * @return
     */
    public static Date dateString2Date(String dateString) {
        Date date = null;
        try {
            DateFormat dateFormat = DateFormat.getDateInstance();
            date = dateFormat.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * return year by date string, it may be return -1 when error
     *
     * @param dateString
     * @return
     */
    public static int getYearByDateString(String dateString) {
        return getFieldByDateString(dateString, Calendar.YEAR);
    }

    /**
     * return month by date string, it may be return -1 when error
     *
     * @param dateString
     * @return
     */
    public static int getMonthByDateString(String dateString) {
        return getFieldByDateString(dateString, Calendar.MONTH);
    }

    /**
     * return month by date string, it may be return -1 when error
     *
     * @param dateString
     * @return
     */
    public static int getDayByDateString(String dateString) {
        return getFieldByDateString(dateString, Calendar.DAY_OF_MONTH);
    }

    /**
     * return hour by date string, it may be return -1 when error
     *
     * @param dateString
     * @return
     */
    public static int getHourByDateString(String dateString) {
        //24hours
        return getFieldByDateString(dateString, Calendar.HOUR_OF_DAY);
    }

    /**
     * return minute by date string, it may be return -1 when error
     *
     * @param dateString
     * @return
     */
    public static int getMinuteByDateString(String dateString) {
        return getFieldByDateString(dateString, Calendar.MINUTE);
    }

    /**
     * return second by date string, it may be return -1 when error
     *
     * @param dateString
     * @return
     */
    public static int getSecondByDateString(String dateString) {
        return getFieldByDateString(dateString, Calendar.SECOND);
    }

    /**
     * return Millisecond by date string, it may be return -1 when error
     *
     * @param dateString
     * @return
     */
    public static int getMillisecondByDateString(String dateString) {
        return getFieldByDateString(dateString, Calendar.MILLISECOND);
    }

    /**
     * return appoint value(year、month、day and so on) by timeMillis string
     *
     * @param dateString
     * @param field
     * @return
     */
    public static int getFieldByDateString(String dateString, int field) {
        Date date = dateString2Date(dateString);
        if (null == date) {
            return -1;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(field);
    }

    /**
     *
     * @Title getFirstAndLastOfWeek
     * @Description 获取指定日期所在周的第一天和最后一天日期
     * @author ZengXiaoJiang
     * @param dateStr 指定日期
     * @param dateStrFormat 日期格式
     * @return
     * @throws ParseException
     * String[] 数组长度为2，数组index=0: 开始日期；数组index=1: 结束日期
     */
    public static String[] getFirstAndLastOfWeek(String dateStr, String dateStrFormat) throws ParseException {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(new SimpleDateFormat(dateStrFormat).parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        // 所在周开始日期

        String[] dateArray = new String[2];
        dateArray[0] = new SimpleDateFormat(dateStrFormat).format(cal.getTime());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        // 所在周结束日期
        dateArray[1] = new SimpleDateFormat(dateStrFormat).format(cal.getTime());

        return dateArray;
    }

    /**
     * 功能：判断字符串是否为日期格式
     *
     * @param str
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        return m.matches();
    }

    /**
     * 获取Unix时间戳
     *
     * @return
     */
    public static long getUnixTimestamp() {
        return System.currentTimeMillis() / 1000;
    }
}
