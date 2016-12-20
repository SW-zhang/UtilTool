package com.wang.utils.file.excel;

import com.wang.utils.string.StringUtil;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成excel工具类
 */
public class WriteExcelUtil {
    private static final Logger log = LoggerFactory.getLogger(WriteExcelUtil.class);

    /**
     * @param os        输出文件流
     * @param title     抬头
     * @param field     字段域
     * @param datas     数据
     * @param sheetName sheet名称
     */
    public static void writeExcel(OutputStream os, String[] title, String[] field, List datas, String sheetName) {
        writeExcel(os, Arrays.asList(new ExcelObject(sheetName, title, field, datas)));
    }

    /**
     * @param os   输出文件流
     * @param objs 文件对象（可以是多个）
     * @throws Exception
     */
    public static void writeExcel(OutputStream os, List<ExcelObject> objs) {
        try {
            if (os == null) return;
            HSSFWorkbook workbook = new HSSFWorkbook();
            for (int k = 0; k < objs.size(); k++) {
                ExcelObject excelObject = objs.get(k);
                HSSFSheet sheet = workbook.createSheet(StringUtil.isEmpty(excelObject.getSheetName()) ? "sheet_" + k : excelObject.getSheetName());
                // 第0行
                HSSFRow titleRow = sheet.createRow(0);
                excelTitle(workbook, titleRow, excelObject.getTitle());

                for (int i = 0; i < excelObject.getDatas().size(); i++) {
                    HSSFRow row = sheet.createRow(i + 1);
                    Map<String, Object> obj = getMap(excelObject.getDatas().get(i));
                    for (int j = 0; j < excelObject.getField().length; j++) {
                        row.createCell(j).setCellValue(StringUtil.nullTotrim(obj.get(excelObject.getField()[j])));
                    }
                }
            }
            workbook.write(os);
            os.flush();
            log.info("写入excel成功。");
        } catch (Exception e) {
            log.error("生成excel发生未知异常", e);
        } finally {
            if (os != null)
                try {
                    os.close();
                } catch (IOException e) {
                }
        }
    }

    /**
     * 标题头样式
     *
     * @param workbook
     * @param titleRow
     * @param titles
     */
    private static void excelTitle(HSSFWorkbook workbook, HSSFRow titleRow, String[] titles) {
        // 标题头样式
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFFont.COLOR_NORMAL);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);

        for (int i = 0; i < titles.length; i++) {
            HSSFCell cell = titleRow.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(titles[i]);
        }
    }

    /**
     * 将object转换成map
     *
     * @param obj
     * @return
     * @throws Exception
     */
    private static Map<String, Object> getMap(Object obj) throws Exception {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String fieldName = field.getName();
            String getMethodName = "get" + toFirstLetterUpperCase(fieldName);
            Object value = clazz.getMethod(getMethodName).invoke(obj).toString();
            objectMap.put(fieldName, value);
        }
        return objectMap;
    }

    /**
     * 首字母转大写
     *
     * @param str
     * @return
     */
    private static String toFirstLetterUpperCase(String str) {
        if (str == null || str.length() < 2) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
    }
}
