package com.services.utils.Date;

import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author wang
 **/
public class WorkTimeUtil {

    /**
     * 工作日历
     */
    private static Map<String, WorkCalendar> workCalendar = new TreeMap<String, WorkCalendar>();

    /**
     * 每天的工作开始时间 和 结束时间
     */
    private static Calendar dayWorkStart = Calendar.getInstance();
    private static Calendar dayWorkEnd = Calendar.getInstance();

    static {
        try {
            //初始化工作时间
            dayWorkStart.setTime(new SimpleDateFormat("HH:mm:ss").parse("09:00:00"));
            dayWorkEnd.setTime(new SimpleDateFormat("HH:mm:ss").parse("18:00:00"));

            //初始化工作日历
            workCalendar.put("201701", new WorkCalendar(2017, 1, "2,3,4,5,6,9,10,11,12,13,16,17,18,19,20,23,24,25,26,27,30,31"));
            workCalendar.put("201702", new WorkCalendar(2017, 2, "1,2,3,6,7,8,9,10,13,14,15,16,17,20,21,22,23,24,27,28"));
            workCalendar.put("201703", new WorkCalendar(2017, 3, "1,2,3,6,7,8,9,10,13,14,15,16,17,20,21,22,23,24,27,28,29,30,31"));
            workCalendar.put("201704", new WorkCalendar(2017, 4, "3,4,5,6,7,10,11,12,13,14,17,18,19,20,21,24,25,26,27,28"));
            workCalendar.put("201705", new WorkCalendar(2017, 5, "1,2,3,4,5,8,9,10,11,12,15,16,17,18,19,22,23,24,25,26,29,30,31"));
            workCalendar.put("201706", new WorkCalendar(2017, 6, "1,2,5,6,7,8,9,12,13,14,15,16,19,20,21,22,23,26,27,28,29,30"));
            workCalendar.put("201707", new WorkCalendar(2017, 7, "3,4,5,6,7,10,11,12,13,14,17,18,19,20,21,24,25,26,27,28,31"));
            workCalendar.put("201708", new WorkCalendar(2017, 8, "1,2,3,4,7,8,9,10,11,14,15,16,17,18,21,22,23,24,25,28,29,30,31"));
            workCalendar.put("201709", new WorkCalendar(2017, 9, "1,4,5,6,7,8,11,12,13,14,15,18,19,20,21,22,25,26,27,28,29"));
            workCalendar.put("201710", new WorkCalendar(2017, 10, "2,3,4,5,6,9,10,11,12,13,16,17,18,19,20,23,24,25,26,27,30,31"));
            workCalendar.put("201711", new WorkCalendar(2017, 11, "1,2,3,6,7,8,9,10,13,14,15,16,17,20,21,22,23,24,27,28,29,30"));
            workCalendar.put("201712", new WorkCalendar(2017, 12, "1,4,5,6,7,8,11,12,13,14,15,18,19,20,21,22,25,26,27,28,29"));

            workCalendar.put("201801", new WorkCalendar(2018, 1, "1,2,3,4,5,8,9,10,11,12,15,16,17,18,19,22,23,24,25,26,29,30,31"));
            workCalendar.put("201802", new WorkCalendar(2018, 2, "1,2,5,6,7,8,9,12,13,14,15,16,19,20,21,22,23,26,27,28"));
            workCalendar.put("201803", new WorkCalendar(2018, 3, "1,2,5,6,7,8,9,12,13,14,15,16,19,20,21,22,23,26,27,28,29,30"));
            workCalendar.put("201804", new WorkCalendar(2018, 4, "2,3,4,5,6,9,10,11,12,13,16,17,18,19,20,23,24,25,26,27,30"));
            workCalendar.put("201805", new WorkCalendar(2018, 5, "1,2,3,4,7,8,9,10,11,14,15,16,17,18,21,22,23,24,25,28,29,30,31"));
            workCalendar.put("201806", new WorkCalendar(2018, 6, "1,4,5,6,7,8,11,12,13,14,15,18,19,20,21,22,25,26,27,28,29"));
            workCalendar.put("201807", new WorkCalendar(2018, 7, "2,3,4,5,6,9,10,11,12,13,16,17,18,19,20,23,24,25,26,27,30,31"));
            workCalendar.put("201808", new WorkCalendar(2018, 8, "1,2,3,6,7,8,9,10,13,14,15,16,17,20,21,22,23,24,27,28,29,30,31"));
            workCalendar.put("201809", new WorkCalendar(2018, 9, "3,4,5,6,7,10,11,12,13,14,17,18,19,20,21,24,25,26,27,28"));
            workCalendar.put("201810", new WorkCalendar(2018, 10, "1,2,3,4,5,8,9,10,11,12,15,16,17,18,19,22,23,24,25,26,29,30,31"));
            workCalendar.put("201811", new WorkCalendar(2018, 11, "1,2,5,6,7,8,9,12,13,14,15,16,19,20,21,22,23,26,27,28,29,30"));
            workCalendar.put("201812", new WorkCalendar(2018, 12, "3,4,5,6,7,10,11,12,13,14,17,18,19,20,21,24,25,26,27,28,31"));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    /**
     * 计算指定工时之后的日期
     *
     * @param date
     * @param workHourTime
     * @return
     * @throws ParseException
     */
    public Date getAddWorkHourDate(Date date, Double workHourTime) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Calendar origin = (Calendar) c.clone();
        // 如果当天是工作日 则将日期定位到工作日起始时间，并加上当日已过的工时
        if (isWorkDay(c)) {
            locationWorkStart(c);
            workHourTime += (double) workMillis(c, origin) / (60 * 60 * 1000);
        } else {// 如果当日不是工作日
            // 定位到工作日 开始时间
            locationWorkDate(c);
        }
        // 计算一共多少个工作日
        double days = (workHourTime * 60 * 60 * 1000L) / getDayWorkMillis();
        // 整数天
        int day = (int) days;
        // 小时数
        double hours = (days - day) * getDayWorkMillis() / (60 * 60 * 1000);
        for (int i = 0; i < day; i++) {
            addWorkDay(c);
        }
        if (hours > 0) {
            c.set(Calendar.HOUR_OF_DAY, dayWorkStart.get(Calendar.HOUR_OF_DAY) + (int) hours);
            c.set(Calendar.MINUTE, (int) (dayWorkStart.get(Calendar.MINUTE) + (hours - (int) hours) * 60));
            c.set(Calendar.SECOND, dayWorkStart.get(Calendar.SECOND));
        }

        return c.getTime();
    }

    /**
     * 计算2个时间之间的工时（工作日 工作时）
     *
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    public Double countWorkTime(Date startDate, Date endDate) throws ParseException {
        Calendar startTime = Calendar.getInstance();
        startTime.setTime(startDate);
        Calendar endTime = Calendar.getInstance();
        endTime.setTime(endDate);

        BigDecimal hours = new BigDecimal((double) countTotalMillis(startTime, endTime) / (60 * 60 * 1000));
        return hours.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 计算2个日期之间的全部工时-毫秒数
     *
     * @param startTime
     * @param endTime
     * @return
     * @throws ParseException
     */
    private long countTotalMillis(Calendar startTime, Calendar endTime) throws ParseException {
        long workMillis = 0;
        if (!DateUtils.isSameDay(startTime, endTime)) {// 跨天
            // 相差天数
            Integer diffWorkDay = countWorkDayDiff(startTime, endTime);
            workMillis += diffWorkDay * getDayWorkMillis();
            // 加上起始天的工时
            if (isWorkDay(startTime)) {
                workMillis += workMillis(startTime, dayWorkEnd);
            }
            // 加上结束天的工时
            if (isWorkDay(endTime)) {
                workMillis += workMillis(dayWorkStart, endTime);
            }
        } else {// 同一天
            if (isWorkDay(startTime)) {
                workMillis += workMillis(startTime, endTime);
            }
        }

        return workMillis;
    }

    /**
     * 2个日期之间相差的天数（工作日）
     *
     * @param startTime
     * @param endTime
     * @return
     */
    private Integer countWorkDayDiff(Calendar startTime, Calendar endTime) {
        int workDays = 0;
        Calendar start = (Calendar) startTime.clone();
        start.add(Calendar.DAY_OF_YEAR, 1);
        Calendar end = (Calendar) endTime.clone();
        end.add(Calendar.DAY_OF_YEAR, -1);
        if (start.before(end)) {
            if (!sameMonth(start, end)) {// 不同月份
                // 开始日期当月
                workDays += workDayMonth(start, start.get(Calendar.DAY_OF_MONTH), start.getActualMaximum(Calendar.DATE));
                start.add(Calendar.MONTH, 1);
                start.set(Calendar.DAY_OF_MONTH, 1);
                while (!sameMonth(start, end)) {
                    WorkCalendar current = findCalendar(start.get(Calendar.YEAR), start.get(Calendar.MONTH));
                    workDays += current.listDays().size();
                    start.add(Calendar.MONTH, 1);
                }
                // 截止日期当月
                workDays += workDayMonth(start, 1, end.get(Calendar.DAY_OF_MONTH));
            } else {// 同一个月份
                workDays += workDayMonth(start, start.get(Calendar.DAY_OF_MONTH), end.get(Calendar.DAY_OF_MONTH));
            }
        }

        return workDays < 0 ? 0 : workDays;
    }

    /**
     * 计算当天2个时间之间的工时
     *
     * @param endTime
     * @return
     */
    private long workMillis(Calendar startTime, Calendar endTime) {
        Calendar start = cloneCalendarAndSetDate(startTime);
        Calendar end = cloneCalendarAndSetDate(endTime);
        long millis = Math.min(dayWorkEnd.getTimeInMillis(), end.getTimeInMillis()) - Math.max(dayWorkStart.getTimeInMillis(), start.getTimeInMillis());
        return millis > 0 ? millis : 0;
    }

    /**
     * 一个工作日内工作的毫秒数
     *
     * @return
     * @throws ParseException
     */
    private long getDayWorkMillis() {
        return dayWorkEnd.getTimeInMillis() - dayWorkStart.getTimeInMillis();
    }

    /**
     * 计算当月工作日天数
     *
     * @param start
     * @param end
     * @return
     */
    private int workDayMonth(Calendar c, Integer start, Integer end) {
        int workDays = 0;
        WorkCalendar month = findCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH));
        for (int i = start; i <= end; i++) {
            if (month.listDays().contains(String.valueOf(i))) {
                workDays += 1;
            }
        }
        return workDays;
    }

    /**
     * 查看某一天是否是工作日
     *
     * @param c
     * @return
     */
    public boolean isWorkDay(Calendar c) {
        WorkCalendar workCalendar = findCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH));
        return workCalendar.listDays().contains(String.valueOf(c.get(Calendar.DAY_OF_MONTH)));
    }

    /**
     * 设置比较日期为同一天
     *
     * @param time
     */
    private Calendar cloneCalendarAndSetDate(Calendar time) {
        Calendar c = (Calendar) time.clone();
        c.set(dayWorkStart.get(Calendar.YEAR), dayWorkStart.get(Calendar.MONTH), dayWorkStart.get(Calendar.DAY_OF_MONTH));
        return c;
    }

    /**
     * 是否是同一个月
     *
     * @param startTime
     * @param endTime
     * @return
     */
    private boolean sameMonth(Calendar startTime, Calendar endTime) {
        return startTime.get(Calendar.YEAR) == endTime.get(Calendar.YEAR) && startTime.get(Calendar.MONTH) == endTime.get(Calendar.MONTH);
    }

    /**
     * 将日期定位到下一个工作日 开始时间
     *
     * @param c
     */
    private void locationWorkDate(Calendar c) {
        if (!isWorkDay(c)) {
            c.add(Calendar.DAY_OF_MONTH, 1);
            locationWorkDate(c);
        } else {
            locationWorkStart(c);
        }
    }

    /**
     * 将时间定在当天 工作时开始时间
     *
     * @param c
     */
    private void locationWorkStart(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, dayWorkStart.get(Calendar.HOUR_OF_DAY));
        c.set(Calendar.MINUTE, dayWorkStart.get(Calendar.MINUTE));
        c.set(Calendar.SECOND, dayWorkStart.get(Calendar.SECOND));
    }

    /**
     * 加一个工作日
     *
     * @param c
     */
    private void addWorkDay(Calendar c) {
        c.add(Calendar.DAY_OF_MONTH, 1);
        if (!isWorkDay(c)) {
            addWorkDay(c);
        }
    }

    /**
     * 查询工作日历
     *
     * @param year
     * @param month
     * @return
     */
    private WorkCalendar findCalendar(Integer year, Integer month) {
        month = month + 1;
        String key = String.valueOf(year);
        if (month < 10) {
            key += "0";
        }
        key += month;
        WorkCalendar calendar = workCalendar.get(key);
        if (calendar == null) {
            throw new RuntimeException(String.format("%s年%s月的工作日历不存在", year, month));
        }
        return calendar;
    }
}
