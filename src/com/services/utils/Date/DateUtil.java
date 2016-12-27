package com.services.utils.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static final String DEFAULT_FULL_FORMATER = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_FORMATER = "yyyy-MM-dd";

    private static SimpleDateFormat getSimpleDateFormat(String format, Locale locale) {
        return new SimpleDateFormat(format, locale);
    }

    /**
     * 格式化日期
     *
     * @param date
     * @param formatString
     * @return
     */
    public static String format(Date date, String formatString) {
        if (null == date) {
            return null;
        }
        SimpleDateFormat df = getSimpleDateFormat(formatString, Locale.CHINA);
        return df.format(date);
    }

    /**
     * 格式化日期(使用默认格式)
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, DEFAULT_FULL_FORMATER);
    }

    /**
     * 转换成日期
     *
     * @param dateString
     * @param formatString
     * @return
     */
    public static Date parse(String dateString, String formatString, Locale locale) {
        SimpleDateFormat df = getSimpleDateFormat(formatString, locale);
        try {
            return df.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 转换成日期
     *
     * @param dateString
     * @param formatString
     * @return
     */
    public static Date parse(String dateString, String formatString) {
        return parse(dateString, formatString, Locale.CHINA);
    }

    /**
     * 转换成日期(使用默认格式)
     *
     * @param dateString
     * @return
     */
    public static Date parseyyyyMMddHHmmss(String dateString) {
        return parse(dateString, DEFAULT_FULL_FORMATER);
    }

    /**
     * 转换成日期(使用默认格式)
     *
     * @param dateString
     * @return
     */
    public static Date parseyyyyMMdd(String dateString) {
        return parse(dateString, DEFAULT_DATE_FORMATER);
    }

    /**
     * 当前月第一天
     *
     * @return
     */
    public static Date getMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 当前月最后一天
     *
     * @return
     */

    public static Date getMonthLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.roll(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getNowWeekBegin() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 1);
        return c.getTime();
    }

    /**
     * 昨天
     *
     * @return
     */
    public static Date yesterday() {
        return addDay(-1);
    }

    /**
     * 明天(精度：yyyy-MM-dd)
     *
     * @return
     */
    public static Date tomorrow() {
        return round2Day(addDay(1));
    }

    /**
     * 现在
     *
     * @return
     */
    public static Date now() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 今天(精度：yyyy-MM-dd)
     *
     * @return
     */
    public static Date today() {
        return round2Day(now());
    }

    /**
     * 按日加
     *
     * @param value
     * @return
     */
    public static Date addDay(int value) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_YEAR, value);
        return now.getTime();
    }

    /**
     * 按日加,指定日期
     *
     * @param date
     * @param value
     * @return
     */
    public static Date addDay(Date date, int value) {
        if (null == date) {
            return null;
        }
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.DAY_OF_YEAR, value);
        return now.getTime();
    }

    /**
     * 按月加
     *
     * @param value
     * @return
     */
    public static Date addMonth(int value) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MONTH, value);
        return now.getTime();
    }

    /**
     * 按月加,指定日期
     *
     * @param date
     * @param value
     * @return
     */
    public static Date addMonth(Date date, int value) {
        if (null == date) {
            return null;
        }
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.MONTH, value);
        return now.getTime();
    }

    /**
     * 按年加
     *
     * @param value
     * @return
     */
    public static Date addYear(int value) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.YEAR, value);
        return now.getTime();
    }

    /**
     * 按年加,指定日期
     *
     * @param date
     * @param value
     * @return
     */
    public static Date addYear(Date date, int value) {
        if (null == date) {
            return null;
        }
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.YEAR, value);
        return now.getTime();
    }

    /**
     * 按小时加
     *
     * @param value
     * @return
     */
    public static Date addHour(int value) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.HOUR_OF_DAY, value);
        return now.getTime();
    }

    /**
     * 按小时加,指定日期
     *
     * @param date
     * @param value
     * @return
     */
    public static Date addHour(Date date, int value) {
        if (null == date) {
            return null;
        }
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.HOUR_OF_DAY, value);
        return now.getTime();
    }

    /**
     * 按分钟加
     *
     * @param value
     * @return
     */
    public static Date addMinute(int value) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, value);
        return now.getTime();
    }

    /**
     * 按分钟加,指定日期
     *
     * @param date
     * @param value
     * @return
     */
    public static Date addMinute(Date date, int value) {
        if (null == date) {
            return null;
        }
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.MINUTE, value);
        return now.getTime();
    }

    /**
     * 按秒加
     *
     * @param value
     * @return
     */
    public static Date addSec(int value) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.SECOND, value);
        return now.getTime();
    }

    /**
     * 按秒加
     *
     * @param value
     * @return
     */
    public static Date addSec(Date date, int value) {
        if (null == date) {
            return null;
        }
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.SECOND, value);
        return now.getTime();
    }

    /**
     * 星期几(礼拜几)
     *
     * @return
     */
    public static int weekday(Date d) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        return now.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 星期几(礼拜几)
     *
     * @return
     */
    public static int weekday() {
        return weekday(now());
    }

    /**
     * 年份
     *
     * @return
     */
    public static int year(Date d) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        return now.get(Calendar.YEAR);
    }

    /**
     * 年份
     *
     * @return
     */
    public static int year() {
        return year(now());
    }

    /**
     * 月份
     *
     * @return
     */
    public static int month(Date d) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        return now.get(Calendar.MONTH) + 1;
    }

    /**
     * 月份
     *
     * @return
     */
    public static int month() {
        return month(now());
    }

    /**
     * 日(号)
     *
     * @return
     */
    public static int day(Date d) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        return now.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 日(号)
     *
     * @return
     */
    public static int day() {
        return day(now());
    }

    /**
     * 小时(点)
     *
     * @return
     */
    public static int hour(Date d) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        return now.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 小时(点)
     *
     * @return
     */
    public static int hour() {
        return hour(now());
    }

    /**
     * 分钟
     *
     * @return
     */
    public static int minute(Date d) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        return now.get(Calendar.MINUTE);
    }

    /**
     * 分钟
     *
     * @return
     */
    public static int minute() {
        return minute(now());
    }

    /**
     * 秒
     *
     * @return
     */
    public static int second(Date d) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        return now.get(Calendar.SECOND);
    }

    /**
     * 秒
     *
     * @return
     */
    public static int second() {
        return second(now());
    }

    /**
     * 是上午吗?
     *
     * @return
     */
    public static boolean isAm(Date d) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        return now.get(Calendar.AM_PM) == 0;
    }

    /**
     * 是上午吗?
     *
     * @return
     */
    public static boolean isAm() {
        return isAm(now());
    }

    /**
     * 是下午吗?
     *
     * @return
     */
    public static boolean isPm(Date d) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        return now.get(Calendar.AM_PM) == 1;
    }

    /**
     * 是下午吗?
     *
     * @return
     */
    public static boolean isPm() {
        return isPm(now());
    }

    /**
     * 取整到天，如：2012-06-12 18:06:09 -> 2012-06-12 00:00:00
     *
     * @param date
     * @return
     */
    public static Date round2Day(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 天数差(d2 - d1)
     *
     * @param d1
     * @param d2
     * @return (d2 - d1)
     */
    public static int diffOfDay(Date d1, Date d2) {
        return diffOfHour(d1, d2) / 24;
    }

    /**
     * 分钟差(d2 - d1)
     *
     * @param d1
     * @param d2
     * @return (d2 - d1)
     */
    public static int diffOfHour(Date d1, Date d2) {
        return diffOfMinute(d1, d2) / 60;
    }

    /**
     * 分钟差(d2 - d1)
     *
     * @param d1
     * @param d2
     * @return (d2 - d1)
     */
    public static int diffOfMinute(Date d1, Date d2) {
        return diffOfSec(d1, d2) / 60;
    }

    /**
     * 秒差(d2 - d1)
     *
     * @param d1
     * @param d2
     * @return (d2 - d1)
     */
    public static int diffOfSec(Date d1, Date d2) {
        long ts = d2.getTime() - d1.getTime();
        return (int) (ts / 1000);
    }

    public static void main(String[] args) {
    }
}
