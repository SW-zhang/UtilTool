package com.services.utils.file.excel;

import java.util.List;


public class ExcelObject {
    private String sheetName; //sheet名字
    private String[] title; //抬头
    private String[] field;//数据字段域
    private List datas;//数据

    public ExcelObject() {
    }

    public ExcelObject(String sheetName, String[] title, String[] field, List datas) {
        this.sheetName = sheetName;
        this.title = title;
        this.field = field;
        this.datas = datas;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String[] getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    public String[] getField() {
        return field;
    }

    public void setField(String[] field) {
        this.field = field;
    }

    public List getDatas() {
        return datas;
    }

    public void setDatas(List datas) {
        this.datas = datas;
    }
}
