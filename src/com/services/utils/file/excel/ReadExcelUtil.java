package com.services.utils.file.excel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取excel工具类
 *
 */
public class ReadExcelUtil {
    private static final Logger log = LoggerFactory.getLogger(ReadExcelUtil.class);

    private int startReadPos = 0; // 设定开始读取的位置，默认为0

    private int endReadPos = 0;// 设定结束读取的位置，默认为0，用负数来表示倒数第n行

    private boolean onlyReadOneSheet = true; // 设定是否只操作第一个sheet

    private int selectedSheetIdx = 0;// 设定操作的sheet在索引值

    private String selectedSheetName = "";// 设定操作的sheet的名称

    private int startSheetIdx = 0;// 设定开始读取的sheet，默认为0

    private int endSheetIdx = 0;// 设定结束读取的sheet，默认为0，用负数来表示倒数第n行

    public ReadExcelUtil() {
    }

    /**
     * 自动根据文件扩展名，调用对应的读取方法
     *
     * @param xlsPath
     * @throws IOException
     * @Title: writeExcel
     */
    public List<Row> readExcel(String xlsPath) throws IOException {

        // 扩展名为空时，
        if (xlsPath.equals("")) {
            throw new IOException("文件路径不能为空！");
        } else {
            File file = new File(xlsPath);
            if (!file.exists()) {
                throw new IOException("文件不存在！");
            }
        }

        // 获取扩展名
        String ext = xlsPath.substring(xlsPath.lastIndexOf(".") + 1);

        try {

            if ("xls".equals(ext)) { // 使用xls方式读取
                return readExcel_xls(xlsPath);
            } else if ("xlsx".equals(ext)) { // 使用xlsx方式读取
                return readExcel_xlsx(xlsPath);
            } else { // 依次尝试xls、xlsx方式读取
                out("您要操作的文件没有扩展名，正在尝试以xls方式读取...");
                try {
                    return readExcel_xls(xlsPath);
                } catch (IOException e1) {
                    out("尝试以xls方式读取，结果失败！，正在尝试以xlsx方式读取...");
                    try {
                        return readExcel_xlsx(xlsPath);
                    } catch (IOException e2) {
                        out("尝试以xls方式读取，结果失败！\n请您确保您的文件是Excel文件，并且无损，然后再试。");
                        throw e2;
                    }
                }
            }
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 读取Excel 2007版，xlsx格式
     *
     * @return
     * @throws Exception
     * @Title: readExcel_xlsx
     */
    public List<Row> readExcel_xlsx(String xlsPath) throws IOException {
        // 判断文件是否存在
        File file = new File(xlsPath);
        if (!file.exists()) {
            throw new IOException("文件名为" + file.getName() + "Excel文件不存在！");
        }

        Workbook wb = null;
        List<Row> rowList = new ArrayList<Row>();
        try {
            FileInputStream fis = new FileInputStream(file);
            // 去读Excel
            wb = WorkbookFactory.create(fis);

            // 读取Excel 2007版，xlsx格式
            rowList = readExcel(wb);

        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
        return rowList;
    }

    /***
     * 读取Excel(97-03版，xls格式)
     *
     * @throws Exception
     * @Title: readExcel
     */
    public List<Row> readExcel_xls(String xlsPath) throws IOException {

        // 判断文件是否存在
        File file = new File(xlsPath);
        if (!file.exists()) {
            throw new IOException("文件名为" + file.getName() + "Excel文件不存在！");
        }

        Workbook wb = null;// 用于Workbook级的操作，创建、删除Excel
        List<Row> rowList = new ArrayList<Row>();

        try {
            // 读取Excel
            wb = WorkbookFactory.create(new FileInputStream(file));

            // 读取Excel 97-03版，xls格式
            rowList = readExcel(wb);

        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
        return rowList;
    }

    /***
     * 读取单元格的值
     *
     * @param cell
     * @return
     * @Title: getCellValue
     */
    public static String getCellValue(Cell cell) {
        Object result = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    result = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    result = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    result = cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    result = cell.getCellFormula();
                    break;
                case Cell.CELL_TYPE_ERROR:
                    result = cell.getErrorCellValue();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    break;
                default:
                    break;
            }
        }
        return result.toString();
    }

    /**
     * 通用读取Excel
     *
     * @param wb
     * @return
     * @Title: readExcel
     */
    private List<Row> readExcel(Workbook wb) {
        List<Row> rowList = new ArrayList<Row>();

        int sheetCount = 1;// 需要操作的sheet数量

        Sheet sheet = null;
        if (onlyReadOneSheet) { // 只操作一个sheet
            // 获取设定操作的sheet(如果设定了名称，按名称查，否则按索引值查)
            sheet = selectedSheetName.equals("") ? wb.getSheetAt(selectedSheetIdx) : wb.getSheet(selectedSheetName);
        } else { // 操作多个sheet
            sheetCount = wb.getNumberOfSheets();// 获取可以操作的总数量
        }

        // 获取sheet数目
        for (int t = startSheetIdx; t < sheetCount + endSheetIdx; t++) {
            // 获取设定操作的sheet
            if (!onlyReadOneSheet) {
                sheet = wb.getSheetAt(t);
            }

            // 获取最后行号
            int lastRowNum = sheet.getLastRowNum();

            if (lastRowNum > 0) { // 如果>0，表示有数据
                out("\n开始读取名为【" + sheet.getSheetName() + "】的内容：");
            }

            Row row = null;
            // 循环读取
            for (int i = startReadPos; i <= lastRowNum + endReadPos; i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    rowList.add(row);
                    out("第" + (i + 1) + "行：", false);
                    // 获取每一单元格的值
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        String value = getCellValue(row.getCell(j));
                        if (!value.equals("")) {
                            out(value + " | ", false);
                        }
                    }
                    out("");
                }
            }
        }
        return rowList;
    }

    /**
     * 打印消息，
     *
     * @param msg 消息内容
     */
    private void out(String msg) {
        out(msg, true);
    }

    /**
     * 打印消息，
     *
     * @param msg 消息内容
     * @param tr  换行
     */
    private void out(String msg, boolean tr) {
        log.info(msg + (tr ? "\n" : ""));
    }

    public int getStartReadPos() {
        return startReadPos;
    }

    public void setStartReadPos(int startReadPos) {
        this.startReadPos = startReadPos;
    }

    public int getEndReadPos() {
        return endReadPos;
    }

    public void setEndReadPos(int endReadPos) {
        this.endReadPos = endReadPos;
    }

    public boolean isOnlyReadOneSheet() {
        return onlyReadOneSheet;
    }

    public void setOnlyReadOneSheet(boolean onlyReadOneSheet) {
        this.onlyReadOneSheet = onlyReadOneSheet;
    }

    public int getSelectedSheetIdx() {
        return selectedSheetIdx;
    }

    public void setSelectedSheetIdx(int selectedSheetIdx) {
        this.selectedSheetIdx = selectedSheetIdx;
    }

    public String getSelectedSheetName() {
        return selectedSheetName;
    }

    public void setSelectedSheetName(String selectedSheetName) {
        this.selectedSheetName = selectedSheetName;
    }

    public int getStartSheetIdx() {
        return startSheetIdx;
    }

    public void setStartSheetIdx(int startSheetIdx) {
        this.startSheetIdx = startSheetIdx;
    }

    public int getEndSheetIdx() {
        return endSheetIdx;
    }

    public void setEndSheetIdx(int endSheetIdx) {
        this.endSheetIdx = endSheetIdx;
    }

}
