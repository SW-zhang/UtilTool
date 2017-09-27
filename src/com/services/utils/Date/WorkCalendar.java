package com.services.utils.Date;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 工作日历
 */
public class WorkCalendar implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer year;// 年
    private Integer month;// 月 从1开始
    private String days;// 日 从1开始

    public WorkCalendar() {
        super();
    }

    public WorkCalendar(Integer year, Integer month, String days) {
        super();
        this.year = year;
        this.month = month;
        this.days = days;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String[] arrayDays() {
        return this.days.split(",");
    }

    public List<String> listDays() {
        return Arrays.asList(this.days.split(","));
    }
}
