package com.jc.utils;

import com.jc.foundation.util.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * isMaxCurrent 是否截止到当前时间（如果结束时间大于当前时间，则取当前时间）
 */
public class CalendarUtil {

    public static class Day {
        public static Date getStartTime() {
            return getStartTime(DateUtils.getSysDate());
        }

        public static Date getStartTime(Date date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return getStartTime(calendar);
        }

        public static Date getEndTime() {
            return getEndTime(false);
        }

        public static Date getStartTime(Calendar calendar) {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
        }

        public static Date getEndTime(boolean isMaxCurrent) {
            return getEndTime(DateUtils.getSysDate(), isMaxCurrent);
        }

        public static Date getEndTime(Date date) {
            return getEndTime(date, false);
        }

        public static Date getEndTime(Date date, boolean isMaxCurrent) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return getEndTime(calendar, isMaxCurrent);
        }

        public static Date getEndTime(Calendar calendar) {
            return getEndTime(calendar, false);
        }

        public static Date getEndTime(Calendar calendar, boolean isMaxCurrent) {
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            if (isMaxCurrent && DateUtils.getSysDate().before(calendar.getTime())) {
                return DateUtils.getSysDate();
            }
            return calendar.getTime();
        }
    }

    public static class Week {
        public static Date getStartTime() {
            return getStartTime(DateUtils.getSysDate());
        }

        public static Date getStartTime(Date date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return getStartTime(calendar);
        }

        public static Date getStartTime(Calendar calendar) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
        }

        public static Date getEndTime() {
            return getEndTime(false);
        }

        public static Date getEndTime(boolean isMaxCurrent) {
            return getEndTime(DateUtils.getSysDate(), isMaxCurrent);
        }

        public static Date getEndTime(Date date) {
            return getEndTime(date, false);
        }

        public static Date getEndTime(Date date, boolean isMaxCurrent) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return getEndTime(calendar, isMaxCurrent);
        }

        public static Date getEndTime(Calendar calendar) {
            return getEndTime(calendar, false);
        }

        public static Date getEndTime(Calendar calendar, boolean isMaxCurrent) {
            calendar.set(Calendar.DATE, 6);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            if (isMaxCurrent && DateUtils.getSysDate().before(calendar.getTime())) {
                return DateUtils.getSysDate();
            }
            return calendar.getTime();
        }
    }

    public static class Month {
        public static Date getStartTime() {
            return getStartTime(DateUtils.getSysDate());
        }

        public static Date getStartTime(Date date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return getStartTime(calendar);
        }

        public static Date getStartTime(Calendar calendar) {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
        }

        public static Date getEndTime() {
            return getEndTime(false);
        }

        public static Date getEndTime(boolean isMaxCurrent) {
            return getEndTime(DateUtils.getSysDate(), isMaxCurrent);
        }

        public static Date getEndTime(Date date) {
            return getEndTime(date, false);
        }

        public static Date getEndTime(Date date, boolean isMaxCurrent) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return getEndTime(calendar, isMaxCurrent);
        }

        public static Date getEndTime(Calendar calendar) {
            return getEndTime(calendar, false);
        }

        public static Date getEndTime(Calendar calendar, boolean isMaxCurrent) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DATE));
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            if (isMaxCurrent && DateUtils.getSysDate().before(calendar.getTime())) {
                return DateUtils.getSysDate();
            }
            return calendar.getTime();
        }
    }

    public static class Year {
        public static Date getStartTime() {
            return getStartTime(DateUtils.getSysDate());
        }

        public static Date getStartTime(Date date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return getStartTime(calendar);
        }

        public static Date getStartTime(Calendar calendar) {
            calendar.set(Calendar.MONTH, 0);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
        }

        public static Date getEndTime() {
            return getEndTime(false);
        }

        public static Date getEndTime(boolean isMaxCurrent) {
            return getEndTime(DateUtils.getSysDate(), isMaxCurrent);
        }

        public static Date getEndTime(Date date) {
            return getEndTime(date, false);
        }

        public static Date getEndTime(Date date, boolean isMaxCurrent) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return getEndTime(calendar, isMaxCurrent);
        }

        public static Date getEndTime(Calendar calendar) {
            return getEndTime(calendar, false);
        }

        public static Date getEndTime(Calendar calendar, boolean isMaxCurrent) {
            calendar.set(Calendar.MONTH, 11);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DATE));
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            if (isMaxCurrent && DateUtils.getSysDate().before(calendar.getTime())) {
                return DateUtils.getSysDate();
            }
            return calendar.getTime();
        }
    }
}
